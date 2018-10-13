package com.heyi.core.blockchain;

import com.heyi.core.blockchain.storage.*;
import com.heyi.core.blockchain.transaciton.FileTransation;
import com.heyi.core.blockchain.transaciton.GenesisTransaction;
import com.heyi.core.blockchain.transaciton.Transaction;
import com.heyi.core.common.UUIDHexGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Iterator;

@Configuration
public class BlockchainConfig {
    /**
     * 目标hash难度值的位数
     **/
    public static final int DEFAULT_BLOCKCHAIN_TARGET_BITS = 10;

    /**
     * 默认单链标识
     **/
    public final static String DEFAULT_BLOCKCHAIN_IDENTITY = "default";

    /**
     * 创世块交易记录默认TXID
     **/
    public final static String DEFAULT_BLOCKCHAIN_TXID = "00000000000000000000000000000000";

//    @Bean
//    public IBlockchainStorage blockchainStorage(){
//        return new SimpleBlockChainStorage();
//    }


    public static void main(String[] args) throws InterruptedException {
        IBlockchainStorage blockchainStorage = new SimpleBlockChainStorage();
        BlockchainBuilder blockchainBuilder = new BlockchainBuilder(blockchainStorage);
        Blockchain blockchain = blockchainBuilder.createBlockChain();

        Transaction transaction1 = new FileTransation(UUIDHexGenerator.generate(), "spring.mvc.book");

        Thread.sleep(1);
        Transaction transaction2 = new FileTransation(UUIDHexGenerator.generate(), "spring.boot.book");

        Thread.sleep(1);
        Transaction transaction3 = new FileTransation(UUIDHexGenerator.generate(), "spring.cloud.book");

        Block block1 = blockchain.mineBlock(new Transaction[]{transaction3, transaction2, transaction1});

        Iterator<Block> blockIterator = blockchain.getBlockchainIterator();
        while (blockIterator.hasNext()) {
            Block block = blockIterator.next();
            System.out.println("-----------------------");
            System.out.println("Block IdentityCode:" + block.getIdentityCode());
            System.out.println("Block Hash:" + block.getHash());

            for (Transaction item : block.getTransactions()) {
                System.out.println("Transaction TxId:" + item.getTxId());
                System.out.println("Transaction TimeStamp:" + item.getTimestamp());
                System.out.println("Transaction FileName:" + item.getDescription());
                System.out.println("-------");
            }


        }

        blockchain.validateBlock(block1);
        blockchain.validateOwnerBlock(block1);

    }
}
