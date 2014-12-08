package com.analysis.nginx.active.topology;

import java.util.HashMap;
import java.util.Map;

import com.analysis.calculate.active.ActiveBean;
import com.analysis.calculate.common.metadata.Constants;

public class NginxLogParser {
	
//	private static final SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.US);
	
	public static ActiveBean parser(String nginxlogStr) {
		ActiveBean bean = new ActiveBean();
		
		String[] arr = nginxlogStr.split(" ");
		Map<String, String> urlmap = parserUrl(arr[6]);
		
		bean.setIp(arr[0]);
		bean.setCtime(arr[3].substring(1));
		bean.setAv(urlmap.get(Constants.NGINX_REQUEST_PARAMS_AV));
		bean.setC(urlmap.get(Constants.NGINX_REQUEST_PARAMS_CHANNEL));
		bean.setCcode(urlmap.get(Constants.NGINX_REQUEST_PARAMS_CITYCODE));
		bean.setImsi(urlmap.get(Constants.NGINX_REQUEST_PARAMS_IMSI));
		bean.setImei(urlmap.get(Constants.NGINX_REQUEST_PARAMS_IMEI));
		bean.setM(urlmap.get(Constants.NGINX_REQUEST_PARAMS_MODEL));
		bean.setPname(urlmap.get(Constants.NGINX_REQUEST_PARAMS_PACKAGENAME));
		bean.setR(urlmap.get(Constants.NGINX_REQUEST_PARAMS_RESOLUTION));
		bean.setTid(urlmap.get(Constants.NGINX_REQUEST_PARAMS_TID));
		
        return bean;
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
