package com.heyi.core.filestorage.entity;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class FsAuditWithNoId {

    @Column(name = "create_time")
    @Temporal(value = TemporalType.TIMESTAMP)
    protected Date createTime;

    @Column(name = "update_time")
    @Temporal(value = TemporalType.TIMESTAMP)
    protected Date updateTime;

    @Column(name = "creator_account")
    protected String creatorAccount;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreatorAccount() {
        return creatorAccount;
    }

    public void setCreatorAccount(String creatorAccount) {
        this.creatorAccount = creatorAccount;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
