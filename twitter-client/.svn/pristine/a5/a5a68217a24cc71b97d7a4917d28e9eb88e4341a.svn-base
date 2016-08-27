package gr.cite.nasa.www.core;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TrendsTwitterResponse {
	
	@JsonProperty("as_of")
	private Date asOf;
	
	@JsonProperty("created_at")
	private Date createdAt;
	
	private List <LocationTwitter> locations;
	
	private List <TrendTwitter> trends;

	public List<LocationTwitter> getLocations() {
		return locations;
	}

	public void setLocations(List<LocationTwitter> locations) {
		this.locations = locations;
	}

	public List<TrendTwitter> getTrends() {
		return trends;
	}

	public void setTrends(List<TrendTwitter> trends) {
		this.trends = trends;
	}

	public Date getAsOf() {
		return asOf;
	}

	public void setAsOf(Date asOf) {
		this.asOf = asOf;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
