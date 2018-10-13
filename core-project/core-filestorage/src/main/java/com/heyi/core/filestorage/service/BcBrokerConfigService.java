package com.heyi.core.filestorage.service;

import com.heyi.core.filestorage.entity.BcBrokerConfig;

import java.util.List;

public interface BcBrokerConfigService {

    // 保存节点配置
    Object saveBcBrokerConfig(BcBrokerConfig bcBrokerConfig);

    // 删除节点配置
    Boolean deleteBcBrokerConfig(Integer id);

    // 修改节点配置
    Object updateBcBrokerConfig(BcBrokerConfig bcBrokerConfig);

    // 查找节点配置
    List<BcBrokerConfig> selectBcBrokerConfig(String identityCode, short type);

    // 查找节点配置
    List<String> selectBcBrokerConfigs(String identityCode, short type);
}
