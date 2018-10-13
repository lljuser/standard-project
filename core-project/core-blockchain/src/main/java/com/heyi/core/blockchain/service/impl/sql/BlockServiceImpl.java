package com.heyi.core.blockchain.service.impl.sql;

import com.heyi.core.blockchain.repository.sql.entity.SqlBlock;
import com.heyi.core.blockchain.repository.sql.entity.SqlBlockChain;
import com.heyi.core.blockchain.repository.sql.impl.SqlBlockAggregatorRepository;
import com.heyi.core.blockchain.service.BlockService;
import com.heyi.core.blockchain.storage.Block;
import com.heyi.core.common.PageQuery;
import com.heyi.core.common.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class BlockServiceImpl implements BlockService {

    @Autowired
    private SqlBlockAggregatorRepository repository;

    @Override
    public String getLastBlockHash(String identity) {
        return repository.getLastBlockHash( identity );
    }

    @Override
    public void putLastBlockHash(String identity, String tipBlockHash) {
        repository.putLastBlockHash( identity, tipBlockHash );
    }

    @Override
    public void putBlock(Block block) {
        repository.putBlock( block );
    }

    @Override
    public Block getBlock(String blockHash) {
        return repository.getBlock( blockHash );
    }

    @Override
    public PageResult<Block> getBlock(Block blockHash) {
        Page result = repository.getBlock( blockHash );
        return ObjectUtils.isEmpty( result ) ? null : PageResult.build( result );
    }

    @Override
    public PageResult getBlockByChain(String identityCode, PageQuery pageQuery) {
        Page result = repository.getBlockByChain( identityCode, pageQuery );
        return ObjectUtils.isEmpty( result ) ? null : PageResult.build( result );
    }

    @Override
    public List<Block> getBlockByChainAndTimestamp(String identityCode, long timestamp) {
        return repository.getBlockByChainAfterTs( identityCode, timestamp );
    }

    @Override
    public PageResult getChains(PageQuery pageQuery) {
        Page<SqlBlockChain> result = repository.getBlockChains( pageQuery );
        if (ObjectUtils.isEmpty( result )) return null;
        if (ObjectUtils.isEmpty( result.getContent() )) return null;
        return PageResult.build( result );
    }

    @Override
    public PageResult getBlocks(PageQuery pageQuery) {
        Page<SqlBlockChain> result = repository.getBlockByChain( "", pageQuery );
        if (ObjectUtils.isEmpty( result )) return null;
        if (ObjectUtils.isEmpty( result.getContent() )) return null;
        return PageResult.build( result );
    }

    @Override
    public void saveOrUpdate(SqlBlockChain item) {
        repository.saveOrUpdate( item );
    }

    @Override
    public void saveOrUpdate(SqlBlock item) {
        repository.saveOrUpdate( item );
    }
}
