package com.startup.trends.source;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import twitter4j.FilterQuery;
import twitter4j.Status;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

import com.startup.trends.vendor.TwitterStreamer;

public class TwitterSpout extends BaseRichSpout {
	private static final long serialVersionUID = 6652429357925759307L;

	private SpoutOutputCollector collector = null;
	private LinkedBlockingQueue<Status> tweetQueue = null;

	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		// TODO Auto-generated method stub
		this.collector = collector;
		this.tweetQueue = new LinkedBlockingQueue<Status>();
		TwitterStreamer streamer = new TwitterStreamer(tweetQueue);
		final FilterQuery filterQuery = new FilterQuery();
		filterQuery.track(new String[] { "startup" });
		streamer.stream().filter(filterQuery);
	}

	public void nextTuple() {
		// TODO Auto-generated method stub
		final Status status = this.tweetQueue.poll();
		if (status == null) {
			Utils.sleep(10000);
		}
		this.collector.emit(new Values(status));
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("tweet"));
	}

}
