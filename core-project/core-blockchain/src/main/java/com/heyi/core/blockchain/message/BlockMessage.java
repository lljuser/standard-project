package com.heyi.core.blockchain.message;

import com.heyi.core.blockchain.storage.Block;

public class BlockMessage implements IMessage {
    /**
     * 消息签名信息
     **/
    private String sign;

    /**
     * 区块信息
     **/
    private Block block;

    /**
     * 时间戳
     **/
    private long timeStamp;
}
