package com.startup.trends.publish;

import java.util.Map;

import backtype.storm.task.ShellBolt;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;

public class WebSocketBolt extends ShellBolt implements IRichBolt{
	private static final long serialVersionUID = 7247834849890513770L;
	
	public WebSocketBolt() {
		// TODO Auto-generated constructor stub
		super("python","websocket.py");
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("count"));
	}

	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	 
}
