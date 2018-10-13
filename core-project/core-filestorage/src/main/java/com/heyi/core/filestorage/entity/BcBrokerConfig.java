package com.heyi.core.filestorage.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "bc_broker_config")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class BcBrokerConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "identity_code")
    private String identityCode;

    private String subscriber;

    // 配置类型(1关注节点，2确认节点)
    private short type;

    // 注解 @CreatedDate 数据库生成创造时间
    @Column(name = "create_time")
    @CreatedDate
    private Timestamp createTime;

    // 注解 @LastModifiedDate 数据库更新时间
    @Column(name = "update_time")
    @LastModifiedDate
    private Timestamp updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdentityCode() {
        return identityCode;
    }

    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
    }

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public interface Type {
        // 关注节点
        short FOLLOW = 1;

        // 确认节点
        short CONFIRM = 2;
    }
}
