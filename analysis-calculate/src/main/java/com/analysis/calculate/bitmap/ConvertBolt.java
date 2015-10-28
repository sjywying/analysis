package com.analysis.calculate.bitmap;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import com.analysis.calculate.common.spring.SpringApplicationContextFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.Map;


public class ConvertBolt extends BaseBasicBolt {

	private static ApplicationContext applicationContext = SpringApplicationContextFactory.instance();

	public static final String FIELDS_TID = "tid";
	public static final String FIELDS_CONTENT = "content";
	
	private static final long serialVersionUID = 1L;

	private RedisTemplate<String, String> redisTemplate ;

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		try {
			LogBean bean = (LogBean) input.getValueByField(WeblogFormatBolt.FIELDS_CONTENT);

			collector.emit(new Values((int)createUnique(bean.getTid()), bean));
		} catch (Exception e) {
			e.printStackTrace();
//			collector.emit(new Values(FIELDS_TID_VALUE_ERROR_DEFAULT, JSON.toJSONString(bean)));
		}
	}

	private long createUnique(String tid) {
		/**
		 * 1. 获取 hget myhash field1    return 111
		 * 2. 如果1无值 incr mykey		return 112
		 * 3. hset myhash field1 112
		 *
		 * return 112
		 *
		 */

		if("".equals(tid) || null == tid) {
			return -2;
		}

		long v = 0;
		Object intTid = redisTemplate.opsForHash().get("g_tid", tid);
		if(null != intTid) {
			try {
				v = Integer.parseInt((String)intTid);
			} catch (Exception e) {
				v = -1;
			}

			return v;
		}

		v = redisTemplate.opsForValue().increment("g_int", 1l);

		boolean setValue = redisTemplate.opsForHash().putIfAbsent("g_tid", tid, String.valueOf(v));
		if(!setValue) {
			Object intTid_ = redisTemplate.opsForHash().get("g_tid", tid);
			if(null != intTid_) {
				try {
					v = Integer.parseInt((String)intTid_);
				} catch (Exception e) {
					v = -1;
				}
			}
		}

		return v;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(FIELDS_TID, FIELDS_CONTENT));
	}

	@Override
	public void prepare(Map stormConf, TopologyContext context) {
		redisTemplate = (RedisTemplate<String, String>) applicationContext.getBean("redisTemplate");
	}

	@Override
	public void cleanup() {
		super.cleanup();
	}
}