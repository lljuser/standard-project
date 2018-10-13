package com.heyi.core.blockchain.repository.sql;

import com.heyi.core.blockchain.repository.sql.entity.SqlBlockChain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SqlBlockChainRepository extends JpaRepository<SqlBlockChain, Long> {
    SqlBlockChain findByIdentityCode(String identityCode);
}
