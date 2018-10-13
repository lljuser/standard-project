package com.heyi.webapp.filesystem.controller;

import com.heyi.core.common.ApiResponse;
import com.heyi.core.common.PageQuery;
import com.heyi.core.filestorage.entity.FsFile;
import com.heyi.core.filestorage.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileService fileService;

    /**
     * 文件上传
     *
     * @param file     上传的文件
     * @param folderId 上传的目录
     * @return
     */
    @PostMapping("/upload")
    public ApiResponse upload(@RequestBody MultipartFile file, @RequestParam("folderId") String folderId, HttpServletRequest request) {
        FsFile result = fileService.saveFile( file, folderId, request );
        return ObjectUtils.isEmpty( result ) ? ApiResponse.error( "文件上传失败！" ) : ApiResponse.success( result );
    }

    /**
     * 文件下载（判断文件是否存在）
     *
     * @param code 文件外部唯一标识
     * @return
     */
    @GetMapping("/download/{code}")
    public Object download(@PathVariable String code) {
        // 找到文件，将信息返回
        String path = fileService.getFsFileByCode( code );
        FileSystemResource file = new FileSystemResource( path );
        if (!file.exists()) return ApiResponse.error( "文件不存在，请重新确认！" );
        return ApiResponse.success( "/file/formal-download/" + code );
    }

    /**
     * 文件下载
     *
     * @param code 文件外部唯一标识
     * @return
     */
    @GetMapping("/formal-download/{code}")
    public Object formalDownload(@PathVariable String code) {
        // 找到文件，将信息返回
        String path = fileService.getFsFileByCode( code );
        FileSystemResource file = new FileSystemResource( path );
        HttpHeaders headers = new HttpHeaders();
        headers.add( "Cache-Control", "no-cache, no-store, must-revalidate" );
        try {
            headers.add( "Content-Disposition", "attachment;filename*=UTF-8''" + URLEncoder.encode( file.getFilename(), "UTF-8" ) );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        headers.add( "Pragma", "no-cache" );
        headers.add( "Expires", "0" );
        try {
            return ResponseEntity
                    .ok()
                    .headers( headers )
                    .contentLength( file.contentLength() )
                    .contentType( MediaType.parseMediaType( "application/octet-stream" ) )
                    .body( new InputStreamResource( file.getInputStream() ) );
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 文件删除
     *
     * @param code 文件外部唯一标识
     * @return
     */
    @GetMapping("/delete/{code}")
    public ApiResponse<String> delete(@PathVariable String code) {
        boolean flag = fileService.deleteFile( code );
        return flag ? ApiResponse.success( "文件删除成功！" ) : ApiResponse.error( "文件删除失败！" );
    }

    /**
     * 文件的分页查询
     *
     * @param pageQuery 每页显示的容量/显示的是第几页
     * @param fsFile
     * @return
     */
    @PostMapping("/search")
    public Object searchFile(PageQuery pageQuery, @Nullable @RequestBody FsFile fsFile) {
        return ApiResponse.success( fileService.getAll( pageQuery, fsFile ) );
    }


    @PostMapping("/update-status")
    public ApiResponse<Boolean> updateFileStatus(String fileCode) {
        try {
            fileService.updateFileStatus( fileCode );
        } catch (Exception e) {
            return ApiResponse.error( e.getMessage() );
        }
        return ApiResponse.success( true );
    }
}
