package com.heyi.core.filestorage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heyi.core.common.UUIDHexGenerator;
import com.heyi.core.filestorage.entity.BcBrokerConfig;
import com.heyi.core.filestorage.entity.BcTask;
import com.heyi.core.filestorage.entity.BcTempFileTransaction;
import com.heyi.core.filestorage.service.*;
import com.heyi.core.filestorage.util.MapUtil;
import com.heyi.core.filestorage.util.RequestUtil;
import com.heyi.core.filestorage.util.RestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName OrderServiceImpl
 * @Description: TODO
 * @Author: lidong
 * @CreateDate: 2018/8/30$ 9:57 AM$
 * @UpdateUser: lidong
 * @UpdateDate: 2018/8/30$ 9:57 AM$
 * @UpdateRemark: TODO
 * @Version: 1.0
 **/
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private BcTempFileTransactionService bcTempFileTransactionService;

    @Autowired
    private BcBrokerConfigService bcBrokerConfigService;

    @Autowired
    private BcTaskService bcTaskService;

    @Autowired
    private FileService fileService;

    @Value("${app.endpoints.local:127.0.0.1}:${app.ports.blockchain:8109}")
    private String localChainServerPort;

    @Value("${app.endpoints.order:127.0.0.1}:${app.ports.blockchain:8109}")
    private String orderChainServerPort;

    private int pageSize = 100;

    /**
     * 分发交易到所有确认节点、关注节点
     * <p>
     * 提取确认节点及关注节点
     * 添加任务信息到任务表
     * 将交易信息 提交到确认节点和关注节点(确认节点接收交易数据，真正确认操作是用户触发的)
     * 收到反馈后更新任务状态为已送达
     *
     * @param transaction
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dispatchTrans(BcTempFileTransaction transaction, HttpServletRequest request) throws Exception {
        if (ObjectUtils.isEmpty(transaction)) {
            throw new Exception("交易信息不能为空");
        }
        String description = transaction.getDescription();
        if (StringUtils.isEmpty(description)) {
            throw new Exception("交易描述不能为空");
        }
        String fileCode = transaction.getFileCode();
        if (StringUtils.isEmpty(fileCode)) {
            throw new Exception("file code不能为空");
        }
        String chainCode = transaction.getChainCode();
        if (StringUtils.isEmpty(chainCode)) {
            throw new Exception("chain code不能为空");
        }
        String sender = RequestUtil.getClientIpAddress(request);
        // 获取确认节点和关注节点
        String follow = StringUtils.collectionToDelimitedString(bcBrokerConfigService.selectBcBrokerConfigs(chainCode, BcBrokerConfig.Type.FOLLOW), ";");
        String confirm = StringUtils.collectionToDelimitedString(bcBrokerConfigService.selectBcBrokerConfigs(chainCode, BcBrokerConfig.Type.CONFIRM), ";");
        // 判断是否一保存数据
        BcTempFileTransaction temp = bcTempFileTransactionService.getByFileCode(fileCode);
        if (!ObjectUtils.isEmpty(temp)) {
            throw new Exception("交易已经发送");
        }
        // 保存信息
        BcTempFileTransaction tx = new BcTempFileTransaction();
        tx.setTxId(UUIDHexGenerator.generate());
        tx.setDescription(description);
        tx.setSender(sender);
        tx.setFileCode(fileCode);
        tx.setChainCode(chainCode); // 文件夹名称
        tx.setFollower(StringUtils.isEmpty(follow) ? "" : follow);
        tx.setReceiver(StringUtils.isEmpty(confirm) ? "" : confirm);
        tx.setStatus(BcTempFileTransaction.Status.BE_CONFIRMED);
        bcTempFileTransactionService.save(tx); // 交易信息存入临时交易池
        if (!dispatchTransaction(tx)) {
            throw new Exception("交易发送失败");
        }
        return true;
    }

    private boolean dispatchTransaction(BcTempFileTransaction transaction) {
        String[] followers = {};
        String[] receivers = {};
        if (transaction.getFollower() != null)
            followers = transaction.getFollower().split(";");
        if (transaction.getReceiver() != null)
            receivers = transaction.getReceiver().split(";");
        for (String follower : followers) {
            // dispatchToSingleNode(BcTask.Type.FOLLOW_TRANSACTION, follower, transaction);
        }
        for (String receiver : receivers) {
            if (!dispatchToSingleNode(BcTask.Type.CONFIRM_TRANSACTION, receiver, transaction)) {
                return false;
            }
        }
        return true;
    }

    private boolean dispatchToSingleNode(short taskType, String broker, BcTempFileTransaction transaction) {
        String targetUrl = "";
        if (taskType == BcTask.Type.CONFIRM_TRANSACTION)
            targetUrl = "http://" + broker + "/node/tx/recv";
        try {
            BcTask initialTask = setTask(transaction, taskType, broker);
            if (initialTask.getId() < 1) return false;
            ResponseEntity result = RestTemplateUtil.post(targetUrl, transaction);
            if (result == null) return false;
            Map map = MapUtil.object2Map(result.getBody());
            if (map != null && !map.isEmpty() && map.get("status").toString().equals("OK")) {
                // 收到目标的成功反馈
                initialTask.setStatus(BcTask.Status.SENT);
                bcTaskService.updateTaskStateWithPreviousState(initialTask, BcTask.Status.INIT);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 设置任务
     *
     * @param transaction
     * @param taskType    任务类型
     * @param receiver    接收者地址
     * @return
     */
    private BcTask setTask(BcTempFileTransaction transaction, short taskType, String receiver) {
        BcTask task = new BcTask();
        task.setTxId(transaction.getTxId());
        task.setSendBroker(transaction.getSender());
        task.setReceiveBroker(receiver);
        task.setType(taskType);
        task.setStatus(BcTask.Status.INIT);
        bcTaskService.saveTask(task);
        return task;
    }


    /**
     * 排序节点最终确认交易
     *
     * @param transactionId
     * @param receiver
     * @throws Exception
     */
    @Override
    public void finalConfirmFileTransaction(String transactionId, String receiver) throws Exception {
        // 更新Order节点上，当前任务的状态
        BcTask task = new BcTask();
        task.setReceiveBroker(receiver);
        task.setTxId(transactionId);
        task.setStatus(BcTask.Status.CONFIRMED);
        bcTaskService.updateTaskStateWithPreviousState(task, BcTask.Status.SENT);
        // 检查是否全部确认节点都已经确认完成(最终确认)
        boolean allReceiveBrokerConfirmed = bcTaskService.allReceiveBrokerConfirmed(transactionId);
        if (!allReceiveBrokerConfirmed) return;
        // 更新临时交易池交易状态(已确认)
        BcTempFileTransaction transaction = bcTempFileTransactionService.updateStatusByTxId(transactionId);
        if (transaction == null) throw new RuntimeException("指定交易不存在");
        // 更新文件确认状态(已确认)
        fileService.updateFileStatus(transaction.getFileCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void synchronousData() {
        int pageNumber = 0;
        synchronousChain(pageNumber);
        synchronousBlock(pageNumber);
        synchronousTransaction(pageNumber);
    }

    private void synchronousChain(int pageNumber) {
        String data = getResult("/blockchain/getChains", pageNumber);
        if (StringUtils.isEmpty(data)) return;
        List bcList = JSONObject.parseArray(data).toJavaList(Object.class);
        if (ObjectUtils.isEmpty(bcList)) return;
        // 保存同步的数据
        syncResult("/blockchain/syncChains", data);
        if (bcList.size() <= pageSize) return;
        // 再次查询信息
        pageNumber++;
        synchronousChain(pageNumber);
    }

    private void synchronousBlock(int pageNumber) {
        String data = getResult("/blockchain/getBlocks", pageNumber);
        if (StringUtils.isEmpty(data)) return;
        List bcList = JSONObject.parseArray(data).toJavaList(Object.class);
        if (ObjectUtils.isEmpty(bcList)) return;
        // 保存同步的数据
        syncResult("/blockchain/syncBlocks", data);
        if (bcList.size() <= pageSize) return;
        // 再次查询信息
        pageNumber++;
        synchronousBlock(pageNumber);

    }

    private void synchronousTransaction(int pageNumber) {
        String data = getResult("/blockchain/getTxs", pageNumber);
        if (StringUtils.isEmpty(data)) return;
        List bcList = JSONObject.parseArray(data).toJavaList(Object.class);
        if (ObjectUtils.isEmpty(bcList)) return;
        // 保存同步的数据
        syncResult("/blockchain/syncTxs", data);
        if (bcList.size() <= pageSize) return;
        // 再次查询信息
        pageNumber++;
        synchronousTransaction(pageNumber);
    }


    /**
     * 获取数据
     *
     * @param url
     * @param pageNumber
     * @return
     */
    private String getResult(String url, int pageNumber) {
        ResponseEntity result = RestTemplateUtil.post( "http://" + orderChainServerPort + url, getParams( pageNumber ) );
        if (ObjectUtils.isEmpty( result ) | ObjectUtils.isEmpty( result.getBody() )) return "";
        Map map = MapUtil.object2Map( result.getBody() );
        if (ObjectUtils.isEmpty( map ) || ObjectUtils.isEmpty( map.get( "data" ) )) return "";
        // 请求的数据
        return JSONObject.toJSONString(MapUtil.object2Map(map.get("data")).get("content"));
    }

    /**
     * 同步数据
     *
     * @param url
     * @param data
     */
    private void syncResult(String url, String data) {
        Map<String, String> requestBody = new LinkedHashMap<>();
        requestBody.put( "data", data );
        RestTemplateUtil.post( "http://" + localChainServerPort + url, requestBody );
    }

    /**
     * 设置参数
     *
     * @param pageNumber
     * @return
     */
    private Map getParams(int pageNumber) {
        Map<String, String> requestBody = new LinkedHashMap<>();
        requestBody.put("pageSize", pageSize + "");
        requestBody.put("pageNumber", pageNumber + "");
        return requestBody;
    }

}
