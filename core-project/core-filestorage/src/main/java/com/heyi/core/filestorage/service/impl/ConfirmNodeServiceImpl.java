package com.heyi.core.filestorage.service.impl;

import com.heyi.core.filestorage.entity.BcMessage;
import com.heyi.core.filestorage.entity.BcTask;
import com.heyi.core.filestorage.entity.BcTempFileTransaction;
import com.heyi.core.filestorage.service.*;
import com.heyi.core.filestorage.util.RestTemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ConfirmNodeServiceImpl implements ConfirmNodeService {

    private static final Logger log = LoggerFactory.getLogger( ConfirmNodeServiceImpl.class );

    @Autowired
    BcTaskService taskService;

    @Autowired
    FileService fileService;

    @Autowired
    BcTempFileTransactionService tempFileTransactionService;

    @Autowired
    MessageService messageService;

    @Value("${app.endpoints.local:127.0.0.1}:${app.ports.file:8103}")
    private String localFileServerPort;

    @Value("${app.endpoints.order:127.0.0.1}:${app.ports.file:8103}")
    private String orderFileServerEndpoint;

    /**
     * 接收文件交易
     *
     * @param transaction
     */
    public void receiveFileTransaction(BcTempFileTransaction transaction) {
        createConfirmFileTransactionTask( transaction );
        createFileTransactionMessage( transaction );
    }

    /**
     * 确认文件交易
     *
     * @param msgId
     * @param txId
     */
    @Override
    public void confirmFileTransaction(Long msgId, String txId) {
        // 更新消息状态
        try {
            messageService.updateMessageStatusById( msgId, BcMessage.Status.HANDLED );
        } catch (Exception e) {
            log.warn( String.format( "更新交易确认消息状态失败=%s", txId ) );
        }
        // 更新本地任务状态
        BcTask task = new BcTask();
        task.setTxId( txId );
        task.setStatus( BcTask.Status.CONFIRMED );
        task.setReceiveBroker( localFileServerPort );
        boolean result = taskService.updateTaskStateWithPreviousState( task, BcTask.Status.RECEIVED );
        if (!result) throw new RuntimeException( "无法确认该交易，没有找到指定任务" );
        // 通知排序节点最终确认交易
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put( "txId", txId );
        requestBody.put( "receiveBroker", task.getReceiveBroker() );
        // todo: order没有收到反馈通知逻辑处理(后台单独线程不断尝试)
        RestTemplateUtil.post( "http://" + orderFileServerEndpoint + "/order/confirm-file-transaction", requestBody );
    }

    private void createConfirmFileTransactionTask(BcTempFileTransaction transaction) {
        BcTask task = new BcTask();
        task.setTxId( transaction.getTxId() );
        task.setReceiveBroker( localFileServerPort );
        task.setSendBroker( transaction.getSender() );
        task.setStatus( BcTask.Status.RECEIVED );
        task.setType( BcTask.Type.CONFIRM_TRANSACTION );
        taskService.saveTask( task );
    }

    private void createFileTransactionMessage(BcTempFileTransaction transaction) {
        BcMessage message = new BcMessage();
        String title = String.format( "%s [交易确认]", transaction.getDescription() );
        message.setTitle( title );
        message.setType( BcMessage.Type.CONFIRM_TRANSACTION );
        message.setSid( transaction.getTxId() );
        message.setReceiveBroker( localFileServerPort );
        message.setStatus( BcMessage.Status.UNHANDLED );
        try {
            messageService.createMessage( message );
        } catch (Exception e) {
            log.warn( String.format( "创建交易确认消息失败=%s", e.getMessage() ) );
        }
    }
}
