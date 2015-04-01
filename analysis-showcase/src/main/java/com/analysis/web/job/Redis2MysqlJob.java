package com.analysis.web.job;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.analysis.api.bean.ConfigRegActive;
import com.analysis.api.bean.RegActive;
import com.analysis.api.bean.Register;
import com.analysis.common.constants.RedisConstants;
import com.analysis.common.utils.IPMetadataParser;
import com.analysis.common.utils.Strings;

@Component
public class Redis2MysqlJob {
	
	private static final Logger logger = LoggerFactory.getLogger(Redis2MysqlJob.class);
	
	@Autowired private StringRedisTemplate redisTemplate;

	static {
		IPMetadataParser.init();
	}
	
	@Scheduled(fixedDelay = 1000*10)
	public void regPersistence() {
//		TODO 批处理强数据
	}
	
	@Scheduled(fixedDelay = 1000*10)
	public void configRegActivePersistence() {
	}
	
	@Scheduled(fixedDelay = 1000*10)
	public void regActivePersistence() {
	}
	
	protected RedisSerializer<String> getRedisSerializer() {
		return redisTemplate.getStringSerializer();
	}
	
}
