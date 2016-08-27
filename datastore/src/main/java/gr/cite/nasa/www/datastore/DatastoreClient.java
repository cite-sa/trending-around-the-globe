package gr.cite.nasa.www.datastore;

import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import gr.cite.nasa.www.core.Location;
import gr.cite.nasa.www.core.TrendWrapper;
import gr.cite.nasa.www.core.TrendWrapperInfo;

public interface DatastoreClient {

	void insertLocation(Location location);

	Location getLocationByWoeid(String woeid);

	List<TrendWrapper> getTrendByName(String name);

	List<TrendWrapper> listTrends(Integer limit,Integer flag);

	List<Location> listLocations();

	void insertLocationData(List<Location> loclist);

	void insertTrendData(List<TrendWrapper> trendList, String lid);

	List<TrendWrapper> topXTrendsToday(int counter);

	void insertTrendIntoLocation(String lid, String tid);

	Location getNoTrendsLocation(String woeid);

	List<Location> listLocationsWithNoTrends();

	void insertTrend(TrendWrapper trendWrapper, String lid);

	List<Location> getLocationByName(String name);

	List<Document> getLocationDocuments();

	List<Document> getTrendWrapperDocuments();
	
	List<TrendWrapperInfo> topTrendsPerLocation(String woeid, Integer limit, Integer active);

	Document getDocumentById(String id, MongoCollection<Document> mongoCollection);
	
	Document getTrendWrapperDocumentByQuery(String query);
	
	TrendWrapper getTrendByQuery(String query);
	
	List<TrendWrapper> getTrendByReguLarExpression(String name);

	List<Location> getLocationsForTrend(String name, Integer flag);

	List<Location> getLocationsByPopRank(Integer popRank);

	List<Location> getLocationsByAreaRank(Integer areaRank);
	
	void setAllTrendsToInactive(List<TrendWrapper> trends);
}