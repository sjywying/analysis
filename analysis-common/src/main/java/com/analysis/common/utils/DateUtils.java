package com.analysis.common.utils;

import java.text.SimpleDateFormat;

public class DateUtils {

//	private static final SimpleDateFormat commondf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	private static final SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
	
	/**
	 * "yyyy-MM-dd HH:mm:ss"转换为"yyyyMMddHHmmss"
	 * 
	 * @param dateStr
	 * @return
	 * @throws RuntimeException
	 */
	public static String transition(String dateStr) throws RuntimeException {
		if(Strings.isEmpty(dateStr)) {
			throw new RuntimeException("date string is empty");
		}
		
		return dateStr.replace("-", "").replace(":", "").replace(" ", "");
	}
}
