package com.analysis.web.mapper.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.analysis.api.bean.Registe;



public class ActiveNumMapper {
	
	private static final String TABLE_NAME_PREFIX = "biz_active_";
    
	public SQL insert(Map<String, Object> parameters) {
		Registe registe = (Registe) parameters.get("registe");
		String ctime = registe.getCtime().substring(0, 7);
		
		SQL sql = new SQL();
		sql.INSERT_INTO(TABLE_NAME_PREFIX + ctime)
			.VALUES("tid", registe.getTid())
			.VALUES("ctime", registe.getCtime())
			.VALUES("imsi", registe.getImsi())
			.VALUES("imei", registe.getImei())
			.VALUES("channel", registe.getC())
			.VALUES("model", registe.getM())
			.VALUES("av", registe.getAv())
			.VALUES("an", registe.getR())
			.VALUES("ip", registe.getIp())
			.VALUES("type", registe.getType().toString());
		
		return sql;
	}

}