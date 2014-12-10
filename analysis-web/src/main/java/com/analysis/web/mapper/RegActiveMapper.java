package com.analysis.web.mapper;

import org.apache.ibatis.annotations.InsertProvider;

import com.analysis.api.bean.Active;
import com.analysis.web.mapper.provider.RegActiveProvider;


public interface RegActiveMapper {
    
	@InsertProvider(type=RegActiveProvider.class, method="insert")
	void insert(Active bean);
	
}