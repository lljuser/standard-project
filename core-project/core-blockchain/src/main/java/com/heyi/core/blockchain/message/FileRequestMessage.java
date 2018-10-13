package com.heyi.core.blockchain.message;

public class FileRequestMessage implements IMessage {
    /**
     * 文件信息对应合约或链的标识
     * **/
    private String identityCode;

    /**
     * 文件的MD5或Hash
     * **/
    private String fileCode;


    /**
     * 文件的浏览地址
     * **/
    private String fileAddress;


    /**
     * 文件发送人
     * **/
    private String sender;

    /**
     * 文件的接收人
     * **/
    private String[] receivers;



}
