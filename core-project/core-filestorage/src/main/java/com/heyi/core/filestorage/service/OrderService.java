package com.heyi.core.filestorage.service;

import com.heyi.core.filestorage.entity.BcTempFileTransaction;

import javax.servlet.http.HttpServletRequest;

/**
 * @InterfaceName OrderService
 * @Description: TODO
 * @Author: lidong
 * @CreateDate: 2018/8/30$ 9:55 AM$
 * @UpdateUser: lidong
 * @UpdateDate: 2018/8/30$ 9:55 AM$
 * @UpdateRemark: TODO
 * @Version: 1.0
 **/
public interface OrderService {

    /**
     * 处理交易信息
     *
     * @param transaction
     */
    boolean dispatchTrans(BcTempFileTransaction transaction, HttpServletRequest request) throws Exception;

    /**
     * 排序节点（最终）确认交易
     *
     * @param transactionId
     * @param receiver
     * @throws Exception
     */
    void finalConfirmFileTransaction(String transactionId, String receiver) throws Exception;

    /**
     * 从order节点同步数据到local
     */
    void synchronousData();
}
