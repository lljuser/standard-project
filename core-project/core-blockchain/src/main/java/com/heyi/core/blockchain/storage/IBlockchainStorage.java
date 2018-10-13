package com.heyi.core.blockchain.storage;

/**
 * Block存储接口
 * <p>
 * 1.存储Block信息
 * 2.存储最后一个区块hash
 * 3.存储交易记录
 **/

public interface IBlockchainStorage {
    boolean addBlock(Block block);

    Block getBlock(String blockHash);

    String getLastBlockHash(String identityCode);

    String getLastBlockHash();
}
