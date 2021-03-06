package exporter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Johannes Kuepper (johannes.kuepper@mailbox.tu-dresden.de)
 *
 */
public class Exporter {

	private String exportPath;
	private String fileName;

	/**
	 * @param exportPath
	 * @param fileName
	 */
	public Exporter(String exportPath, String fileName) {
		this.exportPath = exportPath;
		this.fileName = fileName;
	}

	// WRITE INFORMATION TO CSV
	/**
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public boolean writeCSV(Map<String, ArrayList<String>> data)
			throws IOException {
		File file = new File(exportPath + fileName + ".csv");
		FileWriter fr = new FileWriter(file);

		Iterator<Entry<String, ArrayList<String>>> it = data.entrySet()
				.iterator();
		while (it.hasNext()) {
			Entry<String, ArrayList<String>> pairs = (Entry<String, ArrayList<String>>) it
					.next();
			String metadata = new String();
			for (String st : pairs.getValue()) {
				metadata = metadata.concat(st + ", ");
			}
			fr.write(pairs.getKey() + " " + metadata + "\n");
			fr.close();
			fr.flush();
			System.out.println("Wrote line '" + it.hashCode()
					+ "' to CSV file.");
			// it.remove(); // avoids a ConcurrentModificationException
		}

		if (fr.toString() == "") {
			return false;
		} else
			return true;

	}

}