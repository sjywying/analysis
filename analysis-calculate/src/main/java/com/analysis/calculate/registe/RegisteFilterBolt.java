package com.analysis.calculate.registe;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import com.alibaba.fastjson.JSON;
import com.analysis.api.bean.Registe;
import com.analysis.common.utils.Strings;

public class RegisteFilterBolt implements IRichBolt {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(RegisteFilterBolt.class);
	
	private transient OutputCollector collector;
	
	@Override
	public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}
	
	@Override
	public void execute(Tuple tuple) {
		try {
			String tid = tuple.getStringByField(RegisteParserBolt.FIELDS_TID);
			Registe bean = null;
			if(RegisteParserBolt.FIELDS_TID_VALUE_ERROR_DEFAULT.equals(tid)) {
				String content = tuple.getStringByField(RegisteParserBolt.FIELDS_CONTENT);
				collector.emit(new Values(RegisteParserBolt.FIELDS_TID_VALUE_ERROR_DEFAULT, content));
			} else {
				bean = (Registe) tuple.getValueByField(RegisteParserBolt.FIELDS_CONTENT);
				
				if(Strings.compareTidMD5(tid, bean.getUa())) {
					collector.emit(new Values(tid, JSON.toJSONString(bean)));
				} else {
					collector.emit(new Values(RegisteParserBolt.FIELDS_TID_VALUE_ERROR_DEFAULT, JSON.toJSONString(bean)));
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			collector.ack(tuple);
		}
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(RegisteParserBolt.FIELDS_TID, RegisteParserBolt.FIELDS_CONTENT));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
	
	@Override
	public void cleanup() {
		
	}
}
