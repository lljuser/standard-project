package com.heyi.core.blockchain.storage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;

/**
 * 简单的区块链存储
 **/
public class SimpleBlockChainStorage implements IBlockchainStorage {
    private final static Log logger = LogFactory.getLog( SimpleBlockChainStorage.class );

    private ArrayList<Block> blockList = new ArrayList<Block>();

    public boolean addBlock(Block block) {
        if (block.getPreBlockHash().length() == 0) {
            if (blockList.isEmpty()) {
                this.blockList.add( block );
                return true;
            } else {
                logger.error( "Fail: genesis block is exist" );
                return false;
            }

        } else {
            Block lastBlock = this.blockList.get( this.blockList.size() - 1 );
            if (lastBlock == null) {
                logger.error( "Fail: not find this last block" );
                return false;
            }
            if (lastBlock.getHash() == block.getPreBlockHash()) {
                this.blockList.add( block );
                return true;
            } else {
                logger.error( "Fail: this preBlockHash of block is wrong" );
                return false;
            }
        }

    }

    public Block getBlock(String blockHash) {
        Block block = null;
        for (Block item : this.blockList) {
            if (item.getHash() == blockHash) {
                block = item;
                break;
            }
        }
        return block;
    }

    @Override
    public String getLastBlockHash(String identityCode) {
        return this.getLastBlockHash();
    }


    public String getLastBlockHash() {
        if (this.blockList.size() == 0)
            return null;
        else
            return this.blockList.get( this.blockList.size() - 1 ).getHash();
    }
}
