package com.analysis.web.mapper.provider;

import org.apache.ibatis.jdbc.SQL;

import com.analysis.api.bean.ConfigRegActive;
import com.analysis.common.constants.Constants;

public class ConfigRegActiveProvider {
	
	private static final String TABLE_NAME_PREFIX = "biz_configregactive_";
    
	public String insert(ConfigRegActive bean) {
		String ctime = bean.getCtime().substring(0, 6);
		
		SQL sql = new SQL();
		sql.INSERT_INTO(TABLE_NAME_PREFIX + ctime)
			.VALUES("tid", Constants.SQL_SINGLE_QUOTES + bean.getTid() + Constants.SQL_SINGLE_QUOTES)
			.VALUES("ctime", Constants.SQL_SINGLE_QUOTES + bean.getCtime() + Constants.SQL_SINGLE_QUOTES)
			.VALUES("imsi", Constants.SQL_SINGLE_QUOTES + bean.getImsi() + Constants.SQL_SINGLE_QUOTES)
			.VALUES("imei", Constants.SQL_SINGLE_QUOTES + bean.getImei() + Constants.SQL_SINGLE_QUOTES)
			.VALUES("channel", Constants.SQL_SINGLE_QUOTES + bean.getC() + Constants.SQL_SINGLE_QUOTES)
			.VALUES("model", Constants.SQL_SINGLE_QUOTES + bean.getM() + Constants.SQL_SINGLE_QUOTES)
			.VALUES("ccode", Constants.SQL_SINGLE_QUOTES + bean.getCcode() + Constants.SQL_SINGLE_QUOTES);
		
		if(bean.getAv() != null) sql.VALUES("av", Constants.SQL_SINGLE_QUOTES + bean.getAv() + Constants.SQL_SINGLE_QUOTES);
		if(bean.getAn() != null) sql.VALUES("an", Constants.SQL_SINGLE_QUOTES + bean.getAn() + Constants.SQL_SINGLE_QUOTES);
		if(bean.getIp() != null) sql.VALUES("ip", Constants.SQL_SINGLE_QUOTES + bean.getIp() + Constants.SQL_SINGLE_QUOTES);
		if(bean.getType() != null) sql.VALUES("type", Constants.SQL_SINGLE_QUOTES + bean.getType() + Constants.SQL_SINGLE_QUOTES);
		if(bean.getPname() != null) sql.VALUES("pname", Constants.SQL_SINGLE_QUOTES + bean.getPname() + Constants.SQL_SINGLE_QUOTES);
		
		return sql.toString();
	}
	
}