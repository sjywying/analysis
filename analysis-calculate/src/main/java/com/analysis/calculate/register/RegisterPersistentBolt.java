package com.analysis.calculate.register;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

import com.analysis.calculate.common.spring.SpringApplicationContextFactory;

public class RegisterPersistentBolt implements IRichBolt {
	
	private static final long serialVersionUID = 1L;
	
	public static final String REDIS_REGISTE_SET_TID_MEMCACHE = "reg_tid_memcache";
	public static final String REDIS_REGISTE_SET_TID_PERSISTENT = "reg_tid_persistent";
	
	private static final String REDIS_REGISTE_HASH_CONTENT_ACTIVE = "reg_content";
	private static final String REDIS_REGISTE_HASH_CONTENT_ERROR = "reg_content_error";
	
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
		String tid = tuple.getStringByField(RegisterParserBolt.FIELDS_TID);
		String content = tuple.getStringByField(RegisterParserBolt.FIELDS_CONTENT);
		
		try {
			boolean isExistMem = redisTemplate.opsForSet().isMember(REDIS_REGISTE_SET_TID_MEMCACHE, tid);
			if(!isExistMem) {
				isExistMem = redisTemplate.opsForSet().isMember(REDIS_REGISTE_SET_TID_PERSISTENT, tid);
			}
			
			if(!isExistMem) {
//				TODO transaction
				redisTemplate.opsForSet().add(REDIS_REGISTE_SET_TID_MEMCACHE, tid);
				redisTemplate.opsForHash().putIfAbsent(REDIS_REGISTE_HASH_CONTENT_ACTIVE, tid, content);
			}
			
		} catch (Exception e) {
			redisTemplate.opsForHash().put(REDIS_REGISTE_HASH_CONTENT_ERROR, tid, content+"#"+this.getClass().getName()+"#"+e.getMessage());
		} finally {
			collector.ack(tuple);
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
