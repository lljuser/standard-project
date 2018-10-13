package com.heyi.core.blockchain.repository.sql;

import com.heyi.core.blockchain.repository.sql.entity.SqlTempFileTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface SqlBcTempFileTransactionRepository
        extends JpaRepository<SqlTempFileTransaction, Integer>, JpaSpecificationExecutor<SqlTempFileTransaction> {

    Page<SqlTempFileTransaction> findAllByStatus(short status, Pageable pageable);

    @Modifying
    @Query("update SqlTempFileTransaction t set t.status = ?2 where t.txId in ?1")
    @Transactional
    void batchUpdateStatus(List<String> txIds, Short status);
}
