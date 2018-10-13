package com.heyi.core.filestorage.service.impl;

import com.heyi.core.common.UUIDHexGenerator;
import com.heyi.core.filestorage.entity.FsFolder;
import com.heyi.core.filestorage.entity.folder.DirectoryNode;
import com.heyi.core.filestorage.repository.FolderRepository;
import com.heyi.core.filestorage.service.FolderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class FolderServiceImpl implements FolderService {

    private static final Logger log = LoggerFactory.getLogger(FolderServiceImpl.class);
    private static final String ROOT_FOLDER_ID = UUIDHexGenerator.generateEmpty();

    @Autowired
    private FolderRepository folderRepository;
    private List<FsFolder> removeFolderList;

    @Override
    public FsFolder addFolder(String name, String parent, String creator) throws Exception {
        if (!StringUtils.hasLength(name)) {
            throw new Exception("文件夹名称不能为空");
        }
        if (parent == null) {
            throw new Exception("父文件夹id不能为空");
        }
        if (!StringUtils.hasLength(creator)) {
            // TODO: Creator must not be null
        }

        Optional<FsFolder> optionalFsFolder = folderRepository.findById(parent);
        // Create root folder
        if (parent == ROOT_FOLDER_ID && optionalFsFolder.isPresent()) {
            throw new Exception("根文件夹已存在");
        } else if (!optionalFsFolder.isPresent()) {
            throw new Exception(String.format("父文件夹（id=%d）不存在", parent));
        }
        FsFolder folder = new FsFolder();
        folder.setId(UUIDHexGenerator.generate());
        folder.setName(name);
        folder.setCreatorAccount(creator);
        folder.setCreateTime(new Date());
        folder.setParent(parent);
        FsFolder node = folderRepository.save(folder);
        return node;
    }

    @Override
    public Boolean removeFolder(String id) throws Exception {
        if (ObjectUtils.isEmpty(id)) {
            throw new Exception("文件夹id不能为空");
        }
        Optional<FsFolder> folderOptional = folderRepository.findById(id);
        if (!folderOptional.isPresent()) {
            throw new Exception(String.format("文件夹（id=%s）不存在", id));
        }
        removeFolderList = new ArrayList<>();
        FsFolder folder = folderOptional.get();
        if (folder.getParent() == ROOT_FOLDER_ID) {
            throw new Exception("不支持删除根文件夹");
        }
        removeFolderList.add(folder);
        iterateFolderChildren(folder);
        folderRepository.deleteInBatch(removeFolderList);
        removeFolderList = null;
        return true;
    }

    @Override
    public FsFolder updateFolder(String id, String name) throws Exception {
        if (id == null) {
            throw new Exception("文件夹id不能为空");
        }
        if (!StringUtils.hasLength(name)) {
            throw new Exception("文件夹名称不能为空");
        }
        Optional<FsFolder> folderOptional = folderRepository.findById(id);
        if (!folderOptional.isPresent()) {
            throw new Exception(String.format("文件夹（id=%s）不存在", id));
        }
        FsFolder oldFolder = folderOptional.get();
        oldFolder.setName(name);
        oldFolder.setUpdateTime(new Date());
        FsFolder folder = folderRepository.save(oldFolder);
        return folder;
    }

    @Override
    public DirectoryNode fullTree() {
        DirectoryNode rootNode = new DirectoryNode();
        Optional<List<FsFolder>> optionalFsFolders = folderRepository.findByParent(ROOT_FOLDER_ID);
        if (optionalFsFolders.isPresent()) {
            List<FsFolder> folders = optionalFsFolders.get();
            if (folders.size() > 0) {
                FsFolder rootFolder = folders.get(0);
                rootNode.setId(rootFolder.getId());
                rootNode.setName(rootFolder.getName());
            }
        }
        if (rootNode.getId() == null) {
            return null; // No root node
        }
        Optional<List<FsFolder>> optionalFolderChildren = folderRepository.findAllChildNodes(ROOT_FOLDER_ID);
        if (optionalFolderChildren.isPresent()) {
            List<FsFolder> folders = optionalFolderChildren.get();
            rootNode = getDirectoryNode(folders, rootNode);
        }
        return rootNode;
    }

    private DirectoryNode getDirectoryNode(List<FsFolder> folders, DirectoryNode parentNode) {
        List<DirectoryNode> children = new ArrayList<>();
        parentNode.setChildren(children);
        if (folders.isEmpty()) {
            return parentNode;
        }
        for (FsFolder folder : folders) {
            if (folder.getParent().equals(parentNode.getId())) {
                DirectoryNode node = new DirectoryNode();
                node.setId(folder.getId());
                node.setName(folder.getName());
                children.add(node);
                getDirectoryNode(folders, node);
            }
        }
        return parentNode;
    }

    private void iterateFolderChildren(FsFolder fsFolder) {
        String parent = fsFolder.getId();
        Optional<List<FsFolder>> optionalFsFolders = folderRepository.findByParent(parent);
        if (!optionalFsFolders.isPresent()) {
            return;
        }
        List<FsFolder> children = optionalFsFolders.get();
        removeFolderList.addAll(children);
        for (FsFolder folder : children) {
            iterateFolderChildren(folder);
        }
    }
}
