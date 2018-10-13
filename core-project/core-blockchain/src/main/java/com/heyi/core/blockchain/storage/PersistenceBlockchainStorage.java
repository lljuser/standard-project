package com.heyi.core.blockchain.storage;

import com.heyi.core.blockchain.BlockchainConfig;
import com.heyi.core.blockchain.repository.IBlockRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 基于数据库的区块链持久化存储
 **/
@Component
public class PersistenceBlockchainStorage implements IBlockchainStorage {
    private final static Log logger = LogFactory.getLog( SimpleBlockChainStorage.class );

    @Autowired
    private IBlockRepository blockRepository;

    public boolean addBlock(Block block) {
        try {
            // 存储block，最后一块hash，transaciton
            this.blockRepository.putBlock( block );
            this.blockRepository.putLastBlockHash( block.getIdentityCode(), block.getHash() );
        } catch (Exception ex) {
            logger.error( "Fail: save block to store error " + ex.getMessage() );
            return false;
        }
        return true;
    }

    @Override
    public Block getBlock(String blockHash) {
        return this.blockRepository.getBlock( blockHash );
    }

    @Override
    public String getLastBlockHash(String identityCode) {
        return this.blockRepository.getLastBlockHash( identityCode );
    }

    @Override
    public String getLastBlockHash() {
        return this.blockRepository.getLastBlockHash( BlockchainConfig.DEFAULT_BLOCKCHAIN_IDENTITY );
    }
}
