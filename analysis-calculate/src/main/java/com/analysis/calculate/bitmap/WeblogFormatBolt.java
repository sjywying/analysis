package com.analysis.calculate.bitmap;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.alibaba.fastjson.JSON;
import com.analysis.calculate.nginx.NginxLogParser;
import com.analysis.common.weblog.ParseWebLogInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class WeblogFormatBolt extends BaseBasicBolt {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(WeblogFormatBolt.class);

	protected static final String FIELDS_CONTENT = "content";

	private transient ParseWebLogInstance parseWebLogInstance;
	
	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		String log = (String) input.getValue(0);
		try {
			com.analysis.common.weblog.LogBean logBean = parseWebLogInstance.parseLog(log);

			LogBean bean = new LogBean();
			bean.setAv(logBean.getAv());
			bean.setCcode(logBean.getCcode());
			bean.setChannel(logBean.getChannel());
			bean.setCtime(logBean.getCtime());
			bean.setModel(logBean.getModel());
			bean.setTid(logBean.getTid());
			
			collector.emit(new Values(bean));
		}catch (Exception e) {
//			collector.emit(new Values(TopicType.OTHER.getCode(), log));
			logger.error("exception message : {}", e.getMessage());
		}
	}

	@Override
	public void prepare(Map stormConf, TopologyContext context) {
		parseWebLogInstance = ParseWebLogInstance.getInstance();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(FIELDS_CONTENT));
	}

//	public static void main(String[] args) {
//		String aa = "223.86.243.215 - - [06/May/2015:10:39:31 +0800] \"POST /config/ HTTP/1.1\" \"-\" 200 189 {\\x22adccompany\\x22:\\x22101702\\x22,\\x22an\\x22:\\x22w-Android-gen-720x1280\\x22,\\x22av\\x22:\\x22APKPRJ112_1.0.0.15_20141231\\x22,\\x22cityid\\x22:\\x22110000\\x22,\\x22configMap\\x22:{\\x221\\x22:\\x22\\x22},\\x22imei\\x22:\\x22865914020216947\\x22,\\x22imsi\\x22:\\x22460008242744683\\x22,\\x22isSale\\x22:\\x221\\x22,\\x22model\\x22:\\x22UIMI4\\x22,\\x22pkgname\\x22:\\x22com.ishow.client.android.plugin.company\\x22,\\x22tid\\x22:\\x22e865914020216947\\x22} \"Dalvik/1.6.0 (Linux; U; Android 4.4.2; UIMI4 Build/KOT49H)\" \"-\"";
//		ParseWebLogInstance parseWebLogInstance = ParseWebLogInstance.getInstance();
//		com.analysis.common.weblog.LogBean logBean = parseWebLogInstance.parseLog(aa);
//		System.out.println(logBean.getCtime());
//	}
}