package com.heyi.core.filestorage.entity.folder;

import java.util.ArrayList;
import java.util.List;

public class DirectoryNode {

    public String id;
    public String name;
    public List<DirectoryNode> children;

    public DirectoryNode() {
        children = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DirectoryNode> getChildren() {
        return children;
    }

    public void setChildren(List<DirectoryNode> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return String.format("id=%d, name=%s, children", id, name);
    }
}
