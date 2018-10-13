package com.heyi.core.blockchain.service.impl.sql;

import com.heyi.core.blockchain.repository.sql.SqlFileTransactionRepository;
import com.heyi.core.blockchain.repository.sql.entity.SqlFileTransaction;
import com.heyi.core.blockchain.service.FileTransactionService;
import com.heyi.core.blockchain.transaciton.FileTransation;
import com.heyi.core.common.PageQuery;
import com.heyi.core.common.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class FileTransactionServiceImpl implements FileTransactionService {

    @Autowired
    private SqlFileTransactionRepository repository;

    public FileTransation getTransactionByTxId(String txId) {
        SqlFileTransaction sqlTrans = repository.findByTxId( txId );
        return SqlFileTransaction.castTo( sqlTrans );
    }

    public PageResult<FileTransation> findAllByBlockHash(String blockHash, PageQuery pageQuery) {
        Pageable pageable = PageRequest.of( pageQuery.getPageNumber(), pageQuery.getPageSize(), Sort.Direction.DESC, ("timestamp") );
        Page<FileTransation> page = repository.findAllByBlockHash( blockHash, pageable ).map( SqlFileTransaction::castTo );
        return PageResult.build( page );
    }

    public PageResult<SqlFileTransaction> getAll(PageQuery pageQuery) {
        Pageable pageable = PageRequest.of( pageQuery.getPageNumber(), pageQuery.getPageSize(), Sort.Direction.DESC, ("timestamp") );
        Page<SqlFileTransaction> page = repository.findAll( pageable );
        return PageResult.build( page );
    }

    public PageResult<SqlFileTransaction> findByIdentityCode(String identityCode, PageQuery pageQuery) {
        Pageable pageable = PageRequest.of( pageQuery.getPageNumber(), pageQuery.getPageSize(), Sort.Direction.DESC, ("timestamp") );
        Page<SqlFileTransaction> page = repository.findByIdentityCode( identityCode, pageable );
        return PageResult.build( page );
    }

    public void saveOrUpdate(SqlFileTransaction item) {
        try {
            SqlFileTransaction data = repository.findByBlockHash( item.getBlock().getHash() );
            if (ObjectUtils.isEmpty( data ))
                repository.save( item );
        } catch (Exception e) {
        }
    }
}
