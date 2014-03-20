package com.startup.trends.vendor;

import java.util.concurrent.LinkedBlockingQueue;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterStreamer {
	private String OAUTH_ACCESS_TOKEN = "";
	private String OAUTH_ACCESS_TOKEN_SECRET = "";
	private String OAUTH_CONSUMER_KEY = "";
	private String OAUTH_CONSUMER_SECRET = "";

	private LinkedBlockingQueue<Status> streamQueue;
	private TwitterStream twitterStream;
	public TwitterStreamer(LinkedBlockingQueue<Status> streamQueue) {
		// TODO Auto-generated constructor stub
		this.streamQueue = streamQueue;
	}

	protected ConfigurationBuilder configuration() {
		final ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
		configurationBuilder.setIncludeEntitiesEnabled(true);
		configurationBuilder.setOAuthAccessToken(OAUTH_ACCESS_TOKEN);
		configurationBuilder
				.setOAuthAccessTokenSecret(OAUTH_ACCESS_TOKEN_SECRET);
		configurationBuilder.setOAuthConsumerKey(OAUTH_CONSUMER_KEY);
		configurationBuilder.setOAuthConsumerSecret(OAUTH_CONSUMER_SECRET);

		return configurationBuilder;
	}

	protected StatusListener statusListener() {
		final StatusListener statusListener = new StatusListener() {

			public void onException(Exception arg0) {
				// TODO Auto-generated method stub

			}

			public void onDeletionNotice(StatusDeletionNotice arg0) {
				// TODO Auto-generated method stub

			}

			public void onScrubGeo(long arg0, long arg1) {
				// TODO Auto-generated method stub

			}

			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub

			}

			public void onStatus(Status status) {
				// TODO Auto-generated method stub
				streamQueue.offer(status);
			}

			public void onTrackLimitationNotice(int arg0) {
				// TODO Auto-generated method stub

			}
		};

		return statusListener;
	}

	public TwitterStream stream() {
		TwitterStreamFactory streamFactory = new TwitterStreamFactory(
				configuration().build());
		this.twitterStream = streamFactory.getInstance();
		this.twitterStream.addListener(statusListener());
		return this.twitterStream;
	}
	
	public void stop(){
		this.twitterStream.cleanUp();
		this.twitterStream.shutdown();
	}

}
