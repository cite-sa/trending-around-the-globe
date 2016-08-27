package gr.cite.nasa.www.streaming;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GetPropertyValues {

	String result = "";
	InputStream inputStream;

	List<String> keys = new ArrayList<>();

	public List<String> getPropValues() throws IOException {

		try {

			Properties prop = new Properties();
			String propFileName = "twitter4j.properties";

			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

			String oauthConsumerKey = prop.getProperty("oauth.consumerKey");
			keys.add(oauthConsumerKey);

			String oauthConsumerSecret = prop.getProperty("oauth.consumerSecret");
			keys.add(oauthConsumerSecret);

			String oauthAccessToken = prop.getProperty("oauth.accessToken");
			keys.add(oauthAccessToken);

			String oauthAccessTokenSecret = prop.getProperty("oauth.accessTokenSecret");
			keys.add(oauthAccessTokenSecret);

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return keys;
	}
}
