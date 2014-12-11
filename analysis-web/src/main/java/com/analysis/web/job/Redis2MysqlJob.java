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
import com.analysis.api.bean.RegActive;
import com.analysis.api.bean.Register;
import com.analysis.common.constants.RedisConstants;
import com.analysis.common.utils.IPMetadataParser;
import com.analysis.common.utils.Strings;
import com.analysis.web.mapper.RegActiveMapper;
import com.analysis.web.mapper.RegisterMapper;

@Component
public class Redis2MysqlJob {
	
	private static final Logger logger = LoggerFactory.getLogger(Redis2MysqlJob.class);
	
	@Autowired private StringRedisTemplate redisTemplate;
	@Autowired private RegisterMapper registeMapper;
	@Autowired private RegActiveMapper regActiveMapper;

	static {
		IPMetadataParser.init();
	}
	
	@Scheduled(fixedDelay = 1000*10)
	public void regPersistence() {
//		TODO 批处理强数据一致性未实现
		Set<String> tids = redisTemplate.opsForSet().members(RedisConstants.REGISTE_SET_TID_MEMCACHE);
		
		if(tids == null || tids.size() == 0) {
			logger.debug("tids is empty.");
			return;
		}
		
		for (String tid : tids) {
			String content = (String) redisTemplate.opsForHash().get(RedisConstants.REGISTE_HASH_CONTENT, tid);
			if(Strings.isEmpty(content)) {
				logger.warn("tid : {}, content is empty.", tid);
				continue;
			}
			
			try {
				Register registe = JSON.parseObject(content, Register.class);
				registe.setType(IPMetadataParser.getCountry(registe.getIp()));
				registeMapper.insert(registe);
				redisTemplate.opsForSet().move(RedisConstants.REGISTE_SET_TID_MEMCACHE, tid, RedisConstants.REGISTE_SET_TID_PERSISTENT);
				logger.debug("redis data to mysql finish");
			} catch (Exception e) {
				logger.error("redis data to mysql error, error message {}", e.getMessage());
//				e.printStackTrace();
				continue;
			}
		}
	}
	
	@Scheduled(fixedDelay = 1000*10)
	public void configRegActivePersistence() {
//		TODO 批处理强数据一致性未实现
		Set<String> tids = redisTemplate.opsForSet().members(RedisConstants.CONFIGREGACTIVE_SET_TID_MEMCACHE);
		
		if(tids == null || tids.size() == 0) {
			logger.debug("tids is empty.");
			return;
		}
		
		for (String tid : tids) {
			String content = (String) redisTemplate.opsForHash().get(RedisConstants.CONFIGREGACTIVE_HASH_CONTENT, tid);
			if(Strings.isEmpty(content)) {
				logger.warn("tid : {}, content is empty.", tid);
				continue;
			}
			
			try {
				Register registe = JSON.parseObject(content, Register.class);
				registe.setType(IPMetadataParser.getCountry(registe.getIp()));
				registeMapper.insert(registe);
				redisTemplate.opsForSet().move(RedisConstants.CONFIGREGACTIVE_SET_TID_MEMCACHE, tid, RedisConstants.CONFIGREGACTIVE_SET_TID_PERSISTENT);
				logger.debug("redis data to mysql finish");
			} catch (Exception e) {
				logger.error("redis data to mysql error, error message {}", e.getMessage());
//				e.printStackTrace();
				continue;
			}
		}
	}
	
	@Scheduled(fixedDelay = 1000*10)
	public void regActivePersistence() {
		//		TODO 批处理强数据一致性未实现
		Set<String> tids = redisTemplate.opsForSet().members(RedisConstants.REGACTIVE_SET_TID_MEMCACHE);
		
		if(tids == null || tids.size() == 0) {
			logger.debug("tids is empty.");
			return;
		}
		
		for (String tid : tids) {
			String content = (String) redisTemplate.opsForHash().get(RedisConstants.REGACTIVE_HASH_CONTENT, tid);
			if(Strings.isEmpty(content)) {
				logger.warn("tid : {}, content is empty.", tid);
				continue;
			}
			
			try {
				RegActive bean = JSON.parseObject(content, RegActive.class);
				
				bean.setType(IPMetadataParser.getCountry(bean.getIp()));
				regActiveMapper.insert(bean);
				redisTemplate.opsForSet().move(RedisConstants.REGACTIVE_SET_TID_MEMCACHE, tid, RedisConstants.REGACTIVE_SET_TID_PERSISTENT);
				redisTemplate.opsForHash().delete(RedisConstants.REGACTIVE_HASH_CONTENT, tid);
				logger.debug("redis data to mysql finish");
			} catch (Exception e) {
				logger.error("redis data to mysql error, error message {}", e.getMessage());
//				e.printStackTrace();
				continue;
			}
		}
	}
	
	protected RedisSerializer<String> getRedisSerializer() {
		return redisTemplate.getStringSerializer();
	}
	
}
