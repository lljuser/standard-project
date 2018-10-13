package com.heyi.core.filestorage.entity;

import javax.persistence.*;

@Entity
@Table(name = "fs_folder")
public class FsFolder extends FsAuditWithNoId {

    @Id
    private String id;

    private String name;

    private String parent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
