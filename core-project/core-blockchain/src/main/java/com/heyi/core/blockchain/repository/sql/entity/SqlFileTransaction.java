package com.heyi.core.blockchain.repository.sql.entity;

import com.heyi.core.blockchain.transaciton.FileTransation;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

@Table(name = "bc_filetransaction")
@Entity
public final class SqlFileTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 交易所属区块标识
     */
    @ManyToOne
    @JoinColumn(name = "block_hash", referencedColumnName = "hash")
    private SqlBlock block;

    @Column(name = "txid", nullable = false)
    private String txId;

    @Column(nullable = false)
    private Long timestamp;

    private String description;

    @Column(name = "send_broker", nullable = false)
    private String sender;

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
     * 挖矿节点
     */
    @Column(name = "miner_broker", nullable = false)
    private String miner;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public SqlBlock getBlock() {
        return block;
    }

    public void setBlock(SqlBlock block) {
        this.block = block;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public Long getTimestamp() {
        return timestamp;
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

    public String getMiner() {
        return miner;
    }

    public void setMiner(String miner) {
        this.miner = miner;
    }

    public static SqlFileTransaction castFrom(FileTransation fileTransation) {
        if (fileTransation == null) return null;
        char seperator = ';';
        SqlFileTransaction sqlFileTransaction = new SqlFileTransaction();
        sqlFileTransaction.setDescription(fileTransation.getDescription());
        sqlFileTransaction.setFollower(StringUtils.join(fileTransation.getFollower(), seperator));
        sqlFileTransaction.setMiner(fileTransation.getMiner());
        sqlFileTransaction.setReceiver(StringUtils.join(fileTransation.getReceivers(), seperator));
        sqlFileTransaction.setTimestamp(fileTransation.getTimestamp());
        sqlFileTransaction.setSender(fileTransation.getSender());
        sqlFileTransaction.setTxId(fileTransation.getTxId());

        return sqlFileTransaction;
    }

    public static FileTransation castTo(SqlFileTransaction transaction) {
        if (transaction == null) return null;
        char seperator = ';';
        FileTransation fileTransation = new FileTransation(transaction.getBlock().getIdentityCode(), transaction.description);
        fileTransation.setFollower(StringUtils.split(transaction.follower, seperator));
        fileTransation.setMiner(transaction.miner);
        fileTransation.setReceivers(StringUtils.split(transaction.receiver, seperator));
        fileTransation.setSender(transaction.sender);
        fileTransation.setTxId(transaction.txId);
        fileTransation.setTimestamp(transaction.timestamp);

        return fileTransation;
    }
}
