package com.heyi.core.blockchain.pow;

import com.heyi.core.blockchain.BlockchainConfig;
import com.heyi.core.blockchain.common.ByteUtils;
import com.heyi.core.blockchain.storage.Block;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigInteger;

public class ProofOfWork {
    private final static Log logger = LogFactory.getLog(ProofOfWork.class);


    /**
     * 待计算块
     * **/
    private Block block;

    /**
     * 目标难度值
     * **/
    private BigInteger target;

    private ProofOfWork(Block block,BigInteger target){
        this.block=block;
        this.target=target;
    }

    /**
     * 创建工作量证明
     * @param block
     * @return
     */
    public static ProofOfWork newProofOfWork(Block block){
        BigInteger targetValue=BigInteger.valueOf(1).shiftLeft(256-BlockchainConfig.DEFAULT_BLOCKCHAIN_TARGET_BITS);
        return new ProofOfWork(block,targetValue);
    }



    public PowResult run(){
        long nonce = 0;
        String shaHex= "";
        long startTime= System.currentTimeMillis();

        while (nonce < Long.MAX_VALUE) {
            logger.info("Pow running, nonce="+nonce);
            byte[] data=this.prepareData(nonce);
            shaHex = DigestUtils.sha256Hex(data);

            //hash 16进制转换与目标值难度比较
            if(new BigInteger(shaHex,16).compareTo(this.target) == -1){
                logger.info("Pow run elapsed Time seconds :" + (System.currentTimeMillis() - startTime) / 1000);
                logger.info("Pow run correct hash Hex: "+ shaHex);
                break;
            }else{
                nonce++;
            }

        }
        return new PowResult(nonce, shaHex);
    }


    /**
     * 准备数据
     * <p>
     * 注意：在准备区块数据时，一定要从原始数据类型转化为byte[]，不能直接从字符串进行转换
     *
     * @param nonce
     * @return
     */
    private byte[] prepareData(long nonce) {
        byte[] prevBlockHashBytes = {};
        if (StringUtils.isNoneBlank(this.block.getPreBlockHash())) {
            prevBlockHashBytes = new BigInteger(this.block.getPreBlockHash(), 16).toByteArray();
        }

        return ByteUtils.merge(
                prevBlockHashBytes,
                this.block.hashTransaction(),
                ByteUtils.toBytes(this.block.getTimeStamp()),
                ByteUtils.toBytes(BlockchainConfig.DEFAULT_BLOCKCHAIN_TARGET_BITS),
                ByteUtils.toBytes(nonce),
                DigestUtils.sha256(this.block.getIdentityCode())

        );
    }

    /**
     * 验证区块是否有效
     *
     * @return
     */
    public boolean validate() {
        boolean isBlock=false;
        if(StringUtils.isEmpty(this.block.getHash())){
            logger.info("validate block: this block hash is empty,may be not run pow");
            isBlock = false;
        }else{
            byte[] data = this.prepareData(this.block.getNonce());
            String shaHex = DigestUtils.sha256Hex(data);
            boolean isFindHash = new BigInteger(shaHex, 16).compareTo(this.target) == -1;
            /**
             * 只验证当前hash值是否相同即可，不需要再验前一hash
             * 因为前hash如果被修改了，当前hash通过工作量证明计算必然不相同
             * ***比特币不需要验证这个hash是否正确，难度太高，伪造成本太高，最后未必会被确认**
             * ***但是这里降低了挖矿难度，很容易计算出一个hash，伪造成本低，因此需要再与之前计算过的hash再验证一次
             * **/
            if(isFindHash && shaHex.equals(this.block.getHash())) {
                isBlock = true;
            }
            else
            {
                logger.info("validate block: block hash is wrong");
                isBlock = false;
            }
        }

        return isBlock;

    }
}
