package com.heyi.core.netty.mapper;

import com.heyi.core.netty.domain.OgProperty;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @描述 com.heyi.core.netty.mapper
 * @创建人 ljliu
 * @创建时间 2018/10/22
 * @修改人和其它信息
 */
@Repository
public interface OgPropertyMapper {
    OgProperty getPropertyByPropertyId(String propertyId);

    List<OgProperty> getPropertyByUserId(String userId);
}
