package com.heyi.core.filestorage.service;

import com.heyi.core.common.PageQuery;
import com.heyi.core.common.PageResult;
import com.heyi.core.filestorage.entity.BcTempFileTransaction;

import java.util.Optional;


/**
 * @ClassName BcTempFileTransactionService
 * @Description: TODO
 * @Author: lidong
 * @CreateDate: 2018/8/29$ 9:44 AM$
 * @UpdateUser: lidong
 * @UpdateDate: 2018/8/29$ 9:44 AM$
 * @UpdateRemark: TODO
 * @Version: 1.0
 **/
public interface BcTempFileTransactionService {

    /**
     * 保存临时交易、更新临时交易
     *
     * @param data
     */
    boolean save(BcTempFileTransaction data);

    /**
     * 删除临时交易
     *
     * @param id
     */
    boolean delete(Integer id);

    /**
     * 未确认的交易列表
     *
     * @param status
     */
    PageResult<BcTempFileTransaction> findAllByStatusAndChainCode(short status, String chainCode, PageQuery pageQuery);

    /**
     * 获取交易信息
     *
     * @param txId
     * @return
     */
    Optional<BcTempFileTransaction> getTransaction(String txId);

    /**
     * 通过ID修改状态
     *
     * @param id 交易编码
     * @return
     */
    BcTempFileTransaction updateStatusByTxId(String id);

    /**
     * 查询交易信息（包括了文件信息）
     *
     * @param txId
     * @return
     */
    Object getTxInfo(String txId);

    /**
     * 打包临时交易
     */
    void packTransactions();

    /**
     * 根据filecode查找信息
     *
     * @param fileCode
     * @return
     */
    BcTempFileTransaction getByFileCode(String fileCode);
}
