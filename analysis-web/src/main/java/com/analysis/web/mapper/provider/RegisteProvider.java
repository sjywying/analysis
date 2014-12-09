package com.analysis.web.mapper.provider;

import org.apache.ibatis.jdbc.SQL;

import com.analysis.api.bean.Registe;
import com.analysis.common.constants.Constants;

public class RegisteProvider {
	
	private static final String TABLE_NAME_PREFIX = "biz_registe_";
    
	public String insert(Registe registe) {
//		Registe registe = (Registe) parameters.get("registe");
		String ctime = registe.getCtime().substring(0, 7).replace("-", "");
		
		SQL sql = new SQL();
		sql.INSERT_INTO(TABLE_NAME_PREFIX + ctime)
			.VALUES("tid", Constants.SQL_SINGLE_QUOTES + registe.getTid() + Constants.SQL_SINGLE_QUOTES)
			.VALUES("ctime", Constants.SQL_SINGLE_QUOTES + registe.getCtime() + Constants.SQL_SINGLE_QUOTES)
			.VALUES("imsi", Constants.SQL_SINGLE_QUOTES + registe.getImsi() + Constants.SQL_SINGLE_QUOTES)
			.VALUES("imei", Constants.SQL_SINGLE_QUOTES + registe.getImei() + Constants.SQL_SINGLE_QUOTES)
			.VALUES("channel", Constants.SQL_SINGLE_QUOTES + registe.getC() + Constants.SQL_SINGLE_QUOTES)
			.VALUES("model", Constants.SQL_SINGLE_QUOTES + registe.getM() + Constants.SQL_SINGLE_QUOTES);
		
		if(registe.getAv() != null) sql.VALUES("av", Constants.SQL_SINGLE_QUOTES + registe.getAv() + Constants.SQL_SINGLE_QUOTES);
		if(registe.getR() != null) sql.VALUES("an", Constants.SQL_SINGLE_QUOTES + registe.getR() + Constants.SQL_SINGLE_QUOTES);
		if(registe.getIp() != null) sql.VALUES("ip", Constants.SQL_SINGLE_QUOTES + registe.getIp() + Constants.SQL_SINGLE_QUOTES);
		if(registe.getType() != null) sql.VALUES("type", Constants.SQL_SINGLE_QUOTES + registe.getType() + Constants.SQL_SINGLE_QUOTES);
		
		return sql.toString();
	}
    
}