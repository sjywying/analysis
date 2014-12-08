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
import com.analysis.api.bean.Registe;
import com.analysis.common.constants.RedisConstants;
import com.analysis.common.utils.Strings;
import com.analysis.web.mapper.RegisteMapper;

@Component
public class Redis2MysqlJob {
	
	private static final Logger logger = LoggerFactory.getLogger(Redis2MysqlJob.class);
	
	@Autowired private StringRedisTemplate redisTemplate;
	@Autowired private RegisteMapper registeMapper;
	
	@Scheduled(fixedDelay = 1000*10)
	public void regPersistence() {
//		redisTemplate.opsForSet().differenceAndStore(key, otherKeys, destKey)members(RedisConstants.REGISTE_SET_TID_MEMCACHE);
//		TODO 批处理
		
		Set<String> tids = redisTemplate.opsForSet().members(RedisConstants.REGISTE_SET_TID_MEMCACHE);
		
		if(tids == null || tids.size() == 0) {
			logger.debug("tids is empty.");
			return;
		}
		
		for (String tid : tids) {
			String content = (String) redisTemplate.opsForHash().get(RedisConstants.REGISTE_HASH_CONTENT_ACTIVE, tid);
			if(Strings.isEmpty(content)) {
				logger.warn("tid : {}, content is empty.", tid);
				continue;
			}
			
			try {
				Registe registe = JSON.parseObject(content, Registe.class);
				registeMapper.insert(registe);
				redisTemplate.opsForSet().move(RedisConstants.REGISTE_SET_TID_MEMCACHE, tid, RedisConstants.REGISTE_SET_TID_PERSISTENT);
				logger.debug("redis data to mysql finish");
			} catch (Exception e) {
				logger.error("redis data to mysql error, error message {}", e.getMessage());
				e.printStackTrace();
				continue;
			}
		}
		
	}
	
	protected RedisSerializer<String> getRedisSerializer() {
		return redisTemplate.getStringSerializer();
	}
	
}
