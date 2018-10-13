package com.heyi.core.filestorage.repository;

import com.heyi.core.filestorage.entity.BcBrokerConfig;
import com.heyi.core.filestorage.entity.BcTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface BcBrokerConfigRepositroy extends JpaRepository<BcBrokerConfig, Integer> {
    /**
     *  更新配置
     * @param
     * @param
     * @param
     * @return
     */
    @Transactional
    @Query(value = "update BcBrokerConfig b set b.identityCode=:identityCode, b.subscriber=:subscriber where b.id=:id ")
    @Modifying
    int updateBcBrokerConfig(@Param("id") Integer id, @Param("identityCode") String identityCode, @Param("subscriber") String subscriber);


    List<BcBrokerConfig> findByIdentityCodeAndType(String identityCode, short type);

}
