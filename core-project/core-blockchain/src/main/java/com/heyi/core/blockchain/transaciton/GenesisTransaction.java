package com.heyi.core.blockchain.transaciton;

import com.heyi.core.blockchain.BlockchainConfig;

/**
 * 创世块交易流水
 * **/
public class GenesisTransaction extends Transaction {

    public GenesisTransaction(String identityCode){
        super(BlockchainConfig.DEFAULT_BLOCKCHAIN_TXID,"2018.9.10 [heyi crop.]-blockchain-[" +identityCode+"] was created");
    }
}
