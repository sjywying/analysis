package com.analysis.collect.plugins.source.interceptor;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class TestUrl {

	public static void main(String[] args) {
//		String url = "/ypsearch//websearch/?model=AOSPonMako&av=APKPRJ100_1.0.0.92_20141205&an=w-Android-gen-768x1184&adccompany=100002&pkgname=com.ishow.client.android.plugin.company&imsi=460022105540226&imei=353918052819543&tid=e353918052819543&cityid=110000&isSale=1&lat=39.919417&lng=116.378939&wd=%E5%95%86%E5%9C%BA&selectedCityId=110000";
//		try {
//			System.out.println(URLDecoder.decode(url,"utf-8"));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		char spe = '\t';
		String nginxlog = "218.240.33.174	-	05/Dec/2014:15:51:00 +0800	GET /ypsearch/urldeal?model=2&adccompany=2&av=2&pkgname=2&imsi=2&imei=2&cityid=5&lat=1&lng=1&m=zhaopin HTTP/1.1	404	571	-     Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36";
		String[] arr = nginxlog.split("\t");
		for (String str : arr) {
			System.out.println(str);
		}
	}
}
