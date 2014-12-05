package com.winksi.nginx.topology;

import java.util.HashMap;
import java.util.Map;

import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.StringScheme;
import storm.kafka.ZkHosts;
import storm.kafka.bolt.KafkaBolt;
import storm.kafka.bolt.mapper.FieldNameBasedTupleToKafkaMapper;
import storm.kafka.bolt.selector.KafkaTopicSelector;
//import storm.kafka.bolt.mapper.FieldNameBasedTupleToKafkaMapper;
//import storm.kafka.bolt.selector.KafkaTopicSelector;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Tuple;
import backtype.storm.utils.Utils;

import com.winksi.calculate.common.metadata.Configuration;
import com.winksi.calculate.common.metadata.Constants;
import com.winksi.calculate.common.metadata.TopicType;

public class NginxLogTopology {
	public static void main(String[] args) throws Exception {
		
		final String spoutName = "nginx_spout";
		final String parserBoltName = "parser_bolt";
		final String kafkaBoltName = "kafka_bolt";
		
		final String KAFKA_METADATA_BROKER_LIST_KEY = "metadata.broker.list";
		final String KAFKA_METADATA_BROKER_LIST_VALUE = "server-98:9092";
		
		final int STORM_WORKER_NUM = 4;
		
//		final String KAFKA_SERIALIZER_CLASS_KEY = "serializer.class";
//		final String KAFKA_SERIALIZER_CLASS_VALUE = "kafka.serializer.StringEncoder";
		
		BrokerHosts brokerHosts = new ZkHosts(Configuration.ZOOKEEPER_KAFKA_ROOT_PATH, Configuration.ZOOKEEPER_KAFKA_BROKER_PATH);
		SpoutConfig spoutConfig = new SpoutConfig(brokerHosts, TopicType.NGINX.getTopic(), Constants.KAFKA_ZKROOT_PREFIX+TopicType.NGINX.getTopic(), spoutName);
		spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());

		
		// 配置KafkaBolt中的kafka.broker.properties
		Config conf = new Config();
		Map<String, String> map = new HashMap<String, String>();
		map.put(KAFKA_METADATA_BROKER_LIST_KEY, KAFKA_METADATA_BROKER_LIST_VALUE);
//		map.put(KAFKA_SERIALIZER_CLASS_KEY, "kafka.serializer.StringEncoder");
		conf.put(KafkaBolt.KAFKA_BROKER_PROPERTIES, map);
//		conf.put(KafkaBolt.TOPIC, spoutId);

		
		KafkaBolt<String, String> kafkaBolt = new KafkaBolt<String, String>();
		kafkaBolt.withTopicSelector(new KafkaTopicSelector() {
			private static final long serialVersionUID = 6214197204121214783L;

			@Override
			public String getTopic(Tuple tuple) {
				String type = tuple.getStringByField(NginxLogParserBolt.FIELDS_TYPE);
				String topic = Constants.CODE2TOPIC.get(type);
				if(topic == null || "".equals(topic)) {
					return Constants.CODE2TOPIC.get(TopicType.OTHER.getCode());
				} else {
					return topic;
				}
			}
		});
		kafkaBolt.withTupleToKafkaMapper(new FieldNameBasedTupleToKafkaMapper<String, String>(NginxLogParserBolt.FIELDS_TYPE, NginxLogParserBolt.FIELDS_CONTENT));
		
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout(spoutName, new KafkaSpout(spoutConfig));
		builder.setBolt(parserBoltName, new NginxLogParserBolt()).shuffleGrouping(spoutName);
		builder.setBolt(kafkaBoltName, kafkaBolt).shuffleGrouping(parserBoltName);

		if (args != null && args.length > 0) {
			conf.setNumWorkers(STORM_WORKER_NUM);
			StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
		} else {
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("local_nginx", conf, builder.createTopology());
			Utils.sleep(100000);
			cluster.killTopology("local_nginx");
			cluster.shutdown();
		}
	}
}