package gr.cite.nasa.www.core;

import java.util.List;

public class TrendWrapper {

	private String tid;

	private String name;

	private String url;

	private String promoted_content;

	private String query;

	private List<TrendWrapperInfo> locations;

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

	public String getPromoted_content() {
		return promoted_content;
	}

	public void setPromoted_content(String promoted_content) {
		this.promoted_content = promoted_content;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public List<TrendWrapperInfo> getLocations() {
		return locations;
	}

	public void setLocations(List<TrendWrapperInfo> locations) {
		this.locations = locations;
	}	
}