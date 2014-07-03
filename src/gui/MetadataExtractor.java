package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import logic.ReadData;
import logic.WriteData;

public class MetadataExtractor {

	public static void main(String[] args) throws IOException {

		new MetadataExtractorApplication();

		Console c = System.console();

		if (c == null) {
			System.err.println("No console.");
			System.exit(1);
		}

		// GET ARGUMENTS
		// String internet =
		// c.readLine("Do you have a Internet connection? Enter 'YES' or 'NO': ");

		// if (internet == "YES"){

		String importPath = c
				.readLine("Please let me know the import folder as an absolute path: ");
		String exportPath = c
				.readLine("Where should I save the csv file? Please Enter: ");
		String fileName = c
				.readLine("I beg your pardon, please enter a name for the csv file: ");
		File importFolder = new File(importPath);

		// READ DATA
		ReadData rd = new ReadData();
		rd.listFilesForFolder(importFolder);
		boolean okrd = rd.extractFileData();
		if (okrd) {
			System.out.println("------ READ DATA: COMPLETE ------");
		} else {
			System.out.println("------ READ DATA: ERROR ------");
		}

		// WRITE DATA
		WriteData wd = new WriteData(exportPath, fileName);
		boolean okwd = wd.writeCSV(rd.getData());
		if (okwd) {
			System.out
					.println("------ WRITE DATA: COMPLETE ------ \n FIND YOUR CSV HERE... AND ENJOY: "
							+ exportPath);

		} else {
			System.out.println("------ WRITE DATA: ERROR ------");
		}

		// } else {
		// System.out.println("Get some internet and restart the application.");
		// }

	}

	public static class MetadataExtractorApplication extends JFrame {

		private static final long serialVersionUID = 1L;

		public MetadataExtractorApplication() {
			super(MetadataExtractorApplication.class.getName());
			// SET PARAMETER
			getContentPane().setLayout(new BorderLayout());
			final JFileChooser fcif = new JFileChooser();
			final JFileChooser fcof = new JFileChooser();

			getContentPane().add(
					new JTextField("Please enter the desired file name."),
					BorderLayout.NORTH);

			// Button anlegen
			JButton button = new JButton("Go. Execute.");

			// Der Button soll einen Dialog starten
			// Hier wird eine anonyme, innere Klasse für das
			// Event-Handling benutzt.
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					JOptionPane.showMessageDialog((JButton) e.getSource(),
							"Do you really want to execute?");
				}
			});

			// Den Button in der Mitte platzieren
			getContentPane().add(button, BorderLayout.CENTER);

			// Größe setzen (oder pack() aufrufen)
			setSize(200, 100);
			// Wenn jemand das Fenster schließt, soll
			// die Applikation beendet werden
			// (System.exit (0) wird aufgerufen)
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			// Fenster sichtbar machen
			setVisible(true);
		}

		// DIRECTORY CHOOSER
		JButton bfip;

		JFileChooser chooser;
		String choosertitle;

		public void DemoJFileChooser() {
			bfip = new JButton("Browse for input folder.");
			bfip.addActionListener((ActionListener) this);
			add(bfip);
		}

		public void actionPerformed(ActionEvent e) {
			int result;

			chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle(choosertitle);
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			//
			// disable the "All files" option.
			//
			chooser.setAcceptAllFileFilterUsed(false);
			//
			if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				System.out.println("getCurrentDirectory(): "
						+ chooser.getCurrentDirectory());
				System.out.println("getSelectedFile() : "
						+ chooser.getSelectedFile());
			} else {
				System.out.println("No Selection.");
			}
		}

	}
}