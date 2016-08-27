package gr.cite.nasa.www.harvester;

import org.apache.commons.lang3.RandomStringUtils;

import org.junit.Test;

import gr.cite.nasa.www.core.Location;
import gr.cite.nasa.www.core.TrendWrapper;
import gr.cite.nasa.www.datastore.DatastoreClient;
import gr.cite.nasa.www.datastore.mongo.MongoDatastoreClient;

public class HarvesterTest {
	
	private DatastoreClient client;
	
	public HarvesterTest(){
		
		client = new MongoDatastoreClient();
		
	}
	
	private static Location createDummyLocation(){
		
		Location dummyLocation = new Location();
		dummyLocation.setName(RandomStringUtils.randomAlphanumeric(10));
		dummyLocation.setWoeid(RandomStringUtils.randomAlphanumeric(10));
		
		return null;		
	}
	
	private static TrendWrapper createDummyTrend(){
		
		TrendWrapper dummyTrend = new TrendWrapper();
		dummyTrend.setName(RandomStringUtils.randomAlphanumeric(10));
		dummyTrend.setQuery(RandomStringUtils.randomAlphanumeric(10));
		dummyTrend.setUrl(RandomStringUtils.randomAlphanumeric(10));
		
		return dummyTrend;		
	}
	
	public  void main() {
		
		Location dummyLocation1 = createDummyLocation();
		Location dummyLocation2 = createDummyLocation();
		
		TrendWrapper dummyTrend1 = createDummyTrend();
		TrendWrapper dummyTrend2 = createDummyTrend();
		
		
	}

}
