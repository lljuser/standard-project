package com.heyi.core.blockchain.repository.sql;

import com.heyi.core.blockchain.repository.sql.entity.SqlFileTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface SqlFileTransactionRepository extends JpaRepository<SqlFileTransaction, Long>, JpaSpecificationExecutor<SqlFileTransaction> {

    SqlFileTransaction findByTxId(String txId);

    Page<SqlFileTransaction> findAllByBlockHash(String blockHash, Pageable pageable);

    @Query(value = "select  *  from bc_filetransaction as f where f.block_hash in (select c.hash from bc_block as c where c.identity_code = ?1)", nativeQuery = true)
    Page<SqlFileTransaction> findByIdentityCode(String identityCode, Pageable pageable);

    SqlFileTransaction findByBlockHash(String blockHash);
}
