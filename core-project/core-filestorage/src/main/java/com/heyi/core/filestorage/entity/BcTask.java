package com.heyi.core.filestorage.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "bc_task")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class BcTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String txId;

    /**
     * 任务接受者
     */
    @Column(name = "receive_broker")
    private String receiveBroker;

    /**
     * 任务发送者
     */
    @Column(name = "send_broker")
    private String sendBroker;

    /**
     * 任务状态(0初始化，5已送达，10已接收，15已确认)
     */
    @Column(name = "status")
    private short status;

    /**
     * 时间数据库自动生成
     */
    @Column(name = "create_time")
    @CreatedDate
    private Timestamp createTime;

    /**
     * 任务类型(1:确认交易)
     */
    private short type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getReceiveBroker() {
        return receiveBroker;
    }

    public void setReceiveBroker(String receiveBroker) {
        this.receiveBroker = receiveBroker;
    }

    public String getSendBroker() {
        return sendBroker;
    }

    public void setSendBroker(String sendBroker) {
        this.sendBroker = sendBroker;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
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

    public BcTask() {
        super();
    }

    public BcTask(String txId, Short type, Short status, String sendBroker, String receiveBroker) {
        super();
        this.txId = txId;
        this.type = type;
        this.status = status;
        this.sendBroker = sendBroker;
        this.receiveBroker = receiveBroker;
    }

    public interface Type {
        // 确认交易
        short CONFIRM_TRANSACTION = 1;
        short FOLLOW_TRANSACTION = 2;
    }


    public interface Status {
        // 初始化
        short INIT = 0;

        // 已送达
        short SENT = 5;

        // 已接收
        short RECEIVED = 10;

        // 已确认
        short CONFIRMED = 15;
    }

}
