package com.analysis.collect.plugins.source.interceptor;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import scribe.thrift.LogEntry;

public class NginxLogParser {

	private static final SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss");

	public final char LOG_LINE_SEP = '\n';
	public final char LOG_FIELD_SEP = '\t';

	// 日志中的域的位置信息
	public final int LOG_FIELD_REMOTE_ADDR_IDX = 0;
	public final int LOG_FIELD_REMOTE_USER_IDX = 1;
	public final int LOG_FIELD_TIME_LOCAL_IDX = 2;
	public final int LOG_FIELD_REQUEST_IDX = 3;
	public final int LOG_FIELD_STATUS_IDX = 4;
	public final int LOG_FIELD_BODY_BYTES_SENT_IDX = 5;
	public final int LOG_FIELD_HTTP_REFERER_IDX = 6;
	public final int LOG_FIELD_HTTP_X_FORWARDED_FOR_IDX = 7;
	public final int LOG_FIELD_HTTP_USER_AGENT_IDX = 8;

	public static final Set<String> supportedMethod = new HashSet<String>();

	public NginxLogParser() {
		// 支持的HTTP方法
		supportedMethod.add("GET");
		supportedMethod.add("POST");
		supportedMethod.add("PUT");
		supportedMethod.add("HEAD");
	}

	/*
	 * only support log format as below log_format iss_log
	 * '$server_addr\t$host\t$remote_addr\t$time_local\t$request_uri\t'
	 * '$request_length\t$bytes_sent\t$request_time\t$status\t$upstream_addr'
	 * '\t$upstream_cache_status\t$upstream_response_time\t$request_method\t$http_referer\t$http_user_agent';
	 */

	public static final String CACHE_HIT_STRING = "HIT";

	private static Logger LOGGER = LoggerFactory
			.getLogger(NginxLogParser.class);

	public NginxLog parse(String category, String record) {
		/*
		 * 不处理空的字符串
		 */
		if (StringUtils.isEmpty(record)) {
			return null;
		}

		int lastIdx = 0;
		int curIdx = 0;
		int curField = 0;

		NginxLog bean = new NginxLog();

		while ((curIdx = record.indexOf(LOG_FIELD_SEP, lastIdx)) != -1) {

			String field = record.substring(lastIdx, curIdx);

			// 获取关注的
			switch (curField) {
			case LOG_FIELD_REMOTE_ADDR_IDX:
				bean.setRemote_addr(field);
				break;
			case LOG_FIELD_REMOTE_USER_IDX:
				bean.setRemote_user(field);
				break;
			case LOG_FIELD_TIME_LOCAL_IDX:
				bean.setTime_local(field.substring(0, field.indexOf(" ")));
				break;
			case LOG_FIELD_REQUEST_IDX:
				String[] reqArr = field.split(" ");
				String[] reqUrl = reqArr[1].split("?");
				if (reqUrl.length >= 1)
					bean.setRequest_uri(reqUrl[0]);
				if (reqUrl.length == 2)
					bean.setRequest_uri(reqUrl[1]);
				bean.setRequest_method(reqArr[0]);
				bean.setRequest_protocol(reqArr[2]);
				break;
			case LOG_FIELD_STATUS_IDX:
				bean.setStatus(NumberUtils.toInt(field));
				break;
			case LOG_FIELD_BODY_BYTES_SENT_IDX:
				bean.setBody_bytes_sent(NumberUtils.toLong(field));
				break;
			case LOG_FIELD_HTTP_REFERER_IDX:
				bean.setHttp_referer(field);
				break;
			case LOG_FIELD_HTTP_X_FORWARDED_FOR_IDX:
				bean.setHttp_x_forwarded_for(field);
				break;
			case LOG_FIELD_HTTP_USER_AGENT_IDX:
				bean.setHttp_user_agent(field);
				break;
			}

			lastIdx = curIdx + 1;
			curField++;
		}
		return bean;
	}
}