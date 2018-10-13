package com.heyi.core.blockchain.repository.sql.entity;

import javax.persistence.*;

@Table(name = "bc_task")
@Entity
public final class SqlTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String txId;

    /**
     * 任务接受者
     */

    @Column(name = "receive_broker")
    private String receiver;

    /**
     * 任务发送者
     */
    @Column(name = "send_broker")
    private String sender;

    /**
     * 处理结果 -1:NO, 0:未处理, 1:YES
     */
    @Column(name = "handle_result")
    private short handleResult;

    /**
     * 任务类型(1:确认交易)
     */
    private short type;



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

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public short getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(short handleResult) {
        this.handleResult = handleResult;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }
}
