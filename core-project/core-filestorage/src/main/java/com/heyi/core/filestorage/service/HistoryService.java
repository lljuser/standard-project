package com.heyi.core.filestorage.service;

import com.heyi.core.common.PageResult;
import com.heyi.core.filestorage.entity.FsFile;
import org.springframework.data.domain.Pageable;

public interface HistoryService {

    FsFile findFsFileByCode(String code);

    PageResult<FsFile> findFsFileByFolderIdAndName(Integer folder_id, String name, Pageable pageable);
}
