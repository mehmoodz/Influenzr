package com.startup.trends.process;

import java.util.Map;

import twitter4j.Status;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class TweetCounterBolt extends BaseRichBolt {

	private static final long serialVersionUID = -4922677756054234041L;
	private OutputCollector outputCollector;
	private long tweetCount=0;

	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		// TODO Auto-generated method stub
		
	}

	public void execute(Tuple input) {
		// TODO Auto-generated method stub
		final Status status = (Status) input.getValueByField("tweet");
		this.outputCollector.emit(new Values(++tweetCount, status));
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("count", "tweet"));
	}
	 
}
