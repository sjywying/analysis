package com.winksi.nginx.topology;

import java.util.HashMap;
import java.util.Map;

import com.winksi.calculate.common.metadata.Constants;

public class NginxLogParser {
	
//	private static final SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.US);

	public static Map<String, String> parser(String nginxlogStr) {
		String[] arr = nginxlogStr.split(" ");
		
		Map<String, String> urlmap = parserUrl(arr[6]);
		urlmap.put(NginxConstants.NGINX_REQUEST_PARAMS_IP, arr[0]);
		urlmap.put(NginxConstants.NGINX_REQUEST_PARAMS_CTIME, arr[3].substring(1) + arr[4].substring(0, arr[4].length() - 1));
		
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
		
		String paramsUrl = url.substring(index+1);
		String[] paramsArr = paramsUrl.split("&");
		for (String param : paramsArr) {
			String[] paramArr = param.split("=");
			if(paramArr != null && paramArr.length == 2) {
				map.put(paramArr[0], paramArr[1]);
			}
		}
		
		return map;
	}
	
}
