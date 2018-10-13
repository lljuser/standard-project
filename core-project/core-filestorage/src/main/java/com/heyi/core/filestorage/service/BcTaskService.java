package com.heyi.core.filestorage.service;

import com.heyi.core.common.PageQuery;
import com.heyi.core.common.PageResult;
import com.heyi.core.filestorage.entity.BcTask;

import java.util.List;
import java.util.Optional;

public interface BcTaskService {

    /**
     * 增加一个任务
     *
     * @param bctask
     * @return
     */
    Object saveTask(BcTask bctask);

    /**
     * 更新任务状态
     *
     * @param bctask
     * @return
     */
    Object updateTaskState(BcTask bctask);

    boolean updateTaskStateWithPreviousState(BcTask task, Short prevState);

    /**
     * 获取待办(内部根据配置的域名和端口来区分)
     *
     * @param pageQuery
     * @return
     */
    PageResult<BcTask> getToDoList(PageQuery pageQuery);

    /**
     * @param transactionId
     * @return
     * @throws Exception
     */
    boolean allReceiveBrokerConfirmed(String transactionId) throws Exception;

    /**
     * @param txId
     * @param type 1：确认 2：关注
     * @return
     */
    List<BcTask> getDataByTxIdAndType(String txId, short type);
}
