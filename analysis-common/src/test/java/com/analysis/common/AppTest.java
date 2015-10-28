package com.analysis.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.util.*;


public class AppTest {

	static List<String> aa = new ArrayList<String>();
	
	public static void main(String[] args) {
		char a = 'a';
		char z = 'z';

		char[] str = new char[26];
		for (int i = 0; i < str.length; i++) {
			str[i] = (char) ('a' + i);
		}

		for (int i = 0; i < str.length; i++) {
			char c = str[i];
			for (int j = 0; j < str.length; j++) {
				char c1 = str[j];
				for (int k = 0; k < str.length; k++) {
					char c2 = str[k];
//					for (int l = 0; l < str.length; l++) {
//						char c3 = str[l];
//						for (int m = 0; m < str.length; m++) {
//							char c4 = str[m];
//							for (int n = 0; n < str.length; n++) {
//								char c5 = str[n];
								isreg(""+c+c1+c2);
//								System.out.println(""+c+c1+c2+c3+c4+c5?);
//					System.out.println(""+c+c1+c2);
//							}
//						}
//					}
				}
			}
		}

		for (Iterator<String> iterator = aa.iterator(); iterator.hasNext(); ) {
			String next =  iterator.next();
			System.out.println(next);
		}
	}

	private static void isreg(String addr) {
		try {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Content content = Request.Get("http://whois.263.tw/weixinindex.php?domain="+addr+".com").execute().returnContent();
			if(!content.asString().contains("Registry Domain ID")) {
				System.out.println(addr);
				aa.add(addr);
			}

			if(content.asString().contains("Unable to connect to remote host")) {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
//abh	abk		ace  adu adw afd
	}
}
