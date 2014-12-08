package com.analysis.web.mapper.provider;

import org.apache.ibatis.jdbc.SQL;

import com.analysis.api.bean.Registe;

public class RegisteProvider {
	
	private static final String TABLE_NAME_PREFIX = "biz_registe_";
    
	public String insert(Registe registe) {
//		Registe registe = (Registe) parameters.get("registe");
		String ctime = registe.getCtime().substring(0, 7).replace("-", "");
		
		SQL sql = new SQL();
		sql.INSERT_INTO(TABLE_NAME_PREFIX + ctime)
			.VALUES("tid", registe.getTid())
			.VALUES("ctime", registe.getCtime())
			.VALUES("imsi", registe.getImsi())
			.VALUES("imei", registe.getImei())
			.VALUES("channel", registe.getC())
			.VALUES("model", registe.getM());
		
		if(registe.getAv() != null) sql.VALUES("av", registe.getAv());
		if(registe.getR() != null) sql.VALUES("an", registe.getR());
		if(registe.getIp() != null) sql.VALUES("ip", registe.getIp());
		if(registe.getType() != null) sql.VALUES("type", registe.getType().toString());
		
		return sql.toString();
	}
    
}