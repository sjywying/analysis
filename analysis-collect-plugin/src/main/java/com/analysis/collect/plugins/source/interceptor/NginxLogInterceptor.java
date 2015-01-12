package com.analysis.collect.plugins.source.interceptor;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import scala.actors.threadpool.Arrays;

public class NginxLogInterceptor implements Interceptor {

	private static final Logger logger = LoggerFactory.getLogger(NginxLogInterceptor.class);

	
//	useragent必须包含的字符串(合法)
	private Set<String> http_user_agent = new HashSet<String>();
//	非法IP地址(不合法)
	private Set<String> remote_addr = new HashSet<String>();
//	url起始地址(合法)
	private Set<String> urls = new HashSet<String>();
//	参数必须包含(合法)
	private Set<String> paramskey = new HashSet<String>();
//	请求方式(合法)
	private Set<String> request_method = new HashSet<String>();
//	返回状态(合法)
	private Set<Integer> response_status = new HashSet<Integer>();

	public NginxLogInterceptor(Set<String> http_user_agent,
			Set<String> remote_addr, Set<String> urls, Set<String> paramskey,
			Set<String> request_method, Set<Integer> response_status) {
		this.http_user_agent = http_user_agent;
		this.remote_addr = remote_addr;
		this.urls = urls;
		this.paramskey = paramskey;
		this.request_method = request_method;
		this.response_status = response_status;
	}



	@Override
	public void initialize() {

	}

	@Override
	public Event intercept(Event event) {
		try {
			String body = new String(event.getBody(), "UTF-8");
//			URLDecoder.decode(body,"utf-8");
//			logger.warn("url:{}", body);
			
			String[] bodyArr = body.split(" ");

			// post请求无效
			if (bodyArr[5].contains("POST")) {
				logger.warn("post request, url:{}", body);
				return null;
			}

			// 静态数据请求无效
			int index = bodyArr[6].indexOf("?");
			if (index == -1) {
				logger.warn("'?' not exist, url:{}", body);
				return null;
			}
			String address = bodyArr[6].substring(0, index);
			if (address.endsWith(".gif") || address.endsWith(".png")
					|| address.endsWith(".jpg") || address.endsWith(".jpeg")
					|| address.endsWith(".css") || address.endsWith(".js")
					|| address.endsWith(".jpeg")) {
				logger.warn("img、css、js file, url:{}", body);
				return null;
			}

			// 如果参数个数小于基本参数个数，请求无效
			String paramsUrl = bodyArr[6].substring(index + 1);
			String[] paramsArr = paramsUrl.split("&");
			if (paramsArr.length < 9) {
				logger.warn("params length lass then 9, url:{}", body);
				return null;
			}

			// urlMap.put(NGINX_REQUEST_PARAMS_IP, bodyArr[0]);
			// urlMap.put(NGINX_REQUEST_PARAMS_CTIME, bodyArr[3].substring(1) +
			// bodyArr[4].substring(0, bodyArr[4].length() - 1));

			return event;
		} catch (UnsupportedEncodingException e) {
			logger.error("exception : {} ", e.getMessage());
			return null;
		}
	}

	@Override
	public List<Event> intercept(List<Event> events) {
		List<Event> list = new ArrayList<Event>();
		for (Event event : events) {
			Event revent = intercept(event);
			if(revent != null) {
				list.add(revent);
			}
		}
		return list;
	}

	@Override
	public void close() {

	}

	/**
	 * Builder which builds new instances of the HostInterceptor.
	 */
	public static class Builder implements Interceptor.Builder {

		private Set<String> http_user_agent = new HashSet<String>();
		private Set<String> remote_addr = new HashSet<String>();
		private Set<String> urls = new HashSet<String>();
		private Set<String> paramskey = new HashSet<String>();
		private Set<String> request_method = new HashSet<String>();
		private Set<Integer> response_status = new HashSet<Integer>();
		
		static final String HTTP_USER_AGENT_KEY		= "http_user_agent_key";
		static final String HTTP_REMOTE_ADDR		= "http_remote_addr";
		static final String HTTP_URL_PRDFIX			= "http_url_prdfix";
		static final String HTTP_REQUEST_PARAMS_KEY	= "http_request_params_key";
		static final String HTTP_REQUEST_METHOD		= "http_request_method";
		static final String HTTP_RESPONSE_STATUS	= "http_response_status";
		
		@Override
		public Interceptor build() {
			return new NginxLogInterceptor(http_user_agent,remote_addr,urls,paramskey,request_method,response_status);
		}

		@Override
		public void configure(Context context) {
			String userAgentKeyWord = context.getString(HTTP_USER_AGENT_KEY);
			if(isNotEmpty(userAgentKeyWord)) {
				http_user_agent.addAll(Arrays.asList(userAgentKeyWord.split(",")));
			}
			
			String remoteAddr = context.getString(HTTP_REMOTE_ADDR);
			if(isNotEmpty(remoteAddr)) {
				remote_addr.addAll(Arrays.asList(remoteAddr.split(",")));
			}
			
			String urlsStr = context.getString(HTTP_URL_PRDFIX);
			if(isNotEmpty(urlsStr)) {
				urls.addAll(Arrays.asList(urlsStr.split(",")));
			}
			
			String paramskeyStr = context.getString(HTTP_REQUEST_PARAMS_KEY);
			if(isNotEmpty(paramskeyStr)) {
				paramskey.addAll(Arrays.asList(paramskeyStr.split(",")));
			}
			
			String requestMethodStr = context.getString(HTTP_REQUEST_METHOD);
			if(isNotEmpty(requestMethodStr)) {
				urls.addAll(Arrays.asList(requestMethodStr.split(",")));
			}
			
			String responseStatusStr = context.getString(HTTP_RESPONSE_STATUS);
			if(isNotEmpty(responseStatusStr)) {
				response_status.addAll(Arrays.asList(responseStatusStr.split(",")));
			}
		}

		private boolean isNotEmpty(String str) {
			if(str == null || "".equals(str.trim())) {
				return false;
			} else {
				return true;
			}
		}
	}
	

	// private static Map<String, String> parserUrl(String url) {
	// Map<String, String> map = new HashMap<String, String>();
	//
	// int index = url.indexOf("?");
	// if (index == -1) return null;
	//
	// String paramsUrl = url.substring(index+1);
	//
	// String address = url.substring(0, index);
	// map.put(NGINX_LOG_URL, address);
	//
	// String[] paramsArr = paramsUrl.split("&");
	// for (String param : paramsArr) {
	// String[] paramArr = param.split("=");
	// if(paramArr != null && paramArr.length == 2) {
	// map.put(paramArr[0], paramArr[1]);
	// }
	// }
	//
	// return map;
	// }
//
//	public static void main(String[] args) {
//		String body = "218.240.33.174 - - [01/Dec/2014:19:40:24 +0800] \"GET /lbs/nginx-logo.png HTTP/1.1\" 404 3652 \"http://118.192.71.32/lbs/?query=atm&tid=e357541051318188&model=AOSPonMako&adccompany=100002&av=APKPRJ100_1.0.0.86_20141128&pkgname=com.ishow.client.android.plugin.company&imsi=460028106400207&imei=357541051318188&an=w-Android-gen-768x1184&cityId=110000&lat=39.919487&lng=116.378911&id=34337&type=atm\" \"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120 Safari/537.36\" \"-\"";
//		String[] bodyArr = body.split(" ");
//
//		// post请求无效
//		if (bodyArr[5].contains("POST")) {
//			System.out.println("post请求无效");
//			return ;
//		}
//
//		// 静态数据请求无效
//		int index = bodyArr[6].indexOf("?");
//		if (index == -1) {
//			System.out.println("静态数据请求无效1111");
//			return ;
//		}
//		String address = bodyArr[6].substring(0, index);
//		if (address.endsWith(".gif") || address.endsWith(".png")
//				|| address.endsWith(".jpg") || address.endsWith(".jpeg")
//				|| address.endsWith(".css") || address.endsWith(".js")
//				|| address.endsWith(".jpeg")) {
//			if (index == -1) System.out.println("静态数据请求无效2222");
//			return ;
//		}
//
//		// 如果参数个数小于基本参数个数，请求无效
//		String paramsUrl = bodyArr[6].substring(index + 1);
//		String[] paramsArr = paramsUrl.split("&");
//		if (paramsArr.length < 9) {
//			System.out.println("如果参数个数小于基本参数个数，请求无效");
//			return ;
//		}
//
//		// urlMap.put(NGINX_REQUEST_PARAMS_IP, bodyArr[0]);
//		// urlMap.put(NGINX_REQUEST_PARAMS_CTIME, bodyArr[3].substring(1) +
//		// bodyArr[4].substring(0, bodyArr[4].length() - 1));
//
//		System.out.println("成功");
//	}

}
