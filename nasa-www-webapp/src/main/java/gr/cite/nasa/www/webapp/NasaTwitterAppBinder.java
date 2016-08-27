package gr.cite.nasa.www.webapp;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import gr.cite.nasa.www.datastore.DatastoreClient;
import gr.cite.nasa.www.datastore.mongo.MongoDatastoreClient;
import gr.cite.nasa.www.harvester.Harvester;

public class NasaTwitterAppBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(MongoDatastoreClient.class).to(DatastoreClient.class);
		bind(Harvester.class).to(Harvester.class);
	}
}
