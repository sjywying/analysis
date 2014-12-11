package com.analysis.calculate.nginx;

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
import com.analysis.calculate.common.metadata.NginxConstants;
import com.analysis.calculate.common.metadata.NginxTopicType;
import com.analysis.common.constants.Constants;
import com.analysis.common.utils.Strings;

public class NginxLogFilterBolt implements IRichBolt {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(NginxLogFilterBolt.class);
	
	public static final String FIELDS_TYPE = "type";
	public static final String FIELDS_CONTENT = "content";
	
	private transient OutputCollector collector;
	
	@Override
	public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}
	
	@Override
	public void execute(Tuple tuple) {
		try {
			Map<String, String> content = (Map<String, String>) tuple.getValueByField(NginxLogParserBolt.FIELDS_CONTENT);
			
			if(this.check(content)) {
				String tid = content.get(NginxConstants.NGINX_REQUEST_PARAMS_TID);
				String ua = content.get(NginxConstants.NGINX_REQUEST_PARAMS_USERAGENT);
				String uri = content.get(NginxConstants.NGINX_REQUEST_URI);
				NginxTopicType type = NginxTopicType.uriOf(uri);
				if(type != null) {
					String topic = type.getTopic();
					if(Constants.NO_MD5_CHECK || Strings.compareTidMD5(tid, ua)) {
						collector.emit(new Values(topic, JSON.toJSONString(content)));
					} else {
						logger.debug("user-agent passwd is illegal. ua:{}, tid:{}", ua, tid);
					}
				} else {
					logger.debug("uri is match. uri is {}.", uri);
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
		declarer.declare(new Fields(FIELDS_TYPE, FIELDS_CONTENT));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
	
	@Override
	public void cleanup() {
		
	}
	
	private boolean check(Map<String, String> map) {
		if(!map.containsKey(NginxConstants.NGINX_REQUEST_PARAMS_TID) && Strings.isEmpty(map.get(NginxConstants.NGINX_REQUEST_PARAMS_TID))) return false;
		if(!map.containsKey(NginxConstants.NGINX_REQUEST_PARAMS_CHANNEL) && Strings.isEmpty(map.get(NginxConstants.NGINX_REQUEST_PARAMS_CHANNEL))) return false;
		if(!map.containsKey(NginxConstants.NGINX_REQUEST_PARAMS_USERAGENT) && Strings.isEmpty(map.get(NginxConstants.NGINX_REQUEST_PARAMS_USERAGENT))) return false;
		if(!map.containsKey(NginxConstants.NGINX_REQUEST_URI) && Strings.isEmpty(map.get(NginxConstants.NGINX_REQUEST_URI))) return false;
		return true;
	}
	
}
