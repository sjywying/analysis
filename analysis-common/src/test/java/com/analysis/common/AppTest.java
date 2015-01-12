package com.analysis.common;

import java.util.TreeMap;


public class AppTest {
	
//	
//	public static void main(String[] args) {
//		String log = "1123,234";
//		System.out.println(log.substring(0, log.indexOf(',')));
//	}
//	
	
	public static void main(String[] args) {
		TreeMap<String, String> tmap = new TreeMap<String, String>();
		tmap.put("1", "1111");
		tmap.put("2", "2222");
		System.out.println(tmap.firstEntry().getValue());
	}
}
