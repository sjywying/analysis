package com.analysis.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.analysis.web.bean.Role;
import com.analysis.web.bean.RoleExample;

public interface RoleMapper {
//    int countByExample(RoleExample example);
//    int deleteByExample(RoleExample example);
//    int deleteByPrimaryKey(Long id);
//    int insert(Role record);
//    int insertSelective(Role record);
//    List<Role> selectByExample(RoleExample example);
    Role selectByPrimaryKey(Long id);
//    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);
//    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);
//    int updateByPrimaryKeySelective(Role record);
//    int updateByPrimaryKey(Role record);
}