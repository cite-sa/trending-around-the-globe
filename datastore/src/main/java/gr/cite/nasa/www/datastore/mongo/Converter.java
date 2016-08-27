package gr.cite.nasa.www.datastore.mongo;

import gr.cite.nasa.www.core.Location;
import gr.cite.nasa.www.core.TrendWrapper;
import gr.cite.nasa.www.core.TrendWrapperInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;


public class Converter {
	

	public static Location documentToLocation(Document document) {

		Location location = new Location();

		String convLid = document.getObjectId("_id").toString();
		if (convLid != null) {
			location.setlID(convLid);
		}

		String convWoeid = document.getString("woeid");
		if (convWoeid != null) {
			location.setWoeid(convWoeid);
		}

		String convName = document.getString("name");
		if (convName != null) {
			location.setName(convName);
		}

		Integer convParentID = document.getInteger("parentid");
		if (convParentID != null) {
			location.setParentID(convParentID);
		}

		String convCountry = document.getString("country");
		if (convCountry != null) {
			location.setCountry(convCountry);
		}

		String convCountryCode = document.getString("countryCode");
		if (convCountryCode != null) {
			location.setCountryCode(convCountryCode);
		}

		String convUrl = document.getString("url");
		if (convUrl != null) {
			location.setUrl(convUrl);
		}

		Double convCentroidLatitude = document.getDouble("centroidLatitude");
		if (convCentroidLatitude != null) {
			location.setCentroidLatitude(convCentroidLatitude);
		}

		Double convCentroidLongitude = document.getDouble("centroidLongitude");
		if (convCentroidLongitude != null) {
			location.setCentroidLongitude(convCentroidLongitude);
		}

		Double convNorthEastLatitude = document.getDouble("northEastLatitude");
		if (convNorthEastLatitude != null) {
			location.setNorthEastLatitude(convNorthEastLatitude);
		}

		Double convNorthEastLongitude = document.getDouble("northEastLongitude");
		if (convNorthEastLongitude != null) {
			location.setNorthEastLongitude(convNorthEastLongitude);
		}

		Double convSouthWestLatitude = document.getDouble("southWestLatitude");
		if (convSouthWestLatitude != null) {
			location.setSouthWestLatitude(convSouthWestLatitude);
		}

		Double convSouthWestLongitude = document.getDouble("southWestLongitude");
		if (convSouthWestLongitude != null) {
			location.setSouthWestLongitude(convSouthWestLongitude);
		}

		Double convNorthWestLatitude = document.getDouble("northWestLatitude");
		if (convNorthWestLatitude != null) {
			location.setNorthWestLatitude(convNorthEastLatitude);
		}

		Double convNorthWestLongitude = document.getDouble("northWestLongitude");
		if (convNorthWestLongitude != null) {
			location.setNorthWestLongitude(convSouthWestLongitude);
		}

		Double convSouthEastLatitude = document.getDouble("southEastLatitude");
		if (convSouthEastLatitude != null) {
			location.setSouthEastLatitude(convSouthWestLatitude);
		}

		Double convSouthEastLongitude = document.getDouble("southEastLongitude");
		if (convSouthEastLongitude != null) {
			location.setSouthEastLongitude(convNorthWestLongitude);
		}

		Integer convPopRank = document.getInteger("popRank");
		if (convPopRank != null) {
			location.setPopRank(convPopRank);
		}

		Integer convAreaRank = document.getInteger("areaRank");
		if (convAreaRank != null) {
			location.setAreaRank(convAreaRank);
		}

		List<String> trendsList = new ArrayList<String>();
		@SuppressWarnings("unchecked") // TODO relook
		List<Document> trends = (List<Document>) document.get("trends");

		if (trends != null) {
			for (Document document2 : trends) {
				String convTID = document2.getString("tid");
				if (convTID != null) {
					trendsList.add(convTID);
				}
			}
			location.setTrendsIDs(trendsList);
		}
		return location;
	}

	public static Location documentToNoTrendsLocation(Document document) {

		Location location = new Location();

		String convLid = document.getObjectId("_id").toString();
		if (convLid != null) {
			location.setlID(convLid);
		}

		String convWoeid = document.getString("woeid");
		if (convWoeid != null) {
			location.setWoeid(convWoeid);
		}

		String convName = document.getString("name");
		if (convName != null) {
			location.setName(convName);
		}

		Integer convParentID = document.getInteger("parentid");
		if (convParentID != null) {
			location.setParentID(convParentID);
		}

		String convCountry = document.getString("country");
		if (convCountry != null) {
			location.setCountry(convCountry);
		}

		String convCountryCode = document.getString("countryCode");
		if (convCountryCode != null) {
			location.setCountryCode(convCountryCode);
		}

		String convUrl = document.getString("url");
		if (convUrl != null) {
			location.setUrl(convUrl);
		}

		Double convCentroidLatitude = document.getDouble("centroidLatitude");
		if (convCentroidLatitude != null) {
			location.setCentroidLatitude(convCentroidLatitude);
		}

		Double convCentroidLongitude = document.getDouble("centroidLongitude");
		if (convCentroidLongitude != null) {
			location.setCentroidLongitude(convCentroidLongitude);
		}

		Double convNorthEastLatitude = document.getDouble("northEastLatitude");
		if (convNorthEastLatitude != null) {
			location.setNorthEastLatitude(convCentroidLongitude);
		}

		Double convNorthEastLongitude = document.getDouble("northEastLongitude");
		if (convNorthEastLongitude != null) {
			location.setNorthEastLongitude(convCentroidLongitude);
		}

		Double convSouthWestLatitude = document.getDouble("southWestLatitude");
		if (convSouthWestLatitude != null) {
			location.setSouthWestLatitude(convSouthWestLatitude);
		}

		Double convSouthWestLongitude = document.getDouble("southWestLongitude");
		if (convSouthWestLongitude != null) {
			location.setSouthWestLongitude(convSouthWestLongitude);
		}

		Double convNorthWestLatitude = document.getDouble("northWestLatitude");
		if (convNorthWestLatitude != null) {
			location.setNorthWestLatitude(convSouthWestLatitude);
		}

		Double convNorthWestLongitude = document.getDouble("northWestLongitude");
		if (convNorthWestLongitude != null) {
			location.setNorthWestLongitude(convNorthEastLongitude);
		}

		Double convSouthEastLatitude = document.getDouble("southEastLatitude");
		if (convSouthEastLatitude != null) {
			location.setSouthEastLatitude(convNorthEastLatitude);
		}

		Double convSouthEastLongitude = document.getDouble("southEastLongitude");
		if (convSouthEastLongitude != null) {
			location.setSouthEastLongitude(convSouthWestLongitude);
		}

		Integer convPopRank = document.getInteger("popRank");
		if (convPopRank != null) {
			location.setPopRank(convPopRank);
		}

		Integer convAreaRank = document.getInteger("areaRank");
		if (convAreaRank != null) {
			location.setAreaRank(convAreaRank);
		}
		
		return location;
	}

	public static TrendWrapper documentToTrend(Document document) {

		TrendWrapper trendWrapper = new TrendWrapper();

		ObjectId convTID = document.getObjectId("_id");
		if (convTID != null) {
			trendWrapper.setTid(convTID.toString());
		}

		String convName = document.getString("name");
		/*convName = convName.replaceAll("#", "");*/
		if (convName != null) {
			trendWrapper.setName(convName);
		}

		String convUrl = document.getString("url");
		if (convUrl != null) {
			trendWrapper.setUrl(convUrl);
		}

		String convQuery = document.getString("query");
		if (convQuery != null) {
			trendWrapper.setQuery(convQuery);
		}

		@SuppressWarnings("unchecked") // TODO relook
		List<Document> locations = (List<Document>) document.get("locations");
		List<TrendWrapperInfo> trendWrapperInfoList = new ArrayList<>();

		if (locations != null) {
			
			for (Document location : locations) {
				
				TrendWrapperInfo trendWrapperInfo = new TrendWrapperInfo();

				String convLID = location.getString("lid");
				if (convLID != null) {
					trendWrapperInfo.setlID(convLID);
				}
				
/*				String convLocationName = location.getString("name");
				if (convLocationName !=null) {
					trendWrapperInfo.setName(convLocationName);
				}*/

				Integer convTweetVolume = location.getInteger("tweet_volume");
				if (convTweetVolume != null) {
					trendWrapperInfo.setTweet_volume(convTweetVolume);
				}

				Date convLocalDateTime = location.getDate("timestamp");
				if (convLocalDateTime != null) {
					trendWrapperInfo.setTimestamp(convLocalDateTime.toInstant());
				}
				
				Integer convActive = location.getInteger("active");
				if (convActive != null) {
					trendWrapperInfo.setActive(convActive);
				}
				
				trendWrapperInfoList.add(trendWrapperInfo);
			}
			trendWrapper.setLocations(trendWrapperInfoList);
		}

		return trendWrapper;
	}

	public static Document trendToDocument(TrendWrapper trendWrapper) {

		Document document = new Document();

		document.append("name", trendWrapper.getName()).append("url", trendWrapper.getUrl()).append("query",trendWrapper.getQuery());

		List<Document> locationsList = new ArrayList<>();

		for (Document document2 : locationsList) {

			List<TrendWrapperInfo> trendWrapperInfoList = trendWrapper.getLocations();

			for (TrendWrapperInfo trendWrapperInfo : trendWrapperInfoList) {
				document2.append("lid", trendWrapperInfo.getlID())
						 .append("tweet_volume", trendWrapperInfo.getTweet_volume())
				         .append("active" , trendWrapperInfo.getActive());//TODO ?

				Date myDate = Date.from(trendWrapperInfo.getTimestamp());
				document2.append("timestamp", myDate);
			}
		}
		document.append("locations", locationsList);

		return document;
	}

	public static Document trendToNoLocationDocument(TrendWrapper trendWrapper) {

		Document document = new Document();

		document.append("name", trendWrapper.getName()).append("url", trendWrapper.getUrl()).
		append("query",trendWrapper.getQuery());

		return document;
	}

	public static Document locationToDocument(Location location) {

		Document document = new Document();

		document.append("name", location.getName()).append("parentid", location.getParentID())
				.append("country", location.getCountry()).append("woeid", location.getWoeid())
				.append("countryCode", location.getCountryCode()).append("url", location.getUrl())
				.append("centroidLatitude", location.getCentroidLatitude())
				.append("centroidLongitude", location.getCentroidLongitude())
				.append("northEastLatitude", location.getNorthEastLatitude())
				.append("northEastLongitude", location.getNorthEastLongitude())
				.append("southWestLatitude", location.getSouthWestLatitude())
				.append("southWestLongitude", location.getSouthWestLongitude())
				.append("northWestLatitude", location.getNorthWestLatitude())
				.append("northWestLongitude", location.getNorthWestLongitude())
				.append("southEastLatitude", location.getSouthEastLatitude())
				.append("southEastLongitude", location.getSouthEastLongitude()).append("popRank", location.getPopRank())
				.append("areaRank", location.getAreaRank())
				.append("trends", location.getTrendsIDs());

		return document;
	}

	public static Document locationToNoTrendDocument(Location location) {

		Document document = new Document();

		document.append("name", location.getName())
				.append("parentid", location.getParentID())
				.append("country", location.getCountry())
				.append("woeid", location.getWoeid())
				.append("countryCode", location.getCountryCode())
				.append("url", location.getUrl())
				.append("centroidLatitude", location.getCentroidLatitude())
				.append("centroidLongitude", location.getCentroidLongitude())
				.append("northEastLatitude", location.getNorthEastLatitude())
				.append("northEastLongitude", location.getNorthEastLongitude())
				.append("southWestLatitude", location.getSouthWestLatitude())
				.append("southWestLongitude", location.getSouthWestLongitude())
				.append("northWestLatitude", location.getNorthWestLatitude())
				.append("northWestLongitude", location.getNorthWestLongitude())
				.append("southEastLatitude", location.getSouthEastLatitude())
				.append("southEastLongitude", location.getSouthEastLongitude())
				.append("popRank", location.getPopRank())
				.append("areaRank", location.getAreaRank());

		return document;
	}
}