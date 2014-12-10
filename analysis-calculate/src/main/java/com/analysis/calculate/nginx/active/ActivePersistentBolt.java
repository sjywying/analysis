package com.analysis.calculate.nginx.active;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

import com.analysis.calculate.common.spring.SpringApplicationContextFactory;

public class ActivePersistentBolt implements IRichBolt {
	
	private static final long serialVersionUID = 1L;
	
	private static final String REDIS_ACTIVE_SET_TID_MEMCACHE = "active_tid_memcache";
	private static final String REDIS_ACTIVE_SET_TID_PERSISTENT = "active_tid_persistent";
	private static final String REDIS_ACTIVE_HASH_CONTENT = "active_content";
	private static final String REDIS_ACTIVE_HASH_CONTENT_ERROR = "active_content_error";
	
	private static ApplicationContext applicationContext = SpringApplicationContextFactory.instance();
	private RedisTemplate<String, String> redisTemplate ;

	private transient OutputCollector collector;
	
	@Override
	public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		
		redisTemplate = (RedisTemplate<String, String>) applicationContext.getBean("redisTemplate");
	}
	
	@Override
	public void execute(Tuple tuple) {
		String id = tuple.getStringByField(ActiveParserBolt.FIELDS_TID);
		String content = tuple.getStringByField(ActiveParserBolt.FIELDS_CONTENT);
		
		try {
			boolean isExistMem = redisTemplate.opsForSet().isMember(REDIS_ACTIVE_SET_TID_MEMCACHE, id);
			if(!isExistMem) {
				isExistMem = redisTemplate.opsForSet().isMember(REDIS_ACTIVE_SET_TID_PERSISTENT, id);
			}
			
			if(!isExistMem) {
//				TODO transaction
				redisTemplate.opsForSet().add(REDIS_ACTIVE_SET_TID_MEMCACHE, id);
				redisTemplate.opsForHash().putIfAbsent(REDIS_ACTIVE_HASH_CONTENT, id, content);
			}
			
			collector.ack(tuple);
		} catch (Exception e) {
			redisTemplate.opsForHash().put(REDIS_ACTIVE_HASH_CONTENT_ERROR, id, content);
			collector.fail(tuple);
		}
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void cleanup() {
		
	}
}
