package SHPtoCSV;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geonames.PostalCode;
import org.geonames.PostalCodeSearchCriteria;
import org.geonames.Toponym;
import org.geonames.ToponymSearchCriteria;
import org.geonames.ToponymSearchResult;
import org.geonames.WebService;
import org.geonames.WikipediaArticle;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.geometry.BoundingBox;

public class SHPtoCSV {

	public SHPtoCSV() {
	}

	ArrayList<File> files;
	HashMap<String, ArrayList<String>> features = new HashMap<String, ArrayList<String>>();
	ArrayList<String> properties = new ArrayList<String>();

	public void scanFolder(String path) {
		File f = new File(path);
		File[] matchingFiles = f.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith("shp");
			}
		});
		this.files = new ArrayList<File>(Arrays.asList(matchingFiles));
	}

	public void readShp() throws IOException {

		for (File f : files) {

			Map<String, Serializable> map = new HashMap<>();
			map.put("url", f.toURI().toURL().toString());

			DataStore dataStore = DataStoreFinder.getDataStore(map);
			String typeName = dataStore.getTypeNames()[0];
			FeatureSource<?, SimpleFeature> source = dataStore
					.getFeatureSource(typeName);
			dataStore.dispose();
			FeatureCollection<?, SimpleFeature> collection = source
					.getFeatures();
			FeatureIterator<SimpleFeature> results = collection.features();

			try {
				while (results.hasNext()) {

					SimpleFeature feature = (SimpleFeature) results.next();

					String name = feature.getName().toString();
					String geom = feature.getDefaultGeometryProperty()
							.toString();
					String type = feature.getType().toString();
					String id = feature.getID();
					// String nor
					Collection<Property> attr = feature.getProperties();
					BoundingBox bbox = feature.getBounds();
					String left = new Double(bbox.getMinX()).toString();
					String right = new Double(bbox.getMaxX()).toString();
					String top = new Double(bbox.getMaxY()).toString();
					String bottom = new Double(bbox.getMinY()).toString();
					String url = f.toURI().toURL().toString();
					String lastMod = Long.toString(f.lastModified());

					this.properties.add(id);
					this.properties.add(url);
					this.properties.add(name);
					this.properties.add(lastMod);
					this.properties.add("shp");
					this.properties.add(geom);
					this.properties.add(type);
					this.properties.add(left);
					this.properties.add(right);
					this.properties.add(top);
					this.properties.add(bottom);
					for (Property p : attr) {
						this.properties.add(p.getName().toString());
					}

					this.features.put(feature.getID(), properties);
				}
			} finally {
				results.close();
			}
		}
	}

	public void getGeographicInformation() throws Exception {

		ArrayList<String> geographicInfo = new ArrayList<String>();

		for (File f : files) {

			Map<String, Serializable> map = new HashMap<>();
			map.put("url", f.toURI().toURL().toString());

			DataStore dataStore = DataStoreFinder.getDataStore(map);
			String typeName = dataStore.getTypeNames()[0];
			FeatureSource<?, SimpleFeature> source = dataStore
					.getFeatureSource(typeName);
			dataStore.dispose();
			FeatureCollection<?, SimpleFeature> collection = source
					.getFeatures();
			FeatureIterator<SimpleFeature> results = collection.features();
			SimpleFeature feature = (SimpleFeature) results.next();

			BoundingBox bbox = feature.getBounds();
			double latitude = bbox.getMaxX();
			double longitude = bbox.getMinY();

			// LOGIN TO GEONAMES WEBSERVICE AND SET PARAMETERS
			System.out.println("Connecting to geonames webservice...");
			WebService.setUserName("jkuepper");
			String language = "english";

			// GET INFORMATION FROM GEONAMES

			ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria();
			searchCriteria.setQ("zurich");
			ToponymSearchResult searchResult = WebService
					.search(searchCriteria);
			for (Toponym toponym : searchResult.getToponyms()) {
				System.out.println(toponym.getName() + " "
						+ toponym.getCountryName());
			}

			try {
				List<WikipediaArticle> wikiURL = WebService
						.findNearbyWikipedia(latitude, longitude, language);
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
			List<PostalCode> postalCode = WebService
					.findNearbyPostalCodes(pcsc);

			geographicInfo.add(placeName.toString());
			geographicInfo.add(postalCode.toString());
			geographicInfo.add(countryCode);
		}
	}

	public void writeCSV(String path) throws IOException {

		FileWriter fw = new FileWriter(path);
		PrintWriter pw = new PrintWriter(fw);

		for (String key : features.keySet()) {
			pw.println(key);
			ArrayList<String> values = features.get(key);
			for (String s : values) {
				pw.print(s);
			}
		}

		pw.flush();
		pw.close();
		fw.close();

	}

}
