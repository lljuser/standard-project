package com.heyi.core.blockchain.service.impl.sql;

import com.alibaba.fastjson.JSONObject;
import com.heyi.core.blockchain.repository.sql.entity.SqlBlock;
import com.heyi.core.blockchain.repository.sql.entity.SqlBlockChain;
import com.heyi.core.blockchain.repository.sql.entity.SqlFileTransaction;
import com.heyi.core.blockchain.service.BlockService;
import com.heyi.core.blockchain.service.FileTransactionService;
import com.heyi.core.blockchain.service.SyncDataService;
import com.heyi.core.common.PageQuery;
import com.heyi.core.common.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

/**
 * @ClassName SyncDataServiceImpl
 * @Description: TODO
 * @Author: lidong
 * @CreateDate: 2018/9/20$ 10:38 AM$
 * @UpdateUser: lidong
 * @UpdateDate: 2018/9/20$ 10:38 AM$
 * @UpdateRemark: TODO
 * @Version: 1.0
 **/
@Service
public class SyncDataServiceImpl implements SyncDataService {

    @Autowired
    private BlockService blockService;

    @Autowired
    private FileTransactionService fileTransactionService;

    @Override
    public PageResult getChains(PageQuery pageQuery) {
        return blockService.getChains( pageQuery );
    }

    @Override
    public PageResult getBlocks(PageQuery pageQuery) {
        return blockService.getBlocks( pageQuery );
    }

    @Override
    public PageResult getTxs(PageQuery pageQuery) {
        return fileTransactionService.getAll( pageQuery );
    }

    @Override
    public void syncChains(Map data) {
        List<SqlBlockChain> result = getData( data, SqlBlockChain.class );
        result.stream().forEach( item -> blockService.saveOrUpdate( item ) );
    }

    @Override
    public void syncBlocks(Map data) {
        List<SqlBlock> result = getData( data, SqlBlock.class );
        result.stream().forEach( item -> blockService.saveOrUpdate( item ) );
    }

    @Override
    public void syncTxs(Map data) {
        List<SqlFileTransaction> result = getData( data, SqlFileTransaction.class );
        result.stream().forEach( item -> fileTransactionService.saveOrUpdate( item ) );
    }


    private List getData(Map data, Class clasz) {
        if (ObjectUtils.isEmpty( data.get( "data" ) )) return null;
        String result = data.get( "data" ).toString();
        return JSONObject.parseArray( result ).toJavaList( clasz );
    }
}
