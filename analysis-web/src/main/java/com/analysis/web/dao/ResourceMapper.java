package com.analysis.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.analysis.web.bean.Resource;
import com.analysis.web.bean.ResourceExample;

public interface ResourceMapper {
//    int countByExample(ResourceExample example);
//    int deleteByExample(ResourceExample example);
//    int deleteByPrimaryKey(Long id);
//    int insert(Resource record);
//    int insertSelective(Resource record);
    List<Resource> selectByExample(ResourceExample example);
    Resource selectByPrimaryKey(Long id);
    List<Resource> selectByUserId(Long userId);
//    int updateByExampleSelective(@Param("record") Resource record, @Param("example") ResourceExample example);
//    int updateByExample(@Param("record") Resource record, @Param("example") ResourceExample example);
//    int updateByPrimaryKeySelective(Resource record);
//    int updateByPrimaryKey(Resource record);
}