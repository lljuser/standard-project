package com.heyi.core.netty.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;
import java.util.List;

/**
 * @描述 com.heyi.core.netty.domain
 * @创建人 ljliu
 * @创建时间 2018/10/15
 * @修改人和其它信息
 */
//@JsonFilter("myFilter")  //这种方法忽略很坑 objectMapper的时候一定要设置这个filter
public class OgUser extends OperationLog {
    static {
        System.out.println("---con---");
    }

    private String id;
    private String workNo;
    private String userName;
    private String password;
    private String telPhone;
    private String email;
    private String officePhone;
    private String domain;
    private Boolean isEnabled;
    private Boolean sex;
    private Date birthday;
    private String shortMobile;
    private String propertyId;
    private String propertyName;
    private String propertyNo;
    private String duty;
    private String avator;
    private Integer sortIndex;

    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkNo() {
        return workNo;
    }

    public void setWorkNo(String workNo) {
        this.workNo = workNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getShortMobile() {
        return shortMobile;
    }

    public void setShortMobile(String shortMobile) {
        this.shortMobile = shortMobile;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyNo() {
        return propertyNo;
    }

    public void setPropertyNo(String propertyNo) {
        this.propertyNo = propertyNo;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public Integer getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(Integer sortIndex) {
        this.sortIndex = sortIndex;
    }

    private Date timeStamp;

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public List<OgProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<OgProperty> properties) {
        this.properties = properties;
    }

    @JsonIgnore
    private List<OgProperty> properties;


    private OperationLog operationLog;

    public OperationLog getOperationLog() {
        return operationLog;
    }

    public void setOperationLog(OperationLog operationLog) {
        this.operationLog = operationLog;
    }

    public OgUser(){

    }

    public OgUser(String userName){
        this.userName=userName;
    }
}
