package com.analysis.common.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


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
	
	public static String getCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH )+1;
		
		return ""+year+month;
	}
	
	public static List<String> getMonthStartWith(String yyyymm) {
		List<String> yyyymms = new ArrayList<String>();
		
		boolean temp = true;
		int i = 0;
		while (temp) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, i);
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH)+1;
			
			
			String currentDate = "";
			if(month >= 10){
				currentDate = currentDate+year+month;
			} else {
				currentDate = year+"0"+month;
			}
			
			if(currentDate.equals(yyyymm) || i < -11) {
				temp = false;
			}
			
			yyyymms.add(currentDate);
			
			i--;
		}
		
		
		return yyyymms;
	}
	
//	public static void main(String[] args) {
//		System.out.println(getCurrentMonth());
//		
//		List<String> list = getMonthStartWith("201310");
//		
//		for (String str : list) {
//			System.out.println(str);
//		}
//	}
}
