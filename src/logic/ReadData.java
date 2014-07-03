package logic;
import gov.nasa.worldwind.formats.shapefile.Shapefile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;

import nl.knaw.dans.common.dbflib.Field;
import nl.knaw.dans.common.dbflib.Table;

import org.geonames.PostalCode;
import org.geonames.PostalCodeSearchCriteria;
import org.geonames.Toponym;
import org.geonames.ToponymSearchCriteria;
import org.geonames.ToponymSearchResult;
import org.geonames.WebService;
import org.geonames.WikipediaArticle;

public class ReadData {

	private String path;
	private ArrayList<String> shapeFiles = new ArrayList<String>();
	private ArrayList<String> dbfFiles = new ArrayList<String>();
	private Map<String, ArrayList<String>> data = new HashMap<String, ArrayList<String>>();

	public ReadData(){
	}
	
	//SEARCH FOR FILES
	public void listFilesForFolder(final File folder) throws IOException {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else if (fileEntry.getName() == "*.shp") {
	        	shapeFiles.add(fileEntry.getName());
	        	System.out.println("YALLAH... Found shapefile '" + fileEntry.getName() + "'.");
	        } else if (fileEntry.getName() == "*.dbf"); {
	        	dbfFiles.add(fileEntry.getName());
	        	System.out.println("YEAH... Found dbf file '" + fileEntry.getName() + "'.");
	        }
	    }
	    this.path = folder.getAbsolutePath();
	}
	
	
	//EXTRACT GEOGRAPHIC INFORMATION
	public ArrayList<String> getGeographicInformation(Shapefile sf) throws Exception{
		
		ArrayList<String> geographicInfo = new ArrayList<String>();
		
		double[] bbox = sf.getBoundingRectangle();
		double latitude = bbox[0];
		double longitude = bbox[1];
		
		//LOGIN TO GEONAMES WEBSERVICE AND SET PARAMETERS
		System.out.println("Connecting to geonames webservice...");
		WebService.setUserName("jkuepper");
		String language = "english";
		
		//GET INFORMATION FROM GEONAMES
		
		ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria();
		  searchCriteria.setQ("zurich");
		  ToponymSearchResult searchResult = WebService.search(searchCriteria);
		  for (Toponym toponym : searchResult.getToponyms()) {
		     System.out.println(toponym.getName()+" "+ toponym.getCountryName());
		  }
		
		try {
			List<WikipediaArticle> wikiURL = WebService.findNearbyWikipedia(latitude, longitude, language);
			geographicInfo.add(wikiURL.toString());
			System.out.println("Cool... Found wikipedia URL.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String countryCode = WebService.countryCode(latitude, longitude);
		List<Toponym> placeName = WebService.findNearbyPlaceName(latitude, longitude);
		PostalCodeSearchCriteria pcsc = new PostalCodeSearchCriteria();
		pcsc.setLatitude(latitude);
		pcsc.setLongitude(longitude);
		List<PostalCode> postalCode = WebService.findNearbyPostalCodes(pcsc);
		
		geographicInfo.add(placeName.toString());
		geographicInfo.add(postalCode.toString());
		geographicInfo.add(countryCode);
		
		//RETURN INFORMATION
		return geographicInfo;
	}
	
	
	//TODO: change to geotools lib
	//EXTRACT INFORMATION FROM FILES
	public boolean extractFileData()
			throws FileNotFoundException {

		for (String shapeFile : shapeFiles) {

			final Shapefile sf = new Shapefile(shapeFile);
			final ArrayList<String> fileData = new ArrayList<String>();

			fileData.add(this.path);
			final String fileFormat = ".shp";
			fileData.add(fileFormat);
			final String fileName = shapeFile;
			fileData.add(fileName);
			final String dataType = sf.getShapeType();
			fileData.add(dataType);
			final String bbox = sf.getBoundingRectangle().toString();
			fileData.add(bbox);
			final int nor = sf.getNumberOfRecords();
			fileData.add(String.valueOf(nor));
			final File file1 = new File(this.path);
			final SimpleDateFormat dateLastModified = new SimpleDateFormat(
					"MM/dd/yyyy HH:mm:ss");
			dateLastModified.format(file1.lastModified());
			fileData.add(dateLastModified.toString());
			sf.close();
			System.out.println("YIPPIE... Extracted data from shapefile '" + fileName + "'.");
			
			String attributes = new String();
			for (String dbfFile : dbfFiles) {
				final File fl = new File(dbfFile);
				final Table tb = new Table(fl);
				List<Field> lt = tb.getFields();

				for (Field fd : lt) {
					attributes.concat(fd.getName() + ", ");
				}

				fileData.add(attributes);
				System.out.println("YOLO... Extracted data from dbf file '" + dbfFile.toString() + "'.");
			}

			data.put(shapeFile, fileData);
			System.out.println("YES... merged shapefile and dbf file data.");

		}
	
		if(!data.isEmpty()) {
		return true;
		} else return false;
		
	}

	//RETURN INFORMATION
	public Map<String, ArrayList<String>> getData() {
		return data;
	}

}