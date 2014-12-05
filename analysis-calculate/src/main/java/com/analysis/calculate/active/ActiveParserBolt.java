package com.analysis.calculate.active;

import com.alibaba.fastjson.JSON;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

/**
 * 记录：2014-11-24 07:30:48,117.136.45.126,e866029020015865,460005100880864,866029020015865,101502,ZTEG720T,SDKPRJ98_2.2.2_20140826,cn.com.zte.yp,w-Android-gen-1080x1920,110000
 * 格式：
 * 		时间
 * 		ip
 * 		tid
 * 		imsi
 * 		imei
 * 		channel
 * 		model
 * 		av
 * 		packagename
 * 		分辨率
 * 		citycode
 * 
 * @author Crazy/sjy
 *
 */

public class ActiveParserBolt extends BaseBasicBolt {
	
	public static final String FIELDS_TID = "tid";
	public static final String FIELDS_TID_VALUE_ERROR_DEFAULT = "other";
	public static final String FIELDS_CONTENT = "content";
	
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		String log = (String) input.getValue(0);
		
		try {
			ActiveBean bean = JSON.parseObject(log, ActiveBean.class);
		
			collector.emit(new Values(bean.getTid(), JSON.toJSONString(bean)));
		} catch (Exception e) {
			collector.emit(new Values(FIELDS_TID_VALUE_ERROR_DEFAULT, log));
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(FIELDS_TID, FIELDS_CONTENT));
	}
	
}