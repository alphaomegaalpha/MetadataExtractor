package importer;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;


public class ShpImporter extends VectorImporter{

	@Override
	public void listFilesForFolder() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean extractFileData() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void testGeotoolsShp() throws IOException{
		File file = new File("../test/testdata/mamoni.shp");
	    Map<String, Serializable> map = new HashMap<>();
	    map.put( "url", file.toURI().toURL() );

	    DataStore dataStore = DataStoreFinder.getDataStore( map );
	    String typeName = dataStore.getTypeNames()[0];

	    FeatureSource<?, SimpleFeature> source = dataStore.getFeatureSource( typeName );

	    FeatureCollection<?, SimpleFeature> collection =  source.getFeatures();
	    FeatureIterator<SimpleFeature> results = collection.features();
	    try {
	        while (results.hasNext()) {
	            SimpleFeature feature = (SimpleFeature) results.next();
	            String code = feature.getAttribute("").toString();
	            System.out.println( code );
	        }
	    } finally {
	        dataStore.dispose();
	        results.close();
	    }
	}

}
