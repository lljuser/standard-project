package com.heyi.webapp.filesystem.controller;

import com.heyi.core.common.ApiResponse;
import com.heyi.core.filestorage.entity.FsFolder;
import com.heyi.core.filestorage.entity.folder.DirectoryNode;
import com.heyi.core.filestorage.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/folder")
public class FolderController {

    @Autowired
    private FolderService folderService;

    @PostMapping("/add-folder")
    public ApiResponse<FsFolder> addFolder(
            @RequestParam("name") String name,
            @RequestParam("parent") String parent
    ) {
        try {
            String creator = "andy";
            FsFolder node = folderService.addFolder(name, parent, creator);
            return ApiResponse.success(node);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @PostMapping("/remove-folder")
    public ApiResponse<Boolean> removeFolder(@RequestParam("id") String id) {
        try {
            Boolean deleted = folderService.removeFolder(id);
            return ApiResponse.success(deleted);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @PostMapping("/update-folder")
    public ApiResponse<FsFolder> updateFolder(
            @RequestParam("id") String id,
            @RequestParam("name") String name
    ) {
        try {
            FsFolder folder = folderService.updateFolder(id, name);
            return ApiResponse.success(folder);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @GetMapping("/full-tree")
    public ApiResponse<List<DirectoryNode>> fullTree() {
        DirectoryNode directoryNode = folderService.fullTree();
        List<DirectoryNode> nodeList = new ArrayList<>();
        nodeList.add(directoryNode);
        return ApiResponse.success(nodeList);
    }
}
