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
import com.analysis.common.constants.RedisConstants;

public class RegisterPersistentBolt implements IRichBolt {
	
	private static final long serialVersionUID = 1L;
	
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
			if(RegisterParserBolt.FIELDS_TID_VALUE_ERROR_DEFAULT.equals(tid)) {
				redisTemplate.opsForHash().put(RedisConstants.REGISTE_HASH_CONTENT_ERROR, tid, content);
				collector.ack(tuple);
				return ;
			}
			
			boolean isExistMem = redisTemplate.opsForSet().isMember(RedisConstants.REGISTE_SET_TID_MEMCACHE, tid);
			if(!isExistMem) {
				isExistMem = redisTemplate.opsForSet().isMember(RedisConstants.REGISTE_SET_TID_PERSISTENT, tid);
			}
			
			if(!isExistMem) {
//				TODO transaction
				String ctime = tuple.getStringByField(RegisterFilterBolt.FIELDS_CTIME);
				redisTemplate.opsForSet().add(RedisConstants.REGISTE_SET_TID_MEMCACHE, tid);
				redisTemplate.opsForHash().putIfAbsent(RedisConstants.REGISTE_HASH_CONTENT, tid, content);
				redisTemplate.opsForList().rightPush(RedisConstants.CONFIGREGACTIVE_LIST_KEY_PREFIX+tid, ctime);
			} else {
				redisTemplate.opsForHash().put(RedisConstants.REGISTE_HASH_CONTENT_ERROR, tid, content+"#isexisttrue#");
			}
			
		} catch (Exception e) {
			redisTemplate.opsForHash().put(RedisConstants.REGISTE_HASH_CONTENT_ERROR, tid, content+"#"+this.getClass().getName()+"#"+e.getMessage());
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
