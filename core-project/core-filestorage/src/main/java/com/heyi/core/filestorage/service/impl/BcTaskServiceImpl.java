package com.heyi.core.filestorage.service.impl;

import com.heyi.core.common.PageQuery;
import com.heyi.core.common.PageResult;
import com.heyi.core.filestorage.entity.BcTask;
import com.heyi.core.filestorage.repository.BcTaskRepository;
import com.heyi.core.filestorage.service.BcTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BcTaskServiceImpl implements BcTaskService {

    @Autowired
    private BcTaskRepository taskRepository;

    @Value("${app.endpoints.local:127.0.0.1}:${app.ports.file:8103}")
    private String localFileServerEndpoint;

    @Override
    public Object saveTask(BcTask bctask) {
        BcTask save = taskRepository.save( bctask );
        return save;
    }

    @Override
    public Object updateTaskState(BcTask task) {
        String receiveBroker = task.getReceiveBroker();
        String txId = task.getTxId();
        short status = task.getStatus();
        Object gr = taskRepository.updateBcTaskState( receiveBroker, txId, status );
        return gr;
    }

    public boolean updateTaskStateWithPreviousState(BcTask task, Short prevState) {
        String receiveBroker = task.getReceiveBroker();
        String txId = task.getTxId();
        short status = task.getStatus();
        BcTask psTask = taskRepository.findByTxIdAndReceiveBrokerAndStatus( txId, receiveBroker, prevState );
        if (psTask != null) {
            psTask.setStatus( status );
            taskRepository.save( psTask );
            return true;
        } else {
            return false;
        }
    }

    @Override
    public PageResult<BcTask> getToDoList(PageQuery pageQuery) {
        Pageable pageable = PageRequest.of( pageQuery.getPageNumber(), pageQuery.getPageSize(), Sort.by( "createTime" ).descending() );
        Page<BcTask> tasks = taskRepository.findAll(
                (Specification<BcTask>) (root, criteriaQuery, criteriaBuilder) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    predicates.add( criteriaBuilder.and( criteriaBuilder.equal( root.get( "type" ).as( Short.class ), 1 ) ) );
                    predicates.add( criteriaBuilder.and( criteriaBuilder.equal( root.get( "status" ).as( Short.class ), BcTask.Status.RECEIVED ) ) );
                    // todo: 只关心与当前登录账户相关的任务
                    predicates.add( criteriaBuilder.and( criteriaBuilder.equal( root.get( "receiveBroker" ).as( String.class ), localFileServerEndpoint ) ) );
                    Predicate[] predicateArr = new Predicate[predicates.size()];
                    return criteriaQuery.where( predicates.toArray( predicateArr ) ).getRestriction();
                }, pageable );
        return PageResult.build( tasks );
    }

    /**
     * 检查是否所有任务状态均为已确认状态
     */
    @Override
    public boolean allReceiveBrokerConfirmed(String transactionId) throws Exception {
        Optional<List<BcTask>> optionalTasks = taskRepository.findBcTasksByTxId( transactionId );
        if (ObjectUtils.isEmpty(optionalTasks)) {
            throw new Exception( "该交易相关任务不存在" );
        }
        List<BcTask> tasks = optionalTasks.get();
        for (BcTask task : tasks) {
            if (task.getStatus() != BcTask.Status.CONFIRMED) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param txId
     * @param type 1：确认 2：关注
     * @return
     */
    @Override
    public List<BcTask> getDataByTxIdAndType(String txId, short type) {
        Pageable pageable = PageRequest.of( 0, 100, Sort.Direction.DESC, "id" );
        Page<BcTask> data = taskRepository.findAllByTxIdAndType( txId, type, pageable );
        return data.getContent();
    }

}
