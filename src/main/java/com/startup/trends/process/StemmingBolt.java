package com.startup.trends.process;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

/**
 * @author mkhan
 *
 */
public class StemmingBolt extends BaseRichBolt {

	private static final long serialVersionUID = -4922677756054234041L;
	private OutputCollector outputCollector;

	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see backtype.storm.task.IBolt#execute(backtype.storm.tuple.Tuple)
	 */
	public void execute(Tuple input) {
		// TODO Auto-generated method stub
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
	}
	 
}
