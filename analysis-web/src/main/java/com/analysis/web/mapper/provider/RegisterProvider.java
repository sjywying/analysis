package com.analysis.web.mapper.provider;

import org.apache.ibatis.jdbc.SQL;

import com.analysis.api.bean.Register;
import com.analysis.common.constants.Constants;

public class RegisterProvider {
	
	private static final String TABLE_NAME_PREFIX = "biz_register_";
    
	public String insert(Register register) {
		//TODO 源头格式化
		String ctime = register.getCtime().substring(0, 7).replace("-", "");
		
		SQL sql = new SQL();
		sql.INSERT_INTO(TABLE_NAME_PREFIX + ctime)
			.VALUES("tid", Constants.SQL_SINGLE_QUOTES + register.getTid() + Constants.SQL_SINGLE_QUOTES)
			.VALUES("ctime", Constants.SQL_SINGLE_QUOTES + register.getCtime() + Constants.SQL_SINGLE_QUOTES)
			.VALUES("imsi", Constants.SQL_SINGLE_QUOTES + register.getImsi() + Constants.SQL_SINGLE_QUOTES)
			.VALUES("imei", Constants.SQL_SINGLE_QUOTES + register.getImei() + Constants.SQL_SINGLE_QUOTES)
			.VALUES("channel", Constants.SQL_SINGLE_QUOTES + register.getC() + Constants.SQL_SINGLE_QUOTES)
			.VALUES("model", Constants.SQL_SINGLE_QUOTES + register.getM() + Constants.SQL_SINGLE_QUOTES)
			.VALUES("ccode", Constants.SQL_SINGLE_QUOTES + register.getCcode() + Constants.SQL_SINGLE_QUOTES);
		
		if(register.getAv() != null) sql.VALUES("av", Constants.SQL_SINGLE_QUOTES + register.getAv() + Constants.SQL_SINGLE_QUOTES);
		if(register.getR() != null) sql.VALUES("an", Constants.SQL_SINGLE_QUOTES + register.getR() + Constants.SQL_SINGLE_QUOTES);
		if(register.getIp() != null) sql.VALUES("ip", Constants.SQL_SINGLE_QUOTES + register.getIp() + Constants.SQL_SINGLE_QUOTES);
		if(register.getType() != null) sql.VALUES("type", Constants.SQL_SINGLE_QUOTES + register.getType() + Constants.SQL_SINGLE_QUOTES);
		if(register.getPname() != null) sql.VALUES("pname", Constants.SQL_SINGLE_QUOTES + register.getPname() + Constants.SQL_SINGLE_QUOTES);
		
		return sql.toString();
	}
    
}