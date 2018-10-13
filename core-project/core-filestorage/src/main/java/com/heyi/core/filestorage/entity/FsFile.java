package com.heyi.core.filestorage.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "fs_file")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class FsFile extends FsAudit<Integer> {

    private String server;

    private String code;

    @Column(nullable = false)
    private String name;

    @Column(name = "ext_name")
    private String extName;

    private double size;

    private String version;

    @ManyToOne(targetEntity = FsFolder.class)
    @JoinColumn(name = "folder_id")
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnoreProperties(ignoreUnknown = true)
    private FsFolder folder;

    @Column(name = "upload_ip")
    private String uploadIp;

    @Column(name = "req_params")
    private String reqParams;

    // 0: 未确认 1：确认
    private int status;

    private String encryption;

    /**
     * 时间数据库自动生成
     */
    @Column(name = "create_time")
    @CreatedDate
    private Timestamp createTime;

    /**
     * 时间数据库自动生成
     */
    @Column(name = "update_time")
    @LastModifiedDate
    private Timestamp updateTime;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtName() {
        return extName;
    }

    public void setExtName(String extName) {
        this.extName = extName;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public FsFolder getFolder() {
        return folder;
    }

    public void setFolder(FsFolder folder) {
        this.folder = folder;
    }

    public String getUploadIp() {
        return uploadIp;
    }

    public void setUploadIp(String uploadIp) {
        this.uploadIp = uploadIp;
    }

    public String getReqParams() {
        return reqParams;
    }

    public void setReqParams(String reqParams) {
        this.reqParams = reqParams;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getEncryption() {
        return encryption;
    }

    public void setEncryption(String encryption) {
        this.encryption = encryption;
    }

    public enum Status {
        // 未确认
        UNCONFIRMED(0),
        // 已确认
        CONFIRMED(1);

        final int value;

        Status(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
