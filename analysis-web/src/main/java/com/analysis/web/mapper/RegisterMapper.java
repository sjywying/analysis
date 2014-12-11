package com.analysis.web.mapper;

import org.apache.ibatis.annotations.InsertProvider;

import com.analysis.api.bean.Register;
import com.analysis.web.mapper.provider.RegisterProvider;


public interface RegisterMapper {
	
	@InsertProvider(type=RegisterProvider.class, method="insert")
	void insert(Register registe);
    
}