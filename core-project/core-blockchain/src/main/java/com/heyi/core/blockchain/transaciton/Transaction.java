package com.heyi.core.blockchain.transaciton;


import com.heyi.core.blockchain.common.SerializeUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 交易流水 抽象类
 * **/
public abstract class Transaction implements ITransaction {
    private final static Log logger = LogFactory.getLog(Transaction.class);

    private String txId;

    private long timestamp;


    private String description;


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


    public Transaction(String txId){
        this.txId = txId;
        this.timestamp= System.currentTimeMillis();
    }

    public Transaction(String txId,String description){
        this.txId = txId;
        this.timestamp= System.currentTimeMillis();
        this.description=description;
    }


    public byte[] hash() {
        return DigestUtils.sha256(SerializeUtils.serialize(this));
    }

}
