package com.heyi.core.filestorage.service;

import com.heyi.core.filestorage.entity.FsFolder;
import com.heyi.core.filestorage.entity.folder.DirectoryNode;

public interface FolderService {

    FsFolder addFolder(String name, String parent, String creator) throws Exception;

    Boolean removeFolder(String id) throws Exception;

    FsFolder updateFolder(String id, String name) throws Exception;

    DirectoryNode fullTree();
}
