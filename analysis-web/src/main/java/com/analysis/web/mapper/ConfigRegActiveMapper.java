package com.analysis.web.mapper;

import org.apache.ibatis.annotations.InsertProvider;

import com.analysis.api.bean.ConfigRegActive;
import com.analysis.web.mapper.provider.ConfigRegActiveProvider;


public interface ConfigRegActiveMapper {
	
	@InsertProvider(type=ConfigRegActiveProvider.class, method="insert")
	void insert(ConfigRegActive bean);
    
}