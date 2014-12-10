package com.analysis.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.analysis.web.bean.ActiveNum;


public interface ActiveNumMapper {
    
	List<ActiveNum> selectByChannelAndModel(
			@Param("channel") String channel,
			@Param("model")String model);
}