/**
 * 
 */
package ch.nuiCellCAndroid.cellanalyzercore;

import java.io.File;
import java.io.IOException;

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
		// declare src and dest files
		File srcImage = null;
		File dstColorImage = null;
		File dstThresholdImage = null;
		
		// init properties
		Properties properties = new Properties();
		
		// read program arguments
		if(args.length > 0){
			srcImage = new File(args[0]);
			properties.setImageSrc(srcImage);
		}
		if(args.length > 1){
			String destFolderStr = args[1];
			File destFolder = new File(destFolderStr);
			if(!destFolder.exists()){
				destFolder.mkdirs();
			}
			
			String filename = srcImage.getName();
			String file = filename.substring(0, filename.lastIndexOf('.'));
			String fileEnding = filename.substring(filename.lastIndexOf('.'), filename.length());
			
			// set properties for results
			properties.setImageCells(new File(destFolder + System.getProperty("file.separator") + file + "-cells"+fileEnding));
			properties.setImageThreshold(new File(destFolder + System.getProperty("file.separator") + file + "-threshold"+fileEnding));
			properties.setImageCrop(new File(destFolder + System.getProperty("file.separator") + file + "-crop"+fileEnding));
			properties.setImageGray(new File(destFolder + System.getProperty("file.separator") + file + "-gray"+fileEnding));
			properties.setLogFile(new File(destFolder + System.getProperty("file.separator") + file + ".log"));
			
		}
		
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
