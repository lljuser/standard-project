package com.heyi.core.blockchain.storage;

import com.heyi.core.blockchain.BlockchainConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;

/**
 * 链构建类
 * **/
@Component
public class BlockchainBuilder {
    private final static Log logger = LogFactory.getLog(Blockchain.class);

    private IBlockchainStorage blockchainStorage;
    public BlockchainBuilder(IBlockchainStorage blockchainStorage){
        this.blockchainStorage=blockchainStorage;
    }
    /**
     * 已创建链集合 多条链共存 并防止重复创建
     * **/
    private static HashMap<String,Blockchain> chainList =new HashMap<>();

    /**
     * 创建对应链 如果链标识不存在，由默认为创建default链
     * **/
    public synchronized Blockchain createBlockChain(String identityCode) {
        if(StringUtils.isEmpty(identityCode))
            identityCode = BlockchainConfig.DEFAULT_BLOCKCHAIN_IDENTITY;

        Blockchain blockchain=null;
        if(!chainList.containsKey(identityCode)) {
            try{
                //创建链
                blockchain = new DefaultBlockchain(identityCode,blockchainStorage);

                chainList.put(identityCode,blockchain);
            }catch (Exception ex){
                logger.error("Fail: create blockchain->"+ identityCode +" :"+ex.getMessage());
            }

        }else{
            //链池返回
            blockchain =  chainList.get(identityCode);
        }

        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.info("blockchain->"+ identityCode +" load success" );
        return  blockchain;
    }

    /**
     * 创建一条默认链
     * **/
    public synchronized Blockchain createBlockChain(){
        return this.createBlockChain(BlockchainConfig.DEFAULT_BLOCKCHAIN_IDENTITY);
    }

    /**
     * 避免直接New一条链出来，无法直接管理
     * **/
    static class DefaultBlockchain extends Blockchain {
        private DefaultBlockchain(String identityCode,IBlockchainStorage blockchainStorage) {
            super(identityCode, blockchainStorage);
        }

    }








}
