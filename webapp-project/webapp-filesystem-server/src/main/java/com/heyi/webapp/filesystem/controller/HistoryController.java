package com.heyi.webapp.filesystem.controller;

import com.heyi.core.common.ApiResponse;
import com.heyi.core.common.PageResult;
import com.heyi.core.filestorage.entity.FsFile;
import com.heyi.core.filestorage.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    //根据code查询单个文件详情
    @GetMapping(value = "/historydetailed/{code}")
    public Object findFsFileByCode(@PathVariable("code") String code) {
        return ApiResponse.success(historyService.findFsFileByCode(code) );
    }

    // 通过folderid跟文件名称来查询历史版本列表
    @GetMapping(value = "/historylist/{folder_id}")
    public ApiResponse<PageResult<FsFile>> findFsFileByFolderIdAndName(@PathVariable("folder_id") Integer folder_id,
                                                                       @RequestParam("name") String name,
                                                                       @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                                                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize ) {
        //
        if (pageNumber > 0) {
            pageNumber = pageNumber - 1;
        } else {
            pageNumber = 0;
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "id");

        return ApiResponse.success(historyService.findFsFileByFolderIdAndName(folder_id,name,pageable));
    }

}

