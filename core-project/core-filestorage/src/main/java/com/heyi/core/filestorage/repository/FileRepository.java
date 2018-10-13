package com.heyi.core.filestorage.repository;

import com.heyi.core.filestorage.entity.FsFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FsFile, Long>, JpaSpecificationExecutor<FsFile> {

    /**
     * 通过code来查询文件具体详情
     *
     * @param code
     * @return
     */
    Optional<FsFile> findFsFileByCode(String code);

    /**
     * 通过name跟folderid来查询历史版本列表
     *
     * @param folderId
     * @param pageable
     * @return
     */
    Page<FsFile> findFsFileByFolderIdAndName(Integer folderId, String name, Pageable pageable);

    /**
     * 分页查询信息
     *
     * @param folderId
     * @param name
     * @param pageable
     * @return
     */
    @Query(value =
            "SELECT f FROM FsFile AS f " +
                    " WHERE f.folder.id = ?1 and f.name like %?2%  and f.version =  " +
                    " (select max(f1.version) from FsFile as f1 where f1.folder.id = ?1  and f.name = f1.name )")
    Page<FsFile> getAll(String folderId, String name, Pageable pageable);

    /**
     * 分页查询信息
     *
     * @param folderParent
     * @param pageable
     * @return
     */
    @Query(value =
            "SELECT f FROM FsFile AS f " +
                    " left join FsFolder as fo on fo.id = f.folder.id " +
                    " WHERE fo.parent = ?1 and f.version =  " +
                    " (select max(f1.version) from FsFile as f1 where fo.id = f1.folder.id and  f.name = f1.name) ")
    Page<FsFile> getAll(String folderParent, Pageable pageable);
}
