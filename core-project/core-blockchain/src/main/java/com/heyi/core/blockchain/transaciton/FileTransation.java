package com.heyi.core.blockchain.transaciton;

public class FileTransation extends Transaction {

    public FileTransation() {
        super("");
    }

    public FileTransation(String fileCode, String fileName) {
        super(fileCode, fileName);
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


}
