package com.analysis.calculate.nginx;

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
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Tuple;

import com.analysis.calculate.common.metadata.Configuration;
import com.analysis.calculate.common.metadata.NginxConstants;
import com.analysis.calculate.common.metadata.NginxTopicType;

public class NginxLogTopology {
	public static void main(String[] args) throws Exception {
		
		final String spoutName = "nginx_spout";
		final String parserBoltName = "parser_bolt";
		final String filterBoltName = "filter_bolt";
		final String activeBoltName = "active_bolt";
		final String activePersistentBoltName = "active_persistent_bolt";
		final String kafkaBoltName = "kafka_bolt";
		
		final String KAFKA_METADATA_BROKER_LIST_KEY = "metadata.broker.list";
		final String KAFKA_METADATA_BROKER_LIST_VALUE = "server-98:9092";
		final String KAFKA_SERIALIZER_CLASS_KEY = "serializer.class";
		final String KAFKA_SERIALIZER_CLASS_VALUE = "kafka.serializer.StringEncoder";
		
		final int STORM_WORKER_NUM = 4;
		
		BrokerHosts brokerHosts = new ZkHosts(Configuration.ZOOKEEPER_KAFKA_ROOT_PATH, Configuration.ZOOKEEPER_KAFKA_BROKER_PATH);
		SpoutConfig spoutConfig = new SpoutConfig(brokerHosts, NginxTopicType.NGINX.getTopic(), NginxConstants.KAFKA_ZKROOT_PREFIX+NginxTopicType.NGINX.getTopic(), spoutName);
		spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());

		// 配置KafkaBolt中的kafka.broker.properties
		Config conf = new Config();
		Map<String, String> map = new HashMap<String, String>();
		map.put(KAFKA_METADATA_BROKER_LIST_KEY, KAFKA_METADATA_BROKER_LIST_VALUE);
		map.put(KAFKA_SERIALIZER_CLASS_KEY, KAFKA_SERIALIZER_CLASS_VALUE);
		conf.put(KafkaBolt.KAFKA_BROKER_PROPERTIES, map);

		
		KafkaBolt<String, String> kafkaPersistentBolt = new KafkaBolt<String, String>();
		kafkaPersistentBolt.withTopicSelector(new KafkaTopicSelector() {
			private static final long serialVersionUID = 6214197204121214783L;

			@Override
			public String getTopic(Tuple tuple) {
				String topic = tuple.getStringByField(NginxLogFilterBolt.FIELDS_TYPE);
				if(topic == null || "".equals(topic)) {
					return NginxTopicType.OTHER.getTopic();
				} else {
					return topic;
				}
			}
		});
		kafkaPersistentBolt.withTupleToKafkaMapper(new FieldNameBasedTupleToKafkaMapper<String, String>(NginxLogFilterBolt.FIELDS_TYPE, NginxLogFilterBolt.FIELDS_CONTENT));
		
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout(spoutName, new KafkaSpout(spoutConfig));
		builder.setBolt(parserBoltName, new NginxLogParserBolt()).shuffleGrouping(spoutName);
		builder.setBolt(filterBoltName, new NginxLogFilterBolt()).shuffleGrouping(parserBoltName);
		builder.setBolt(kafkaBoltName, kafkaPersistentBolt).shuffleGrouping(filterBoltName);
		builder.setBolt(activeBoltName, new NginxLogActiveBolt()).shuffleGrouping(filterBoltName);
		builder.setBolt(activePersistentBoltName, kafkaPersistentBolt).shuffleGrouping(activeBoltName);

		if (args != null && args.length > 0) {
			conf.setNumWorkers(STORM_WORKER_NUM);
			StormSubmitter.submitTopology("nginx", conf, builder.createTopology());
		} else {
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("nginx_local", conf, builder.createTopology());
//			Utils.sleep(100000);
//			cluster.killTopology("local_nginx");
//			cluster.shutdown();
		}
	}
}