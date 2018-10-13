package com.heyi.core.filestorage.dto;

import com.heyi.core.filestorage.entity.BcTempFileTransaction;

/**
 * 交易传输对象(用于跨系统的交易数据交互)
 */
public class FileTransationDto {

    public FileTransationDto() {
    }

    public FileTransationDto(String txId) {
        this.txId = txId;
        this.timestamp = System.currentTimeMillis();
    }

    public FileTransationDto(String txId, String description) {
        this.txId = txId;
        this.timestamp = System.currentTimeMillis();
        this.description = description;
    }

    /**
     * UUIDHexGenerator.generate()可生成id
     */
    private String txId;

    private long timestamp;

    private String description;

    /**
     * 文件发送节点地址
     **/
    private String sender;

    /**
     * 文件接收确认者地址
     **/
    private String[] receivers;

    /**
     * 挖矿节点地址
     **/
    private String miner;

    /**
     * 关注节点同步的节点地址
     **/
    private String[] follower;


    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
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

    public String[] getReceivers() {
        return receivers;
    }

    public void setReceivers(String[] receivers) {
        this.receivers = receivers;
    }

    public String getMiner() {
        return miner;
    }

    public void setMiner(String miner) {
        this.miner = miner;
    }

    public String[] getFollower() {
        return follower;
    }

    public void setFollower(String[] follower) {
        this.follower = follower;
    }

    /**
     * FileTransationDto与BcTempFileTransaction转换
     */
    public static FileTransationDto castTo(BcTempFileTransaction transaction) {
        FileTransationDto dto = new FileTransationDto();
        dto.setDescription(transaction.getDescription());
        dto.setFollower(transaction.getFollower().split(";"));
        dto.setReceivers(transaction.getReceiver().split(";"));
        dto.setSender(transaction.getSender());
        dto.setTimestamp(transaction.getTimestamp());
        dto.setTxId(transaction.getTxId());

        return dto;
    }
}
