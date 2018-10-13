package com.heyi.core.blockchain.repository.sql.impl;

import com.heyi.core.blockchain.repository.sql.SqlBlockChainRepository;
import com.heyi.core.blockchain.repository.sql.SqlBlockRepository;
import com.heyi.core.blockchain.repository.sql.entity.SqlBlock;
import com.heyi.core.blockchain.repository.sql.entity.SqlBlockChain;
import com.heyi.core.blockchain.storage.Block;
import com.heyi.core.common.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SqlBlockAggregatorRepository {

    @Autowired
    SqlBlockRepository blockRepository;

    @Autowired
    SqlBlockChainRepository chainRepository;

    public String getLastBlockHash(String identity) {
        SqlBlockChain blockChain = chainRepository.findByIdentityCode( identity );
        if (blockChain == null) return null;
        return blockChain.getLastHash();
    }

    public void putLastBlockHash(String identity, String tipBlockHash) {
        SqlBlockChain blockChain = chainRepository.findByIdentityCode( identity );
        if (blockChain == null) {
            blockChain = new SqlBlockChain();
            blockChain.setIdentityCode( identity );
        }
        blockChain.setLastHash( tipBlockHash );
        chainRepository.save( blockChain );
    }

    public void putBlock(Block block) {
        SqlBlock sqlBlock = SqlBlock.castFrom( block );
        blockRepository.save( sqlBlock );
        // 外部逻辑已控制了区块链尾块逻辑，所以这里不考虑尾块逻辑
    }

    public Block getBlock(String blockHash) {
        SqlBlock sqlBlock = blockRepository.findByHash( blockHash );
        return SqlBlock.castTo( sqlBlock );
    }

    public Page<Block> getBlock(Block blockHash) {
        Pageable pageRequest = PageRequest.of( 0, 100, Sort.Direction.DESC, "timestamp" );
        Page<SqlBlock> result = blockRepository.findByHashOrIdentityCode( blockHash.getHash(), blockHash.getIdentityCode(), pageRequest );
        return result.map( SqlBlock::castTo );
    }

    public List<Block> getBlockByChainAfterTs(String identityCode, long timestamp) {
        try {
            List<SqlBlock> result = blockRepository.findAll( (Specification) (root, criteriaQuery, criteriaBuilder) -> {
                // 设置查询条件（按姓名查找相似的文件）
                List<Predicate> list = new ArrayList<>();
                list.add( criteriaBuilder.equal( root.get( "identityCode" ).as( String.class ), identityCode ) );
                list.add( criteriaBuilder.greaterThan( root.get( "timestamp" ).as( Long.class ), timestamp ) );
                return criteriaBuilder.and( list.toArray( new Predicate[list.size()] ) );

            }, Sort.by( Sort.Direction.ASC, "timestamp" ) );
            return result.stream().map( SqlBlock::castTo ).collect( Collectors.toList() );

        } catch (Exception e) {
            return null;
        }
    }


    public Page getBlockByChain(String identityCode, PageQuery pageQuery) {
        try {
            PageRequest pageRequest = PageRequest.of( pageQuery.getPageNumber(), pageQuery.getPageSize(), Sort.Direction.DESC, "timestamp" );
            Page<SqlBlock> result = blockRepository.findAll( (Specification) (root, criteriaQuery, criteriaBuilder) -> {
                // 设置查询条件（按姓名查找相似的文件）
                List<Predicate> list = new ArrayList<>();
                if (!StringUtils.isEmpty( identityCode )) {
                    list.add( criteriaBuilder.equal( root.get( "identityCode" ).as( String.class ), identityCode ) );
                }
                return criteriaBuilder.and( list.toArray( new Predicate[list.size()] ) );
            }, pageRequest );
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public Page getBlockChains(PageQuery pageQuery) {
        try {
            PageRequest pageRequest = PageRequest.of( pageQuery.getPageNumber(), pageQuery.getPageSize() );
            Page<SqlBlockChain> result = chainRepository.findAll( pageRequest );
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public void saveOrUpdate(SqlBlockChain item) {
        try {
            SqlBlockChain data = chainRepository.findByIdentityCode( item.getIdentityCode() );
            if (ObjectUtils.isEmpty( data ))
                chainRepository.save( item );
        } catch (Exception e) {
        }
    }

    public void saveOrUpdate(SqlBlock item) {
        try {
            SqlBlock data = blockRepository.findByHash( item.getHash() );
            if (ObjectUtils.isEmpty( data ))
                blockRepository.save( item );
        } catch (Exception e) {
        }
    }
}
