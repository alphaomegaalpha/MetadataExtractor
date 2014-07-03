/**
 * 
 */
package importer;

import gov.nasa.worldwind.formats.shapefile.Shapefile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;

import nl.knaw.dans.common.dbflib.Field;
import nl.knaw.dans.common.dbflib.Table;

/**
 * @author jkuepper
 *
 */
public abstract class VectorImporter implements Importer {

	private String path;
	private ArrayList<String> shapeFiles = new ArrayList<String>();
	private ArrayList<String> dbfFiles = new ArrayList<String>();
	private Map<String, ArrayList<String>> data = new HashMap<String, ArrayList<String>>();

	public VectorImporter() {
	}

	// SEARCH FOR FILES
	/**
	 * @param folder
	 * @throws IOException
	 */
	public void listFilesForFolder(final File folder) throws IOException {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else if (fileEntry.getName() == "*.shp") {
				shapeFiles.add(fileEntry.getName());
				System.out.println("YALLAH... Found shapefile '"
						+ fileEntry.getName() + "'.");
			} else if (fileEntry.getName() == "*.dbf")
				;
			{
				dbfFiles.add(fileEntry.getName());
				System.out.println("YEAH... Found dbf file '"
						+ fileEntry.getName() + "'.");
			}
		}
		this.path = folder.getAbsolutePath();
	}

	// TODO: change to geotools lib
	// EXTRACT INFORMATION FROM FILES
	/**
	 * @return
	 */
	public boolean extractFileData() {

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
			System.out.println("YIPPIE... Extracted data from shapefile '"
					+ fileName + "'.");

			String attributes = new String();
			for (String dbfFile : dbfFiles) {
				final File fl = new File(dbfFile);
				final Table tb = new Table(fl);
				List<Field> lt = tb.getFields();

				for (Field fd : lt) {
					attributes.concat(fd.getName() + ", ");
				}

				fileData.add(attributes);
				System.out.println("YOLO... Extracted data from dbf file '"
						+ dbfFile.toString() + "'.");
			}

			data.put(shapeFile, fileData);
			System.out.println("YES... merged shapefile and dbf file data.");

		}

		if (!data.isEmpty()) {
			return true;
		} else
			return false;

	}

	// RETURN INFORMATION
	/**
	 * @return
	 */
	public Map<String, ArrayList<String>> getData() {
		return data;
	}

}