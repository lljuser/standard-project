package com.heyi.core.netty.mapper;

import com.heyi.core.netty.domain.OgProperty;
import com.heyi.core.netty.domain.OgUser;
import com.heyi.core.netty.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @描述 com.heyi.core.netty.mapper
 * @创建人 ljliu
 * @创建时间 2018/10/16
 * @修改人和其它信息
 */
@Repository
public interface OgUserMapper {
    OgUser getUserByUserId(String userId);
    OgUser getLastUser();

    List<OgUser> getAll();

    List<OgProperty> getPropertiesByUserId(String userId);

    Integer saveUser(OgUser user);

    Integer updateUser(OgUser user);

    Integer removeUser(String id);

    Integer removeUser(OgUser user);

    Integer removeUserByWorkNoAndUserName(@Param("userName") String userId,@Param("workNo")String workNo);

    Integer removeUserByWorkNoAndUserName(OgUser user);
}
