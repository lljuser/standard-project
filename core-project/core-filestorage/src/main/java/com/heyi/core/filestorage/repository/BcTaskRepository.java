package com.heyi.core.filestorage.repository;

import com.heyi.core.filestorage.entity.BcTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface BcTaskRepository extends JpaRepository<BcTask, Long>, JpaSpecificationExecutor<BcTask> {
    @Transactional
    @Query(value = "update BcTask b set b.status=:status where b.txId=:txId and b.receiveBroker=:receiveBroker")
    @Modifying
    int updateBcTaskState(@Param("receiveBroker") String receiveBroker, @Param("txId") String txId, @Param("status") short status);

    Optional<List<BcTask>> findBcTasksByTxId(String txId);

    Optional<List<BcTask>> findBcTasksByTxIdAndStatus(String txId, Short status);

    Page<BcTask> findAll(Specification<BcTask> bcTaskSpecification, Pageable pageable);

    BcTask findByTxIdAndReceiveBrokerAndStatus(String txid, String receiver, Short status);

    @Query(value = "select distinct new BcTask(b.txId, b.type, b.status, b.sendBroker, b.receiveBroker ) from BcTask b where b.txId=?1 and b.type=?2  and b.status > 5")
    Page<BcTask> findAllByTxIdAndType(String txId, short type, Pageable pageable);
}
