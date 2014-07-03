package geo;

import gov.nasa.worldwind.formats.shapefile.Shapefile;

import java.util.ArrayList;
import java.util.List;

import org.geonames.PostalCode;
import org.geonames.PostalCodeSearchCriteria;
import org.geonames.Toponym;
import org.geonames.ToponymSearchCriteria;
import org.geonames.ToponymSearchResult;
import org.geonames.WebService;
import org.geonames.WikipediaArticle;

public class GeoinformationHandler {

	// EXTRACT GEOGRAPHIC INFORMATION
	/**
	 * @param sf
	 * @return
	 * @throws Exception
	 */
	public ArrayList<String> getGeographicInformation(Shapefile sf)
			throws Exception {

		ArrayList<String> geographicInfo = new ArrayList<String>();

		double[] bbox = sf.getBoundingRectangle();
		double latitude = bbox[0];
		double longitude = bbox[1];

		// LOGIN TO GEONAMES WEBSERVICE AND SET PARAMETERS
		System.out.println("Connecting to geonames webservice...");
		WebService.setUserName("jkuepper");
		String language = "english";

		// GET INFORMATION FROM GEONAMES

		ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria();
		searchCriteria.setQ("zurich");
		ToponymSearchResult searchResult = WebService.search(searchCriteria);
		for (Toponym toponym : searchResult.getToponyms()) {
			System.out.println(toponym.getName() + " "
					+ toponym.getCountryName());
		}

		try {
			List<WikipediaArticle> wikiURL = WebService.findNearbyWikipedia(
					latitude, longitude, language);
			geographicInfo.add(wikiURL.toString());
			System.out.println("Cool... Found wikipedia URL.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String countryCode = WebService.countryCode(latitude, longitude);
		List<Toponym> placeName = WebService.findNearbyPlaceName(latitude,
				longitude);
		PostalCodeSearchCriteria pcsc = new PostalCodeSearchCriteria();
		pcsc.setLatitude(latitude);
		pcsc.setLongitude(longitude);
		List<PostalCode> postalCode = WebService.findNearbyPostalCodes(pcsc);

		geographicInfo.add(placeName.toString());
		geographicInfo.add(postalCode.toString());
		geographicInfo.add(countryCode);

		// RETURN INFORMATION
		return geographicInfo;
	}

}
