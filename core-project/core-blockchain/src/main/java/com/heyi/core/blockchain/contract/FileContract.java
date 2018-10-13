package com.heyi.core.blockchain.contract;

public class FileContract implements IContract {
    /**
     * 合约标识->对应区块链标识
     * **/
    private String identityCode;

    /**
     * 文件发送节点地址
     * **/
    private String sender;

    /**
     * 文件接收确认者地址
     * **/
    private String[] receivers;

    /**
     * 挖矿节点地址
     * **/
    private String miner;

    /**
     * 关注节点同步的节点地址
     * **/
    private String[] follower;


    public void execute(String method,String data,String sign){
        switch (method){
            case "upload":
                break;
            case "request":
                break;
            case "response":
                break;

        }
    }

}
