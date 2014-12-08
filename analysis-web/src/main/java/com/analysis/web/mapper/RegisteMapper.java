package com.analysis.web.mapper;

import org.apache.ibatis.annotations.InsertProvider;

import com.analysis.api.bean.Registe;
import com.analysis.web.mapper.provider.RegisteProvider;


public interface RegisteMapper {
	
//	@Insert("insert into biz_registe_#{tableNameMonth} (tid,ctime,imsi,imei,channel,model,av,an,ip,type) values (#{tid},#{ctime},#{imsi},#{imei},#{c},#{m}),#{av},#{r},#{ip},#{type}")
	@InsertProvider(type=RegisteProvider.class, method="insert")
	void insert(Registe registe);
    
}