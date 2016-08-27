package gr.cite.nasa.www.core;

import java.time.Instant;

public class TrendWrapperInfo {

	private String lID;
	
	private String trendId;
	
	private String name;
	
	private String url;

	private int tweet_volume;

	private Instant timestamp;
	
	private int active;

	public String getlID() {
		return lID;
	}

	public void setlID(String lID) {
		this.lID = lID;
	}

	public String getTrendId() {
		return trendId;
	}

	public void setTrendId(String trendId) {
		this.trendId = trendId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getTweet_volume() {
		return tweet_volume;
	}

	public void setTweet_volume(int tweet_volume) {
		this.tweet_volume = tweet_volume;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
