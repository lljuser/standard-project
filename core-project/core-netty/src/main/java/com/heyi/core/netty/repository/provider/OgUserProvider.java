package com.heyi.core.netty.repository.provider;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * 统一管理SQL
 * **/
public class OgUserProvider {
    public String getUserByWorkNoAndUserName(@Param("workNo") String workNo,@Param("userName") String userName){
         String sql =new SQL(){{
             SELECT("*");
             FROM("sys_oguser");
             WHERE("WorkNo=#{workNo} and UserName=#{userName}");
         }}.toString();

         return sql.toString();

         /* return "SELECT * FROM sys_oguser WHERE " +
                  "WorkNo=#{workNo} and UserName=#{userName}";*/
    }
}
