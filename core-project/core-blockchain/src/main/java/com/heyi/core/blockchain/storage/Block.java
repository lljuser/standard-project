package com.heyi.core.blockchain.storage;

import com.heyi.core.blockchain.transaciton.MerkleTree;
import com.heyi.core.blockchain.transaciton.Transaction;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.Comparator;


/**
 * 区块类 hash,preBlockHash,transactions,timeStamp,nonce
 **/
public class Block {

    private String identityCode;

    public String getIdentityCode() {
        return identityCode;
    }

    /**
     * 本区块hash
     **/
    private String hash;

    /**
     * 前一区块hash
     **/
    private String preBlockHash;

    /**
     * 交易流水信息
     **/
    private Transaction[] transactions;

    /**
     * 区块创建的时间
     **/
    private long timeStamp;

    /**
     * 工作量证明算法随的机数或计数器
     **/
    private long nonce;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPreBlockHash() {
        return preBlockHash;
    }

    public void setPreBlockHash(String preBlockHash) {
        this.preBlockHash = preBlockHash;
    }

    public Transaction[] getTransactions() {
        return transactions;
    }

    public void setTransactions(Transaction[] transactions) {
        this.transactions = transactions;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getNonce() {
        return nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    /**
     * 构造函数
     **/
    public Block(String identityCode, String preBlockHash, Transaction[] transactions, long timeStamp, long nonce) {
        this.identityCode = identityCode;
        this.preBlockHash = preBlockHash;
        this.transactions = transactions;
        this.timeStamp = timeStamp;
        this.nonce = nonce;
        this.sortTransaction();
    }

    public Block(String identityCode, String hash) {
        this.identityCode = identityCode;
        this.hash = hash;
    }


    /**
     * 交易流水数据通过merkletree hash计算
     **/
    public byte[] hashTransaction() {
        byte[][] txArray = new byte[this.transactions.length][];
        for (int i = 0; i < this.transactions.length; i++) {
            txArray[i] = this.transactions[i].hash();
        }
        return new MerkleTree( txArray ).getRoot().getHash();

    }


    /**
     * 对交易记录进行排序，防止存储与读取顺序乱了 导致merkletree不一致
     **/
    public void sortTransaction() {
        if (ObjectUtils.isEmpty( this.transactions )) return;
        Arrays.sort( this.transactions, new TransactionCompare() );
    }

    static class TransactionCompare implements Comparator<Transaction> {

        @Override
        public int compare(Transaction o1, Transaction o2) {
            if (o1.getTimestamp() > o2.getTimestamp())
                return 1;
            else
                return -1;
        }
    }
}
