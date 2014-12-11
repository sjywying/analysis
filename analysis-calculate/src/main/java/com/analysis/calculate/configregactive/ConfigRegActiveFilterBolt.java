package com.analysis.calculate.configregactive;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import com.alibaba.fastjson.JSON;
import com.analysis.api.bean.ConfigRegActive;
import com.analysis.common.constants.Constants;
import com.analysis.common.utils.Strings;

public class ConfigRegActiveFilterBolt implements IRichBolt {
	
	private static final long serialVersionUID = 1L;
	
	protected static final String FIELDS_CTIME = "ctime";
	protected static final String FIELDS_CTIME_DEFAULT_VALUE = "20000101";
	
	private transient OutputCollector collector;
	
	@Override
	public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}
	
	@Override
	public void execute(Tuple tuple) {
		String content = "";
		
		try {
			String tid = tuple.getStringByField(ConfigRegActiveParserBolt.FIELDS_TID);
			String ua = tuple.getStringByField(ConfigRegActiveParserBolt.FIELDS_USERAGENT);
			
			if(ConfigRegActiveParserBolt.FIELDS_TID_VALUE_ERROR_DEFAULT.equals(tid)) {
				
				content = tuple.getStringByField(ConfigRegActiveParserBolt.FIELDS_CONTENT);
				collector.emit(new Values(ConfigRegActiveParserBolt.FIELDS_TID_VALUE_ERROR_DEFAULT, content, FIELDS_CTIME_DEFAULT_VALUE));
				
			} else {
				
				ConfigRegActive bean = (ConfigRegActive) tuple.getValueByField(ConfigRegActiveParserBolt.FIELDS_CONTENT);
				
				content = JSON.toJSONString(bean);
				
				if(!bean.check()) {
					collector.emit(new Values(ConfigRegActiveParserBolt.FIELDS_TID_VALUE_ERROR_DEFAULT, content+"#selfcheckfalse#", FIELDS_CTIME_DEFAULT_VALUE));
					collector.ack(tuple);
					return ;
				}
				
				if(Constants.NO_MD5_CHECK || Strings.compareTidMD5(tid, ua)) {
					collector.emit(new Values(tid, content, bean.getCtime().substring(0, 8)));
				} else {
					collector.emit(new Values(ConfigRegActiveParserBolt.FIELDS_TID_VALUE_ERROR_DEFAULT, content+"#MD5checkfalse#", FIELDS_CTIME_DEFAULT_VALUE));
				}
			}
		} catch (Exception e) {
			collector.emit(new Values(ConfigRegActiveParserBolt.FIELDS_TID_VALUE_ERROR_DEFAULT, content + "#"+this.getClass().getName()+"#" + e.getMessage(), FIELDS_CTIME_DEFAULT_VALUE));
//			logger.error("exception message: {}, content: {} ", e.getMessage(), content);
		} finally {
			collector.ack(tuple);
		}
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(ConfigRegActiveParserBolt.FIELDS_TID, ConfigRegActiveParserBolt.FIELDS_CONTENT, FIELDS_CTIME));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
	
	@Override
	public void cleanup() {
		
	}
}
