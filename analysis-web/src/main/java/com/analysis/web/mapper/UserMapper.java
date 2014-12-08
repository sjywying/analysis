package com.analysis.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.analysis.web.bean.User;

public interface UserMapper {
	
	@ResultMap("UserMap")
	@Select("select id, username, channel, email, phone, password, salt, create_date, status, is_deleted, is_admin from sys_user where is_deleted = 0 and username = #{username}")
    List<User> selectByUsername(@Param("username") String username);
    
	@ResultMap("UserMap")
	@Select("select id, username, channel, email, phone, password, salt, create_date, status, is_deleted, is_admin from sys_user where is_deleted = 0")
    List<User> selectAll();
    
    @ResultMap("UserMap")
	@Select("select id, username, channel, email, phone, password, salt, create_date, status, is_deleted, is_admin from sys_user where is_deleted = 0 and id = #{id}")
    User selectByPrimaryKey(@Param("id") Long id);
    
//    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);
//    int updateByExample(@Param("record") User record, @Param("example") UserExample example);
//    int updateByPrimaryKeySelective(User record);
//    int updateByPrimaryKey(User record);
//    int countByExample(UserExample example);
//    int deleteByExample(UserExample example);
//    int deleteByPrimaryKey(Long id);
//    int insert(User record);
//    int insertSelective(User record);
}