package gr.cite.nasa.www.client;

import java.util.List;

import gr.cite.nasa.www.core.Location;
import gr.cite.nasa.www.core.TrendWrapper;

public interface TwitterApiClient {

	List<Location> getAvailableTrends();

	List<TrendWrapper> getTrendsPlace(Location location);
}