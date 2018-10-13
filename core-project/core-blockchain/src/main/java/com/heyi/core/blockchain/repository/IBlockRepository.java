package com.heyi.core.blockchain.repository;

import com.heyi.core.blockchain.storage.Block;
import com.heyi.core.common.PageResult;

public interface IBlockRepository {
    String getLastBlockHash(String identity);

    void putLastBlockHash(String identity, String tipBlockHash);

    void putBlock(Block block);

    Block getBlock(String blockHash);

    PageResult<Block> getBlock(Block blockHash);
}
