package com.analysis.nginx.active.topology;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

public class FilterConfiguration implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(FilterConfiguration.class);

//	useragent必须包含的字符串(合法)
	private Set<String> http_user_agent_key = new HashSet<String>();
//	非法IP地址(不合法)
	private Set<String> http_remote_addr = new HashSet<String>();
//	url起始地址(合法)
	private Set<String> http_url_prdfix = new HashSet<String>();
//	参数必须包含(合法)
	private Set<String> http_request_params_key = new HashSet<String>();
//	请求方式(合法)
	private Set<String> http_request_method = new HashSet<String>();
//	返回状态(合法)
	private Set<Integer> http_response_status = new HashSet<Integer>();
	
	private RedisTemplate<String, String> redisTemplate;
	
	public FilterConfiguration(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	/******************* 	redis set	************************/
	static final String HTTP_USER_AGENT_KEY		= "http_user_agent_key";
	static final String HTTP_REMOTE_ADDR		= "http_remote_addr";
	static final String HTTP_URL_PRDFIX			= "http_url_prdfix";
	static final String HTTP_REQUEST_PARAMS_KEY	= "http_request_params_key";
	static final String HTTP_REQUEST_METHOD		= "http_request_method";
	static final String HTTP_RESPONSE_STATUS	= "http_response_status";
	
	/******************* 	redis string	************************/
	static final String FILTER_CONFIG_INTERVAL	= "filter_config_interval";
	
	static final int	FILTER_CONFIG_INTERVAL_MINUTE = 60 * 1000;
	
	@Override
	public void run() {
		while (true) {
			http_user_agent_key = redisTemplate.opsForSet().members(HTTP_USER_AGENT_KEY);
			http_remote_addr = redisTemplate.opsForSet().members(HTTP_REMOTE_ADDR);
			http_url_prdfix = redisTemplate.opsForSet().members(HTTP_URL_PRDFIX);
			http_request_params_key = redisTemplate.opsForSet().members(HTTP_REQUEST_PARAMS_KEY);
			http_request_method = redisTemplate.opsForSet().members(HTTP_REQUEST_METHOD);
			
			Set<String> http_response_status_str = redisTemplate.opsForSet().members(HTTP_RESPONSE_STATUS);
			if(http_response_status_str != null || http_response_status_str.size() > 0) {
				Set<Integer> http_response_status_int = new HashSet<Integer>();
				for (String status : http_response_status_str) {
					http_response_status_int.add(Integer.parseInt(status));
				}
				http_response_status = http_response_status_int;
			}
			
			String intervalStr = redisTemplate.opsForValue().get(FILTER_CONFIG_INTERVAL);
			int interval = intervalStr == null ? 5 * FILTER_CONFIG_INTERVAL_MINUTE : Integer.parseInt(intervalStr) * FILTER_CONFIG_INTERVAL_MINUTE;
			
			try {
				Thread.currentThread().sleep(interval);
			} catch (InterruptedException e) {
				logger.error("get configuration thread throw exception : {}", e.getMessage());
			}
		}
	}
	
}
