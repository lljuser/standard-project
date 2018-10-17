package com.heyi.core.netty.domain;

import java.util.Date;

/**
 * @描述 com.heyi.core.netty.domain
 * @创建人 ljliu
 * @创建时间 2018/10/17
 * @修改人和其它信息
 */
public class OgProperty {
    private String id;
    private String parentId;
    private String name;
    private String propertyNo;
    private String propertyType;
    private String roleId;
    private String sortIndex;
    private String description;
    private Boolean isEnabled; private Date createTime;
    private String createUserName;
    private String createWorkNo;
    private Date lastModifyTime;
    private String lastModifyUserName;
    private String LastModifyWorkNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPropertyNo() {
        return propertyNo;
    }

    public void setPropertyNo(String propertyNo) {
        this.propertyNo = propertyNo;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(String sortIndex) {
        this.sortIndex = sortIndex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

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


    private OgUser user;

    public OgUser getUser() {
        return user;
    }

    public void setUser(OgUser user) {
        this.user = user;
    }
}
