/**
 * 
 */
package ch.nuiCellCAndroid.cellanalyzercore;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ch.nuiCellCAndroid.cellanalyzercore.controller.Analyzer;
import ch.nuiCellCAndroid.cellanalyzercore.model.Properties;

/**
 * @author nicolas baer
 *
 */
public class CellAnalyzer {

	/**
	 * main entry point
	 * 
	 * @param args 
	 */
	public static void main(String[] args) {
		// declare src
		File srcImage = null;
		
		// init properties
		Properties properties = new Properties();
		
		// read program arguments
		if(args.length > 0){
			srcImage = new File(args[0]);
			properties.setImageSrc(srcImage);
		} else{
			System.out.println("please specify the image to analyze\n e.g. java -jar /path/to/image");
			System.exit(1);
		}
		
		// read path of image file folder and filename
		String path = srcImage.getParent();
		path += System.getProperty("file.separator");
		String[] uri = null;
		try {
			uri = srcImage.getCanonicalPath().split(System.getProperty("file.separator"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String fileName = uri[uri.length - 1];
		
		// create directory for results
		String destFolder = path + new SimpleDateFormat("yyMMddHHmm").format(new Date()) + fileName + System.getProperty("file.separator");
		new File(destFolder).mkdir();

		String filename = srcImage.getName();
		String file = filename.substring(0, filename.lastIndexOf('.'));
		String fileEnding = filename.substring(filename.lastIndexOf('.'), filename.length());
		
		// set properties for results
		properties.setImageCells(new File(destFolder + System.getProperty("file.separator") + file + "-cells"+fileEnding));
		properties.setImageThreshold(new File(destFolder + System.getProperty("file.separator") + file + "-threshold"+fileEnding));
		properties.setImageCrop(new File(destFolder + System.getProperty("file.separator") + file + "-crop"+fileEnding));
		properties.setImageGray(new File(destFolder + System.getProperty("file.separator") + file + "-gray"+fileEnding));
		properties.setImageChart(new File(destFolder + System.getProperty("file.separator") + file + "-chart.png"));
		properties.setLogFile(new File(destFolder + System.getProperty("file.separator") + file + ".log"));		
		
		// start analyzer
		Analyzer analyzer = new Analyzer();
		try {
			analyzer.analyze(properties);
		} catch (IOException e) {
			// some problems with the given files, print error message
			System.out.println("Images problem. Please check the given image.");
		}
		
		
	}
	
	
	
	
	
}
