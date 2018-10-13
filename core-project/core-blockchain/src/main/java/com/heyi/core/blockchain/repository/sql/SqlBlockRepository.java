package com.heyi.core.blockchain.repository.sql;

import com.heyi.core.blockchain.repository.sql.entity.SqlBlock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SqlBlockRepository extends JpaRepository<SqlBlock, Long>, JpaSpecificationExecutor<SqlBlock> {
    SqlBlock findByHash(String hash);

    Page<SqlBlock> findByHashOrIdentityCode(String hash, String identityCode, Pageable pageRequest);
}
