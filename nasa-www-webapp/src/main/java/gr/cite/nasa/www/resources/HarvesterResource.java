package gr.cite.nasa.www.resources;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gr.cite.nasa.www.harvester.Harvester;

@Path("harvester")
public class HarvesterResource {
	
	private static final Logger logger = LoggerFactory.getLogger(HarvesterResource.class);

	@Inject
	private Harvester twitterHarvester;

	@GET
	@Path("ping")
	public Response ping() {
		return Response.ok("pong").build();
	}
	
	@POST
	@Path("harvest")
	public Response harvest() {
		try {
			twitterHarvester.harvest();
		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return Response.ok().build();
	}
}
