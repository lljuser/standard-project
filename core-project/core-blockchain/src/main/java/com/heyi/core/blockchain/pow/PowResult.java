package com.heyi.core.blockchain.pow;

/**
 * 工作量计算结果
 */
public class PowResult {
    private long nonce;

    private String hash;

    public PowResult(long nonce,String hash){
        this.nonce=nonce;
        this.hash=hash;
    }

    public long getNonce() {
        return nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
