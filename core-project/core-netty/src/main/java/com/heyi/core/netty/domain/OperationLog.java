package com.heyi.core.netty.domain;

import java.util.Date;

/**
 * @描述 com.heyi.core.netty.domain
 * @创建人 ljliu
 * @创建时间 2018/10/22
 * @修改人和其它信息
 */
public class OperationLog implements IEntity {
    private Date createTime;
    private String createUserName;
    private String createWorkNo;
    private Date lastModifyTime;
    private String lastModifyUserName;
    private String LastModifyWorkNo;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateWorkNo() {
        return createWorkNo;
    }

    public void setCreateWorkNo(String createWorkNo) {
        this.createWorkNo = createWorkNo;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public String getLastModifyUserName() {
        return lastModifyUserName;
    }

    public void setLastModifyUserName(String lastModifyUserName) {
        this.lastModifyUserName = lastModifyUserName;
    }

    public String getLastModifyWorkNo() {
        return LastModifyWorkNo;
    }

    public void setLastModifyWorkNo(String lastModifyWorkNo) {
        LastModifyWorkNo = lastModifyWorkNo;
    }
}
