package com.heyi.core.filestorage.service;

import com.heyi.core.common.PageQuery;
import com.heyi.core.common.PageResult;
import com.heyi.core.filestorage.entity.FsFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface FileService {
    /**
     * 获取到所有的文件
     *
     * @return
     */
    List<FsFile> getAll();

    /**
     * 根据查询信息分页显示文件信息
     *
     * @param pageQuery
     * @param fsFile
     * @return
     */
    PageResult<FsFile> getAll(PageQuery pageQuery, FsFile fsFile);

    /**
     * 删除文件
     *
     * @param code 唯一标识符
     * @return
     */
    boolean deleteFile(String code);

    /**
     * 保存文件
     *
     * @param file     上传的文件
     * @param folderId 文件夹的编码
     * @param request
     * @return
     */
    FsFile saveFile(MultipartFile file, String folderId, HttpServletRequest request);


    /**
     * 根据code查找路径信息
     *
     * @param code
     * @return
     */
    String getFsFileByCode(String code);

    /**
     * 更新文件的状态
     *
     * @param fileCode
     * @throws Exception
     */
    void updateFileStatus(String fileCode) throws Exception;

    /**
     * 查找文件信息通过code
     *
     * @param fileCode
     * @return
     */
    FsFile findFsFileByCode(String fileCode);
}
