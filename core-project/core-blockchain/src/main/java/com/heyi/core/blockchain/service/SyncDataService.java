package com.heyi.core.blockchain.service;

import com.heyi.core.common.PageQuery;
import com.heyi.core.common.PageResult;

import java.util.Map;

/**
 * @InterfaceName SyncDataService
 * @Description: TODO
 * @Author: lidong
 * @CreateDate: 2018/9/20$ 10:37 AM$
 * @UpdateUser: lidong
 * @UpdateDate: 2018/9/20$ 10:37 AM$
 * @UpdateRemark: TODO
 * @Version: 1.0
 **/
public interface SyncDataService {
    /**
     * 获取链的信息
     *
     * @param pageQuery
     * @return
     */
    PageResult getChains(PageQuery pageQuery);

    /**
     * 获取区块的信息
     *
     * @param pageQuery
     * @return
     */
    PageResult getBlocks(PageQuery pageQuery);


    /**
     * 获取交易的信息
     *
     * @param pageQuery
     * @return
     */
    PageResult getTxs(PageQuery pageQuery);

    /**
     * 保存数据
     *
     * @param data
     */
    void syncChains(Map data);

    /**
     * 保存数据
     *
     * @param data
     */
    void syncBlocks(Map data);


    /**
     * 保存数据
     *
     * @param data
     */
    void syncTxs(Map data);
}
