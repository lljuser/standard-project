package com.heyi.core.blockchain.service;

import com.heyi.core.blockchain.repository.IBlockRepository;
import com.heyi.core.blockchain.repository.sql.entity.SqlBlock;
import com.heyi.core.blockchain.repository.sql.entity.SqlBlockChain;
import com.heyi.core.blockchain.storage.Block;
import com.heyi.core.common.PageQuery;
import com.heyi.core.common.PageResult;

import java.util.List;

public interface BlockService extends IBlockRepository {

    /**
     * 根据链查询所对应的区块
     *
     * @param identityCode
     * @param pageQuery
     * @return
     */
    PageResult getBlockByChain(String identityCode, PageQuery pageQuery);

    /**
     * 返回指定链在某个时间点之后的区块集合
     *
     * @param identityCode 链标识
     * @param timestamp    时间戳
     */
    List<Block> getBlockByChainAndTimestamp(String identityCode, long timestamp);

    /**
     * 获取给所有的链
     *
     * @param pageQuery
     * @return
     */
    PageResult getChains(PageQuery pageQuery);

    /**
     * 获取给所有的区块
     *
     * @param pageQuery
     * @return
     */
    PageResult getBlocks(PageQuery pageQuery);

    /**
     * 保存数据
     *
     * @param item
     */
    void saveOrUpdate(SqlBlockChain item);

    /**
     * 保存数据
     *
     * @param item
     */
    void saveOrUpdate(SqlBlock item);
}
