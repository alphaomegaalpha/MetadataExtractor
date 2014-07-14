package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JLayeredPane;
import java.awt.FlowLayout;
import javax.swing.JScrollBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class Megui {

	private JFrame frame;
	private JTextField textFieldVectorInput;
	private JTextField textFieldVectorOutput;
	private JTextField textFieldVectorExportFile;
	private JButton buttonVectorInputFolder;
	private JButton buttonVectorExecute;
	private JTextField textFieldRasterInputFolder;
	private JTextField textFieldRasterOutputFolder;
	private JTextField textFieldRasterExportFile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Megui window = new Megui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Megui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 502);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 450, 480);
		frame.getContentPane().add(tabbedPane);
		
		JPanel vector = new JPanel();
		vector.setToolTipText("vector data processing");
		tabbedPane.addTab("vector", null, vector, null);
		vector.setLayout(null);
		
		textFieldVectorInput = new JTextField();
		textFieldVectorInput.setBounds(16, 36, 240, 28);
		vector.add(textFieldVectorInput);
		textFieldVectorInput.setColumns(10);
		
		textFieldVectorOutput = new JTextField();
		textFieldVectorOutput.setBounds(16, 90, 239, 28);
		vector.add(textFieldVectorOutput);
		textFieldVectorOutput.setColumns(10);
		
		textFieldVectorExportFile = new JTextField();
		textFieldVectorExportFile.setBounds(17, 147, 239, 28);
		vector.add(textFieldVectorExportFile);
		textFieldVectorExportFile.setColumns(10);
		
		JButton buttonVectorOutputFolder = new JButton("Browse");
		buttonVectorOutputFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonVectorOutputFolder.setBounds(269, 91, 88, 29);
		vector.add(buttonVectorOutputFolder);
		
		buttonVectorInputFolder = new JButton("Browse");
		buttonVectorInputFolder.setBounds(269, 37, 88, 29);
		vector.add(buttonVectorInputFolder);
		
		buttonVectorExecute = new JButton("Execute");
		buttonVectorExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonVectorExecute.setBounds(314, 148, 93, 29);
		vector.add(buttonVectorExecute);
		
		JProgressBar vectorProgressBar = new JProgressBar();
		vectorProgressBar.setBounds(17, 208, 390, 20);
		vector.add(vectorProgressBar);
		
		JLabel lblSelectInputFolder = new JLabel("Select input folder");
		lblSelectInputFolder.setBounds(17, 16, 115, 16);
		vector.add(lblSelectInputFolder);
		
		JLabel lblSelectOutputFolder = new JLabel("Select output folder");
		lblSelectOutputFolder.setBounds(17, 68, 124, 16);
		vector.add(lblSelectOutputFolder);
		
		JLabel lblSpecifyFileName = new JLabel("Specify file name");
		lblSpecifyFileName.setBounds(17, 130, 107, 16);
		vector.add(lblSpecifyFileName);
		
		JLabel lblProcess = new JLabel("Process");
		lblProcess.setBounds(16, 187, 48, 16);
		vector.add(lblProcess);
		
		JTextArea textVectorAreaConsole = new JTextArea();
		textVectorAreaConsole.setBounds(16, 240, 372, 178);
		vector.add(textVectorAreaConsole);
		
		JScrollBar vectorScrollBar = new JScrollBar();
		vectorScrollBar.setBounds(392, 240, 15, 178);
		vector.add(vectorScrollBar);
		
		JPanel raster = new JPanel();
		raster.setToolTipText("raster data processing");
		tabbedPane.addTab("raster", null, raster, null);
		raster.setLayout(null);
		
		textFieldRasterInputFolder = new JTextField();
		textFieldRasterInputFolder.setColumns(10);
		textFieldRasterInputFolder.setBounds(16, 36, 240, 28);
		raster.add(textFieldRasterInputFolder);
		
		textFieldRasterOutputFolder = new JTextField();
		textFieldRasterOutputFolder.setColumns(10);
		textFieldRasterOutputFolder.setBounds(16, 90, 239, 28);
		raster.add(textFieldRasterOutputFolder);
		
		textFieldRasterExportFile = new JTextField();
		textFieldRasterExportFile.setColumns(10);
		textFieldRasterExportFile.setBounds(17, 147, 239, 28);
		raster.add(textFieldRasterExportFile);
		
		JButton buttonRasterOutputFolder = new JButton("Browse");
		buttonRasterOutputFolder.setBounds(269, 91, 88, 29);
		raster.add(buttonRasterOutputFolder);
		
		JButton buttonRasterInputFolder = new JButton("Browse");
		buttonRasterInputFolder.setBounds(269, 37, 88, 29);
		raster.add(buttonRasterInputFolder);
		
		JButton buttonRasterExecute = new JButton("Execute");
		buttonRasterExecute.setBounds(315, 148, 93, 29);
		raster.add(buttonRasterExecute);
		
		JProgressBar rasterProgressBar = new JProgressBar();
		rasterProgressBar.setBounds(17, 208, 391, 20);
		raster.add(rasterProgressBar);
		
		JLabel label = new JLabel("Select input folder");
		label.setBounds(17, 16, 115, 16);
		raster.add(label);
		
		JLabel label_1 = new JLabel("Select output folder");
		label_1.setBounds(17, 68, 124, 16);
		raster.add(label_1);
		
		JLabel label_2 = new JLabel("Specify file name");
		label_2.setBounds(17, 130, 107, 16);
		raster.add(label_2);
		
		JLabel label_3 = new JLabel("Process");
		label_3.setBounds(16, 187, 48, 16);
		raster.add(label_3);
		
		JTextArea rasterTextArea = new JTextArea();
		rasterTextArea.setBounds(16, 240, 372, 178);
		raster.add(rasterTextArea);
		
		JScrollBar rasterScrollBar = new JScrollBar();
		rasterScrollBar.setBounds(392, 240, 15, 178);
		raster.add(rasterScrollBar);
	}
}
