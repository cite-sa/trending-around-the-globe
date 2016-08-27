package gr.cite.nasa.www.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Location {

	private String lID;

	private String woeid;

	private String name;

	private int parentID;

	private String country;

	private String countryCode;

	private String url;

	private double centroidLatitude;

	private double centroidLongitude;

	private double northEastLatitude;

	private double northEastLongitude;

	private double southWestLatitude;

	private double southWestLongitude;

	private double northWestLongitude;

	private double northWestLatitude;

	private double southEastLongitude;

	private double southEastLatitude;

	private int popRank;

	private int areaRank;

	private List<String> trendsIDs;

	public String getlID() {
		return lID;
	}

	public void setlID(String lID) {
		this.lID = lID;
	}

	public String getWoeid() {
		return woeid;
	}

	public void setWoeid(String woeid) {
		this.woeid = woeid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParentID() {
		return parentID;
	}

	public void setParentID(int parentid) {
		this.parentID = parentid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public double getCentroidLatitude() {
		return centroidLatitude;
	}

	public void setCentroidLatitude(double centroidLatitude) {
		this.centroidLatitude = centroidLatitude;
	}

	public double getCentroidLongitude() {
		return centroidLongitude;
	}

	public void setCentroidLongitude(double centroidLongitude) {
		this.centroidLongitude = centroidLongitude;
	}

	public double getNorthEastLatitude() {
		return northEastLatitude;
	}

	public void setNorthEastLatitude(double northEastLatitude) {
		this.northEastLatitude = northEastLatitude;
	}

	public double getNorthEastLongitude() {
		return northEastLongitude;
	}

	public void setNorthEastLongitude(double northEastLongitude) {
		this.northEastLongitude = northEastLongitude;
	}

	public double getSouthWestLatitude() {
		return southWestLatitude;
	}

	public void setSouthWestLatitude(double southWestLatitude) {
		this.southWestLatitude = southWestLatitude;
	}

	public double getSouthWestLongitude() {
		return southWestLongitude;
	}

	public void setSouthWestLongitude(double southWestLongitude) {
		this.southWestLongitude = southWestLongitude;
	}

	public double getNorthWestLongitude() {
		return southWestLongitude;
	}

	public void setNorthWestLongitude(double southWestLongitude) {
		this.northWestLongitude = southWestLongitude;
	}

	public double getNorthWestLatitude() {
		return northEastLatitude;
	}

	public void setNorthWestLatitude(double northEastLatitude) {
		this.northWestLatitude = northEastLatitude;
	}

	public double getSouthEastLongitude() {
		return northEastLongitude;
	}

	public void setSouthEastLongitude(double northEastLongitude) {
		this.southEastLongitude = northEastLongitude;
	}

	public double getSouthEastLatitude() {
		return southWestLatitude;
	}

	public void setSouthEastLatitude(double southWestLatitude) {
		this.southEastLatitude = southWestLatitude;
	}

	public int getPopRank() {
		return popRank;
	}

	public void setPopRank(int popRank) {
		this.popRank = popRank;
	}

	public int getAreaRank() {
		return areaRank;
	}

	public void setAreaRank(int areaRank) {
		this.areaRank = areaRank;
	}

	public List<String> getTrendsIDs() {
		if (trendsIDs == null) {
			return new ArrayList<>();
		}
		return trendsIDs;
	}

	public void setTrendsIDs(List<String> trendsIDs) {
		this.trendsIDs = trendsIDs;
	}

	@Override
	public int hashCode() {

		return new HashCodeBuilder(17, 31).append(woeid)
										  .append(name)
										  .append(parentID)
										  .append(country)
										  .append(countryCode)
										  .append(url)
										  .append(centroidLatitude)
										  .append(centroidLongitude)
										  .append(northEastLatitude)
										  .append(northEastLongitude)
										  .append(southWestLatitude)
										  .append(southWestLongitude)
										  .append(areaRank)
										  .append(popRank)
										  .toHashCode();
	}

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof Location)) {
			return false;
		}

		if (obj == this) {
			return true;
		}

		if (obj.getClass() != getClass()) {
			return false;
		}

		Location locObj = (Location) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj))
				.append(woeid, locObj.woeid)
				.append(name, locObj.name)
				.append(parentID, locObj.parentID)
				.append(country, locObj.country)
				.append(countryCode, locObj.countryCode)
				.append(url, locObj.url)
				.append(centroidLatitude, locObj.centroidLatitude)
				.append(centroidLongitude, locObj.centroidLongitude)
				.append(northEastLatitude, locObj.northWestLatitude)
				.append(northEastLongitude, locObj.northEastLatitude)
				.append(southWestLatitude, locObj.southWestLatitude)
				.append(southWestLongitude, locObj.southWestLongitude)
				.append(areaRank, locObj.areaRank)
				.append(popRank, locObj.popRank).isEquals();
	}
}
