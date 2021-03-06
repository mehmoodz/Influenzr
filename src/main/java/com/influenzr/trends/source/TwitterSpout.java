package com.influenzr.trends.source;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.FilterQuery;
import twitter4j.Status;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

import com.influenzr.trends.vendor.TwitterStreamer;

public class TwitterSpout extends BaseRichSpout {
	private static final long serialVersionUID = 6652429357925759307L;
	final static Logger log = LoggerFactory.getLogger(TwitterSpout.class);
	private SpoutOutputCollector collector = null;
	private LinkedBlockingQueue<Status> tweetQueue = null;

	private static String[] keywords = new String[] { "startup" };
	private static final String field = "tweet";

	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		// TODO Auto-generated method stub
		this.collector = collector;
		this.tweetQueue = new LinkedBlockingQueue<Status>();
		TwitterStreamer streamer = new TwitterStreamer(this.tweetQueue);
		final FilterQuery filterQuery = new FilterQuery();
		log.info("Listening to:",this.keywords);
		filterQuery.track(this.keywords);
		streamer.stream().filter(filterQuery);
	}

	public void nextTuple() {
		// TODO Auto-generated method stub
		log.info("Polling for next tuple...");
		final Status status = this.tweetQueue.poll();
		if (status == null) {
			Utils.sleep(10000);
		}
		this.collector.emit(new Values(status));
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields(this.field));
	}

}
