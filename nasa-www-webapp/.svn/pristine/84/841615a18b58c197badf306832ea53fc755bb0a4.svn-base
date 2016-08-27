package gr.cite.nasa.www.webapp;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import gr.cite.nasa.www.webapp.NasaTwitterAppBinder;

public class NasaTwitterApp extends ResourceConfig {

	public NasaTwitterApp() {

		register(new NasaTwitterAppBinder());
		register(JacksonFeature.class);
		register(CORSFilter.class);
	}
}
