package com.analysis.calculate.common.metadata;

import java.util.HashMap;
import java.util.Map;


public class NginxConstants {

	public static final String NGINX_REQUEST_URI = "uri";
	public static final String NGINX_REQUEST_PARAMS_IP = "ip";
	public static final String NGINX_REQUEST_PARAMS_CTIME = "ctime";
	public static final String NGINX_REQUEST_PARAMS_USERAGENT = "ua";
	
	public static final String NGINX_REQUEST_PARAMS_TID = "tid";
	public static final String NGINX_REQUEST_PARAMS_IMSI = "imsi";
	public static final String NGINX_REQUEST_PARAMS_IMEI = "imei";
	public static final String NGINX_REQUEST_PARAMS_CHANNEL = "adccompany";
	public static final String NGINX_REQUEST_PARAMS_MODEL = "model";
	public static final String NGINX_REQUEST_PARAMS_AV = "av";
	public static final String NGINX_REQUEST_PARAMS_PACKAGENAME = "pkgname";
	public static final String NGINX_REQUEST_PARAMS_RESOLUTION = "an";
	public static final String NGINX_REQUEST_PARAMS_CITYCODE = "cityid";
	public static final String NGINX_REQUEST_PARAMS_ISSALE = "isSale";
	public static final String NGINX_REQUEST_PARAMS_LAT = "lat";
	public static final String NGINX_REQUEST_PARAMS_LNG = "lng";
	
	
	
	public static final String NGINX_REQUEST_PARAMS_TYPE = "t";
	
	public static final String KAFKA_ZKROOT_PREFIX = "/kafkazkroot/";
	
	
	public static final Map<String, String> URI2TOPIC = new HashMap<String, String>();
	
	static {
		NginxTopicType[] types = NginxTopicType.values();
		for (NginxTopicType t : types) {
			URI2TOPIC.put(t.getUri(), t.getTopic());
		}
	}
	
}
