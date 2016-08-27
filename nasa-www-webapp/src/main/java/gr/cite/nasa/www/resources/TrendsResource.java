package gr.cite.nasa.www.resources;

import javax.inject.Inject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import gr.cite.nasa.www.datastore.DatastoreClient;

@Path("trends")
@Produces(MediaType.APPLICATION_JSON)
public class TrendsResource {

	@Inject
	private DatastoreClient datastoreClient;

	@GET
	@Path("ping")
	public Response ping() {
		return Response.ok("pong").build();
	}
	
	@GET
	@Path("getLocations/{name}")
	public Response getLocations(@PathParam("name") String name) {

		return Response.ok(datastoreClient.getLocationByName(name)).build();
	}

	@GET
	@Path("listAllLocations")
	public Response listAllLocations() {

		return Response.ok(datastoreClient.listLocations()).build();
	}
	
	@GET
	@Path("getLocationsByPopulation/{popRank}")
	public Response getLocationsByPopulation(@PathParam("popRank") Integer popRank){
		
		return Response.ok(datastoreClient.getLocationsByPopRank(popRank)).build();
	}
	
	@GET
	@Path("getLocationsByArea/{areaRank}")
	public Response getLocationsByArea(@PathParam("areaRank") Integer areaRank){
		
		return Response.ok(datastoreClient.getLocationsByAreaRank(areaRank)).build();
	}
	
	@GET
	@Path("getTrends/{name}")
	public Response getTrends(@PathParam("name") String name) {

		return Response.ok(datastoreClient.getTrendByName(name)).build();
	}

	@GET
	@Path("listAllTrends")
	public Response listAllTrends(@QueryParam("limit") Integer limit,@QueryParam("flag") Integer flag) {

		return Response.ok(datastoreClient.listTrends(limit,flag)).build();
	}

	@GET
	@Path("topTrendsToday/{counter}")
	public Response topTrendsToday(@PathParam("counter") int counter) {

		return Response.ok(datastoreClient.topXTrendsToday(counter)).build();
	}

	@GET
	@Path("topTrendsLocation/{woeid}")
	public Response topTrendsLocation(
			@PathParam("woeid") String woeid,
			@QueryParam("limit") Integer limit ,
			@QueryParam ("active") Integer active) {

		return Response.ok(datastoreClient.topTrendsPerLocation(woeid, limit,active)).build();
	}
	
	@GET
	@Path("locationsForTrend/{name}")
	public Response locationsForTrend(@PathParam ("name") String name,@QueryParam("active")Integer active){
		
		return Response.ok(datastoreClient.getLocationsForTrend(name,active)).build();
	}
}
