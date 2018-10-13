package com.heyi.core.filestorage.entity;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Table(name = "bc_temp_filetransaction")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class BcTempFileTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String txId;

    @Column(nullable = false)
    @LastModifiedDate
    private Long timestamp;

    private String description;

    @Column(name = "send_broker", nullable = false)
    private String sender;

    @Column(name = "fileCode", nullable = false)
    private String fileCode;

    /**
     * E文分号分割的字符串(可以有多个接受者)
     */
    @Column(name = "receive_brokers", nullable = false)
    private String receiver;

    /**
     * E文分号分割的字符串(可以有多个关注者)
     */
    @Column(name = "follower_brokers", nullable = false)
    private String follower;

    /**
     * 所属链
     */
    @Column(name = "chain_code", nullable = false)
    private String chainCode;

    /**
     * 交易状态 0:待确认，1:已确认，2:待打包到候选区块, 3:已打包到候选区块
     */
    private short status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public Long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getChainCode() {
        return chainCode;
    }

    public void setChainCode(String chainCode) {
        this.chainCode = chainCode;
    }

    public interface Status {
        // 0:待确认
        short BE_CONFIRMED = 0;
        // 1:已确认
        short HAS_CONFIRMED = 1;
        // 2:待打包到候选区块
        short BE_PACKED = 2;
        // 3:已打包到候选区块
        short HAS_PACKED = 3;
    }
}