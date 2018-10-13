package com.heyi.core.filestorage.repository;

import com.heyi.core.filestorage.entity.FsFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FolderRepository extends JpaRepository<FsFolder, String> {

    Optional<List<FsFolder>> findByParent(String parent);

    @Query("select f from FsFolder f where not (f.parent = ?1)")
    Optional<List<FsFolder>> findAllChildNodes(String parent);
}
