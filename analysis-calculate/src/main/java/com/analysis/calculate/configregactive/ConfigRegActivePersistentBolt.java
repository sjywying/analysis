package com.analysis.calculate.configregactive;

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

public class ConfigRegActivePersistentBolt implements IRichBolt {
	
	private static final long serialVersionUID = 1L;
	
	private static final String LIST_KEY_PREFIX = "regactive_list_";
	
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
		String tid = tuple.getStringByField(ConfigRegActiveParserBolt.FIELDS_TID);
		String content = tuple.getStringByField(ConfigRegActiveParserBolt.FIELDS_CONTENT);
		String ctime = tuple.getStringByField(ConfigRegActiveFilterBolt.FIELDS_CTIME);
		
		try {
			if(ConfigRegActiveParserBolt.FIELDS_TID_VALUE_ERROR_DEFAULT.equals(tid)) {
				redisTemplate.opsForHash().put(RedisConstants.REGACTIVE_HASH_CONTENT_ERROR, tid, content);
				collector.ack(tuple);
				return ;
			}
			
			
			boolean isreg = redisTemplate.opsForSet().isMember(RedisConstants.REGISTE_SET_TID_MEMCACHE, tid);
			if(!isreg) {
				isreg = redisTemplate.opsForSet().isMember(RedisConstants.REGISTE_SET_TID_PERSISTENT, tid);
			}
			
			if(isreg) {
				//	已经注册
				
				boolean isExistMem = redisTemplate.opsForSet().isMember(RedisConstants.REGACTIVE_SET_TID_MEMCACHE, tid);
				if(!isExistMem) {
					isExistMem = redisTemplate.opsForSet().isMember(RedisConstants.REGACTIVE_SET_TID_PERSISTENT, tid);
				}
				
				//	已经激活
				if(!isExistMem) {
					long listlen = redisTemplate.opsForList().size(LIST_KEY_PREFIX+tid);
					if(listlen < 3) {
						// TODO 缺乏对reg时间的对比， 暂时忽略（原因是实时）
						String pdate = redisTemplate.opsForList().index(LIST_KEY_PREFIX+tid, listlen-1);
						if(!ConfigRegActiveFilterBolt.FIELDS_CTIME_DEFAULT_VALUE.equals(ctime) && !ctime.equals(pdate)) {
							redisTemplate.opsForList().rightPush(LIST_KEY_PREFIX+tid, ctime);
							listlen ++;
						} else {
							
						}
					} else {
						
					}
					
					if(listlen == 3) {
						redisTemplate.opsForSet().add(RedisConstants.REGACTIVE_SET_TID_MEMCACHE, tid);
						redisTemplate.opsForHash().putIfAbsent(RedisConstants.REGACTIVE_HASH_CONTENT, tid, content);
						redisTemplate.delete(LIST_KEY_PREFIX+tid);
					}
				} else {
					
				}
			} else {
				// 未注册不做任何操作
			}
			
		} catch (Exception e) {
			redisTemplate.opsForHash().put(RedisConstants.REGACTIVE_HASH_CONTENT_ERROR, tid, content+"#"+this.getClass().getName()+"#"+e.getMessage());
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
