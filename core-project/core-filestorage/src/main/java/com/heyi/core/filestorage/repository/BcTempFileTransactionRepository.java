package com.heyi.core.filestorage.repository;

import com.heyi.core.filestorage.entity.BcTempFileTransaction;
import com.heyi.core.filestorage.repository.batch.BatchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * @ClassName BcTempFileTransactionRepository
 * @Description: TODO
 * @Author: lidong
 * @CreateDate: 2018/8/27$ 4:25 PM$
 * @UpdateUser: lidong
 * @UpdateDate: 2018/8/27$ 4:25 PM$
 * @UpdateRemark: TODO
 * @Version: 1.0
 **/
public interface BcTempFileTransactionRepository
        extends JpaRepository<BcTempFileTransaction, Integer>, JpaSpecificationExecutor<BcTempFileTransaction>, BatchRepository<BcTempFileTransaction> {


    /**
     * 查询未确认的交易表
     *
     * @param status
     * @param pageable
     * @return
     */
    Page<BcTempFileTransaction> findAllByStatusAndChainCode(short status, String chainCode, Pageable pageable);

    /**
     * 查询未确认的交易表 不带chainCode
     *
     * @param status
     * @param pageable
     * @return
     */
    Page<BcTempFileTransaction> findAllByStatus(short status, Pageable pageable);

    /**
     * 通过交易id查询信息
     *
     * @param txId
     * @return
     */
    Optional<BcTempFileTransaction> findByTxId(String txId);

    /**
     * 通过交易fileCode查询信息
     *
     * @param fileCode
     * @return
     */
    Optional<BcTempFileTransaction> getByFileCode(String fileCode);
}
