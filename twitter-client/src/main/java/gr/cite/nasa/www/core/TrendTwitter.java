package gr.cite.nasa.www.core;

public class TrendTwitter {
	
	private String name;
	
    private String url;
    
    private String promoted_content;
    
    private String query;
    
    private int tweet_volume;
    
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public String getQuery() {
		return query;
	}
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	public int getTweet_volume() {
		return tweet_volume;
	}
	
	public void setTweet_volume(int tweet_volume) {
		this.tweet_volume = tweet_volume;
	}
	
	public String getPromoted_content() {
		return promoted_content;
	}
	
	public void setPromoted_content(String promoted_content) {
		this.promoted_content = promoted_content;
	}

}
