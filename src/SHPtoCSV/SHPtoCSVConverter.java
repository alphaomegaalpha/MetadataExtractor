package SHPtoCSV;

import java.io.Console;
import java.io.IOException;

import SHPtoCSV.SHPtoCSV;

public class SHPtoCSVConverter {

	public SHPtoCSVConverter() {
	}

	public static void main(String[] args) throws IOException {

		Console c = System.console();

		if (c == null) {
			System.err.println("No console.");
			System.exit(1);
		}
		String importPath = c
				.readLine("Please let me know the import folder as an absolute path: ");
		String exportPath = c
				.readLine("Where should I save the csv file? Please enter the path: ");
		String fileName = c
				.readLine("I beg your pardon, please enter a name for the csv file: ");

		SHPtoCSV sc = new SHPtoCSV();

		sc.scanFolder(importPath);
		sc.readShp();
		sc.writeCSV(exportPath+fileName);
	}

}
