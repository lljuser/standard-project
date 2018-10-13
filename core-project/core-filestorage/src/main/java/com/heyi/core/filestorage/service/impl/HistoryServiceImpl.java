package com.heyi.core.filestorage.service.impl;

import com.heyi.core.common.PageResult;
import com.heyi.core.filestorage.entity.FsFile;
import com.heyi.core.filestorage.repository.FileRepository;
import com.heyi.core.filestorage.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    private FileRepository fileRepository;

    /**
     * 通过code来查询文件具体详情
     * @param code
     * @return
     */
    @Override
    public FsFile findFsFileByCode(String code) {
        Optional<FsFile> fsFileByCode = fileRepository.findFsFileByCode( code );
        return fsFileByCode.isPresent() ? fsFileByCode.get() : null;
    }

    /**
     *  通过name跟folderid来查询历史版本列表
     * @param folder_id
     * @param name
     * @param pageable
     * @return
     */
    @Override
    public PageResult<FsFile> findFsFileByFolderIdAndName(Integer folder_id, String name, Pageable pageable) {
        return PageResult.build( fileRepository.findFsFileByFolderIdAndName( folder_id, name, pageable ) );
    }

}
