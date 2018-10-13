package com.heyi.core.blockchain.repository.sql.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.heyi.core.blockchain.storage.Block;
import com.heyi.core.blockchain.transaciton.FileTransation;
import com.heyi.core.blockchain.transaciton.GenesisTransaction;
import com.heyi.core.blockchain.transaciton.Transaction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Table(name = "bc_block")
@Entity
public final class SqlBlock implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "identity_code", nullable = false)
    private String identityCode;

    @Column(nullable = false)
    private String hash;

    @Column(name = "pre_block_hash", nullable = false)
    private String preBlockHash;

    @Column(nullable = false)
    private Long timestamp;

    @Column(nullable = false)
    private Long nonce;


    @OneToMany(mappedBy = "block", cascade = {CascadeType.ALL})
    @JsonBackReference
    private List<SqlFileTransaction> transactions = new ArrayList<>();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdentityCode() {
        return identityCode;
    }

    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
    }

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

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getNonce() {
        return nonce;
    }

    public void setNonce(Long nonce) {
        this.nonce = nonce;
    }

    public List<SqlFileTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<SqlFileTransaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(SqlFileTransaction transaction) {
        transaction.setBlock( this );
        transactions.add( transaction );
    }

    public static SqlBlock castFrom(Block block) {
        SqlBlock sqlBlock = new SqlBlock();
        sqlBlock.setHash( block.getHash() );
        sqlBlock.setIdentityCode( block.getIdentityCode() );
        sqlBlock.setNonce( block.getNonce() );
        sqlBlock.setPreBlockHash( block.getPreBlockHash() );
        sqlBlock.setTimestamp( block.getTimeStamp() );
        for (Transaction baseTrans : block.getTransactions()) {
            FileTransation fileTransaction = null;
            if (baseTrans instanceof FileTransation) {
                fileTransaction = (FileTransation) baseTrans;
            } else if (baseTrans instanceof GenesisTransaction) {
                // 创世区块
                GenesisTransaction gtrans = (GenesisTransaction) baseTrans;
                fileTransaction = new FileTransation( "", "" );
                fileTransaction.setTimestamp( gtrans.getTimestamp() );
                fileTransaction.setTxId( gtrans.getTxId() );
                fileTransaction.setDescription( gtrans.getDescription() );
            }
            if (fileTransaction == null)
                throw new RuntimeException( "an invalid transaction shout not put in fileTransaction" );
            SqlFileTransaction sqlFileTransaction = SqlFileTransaction.castFrom( fileTransaction );
            sqlBlock.addTransaction( sqlFileTransaction );
        }
        return sqlBlock;
    }

    public static Block castTo(SqlBlock sqlBlock) {
        Block b = new Block( sqlBlock.identityCode, sqlBlock.preBlockHash, null,
                sqlBlock.timestamp, sqlBlock.nonce );
        List<Transaction> transactions = new ArrayList<>();
        for (SqlFileTransaction sqlFileTransaction : sqlBlock.getTransactions()) {
            transactions.add( SqlFileTransaction.castTo( sqlFileTransaction ) );
        }
        Transaction[] array = new Transaction[transactions.size()];
        b.setTransactions( transactions.toArray( array ) );
        return b;
    }
}
