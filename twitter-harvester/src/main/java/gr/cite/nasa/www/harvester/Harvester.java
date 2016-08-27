package gr.cite.nasa.www.harvester;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gr.cite.nasa.www.client.TwitterApiClient;
import gr.cite.nasa.www.core.Location;
import gr.cite.nasa.www.core.Place;
import gr.cite.nasa.www.core.TrendWrapper;
import gr.cite.nasa.www.core.TrendWrapperInfo;
import gr.cite.nasa.www.queries.GeoPlanetQueries;
import gr.cite.nasa.www.queries.TwitterQueries;
import gr.cite.nasa.www.datastore.DatastoreClient;
import gr.cite.nasa.www.datastore.mongo.MongoDatastoreClient;

public class Harvester {
	
	private static final Logger logger = LoggerFactory.getLogger(Harvester.class);

	private DatastoreClient datastoreClient;

	private TwitterApiClient twitterClient;

	public Harvester() {
		datastoreClient = new MongoDatastoreClient();
		twitterClient = new TwitterQueries("jDDsnpBYjRc2VI4ahrudyL6b2",
				"YpLWuEAYgZO27KQAsZc9nI4JEYwM7oMEHEOXBimuDSE3DuKxHH");
	}
	public Harvester(DatastoreClient datastoreClient, TwitterApiClient twitterClient) {
		this.datastoreClient = datastoreClient;
		this.twitterClient = twitterClient;
	}

	public void harvest() throws JAXBException, IOException {

		List<Location> listOfLocations = twitterClient.getAvailableTrends();

		GeoPlanetQueries geoPlanetQueries = new GeoPlanetQueries(
				"dj0yJmk9MWt2a3Rxb0kxN0ZMJmQ9WVdrOWQxUnVSVkZPT" + "ldVbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD1jZA");

		List<Location> listOfLocationsWithGeo = new ArrayList<Location>();

		logger.info("Twitter locations acquired");
		
		for (Location location : listOfLocations) {

			Place place = geoPlanetQueries.woeidIntoBB(location.getWoeid());

			location.setCentroidLatitude(place.getCentroid().getLatitude());
			location.setCentroidLongitude(place.getCentroid().getLongitude());
			location.setNorthEastLatitude(place.getBoundingBox().getNorthEast().getLatitude());
			location.setNorthEastLongitude(place.getBoundingBox().getNorthEast().getLongitude());
			location.setSouthWestLatitude((place.getBoundingBox().getSouthWest().getLatitude()));
			location.setSouthWestLongitude(place.getBoundingBox().getSouthWest().getLongitude());
			location.setAreaRank(place.getAreaRank());
			location.setPopRank(place.getPopRank());

			listOfLocationsWithGeo.add(location);
		}
		
		List<TrendWrapper> trends = datastoreClient.listTrends(null, null);
		
		datastoreClient.setAllTrendsToInactive(trends);
		
		//datastoreClient.insertLocationData(listOfLocationsWithGeo);

		for (Location location : listOfLocationsWithGeo) {
			
			//checkDuplicateLocations(location);
			datastoreClient.insertLocation(location);
		}

		int counter = 0;
		
		for (Location location : listOfLocationsWithGeo) {
			
			counter++; 
			
			if ((counter % 15) == 0){ 
				try { 
					Thread.sleep(900000);
				} catch (InterruptedException e){ 
					logger.error(e.getMessage(), e);
				} 
			}

			List<TrendWrapper> listOfTrends = twitterClient.getTrendsPlace(location);
			logger.info("Twitter trends acquired");
			
			//datastoreClient.insertTrendData(listOfTrends, location.getlID());
			
			for (TrendWrapper trendWrapper : listOfTrends) {
								
				datastoreClient.insertTrend(trendWrapper, location.getlID());
				datastoreClient.insertTrendIntoLocation(location.getlID(), trendWrapper.getTid());
							
			}
		}
	}	
	
	/*for (TrendWrapper trendWrapper : listOfTrends) {
		
		datastoreClient.insertTrendIntoLocation(location.getlID(), trendWrapper.getTid());
	}*/

	/*
	  
	 */

	public void checkDuplicateLocations(Location location) {

		Location locationInDB = datastoreClient.getLocationByWoeid(location.getWoeid());

		if (!location.equals(locationInDB)) {
			datastoreClient.insertLocation(location);
		}
	}

	public static void main(String[] args) {

		DatastoreClient datastoreClient = new MongoDatastoreClient();
		TwitterApiClient twitterClient = new TwitterQueries("jDDsnpBYjRc2VI4ahrudyL6b2",
				"YpLWuEAYgZO27KQAsZc9nI4JEYwM7oMEHEOXBimuDSE3DuKxHH");

		Harvester harvester = new Harvester(datastoreClient, twitterClient);
		
		try {
			harvester.harvest();
		} catch (JAXBException | IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
}