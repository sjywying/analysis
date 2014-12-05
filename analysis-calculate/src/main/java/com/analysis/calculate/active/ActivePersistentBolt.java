package com.analysis.calculate.active;

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
	
	private static final String REDIS_SET_MEMCACHE = "memcache";
	private static final String REDIS_SET_PERSISTENT = "persistent";
	
	private static final String REDIS_HASH_ACTIVE = "active";
	private static final String REDIS_HASH_ERROR = "error";
	
	private transient OutputCollector collector;
	
	private static ApplicationContext applicationContext = SpringApplicationContextFactory.instance();
	private RedisTemplate<String, String> redisTemplate ;

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
			boolean isExistMem = redisTemplate.opsForSet().isMember(REDIS_SET_MEMCACHE, id);
			if(!isExistMem) {
				isExistMem = redisTemplate.opsForSet().isMember(REDIS_SET_PERSISTENT, id);
			}
			
			if(!isExistMem) {
//				TODO transaction
				redisTemplate.opsForSet().add(REDIS_SET_MEMCACHE, id);
				redisTemplate.opsForHash().putIfAbsent(REDIS_HASH_ACTIVE, id, content);
			}
			
			collector.ack(tuple);
		} catch (Exception e) {
			redisTemplate.opsForHash().put(REDIS_HASH_ERROR, id, content);
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
