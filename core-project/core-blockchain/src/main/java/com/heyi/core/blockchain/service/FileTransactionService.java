package com.heyi.core.blockchain.service;

import com.heyi.core.blockchain.repository.sql.entity.SqlFileTransaction;
import com.heyi.core.blockchain.transaciton.FileTransation;
import com.heyi.core.common.PageQuery;
import com.heyi.core.common.PageResult;

public interface FileTransactionService {
    FileTransation getTransactionByTxId(String txId);

    PageResult<FileTransation> findAllByBlockHash(String identityCode, PageQuery pageQuery);

    PageResult<SqlFileTransaction> getAll(PageQuery pageQuery);

    PageResult<SqlFileTransaction> findByIdentityCode(String identityCode, PageQuery pageQuery);

    void saveOrUpdate(SqlFileTransaction item);
}
