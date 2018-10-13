package com.heyi.core.filestorage.service.impl;

import com.heyi.core.filestorage.entity.BcBrokerConfig;
import com.heyi.core.filestorage.repository.BcBrokerConfigRepositroy;
import com.heyi.core.filestorage.service.BcBrokerConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BcBrokerConfigImpl implements BcBrokerConfigService {

    @Autowired
    private BcBrokerConfigRepositroy bcBrokerConfigRepositroy;

    /**
     * 保存节点配置
     *
     * @param bcBrokerConfig
     * @return
     */
    @Override
    public Object saveBcBrokerConfig(BcBrokerConfig bcBrokerConfig) {
        return bcBrokerConfigRepositroy.save( bcBrokerConfig );
    }

    /**
     * 删除节点配置
     *
     * @param id
     * @return
     */
    @Override
    public Boolean deleteBcBrokerConfig(Integer id) {
        try {
            bcBrokerConfigRepositroy.deleteById( id );
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 修改节点配置
     *
     * @param bcBrokerConfig
     * @return
     */
    @Override
    public Object updateBcBrokerConfig(BcBrokerConfig bcBrokerConfig) {
        Integer id = bcBrokerConfig.getId();
        String IdentityCode = bcBrokerConfig.getIdentityCode();
        String Subscriber = bcBrokerConfig.getSubscriber();
        int count = bcBrokerConfigRepositroy.updateBcBrokerConfig( id, IdentityCode, Subscriber );
        return count;
    }

    /**
     * 查看节点配置
     *
     * @param identityCode
     * @param type
     * @return
     */
    @Override
    public List<BcBrokerConfig> selectBcBrokerConfig(String identityCode, short type) {
        return bcBrokerConfigRepositroy.findByIdentityCodeAndType( identityCode, type );
    }

    /**
     * 查看节点配置
     *
     * @param identityCode
     * @param type
     * @return
     */
    @Override
    public List<String> selectBcBrokerConfigs(String identityCode, short type) {
        try {
            return bcBrokerConfigRepositroy.findByIdentityCodeAndType( identityCode, type ).stream().map( BcBrokerConfig::getSubscriber ).collect( Collectors.toList() );
        } catch (Exception e) {
            return null;
        }
    }

}
