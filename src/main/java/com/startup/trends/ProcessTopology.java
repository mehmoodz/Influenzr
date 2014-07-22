package com.startup.trends;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;

import com.startup.trends.process.TweetCounterBolt;
import com.startup.trends.publish.WebSocketBolt;
import com.startup.trends.source.TwitterSpout;

/**
 * @author mkhan
 * 
 */
public class ProcessTopology {
	final static Logger log = LoggerFactory.getLogger(ProcessTopology.class);
	public static void main(String[] args) throws Exception {
		TopologyBuilder builder = new TopologyBuilder();
		log.info("Topology twitterSpout->countBolt->websocket Started..");
		builder.setSpout("twitterSpout", new TwitterSpout(), 1);
		builder.setBolt("countBolt", new TweetCounterBolt(), 1).shuffleGrouping("twitterSpout");
		builder.setBolt("websocket", new WebSocketBolt(), 8).shuffleGrouping(
				"countBolt");

		Config conf = new Config();
		conf.setDebug(false);

		//Cluster mode
		if (args != null && args.length > 0) {
			conf.setNumWorkers(3);
			log.info("Running Cluster Mode..");
			StormSubmitter.submitTopology(args[0], conf,
					builder.createTopology());
		} else {
			//Local Mode
			log.info("Running Local Mode..");
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("clusterStartup", conf,
					builder.createTopology());
//			Utils.sleep(10000);
//			cluster.killTopology("clusterStartup");
//			cluster.shutdown();
		}

	}
}
