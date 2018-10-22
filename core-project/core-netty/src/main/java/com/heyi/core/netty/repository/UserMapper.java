package com.heyi.core.netty.repository;

import com.heyi.core.netty.domain.OgUser;
import com.heyi.core.netty.domain.TestUser;
import com.heyi.core.netty.repository.provider.OgUserProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @描述 com.heyi.core.netty.repository
 * @创建人 ljliu
 * @创建时间 2018/10/15
 * @修改人和其它信息
 */
@Repository
//@CacheNamespace
public interface UserMapper {
    @Select("SELECT * FROM sys_oguser")
    @Results(id="simpleOgUserMapping",value = {
        @Result(property = "id",column = "Id",id=true),
        @Result(property = "workNo",column = "WorkNo"),
        @Result(property = "userName",column = "UserName")
    })
    List<OgUser> getAll();

    @Select("SELECT * FROM sys_oguser WHERE id=#{userId}")
    OgUser getUserByUserId(@Param("userId") String userId);

    @ResultMap("simpleOgUserMapping")
    @Select("SELECT * FROM sys_oguser order by CreateTime DESC limit 1")
    OgUser getLastUser();

    @Insert({"INSERT INTO sys_oguser(Id,WorkNo,UserName,Password,TelPhone,",
            "Email,OfficePhone,Domain,Sex,IsEnabled,Birthday,ShortMobile,PropertyId,",
            "PropertyName,PropertyNo,Duty,Avator,SortIndex,CreateTime,CreateUserName,",
            "CreateWorkNo,LastModifyTime,LastModifyUserName,LastModifyWorkNo) ",
            "VALUES(#{id},#{workNo},#{userName},#{password},#{telPhone},",
            "#{email},#{officePhone},#{domain},#{sex},#{isEnabled},#{birthday},",
            "#{shortMobile},#{propertyId},#{propertyName},#{propertyNo},#{duty},",
            "#{avator},#{sortIndex},now(),#{createUserName},#{createWorkNo},",
            "now(),#{lastModifyUserName},#{lastModifyWorkNo})"})
    int addUser(OgUser user);

    @Update("UPDATE sys_oguser SET WorkNo=#{workNo},UserName=#{userName},Password=#{password}," +
            "TelPhone=#{telPhone},Email=#{email},OfficePhone=#{officePhone},Domain=#{domain}," +
            "Sex=#{sex},IsEnabled=#{isEnabled},Birthday=#{birthday},ShortMobile=#{shortMobile}," +
            "PropertyId=#{propertyId},PropertyName=#{propertyName},PropertyNo=#{propertyNo},Duty=#{duty}," +
            "Avator=#{avator},SortIndex=#{sortIndex},LastModifyTime=now(),nick_name=#{nickName} WHERE id=#{id}")
    Integer updateUser(OgUser user);

    @Delete("DELETE FROM sys_oguser WHERE id=#{id}")
    Integer removeUser(String id);

    @Delete("DELETE FROM sys_oguser WHERE WorkNo=#{workNo} and UserName=#{userName}")
    Integer removeUserByWorkNoAndUserName(@Param("userName") String userId,@Param("workNo")String workNo);


    @Insert({"INSERT INTO t_user(name,age,birthday,address,createtime)",
            "VALUES(#{name},#{age},#{birthday},#{address},now())"})
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "Id")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()",
            keyProperty = "id",
            keyColumn = "id",
            resultType = Integer.class,
            before = false)
    int addTestUser(TestUser user);



    @SelectProvider(type = OgUserProvider.class,method = "getUserByWorkNoAndUserName")
    OgUser getUserByWorkNoAndUserName(@Param("workNo") String workNo,@Param("userName") String userName);

    @Select({"SELECT * FROM sys_oguser WHERE",
            "WorkNo=#{workNo} and UserName=#{userName}"})
    OgUser getUserByWorkNoAndUserName2();
}
