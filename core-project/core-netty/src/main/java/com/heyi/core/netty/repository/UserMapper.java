package com.heyi.core.netty.repository;

import com.heyi.core.netty.domain.OgUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @描述 com.heyi.core.netty.repository
 * @创建人 ljliu
 * @创建时间 2018/10/15
 * @修改人和其它信息
 */
@Repository
public interface UserMapper {
    @Select("SELECT * FROM sys_oguser WHERE id=#{userId}")
    OgUser getUserByUserId(@Param("userId") String userId);

    @Insert("INSERT INTO sys_oguser(Id,WorkNo,UserName,Password,TelPhone," +
            "Email,OfficePhone,Domain,Sex,IsEnabled,Birthday,ShortMobile,PropertyId," +
            "PropertyName,PropertyNo,Duty,Avator,SortIndex,CreateTime,CreateUserName," +
            "CreateWorkNo,LastModifyTime,LastModifyUserName,LastModifyWorkNo) " +
            "VALUES(#{id},#{workNo},#{userName},#{password},#{telPhone}," +
            "#{email},#{officePhone},#{domain},#{sex},#{isEnabled},#{birthday}," +
            "#{shortMobile},#{propertyId},#{propertyName},#{propertyNo},#{duty}," +
            "#{avator},#{sortIndex},now(),#{createUserName},#{createWorkNo}," +
            "now(),#{lastModifyUserName},#{lastModifyWorkNo})")
    int addUser(OgUser user);
}
