package com.analysis.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.analysis.web.bean.Active;


public interface ActiveMapper {
    
	List<Active> selectByChannelAndModel(
			@Param("channel") String channel,
			@Param("model")String model);
}