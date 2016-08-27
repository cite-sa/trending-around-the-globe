package gr.cite.nasa.www.queries;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import gr.cite.nasa.www.core.Place;

public class GeoPlanetQueries {

	private String clientID;

	public GeoPlanetQueries(String clientID) {

		this.clientID = clientID;
	}

	public Place woeidIntoBB(String woeid) throws JAXBException, IOException {

		String query = "http://where.yahooapis.com/v1/place/" + woeid + "?appid=" + clientID;

		Client client = ClientBuilder.newClient();

		WebTarget webTarget = client.target(query);

		Response response = webTarget.request(MediaType.APPLICATION_XML).get();

		InputStream inputStream = response.readEntity(InputStream.class);

		JAXBContext jaxbContext = JAXBContext.newInstance(Place.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		Source source = new StreamSource(inputStream);
		JAXBElement<Place> place = jaxbUnmarshaller.unmarshal(source, Place.class);

		inputStream.close();

		return place.getValue();
	}
}
