package gr.cite.nasa.www.datastore.mongo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.*;

import gr.cite.nasa.www.datastore.mongo.Converter;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;

import gr.cite.nasa.www.core.Location;
import gr.cite.nasa.www.core.TrendWrapper;
import gr.cite.nasa.www.core.TrendWrapperInfo;
import gr.cite.nasa.www.datastore.DatastoreClient;

public class MongoDatastoreClient implements DatastoreClient {

	private MongoClient mongo;
	private MongoDatabase dbtwitter;
	private MongoCollection<Document> locationCollection;
	private MongoCollection<Document> trendsCollection;

	public MongoDatastoreClient() {

		mongo = new MongoClient("localhost:27017");
		dbtwitter = mongo.getDatabase("twitterData");
		locationCollection = dbtwitter.getCollection("locationsTwitter");
		trendsCollection = dbtwitter.getCollection("trendsTwitter");
	}

	@Override
	public void insertLocation(Location location) {

		Document document = Converter.locationToNoTrendDocument(location);
		Document toUpdate = new Document();
		toUpdate.append("$setOnInsert", new Document().append("trends", new ArrayList<>()));

		FindOneAndUpdateOptions updateOptions = new FindOneAndUpdateOptions();
		updateOptions.returnDocument(ReturnDocument.AFTER);
		updateOptions.upsert(true);
		Document updateDocument = locationCollection.findOneAndUpdate(document, toUpdate, updateOptions);

		location.setlID(updateDocument.getObjectId("_id").toString());
	}

	@Override
	public Location getLocationByWoeid(String woeid) {

		Document document = locationCollection.find(Filters.eq("woeid", woeid)).first();
		Location location = Converter.documentToLocation(document);
		return location;
	}

	@Override
	public List<Location> getLocationByName(String name) {

		List<Location> locationList = new ArrayList<>();

		FindIterable<Document> iterable = dbtwitter.getCollection("locationsTwitter").find(eq("name", name));

		iterable.forEach(new Block<Document>() {
			@Override
			public void apply(final Document document) {
				Location location = Converter.documentToLocation(document);
				locationList.add(location);
			}
		});
		return locationList;
	}

	@Override
	public Location getNoTrendsLocation(String woeid) {

		Document document = locationCollection.find(Filters.eq("woeid", woeid)).limit(1).first();
		Location location = Converter.documentToNoTrendsLocation(document);
		return location;
	}

	@Override
	public List<Document> getLocationDocuments() {

		List<Document> locationsDocuments = new ArrayList<>();
		FindIterable<Document> iterable = dbtwitter.getCollection("locationsTwitter").find();

		iterable.forEach(new Block<Document>() {
			@Override
			public void apply(final Document document) {
				locationsDocuments.add(document);
			}
		});
		return locationsDocuments;
	}

	@Override
	public void insertTrend(TrendWrapper trendWrapper, String lid) {

		List<TrendWrapperInfo> trendWrapperInfo = trendWrapper.getLocations();

		Document newLocation = new Document();

		for (TrendWrapperInfo trendWrapperInfo2 : trendWrapperInfo) {
			newLocation.append("lid", lid).append("tweet_volume", trendWrapperInfo2.getTweet_volume()).append("active",
					1);

			Date myDate = Date.from(trendWrapperInfo2.getTimestamp());
			newLocation.append("timestamp", myDate);
		}

		Document document = Converter.trendToNoLocationDocument(trendWrapper);
		Document toUpdate = new Document();
		toUpdate.append("$addToSet", new Document().append("locations", newLocation));

		FindOneAndUpdateOptions updateOptions = new FindOneAndUpdateOptions();
		updateOptions.returnDocument(ReturnDocument.AFTER);
		updateOptions.upsert(true);
		Document updateDocument = trendsCollection.findOneAndUpdate(document, toUpdate, updateOptions);

		trendWrapper.setTid(updateDocument.getObjectId("_id").toString());
	}

	@Override
	public List<TrendWrapper> getTrendByName(String name) {

		List<TrendWrapper> trendList = new ArrayList<>();

		FindIterable<Document> iterable = dbtwitter.getCollection("trendsTwitter").find(eq("name", name));

		iterable.forEach(new Block<Document>() {
			@Override
			public void apply(final Document document) {
				TrendWrapper trend = Converter.documentToTrend(document);
				trendList.add(trend);
			}
		});
		return trendList;
	}

	@Override
	public TrendWrapper getTrendByQuery(String query) {

		TrendWrapper trend = null;

		FindIterable<Document> iterable = dbtwitter.getCollection("trendsTwitter").find(eq("query", query));

		iterable.forEach(new Block<Document>() {
			@Override
			public void apply(final Document document) {
				TrendWrapper trend = Converter.documentToTrend(document);
			}
		});
		return trend;
	}

	@Override
	public List<Document> getTrendWrapperDocuments() {

		List<Document> trendsDocuments = new ArrayList<>();
		FindIterable<Document> iterable = dbtwitter.getCollection("trendsTwitter").find();

		iterable.forEach(new Block<Document>() {
			@Override
			public void apply(final Document document) {
				trendsDocuments.add(document);
			}
		});
		return trendsDocuments;
	}

	@Override
	public Document getTrendWrapperDocumentByQuery(String query) {

		return trendsCollection.find(Filters.eq("query", query)).limit(1).first();
	}
	
	@Override
	public List<TrendWrapper> listTrends(Integer limit, Integer flag) {

		List<TrendWrapper> result = new ArrayList<>();
							
			FindIterable<Document> iterable = trendsCollection.find();
			
			if (limit!=null) {
				iterable = iterable.limit(limit);
			}
			
			iterable.forEach(new Block<Document>() {
				@Override
				public void apply(final Document document) {
					TrendWrapper trend = Converter.documentToTrend(document);
					result.add(trend);
				}
			});
		
		return result;
	}

	@Override
	public List<Location> listLocations() {

		MongoCursor<Document> cursor = locationCollection.find().iterator();
		List<Location> result = new ArrayList<Location>();

		try {

			while (cursor.hasNext()) {

				result.add(Converter.documentToLocation(cursor.next()));
			}

		} finally {

			cursor.close();
		}
		return result;
	}

	@Override
	public List<Location> listLocationsWithNoTrends() {

		MongoCursor<Document> cursor = locationCollection.find().iterator();
		List<Location> result = new ArrayList<Location>();

		try {

			while (cursor.hasNext()) {

				result.add(Converter.documentToNoTrendsLocation(cursor.next()));
			}

		} finally {

			cursor.close();
		}
		return result;
	}

	@Override
	public List<TrendWrapper> topXTrendsToday(int counter) {

		List<TrendWrapper> topTrendList = new ArrayList<>();

		FindIterable<Document> sorted = dbtwitter.getCollection("trendsTwitter").find()
				.sort(new Document("locations.tweet_volume", -1)).limit(counter);

		sorted.forEach(new Block<Document>() {
			@Override
			public void apply(final Document document) {
				TrendWrapper trend = Converter.documentToTrend(document);
				topTrendList.add(trend);
			}
		});
		return topTrendList;
	}

	@Override
	public List<TrendWrapperInfo> topTrendsPerLocation(String woeid, Integer limit, Integer active) {

		List<TrendWrapperInfo> result = new ArrayList<>();

		Location location = getLocationByWoeid(woeid);
		FindIterable<Document> trends = null;
		
		if (active == null) {
			
			trends = dbtwitter.getCollection("trendsTwitter")
					.find(Filters.eq("locations.lid", location.getlID()))
					.sort(new Document("locations.tweet_volume", -1));
		} else {
			trends = dbtwitter.getCollection("trendsTwitter")
			.find(Filters.and(Filters.eq("locations.lid", location.getlID()), Filters.eq("locations.active", active)))
			.sort(new Document("locations.tweet_volume", -1));
		}
			
		if (limit != null) {
			trends = trends.limit(limit);
		}
		
		MongoCursor<Document> cursor = trends.iterator();
		try {
			while(cursor.hasNext()) {
				Document document = cursor.next();
				
				String trendId = document.getObjectId("_id").toString();
				String name = document.getString("name");
				String url = document.getString("url");
				
				TrendWrapperInfo trend = new TrendWrapperInfo();
				trend.setTrendId(trendId);
				trend.setName(name);
				trend.setUrl(url);
				
				result.add(trend);
				
				
				/*Document document = cursor.next();
				List<Document> list = document.get("locations", List.class);

				for (Document loc : list) {
					if (loc.getString("lid").equals(location.getlID())) {

						TrendWrapperInfo trendInfo = new TrendWrapperInfo();

						trendInfo.setlID(document.getObjectId("_id").toString());
						trendInfo.setName(document.getString("name"));
						trendInfo.setTweet_volume(loc.getInteger("tweet_volume"));
						trendInfo.setTimestamp(loc.getDate("timestamp").toInstant());
						trendInfo.setActive(loc.getInteger("active"));
						
						result.add(trendInfo);
					}
				}*/

			}
		  } finally {
		     cursor.close();
		  }	
		
		return result;
	}

	@Override
	public Document getDocumentById(String id, MongoCollection<Document> mongoCollection) {

		return mongoCollection.find(Filters.eq("_id", new ObjectId(id))).first();
	}

	@Override
	public void insertLocationData(List<Location> locationList) {

		for (Location location : locationList) {

			Document document = Converter.locationToNoTrendDocument(location);
			Document toUpdate = new Document();
			toUpdate.append("$setOnInsert", new Document().append("trends", new ArrayList<>()));

			FindOneAndUpdateOptions updateOptions = new FindOneAndUpdateOptions();
			updateOptions.returnDocument(ReturnDocument.AFTER);
			updateOptions.upsert(true);
			Document updateDocument = locationCollection.findOneAndUpdate(document, toUpdate, updateOptions);

			location.setlID(updateDocument.getObjectId("_id").toString());
		}
	}

	@Override
	public void insertTrendIntoLocation(String lid, String tid) {

		locationCollection.updateOne(Filters.eq("_id", new ObjectId(lid)),
				new Document().append("$addToSet", new Document().append("trends", new Document().append("tid", tid))));
	}

	@Override
	public void insertTrendData(List<TrendWrapper> trendList, String lid) {

		for (TrendWrapper trendWrapper : trendList) {

			List<TrendWrapperInfo> trendWrapperInfo = trendWrapper.getLocations();

			Document newLocation = new Document();

			for (TrendWrapperInfo trendWrapperInfo2 : trendWrapperInfo) {
				newLocation.append("lid", lid).append("tweet_volume", trendWrapperInfo2.getTweet_volume())
						.append("active", trendWrapperInfo2.getActive());

				java.util.Date myDate = Date.from(trendWrapperInfo2.getTimestamp());
				newLocation.append("timestamp", myDate);
			}

			Document document = Converter.trendToNoLocationDocument(trendWrapper);
			Document toUpdate = new Document();
			toUpdate.append("$addToSet", new Document().append("locations", newLocation));

			FindOneAndUpdateOptions updateOptions = new FindOneAndUpdateOptions();
			updateOptions.returnDocument(ReturnDocument.AFTER);
			updateOptions.upsert(true);
			Document updateDocument = trendsCollection.findOneAndUpdate(document, toUpdate, updateOptions);

			trendWrapper.setTid(updateDocument.getObjectId("_id").toString());
		}
	}

	@Override
	public List<Location> getLocationsForTrend(String name, Integer flag) {

		List<Location> locationListResult = new ArrayList<>();

		List<String> tidList = new ArrayList<>();

		if (flag == 0) {

			List<TrendWrapper> trendsForName = getTrendByName(name);

			for (TrendWrapper trendWrapper : trendsForName) {
				tidList.add(trendWrapper.getTid());
			}
		} else if (flag == 1) {

			List<TrendWrapper> trendsForName = getTrendByReguLarExpression(name);

			for (TrendWrapper trendWrapper : trendsForName) {
				tidList.add(trendWrapper.getTid());
			}
		} else {

			List<TrendWrapper> trendsForName = getTrendByName(name);

			for (TrendWrapper trendWrapper : trendsForName) {
				tidList.add(trendWrapper.getTid());
			}
		}

		for (String tid : tidList) {

			FindIterable<Document> iterable = dbtwitter.getCollection("locationsTwitter").find(eq("trends.tid", tid));

			iterable.forEach(new Block<Document>() {
				@Override
				public void apply(final Document document) {
					Location location = Converter.documentToLocation(document);
					locationListResult.add(location);
				}
			});

		}

		return locationListResult;
	}

	@Override
	public List<TrendWrapper> getTrendByReguLarExpression(String name) {

		List<TrendWrapper> trendList = new ArrayList<>();

		Document regQuery = new Document();
		regQuery.append("$regex", "^(?)" + Pattern.quote(name));
		regQuery.append("$options", "i");

		Document findQuery = new Document();
		findQuery.append("name", regQuery);
		FindIterable<Document> iterable = dbtwitter.getCollection("trendsTwitter").find(findQuery);

		iterable.forEach(new Block<Document>() {
			@Override
			public void apply(final Document document) {
				TrendWrapper trend = Converter.documentToTrend(document);
				trendList.add(trend);
			}
		});

		return trendList;
	}

	@Override
	public List<Location> getLocationsByPopRank(Integer popRank) {

		List<Location> locationList = new ArrayList<>();

		FindIterable<Document> iterable = dbtwitter.getCollection("locationsTwitter").find(eq("popRank", popRank));

		iterable.forEach(new Block<Document>() {
			@Override
			public void apply(final Document document) {
				Location location = Converter.documentToLocation(document);
				locationList.add(location);
			}
		});
		return locationList;
	}

	@Override
	public List<Location> getLocationsByAreaRank(Integer areaRank) {

		List<Location> locationList = new ArrayList<>();

		FindIterable<Document> iterable = dbtwitter.getCollection("locationsTwitter").find(eq("areaRank", areaRank));

		iterable.forEach(new Block<Document>() {
			@Override
			public void apply(final Document document) {
				Location location = Converter.documentToLocation(document);
				locationList.add(location);
			}
		});
		return locationList;
	}
	
	@Override
	public void setAllTrendsToInactive(List<TrendWrapper> trends) {
		for (TrendWrapper trend : trends) {
			
			Document locations = new Document();
			for (int i = 0; i < trend.getLocations().size(); i++) {
				locations.append("locations." + i + ".active", 0);
			}
			
			trendsCollection.updateOne(
				Filters.eq("_id", new ObjectId(trend.getTid())),
				new Document().append("$set", locations)
			);
		}
	}
	

}