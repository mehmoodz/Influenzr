package com.influenzr.trends.vendor;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * @author mkhan
 * 
 */
public class TwitterStreamer {
	private String ACCESS_TOKEN = "17955821-Dn9N6SrQrxSG1mN2JXAar1EvK0ZkaVShHhdakAntK";
	private String ACCESS_TOKEN_SECRET = "0nUTVYpG9rjUvb5Gvlgd1hOHm4bljcGTYQPlH8cj45Dw0";
	private String CONSUMER_KEY = "g8vu8INAnuLEELBvkFywfIAKk";
	private String CONSUMER_SECRET = "Qjd7htZQOgUiKDDiZHdjvpQZUp2faxf86tPB1p4wrHEBYVbjYf";

	private LinkedBlockingQueue<Status> streamQueue;
	private TwitterStream twitterStream;

	final Logger log = LoggerFactory.getLogger(TwitterStreamer.class);

	public TwitterStreamer(LinkedBlockingQueue<Status> streamQueue) {
		// TODO Auto-generated constructor stub
		this.streamQueue = streamQueue;
	}

	protected ConfigurationBuilder configuration() {
		final ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
		configurationBuilder.setIncludeEntitiesEnabled(true);
		configurationBuilder.setOAuthAccessToken(ACCESS_TOKEN);
		configurationBuilder.setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
		configurationBuilder.setOAuthConsumerKey(CONSUMER_KEY);
		configurationBuilder.setOAuthConsumerSecret(CONSUMER_SECRET);

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
		log.info("Twitter Stream Started");
		return this.twitterStream;
	}

	public void stop() {
		this.twitterStream.cleanUp();
		this.twitterStream.shutdown();
		log.info("Twitter Stream Stopped");
	}

}
