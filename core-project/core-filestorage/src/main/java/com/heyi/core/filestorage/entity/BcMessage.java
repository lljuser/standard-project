package com.heyi.core.filestorage.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "bc_message")
@EntityListeners(AuditingEntityListener.class)
public class BcMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private short type;

    private String sid;

    @Column(name = "receive_broker")
    private String receiveBroker;

    private short status;

    @Column(name = "create_time")
    @CreatedDate
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getReceiveBroker() {
        return receiveBroker;
    }

    public void setReceiveBroker(String receiveBroker) {
        this.receiveBroker = receiveBroker;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
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
        // 确认交易
        short CONFIRM_TRANSACTION = 1;
    }

    public interface Status {
        // 未处理
        short UNHANDLED = 0;

        // 已处理
        short HANDLED = 1;

        // 屏蔽状态
        short SCREENED = -1;
    }
}
