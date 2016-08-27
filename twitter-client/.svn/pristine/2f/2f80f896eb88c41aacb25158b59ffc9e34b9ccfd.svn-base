package gr.cite.nasa.www.queries;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.oauth1.ConsumerCredentials;
import org.glassfish.jersey.client.oauth1.OAuth1ClientSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gr.cite.nasa.www.client.TwitterApiClient;
import gr.cite.nasa.www.core.Location;
import gr.cite.nasa.www.core.LocationTwitter;
import gr.cite.nasa.www.core.TrendTwitter;
import gr.cite.nasa.www.core.TrendWrapper;
import gr.cite.nasa.www.core.TrendWrapperInfo;
import gr.cite.nasa.www.core.TrendsTwitterResponse;

public class TwitterQueries implements TwitterApiClient {

	private static final Logger logger = LoggerFactory.getLogger(TwitterQueries.class);

	public static final String TWITTER_API_URL = "https://api.twitter.com";
	public static final String TWITTER_API_VERSION = "1.1";

	private Client client;
	private WebTarget webTarget;

	public TwitterQueries(String consumerKey, String consumerSecret) {

		ConsumerCredentials consumerCredentials = new ConsumerCredentials(consumerKey, consumerSecret);
		Feature filterFeature = OAuth1ClientSupport.builder(consumerCredentials).feature().build();

		this.client = ClientBuilder.newBuilder().register(filterFeature).build();
		this.webTarget = client.target(TWITTER_API_URL).path(TWITTER_API_VERSION);
	}

	@Override
	public List<Location> getAvailableTrends() {

		String query = "trends/available.json";

		Invocation.Builder invocationBuilder = webTarget.path(query).request(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.get();

		List<LocationTwitter> twitterAnswer = response.readEntity(new GenericType<List<LocationTwitter>>() {
		});

		List<Location> listOfLocations = twitterAnswer.stream().map(new Function<LocationTwitter, Location>() {

			@Override
			public Location apply(LocationTwitter locationTwitter) {

				Location location = new Location();
				location.setWoeid(locationTwitter.getWoeid());
				location.setName(locationTwitter.getName());
				location.setCountry(locationTwitter.getCountry());
				location.setCountryCode(locationTwitter.getCountryCode());
				location.setParentID(locationTwitter.getParentid());
				location.setUrl(locationTwitter.getUrl());

				return location;
			}
		}).collect(Collectors.toList());
		return listOfLocations;
	}

	@Override
	public List<TrendWrapper> getTrendsPlace(Location location) {

		String woeid = location.getWoeid();
		String lid = location.getlID();

		String query = "trends/place.json";

		WebTarget webTarget = this.webTarget.path(query).queryParam("id", woeid);

		Response response = webTarget.request(MediaType.APPLICATION_JSON).get();

		List<TrendsTwitterResponse> trendTwitterList = null;

		try {
			trendTwitterList = response.readEntity(new GenericType<List<TrendsTwitterResponse>>() {
			});
		} catch (ProcessingException e) {
			logger.error("Error code: " + response.getStatus());
			logger.error(e.getMessage(), e);
		}

		List<TrendWrapper> listOfTrends = trendTwitterList.stream()
				.flatMap(trendsTwitterResponse -> trendsTwitterResponse.getTrends().stream())
				.map(new Function<TrendTwitter, TrendWrapper>() {

					@Override
					public TrendWrapper apply(TrendTwitter trendTwitter) {

						TrendWrapper trendWrapper = new TrendWrapper();
						TrendWrapperInfo trendWrapperInfo = new TrendWrapperInfo();
						List<TrendWrapperInfo> locations = new ArrayList<>();

						trendWrapper.setName(trendTwitter.getName()/*.replace("#", "")*/);// Replace
																						// #
																						// with
																						// whitespace
						//TODO
						trendWrapper.setPromoted_content(trendTwitter.getPromoted_content());
						trendWrapper.setQuery(trendTwitter.getQuery());
						trendWrapper.setUrl(trendTwitter.getUrl());

						trendWrapperInfo.setlID(lid);
						trendWrapperInfo.setTweet_volume(trendTwitter.getTweet_volume());
						trendWrapperInfo.setTimestamp(Instant.now());

						locations.add(trendWrapperInfo);
						trendWrapper.setLocations(locations);

						return trendWrapper;
					}
				}).collect(Collectors.toList());

		List<String> tidList = new ArrayList<>();

		for (TrendWrapper trendWrapper : listOfTrends) {
			tidList.add(trendWrapper.getTid());
		}

		location.setTrendsIDs(tidList);
		return listOfTrends;
	}
}