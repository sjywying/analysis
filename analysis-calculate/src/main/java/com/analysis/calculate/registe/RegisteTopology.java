package com.analysis.calculate.registe;

import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.StringScheme;
import storm.kafka.ZkHosts;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;

import com.analysis.calculate.common.metadata.Configuration;
import com.analysis.calculate.common.metadata.Constants;
import com.analysis.calculate.common.metadata.TopicType;

public class RegisteTopology {
	public static void main(String[] args) throws Exception {
		
		final String topologyName = "reg_topology";
		final String spoutId = "reg_spout";
		final String parserBolt = "reg_parser_bolt";
		final String filterBolt = "reg_filter_bolt";
		final String persistentBolt = "reg_persistent_bolt";
		
		final int STORM_WORKER_NUM = 4;
		
		
		BrokerHosts brokerHosts = new ZkHosts(Configuration.ZOOKEEPER_KAFKA_ROOT_PATH, Configuration.ZOOKEEPER_KAFKA_BROKER_PATH);
		
		SpoutConfig spoutConfig = new SpoutConfig(brokerHosts, TopicType.REGISTE.getTopic(), Constants.KAFKA_ZKROOT_PREFIX+TopicType.REGISTE.getTopic(), spoutId);
		spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());

		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout(spoutId, new KafkaSpout(spoutConfig));
		builder.setBolt(parserBolt, new RegisteParserBolt()).shuffleGrouping(spoutId);
		builder.setBolt(filterBolt, new RegisteFilterBolt()).shuffleGrouping(parserBolt);
		builder.setBolt(persistentBolt, new RegistePersistentBolt()).shuffleGrouping(filterBolt);

		Config conf = new Config();
		if (args != null && args.length > 0) {
			conf.setNumWorkers(STORM_WORKER_NUM);
			StormSubmitter.submitTopology(topologyName, conf, builder.createTopology());
		} else {
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology(topologyName + "_local", conf, builder.createTopology());
//			Utils.sleep(100000);
//			cluster.killTopology("active_topology");
//			cluster.shutdown();
		}
	}
}