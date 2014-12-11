package com.analysis.web.mapper;

import org.apache.ibatis.annotations.InsertProvider;

import com.analysis.api.bean.RegActive;
import com.analysis.web.mapper.provider.RegActiveProvider;


public interface RegActiveMapper {
    
	@InsertProvider(type=RegActiveProvider.class, method="insert")
	void insert(RegActive bean);
	
}