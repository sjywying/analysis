package com.analysis.calculate.nginx;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.analysis.calculate.common.metadata.NginxConstants;


public class NginxLogParser {
	
//	public static void main(String[] args) {
//		String log = "218.240.33.174 - - [09/Dec/2014:16:05:00 +0800] \"GET /ypsearch/urldeal?tid=e862845027110043&model=M351&adccompany=100002&av=APKPRJ100_1.0.0.7_20141205&pkgname=com.ishow.client.android.plugin.company&imsi=460001281849070&imei=862845027110043&an=w-Android-gen-1080x1800&cityId=110000&lat=39.919409&lng=116.378711&m=guahao HTTP/1.1\" 302 0 \"-\" \"Mozilla/5.0 (Linux; U; Android 4.2.1; zh-cn; M351 Build/JOP40D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30	1234567890\" \"-\"";
//		Map<String, String> map = NginxLogParser.parser(log);
//		
//		String[] urlarr = log.split("\" \"");
//		System.out.println(urlarr[1]);
//	}
	
	private static final SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.US);
	private static final SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
	

	public static Map<String, String> parser(String nginxlogStr) throws ParseException {
		String[] arr = nginxlogStr.split(" ");
		
		Map<String, String> urlmap = parserUrl(arr[6]);
		urlmap.put(NginxConstants.NGINX_REQUEST_PARAMS_IP, arr[0]);
		
		String ctime = yyyyMMddHHmmss.format(df.parse(arr[3].substring(1)));
		urlmap.put(NginxConstants.NGINX_REQUEST_PARAMS_CTIME, ctime);
		
//		user-agent
		int beginIndex	= nginxlogStr.indexOf("\" \"");
		int endIndex	= nginxlogStr.lastIndexOf("\" \"");
		String ua = nginxlogStr.substring(beginIndex+3, endIndex);
		int tabindex = ua.lastIndexOf('-');
		String tidmd5 = ua.substring(tabindex+1, ua.length());
		urlmap.put(NginxConstants.NGINX_REQUEST_PARAMS_USERAGENT, tidmd5);
		
        String type = urlmap.get(NginxConstants.NGINX_REQUEST_PARAMS_TYPE);
		if(type == null || "".equals(type)) {
			urlmap.put(NginxConstants.NGINX_REQUEST_PARAMS_TYPE, "0");
		}
		
		
		
        return urlmap;
	}
	
	private static Map<String, String> parserUrl(String url) {
		Map<String, String> map = new HashMap<String, String>();
		
		int index = url.indexOf("?");
		if (index == -1) return null;
		
		String uri = url.substring(0, index);
		map.put(NginxConstants.NGINX_REQUEST_URI, uri);
		
		String paramsUrl = url.substring(index+1);
		String[] paramsArr = paramsUrl.split("&");
		for (String param : paramsArr) {
			String[] paramArr = param.split("=");
			if(paramArr != null && paramArr.length == 2) {
				map.put(paramArr[0].toLowerCase().trim(), paramArr[1]);
			}
		}
		
		return map;
	}
	
}
