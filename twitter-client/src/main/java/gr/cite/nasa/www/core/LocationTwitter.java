package gr.cite.nasa.www.core;

public class LocationTwitter  {

	private String name;
	
	private LocationPlaceType placeType;
	
	private String url;
	
	private int parentID;
	
	private String country;
	
	private String woeid;
	
	private String countryCode;
	
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
	
	public int getParentid() {
		return parentID;
	}

	public void setParentid(int parentid) {
		this.parentID = parentid;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getWoeid() {
		return woeid;
	}

	public void setWoeid(String woeid) {
		this.woeid = woeid;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public LocationPlaceType getPlaceType() {
		return placeType;
	}

	public void setPlaceType(LocationPlaceType placetype) {
		this.placeType = placetype;
	}
	
}
