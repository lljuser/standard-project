package com.heyi.core.filestorage.entity;

import javax.persistence.*;

@Entity
@Table(name = "fs_server")
@Deprecated
public class FsServer extends FsAudit<Integer> {

    @Column(nullable = false, name = "server_key")
    private String serverKey;

    @Column(name = "server_name")
    private String serverName;

    @Column(name = "root_path")
    private String rootPath;

    private String address;

    public String getServerKey() {
        return serverKey;
    }

    public void setServerKey(String serverKey) {
        this.serverKey = serverKey;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
