package com.heyi.core.blockchain.storage;

import com.heyi.core.blockchain.pow.PowResult;
import com.heyi.core.blockchain.pow.ProofOfWork;
import com.heyi.core.blockchain.transaciton.GenesisTransaction;
import com.heyi.core.blockchain.transaciton.Transaction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.Iterator;

/**
 * 区块链类 引入链标识 实现多链切换
 * 打包交易，验证，HASH,创建区块，区块存储
 * 一个智能合约对应一条链
 * **/
public abstract class Blockchain {
    private final static Log logger = LogFactory.getLog(Blockchain.class);

    private IBlockchainStorage blockchainStorage;

    /**
     * 链标识
     * **/
    private String identityCode;

    public String getIdentityCode() {
        return identityCode;
    }


    /**
     * 当前尾部块
     * **/
    private String lastBlockHash;
    public String getLastBlockHash() {
        return lastBlockHash;
    }




    protected Blockchain(String identityCode, IBlockchainStorage blockchainStorage){
        this.blockchainStorage=blockchainStorage;
        this.identityCode=identityCode;
        //初始化链
        init();
    }


    /**
     * 链初始化
     * **/
    protected void init() {
        this.lastBlockHash = this.blockchainStorage.getLastBlockHash(this.identityCode);
        if(StringUtils.isEmpty(this.lastBlockHash)){
            Block genesisBlock = this.newGenesisBlock();
            this.lastBlockHash = genesisBlock.getHash();
            blockchainStorage.addBlock(genesisBlock);
        }
    }

    /**

     * 添加新区块
     * **/
    protected void addBlock(Block block) {
        //存储区块
        boolean isSuccess = blockchainStorage.addBlock(block);
        //当前链的尾块hash标记为最新的区块
        if(isSuccess)
            this.lastBlockHash = block.getHash();
    }

    /**
     * 打包交易数据，验证，并创建区块
     * **/
    public Block mineBlock(Transaction[] transactions) {
        // 挖矿前，先验证交易记录
        for (Transaction tx : transactions) {
            if (!this.verifyTransactions(tx)) {
                logger.error("error: Fail to mine block ! Invalid transaction ! tx=" + tx.toString());
                throw new RuntimeException("error: Fail to mine block ! Invalid transaction ! ");
            }
        }

        //前一区块hash即当前尾块
        if (this.lastBlockHash == null) {
            throw new RuntimeException("error: Fail to get last block hash ! ");
        }

        //创建新区块
        Block block = this.newBlock(lastBlockHash, transactions);
        this.addBlock(block);
        return block;
    }

    /**
     * 根据Hash查找对应的Block
     * **/
    public Block getBlock(String blockHash){
        return this.blockchainStorage.getBlock(blockHash);
    }

    /**
     * 验证block hash工作量证明是否正确
     * **/
    public boolean validateBlock(Block block){
        ProofOfWork proofOfWork=ProofOfWork.newProofOfWork(block);
        return proofOfWork.validate();
    }

    /**
     * 验证 矿工挖出的块并追加到链上
     * **/
    public boolean validateAndAddBlock(Block block){
        boolean isSuccess=false;
        if(this.validateBlock(block)){
            if(block.getPreBlockHash() == this.lastBlockHash){
                this.addBlock(block);
                isSuccess=true;
            }
        }

        return isSuccess;
    }

    /**
     * 验证是否是自己的Block
     * **/
    public  boolean validateOwnerBlock(Block block){
        boolean isBlock = false;
        if(this.validateBlock(block)){
            /**
             * 验证block是否属于自己，需要复原整个链验证
             * **/
            BlockchainIterator iterator=this.getBlockchainIterator();
            while (iterator.hasNext()){
                Block indexBlock=iterator.next();
                //只验证块的hash即可，不需要验证前preBlockHash
                if(indexBlock.getHash().equals(block.getHash())){
                    isBlock = true;
                    break;
                }
                else{
                    logger.info("validateOwnerBlock: the block's pow is wrong");
                    //isBlock = false;
                }

            }
        }else {
            logger.info("validateOwnerBlock: the block's pow is wrong");
            //isBlock = false;
        }

        return isBlock;
    }

    /**
     * 交易签名验证
     *
     * @param tx
     */
    private boolean verifyTransactions(Transaction tx) {
        return true;
    }

    /**
     * 区块迭代器
     * **/
    public BlockchainIterator getBlockchainIterator(){
        return new BlockchainIterator(this.lastBlockHash,this.blockchainStorage);
    }

    /**
     * 区块迭代器
     * 从物理存储迭代获取区块
     * **/
    static class BlockchainIterator implements Iterator<Block> {
        //游标
        private String currentBlockHash;
        private IBlockchainStorage blockchainStorage;

        private BlockchainIterator(String currentBlockHash, IBlockchainStorage blockchainStorage) {
            this.currentBlockHash = currentBlockHash;
            this.blockchainStorage = blockchainStorage;
        }

        /**
         * 是否有下一个区块
         *
         * @return
         */
        public boolean hasNext() {
            if (StringUtils.isEmpty(currentBlockHash)) {
                return false;
            }
            Block lastBlock = this.blockchainStorage.getBlock(currentBlockHash);
            if (lastBlock == null) {
                return false;
            }
            // 创世区块直接放行
            if (lastBlock.getPreBlockHash().length() == 0) {
                return true;
            }
            return this.blockchainStorage.getBlock(lastBlock.getPreBlockHash()) != null;
        }

        /**
         * 返回区块
         *
         * @return
         */
        public Block next() {
            Block currentBlock = this.blockchainStorage.getBlock(currentBlockHash);
            if (currentBlock != null) {
                this.currentBlockHash = currentBlock.getPreBlockHash();
                return currentBlock;
            }
            return null;
        }

        @Deprecated
        public void remove() {

        }
    }


    /**
     * 创建创世块
     * **/
    public Block newGenesisBlock(){
        Transaction transaction=new GenesisTransaction(this.identityCode);
        Block genesisBlock = this.newBlock("",new Transaction[]{transaction});
        return genesisBlock;
    }




    /**
     * 创建新的区块
     * **/
    public  Block newBlock(String preBlockHash,Transaction[] transactions){
        Block block= new Block(this.identityCode, preBlockHash,transactions,Instant.now().toEpochMilli(),0);
        ProofOfWork pow = ProofOfWork.newProofOfWork(block);
        PowResult powResult = pow.run();
        block.setHash(powResult.getHash());
        block.setNonce(powResult.getNonce());
        return block;
    }


    /**
     * 验证某笔交易的真实性
     * **/
    public boolean validateTransaction(Transaction transaction){
        //根据交易查找block
        //验证block
        return true;
    }

    /**
     * 查找某笔交易
     * **/
    public Transaction getTransaction(String txId){
        return null;
    }

}
