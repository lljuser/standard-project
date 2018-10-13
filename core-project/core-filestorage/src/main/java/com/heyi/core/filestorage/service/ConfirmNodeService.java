package com.heyi.core.filestorage.service;

import com.heyi.core.filestorage.entity.BcTempFileTransaction;

public interface ConfirmNodeService {

    void receiveFileTransaction(BcTempFileTransaction transaction);

    void confirmFileTransaction(Long msgId, String txId);
}
