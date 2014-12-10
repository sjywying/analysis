package com.analysis.calculate.nginx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import com.alibaba.fastjson.JSON;
import com.analysis.api.bean.Active;
import com.analysis.calculate.common.metadata.NginxTopicType;

public class NginxLogActiveBolt extends BaseBasicBolt {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(NginxLogActiveBolt.class);

	protected static final String FIELDS_TYPE = "type";
	protected static final String FIELDS_CONTENT = "content";
	
	@Override
	public void execute(Tuple tuple, BasicOutputCollector collector) {
		
		try {
			String content = tuple.getStringByField(NginxLogFilterBolt.FIELDS_CONTENT);
//			Map<String, String> content = (Map<String, String>) tuple.getValueByField(NginxFilterBolt.FIELDS_CONTENT);
			
//			过滤无用数据项
			Active bean = JSON.parseObject(content, Active.class);
			
//			bean.setIp(content.get(NginxConstants.NGINX_REQUEST_PARAMS_IP));
//			bean.setCtime(content.get(NginxConstants.NGINX_REQUEST_PARAMS_CTIME));
//			bean.setTid(content.get(NginxConstants.NGINX_REQUEST_PARAMS_TID));
//			bean.setImsi(content.get(NginxConstants.NGINX_REQUEST_PARAMS_IMSI));
//			bean.setImei(content.get(NginxConstants.NGINX_REQUEST_PARAMS_IMEI));
//			bean.setC(content.get(NginxConstants.NGINX_REQUEST_PARAMS_CHANNEL));
//			bean.setM(content.get(NginxConstants.NGINX_REQUEST_PARAMS_MODEL));
//			bean.setAv(content.get(NginxConstants.NGINX_REQUEST_PARAMS_AV));
//			bean.setPname(content.get(NginxConstants.NGINX_REQUEST_PARAMS_PACKAGENAME));
//			bean.setCcode(content.get(NginxConstants.NGINX_REQUEST_PARAMS_CITYCODE));
//			bean.setR(content.get(NginxConstants.NGINX_REQUEST_PARAMS_RESOLUTION));
			
			if(bean.check()) {
				collector.emit(new Values(NginxTopicType.ACTIVE.getTopic(), JSON.toJSONString(bean)));
			}
		} catch (Exception e) {
			logger.error("exception message : {}", e.getMessage());
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(FIELDS_TYPE, FIELDS_CONTENT));
	}
	
	
}