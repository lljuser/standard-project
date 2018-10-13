package com.heyi.core.blockchain.contract;

public interface IContract {
    void execute(String command,String data,String sign);
}
