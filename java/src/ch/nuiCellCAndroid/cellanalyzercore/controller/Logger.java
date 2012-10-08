/**
 * 
 */
package ch.nuiCellCAndroid.cellanalyzercore.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import ch.nuiCellCAndroid.cellanalyzercore.model.Box;
import ch.nuiCellCAndroid.cellanalyzercore.model.Cell;
import ch.nuiCellCAndroid.cellanalyzercore.model.Point;
import ch.nuiCellCAndroid.cellanalyzercore.model.Properties;

/**
 * Handles the login output of the cell analyzer.
 * 
 * @author nicolas baer
 */
public class Logger {
	
	private final static String NEWLINE = "\n";
	
	private File logFile;
	private File simpleLogFile;
	private Properties properties;
	
	/**
	 * default constructor
	 */
	public Logger(Properties properties){
		this.properties = properties;
		this.logFile = properties.getLogFile();
		this.simpleLogFile = properties.getLogSimpleFile();
	}
	
	/**
	 * Writes the cell analyzer statistics to the given log file.
	 * @param cellsPossible possible list of cells
	 * @param cellsFiltered filtered list of cells
	 * @param threshold 
	 * @param time 
	 * @param imageProcessorThreshold 
	 * @throws IOException 
	 */
	public void writeLog(ArrayList<Cell> cellsPossible, ArrayList<Cell> cellsFiltered, ImageProcessor image, double threshold, long time) throws IOException{
		BufferedWriter log = new BufferedWriter(new FileWriter(this.logFile));
		
		// write overall statistic
		log.append("Cell Analyzer Log:" + NEWLINE);
		log.append("* image source file name: " + properties.getImageSrc().getName());
		log.append("* image width x height: " + image.getImage().width() + "px x " + image.getImage().height() + "px");
		log.append("* threshold value: " + threshold);
		log.append("* analyzer duration: " + time + " ms");
		log.append("* nr of cells in image: "+ cellsPossible.size() + NEWLINE); 
		log.append("* nr of interesting cells in image: " + cellsFiltered.size() + NEWLINE);
		log.append("* nr of filtered cells: " + (cellsPossible.size() - cellsFiltered.size()) + NEWLINE);
		
		log.append( NEWLINE + NEWLINE + NEWLINE);
		
		// write detail statistic
		int cellIndex = 1;
		for(Cell cell : cellsFiltered){
			log.append("details for cell nr: " + cellIndex + NEWLINE);
			log.append(" * nr of points: " + cell.getPointsAmount() + NEWLINE);
			log.append(" * x position limits - min: " + cell.getBoundingBox().getxMin() + " max: " + cell.getBoundingBox().getxMin() + cell.getBoundingBox().getWidth() + NEWLINE);
			log.append(" * y position limits - min: " + cell.getBoundingBox().getyMin() + " max: " + cell.getBoundingBox().getyMin() + cell.getBoundingBox().getHeight() + NEWLINE);
			log.append(" * emphasis point - x: " + cell.getEmphasis().getX() + " y: " + cell.getEmphasis().getY() + NEWLINE);
			
			// print box positions
			for(Point point : cell.getPoints()){
				log.append(" * point - x: " + point.getX() + " y: " + point.getY() + NEWLINE);
			}
			
			log.append( NEWLINE + NEWLINE);
			
			cellIndex++;
		}
		
		
		log.flush();
		log.close();
	}
	
	/**
	 * Write a simple log file in the following form:
	 * EventNr./NrOfPoints/x-Koordinate/y-Koordinate/CircularityValue
	 * 
	 * It uses a tab as delimiter between the figures.
	 * 
	 * @param cellsFiltered
	 * @throws IOException 
	 */
	public void writeSimpleLog(ArrayList<Cell> cellsFiltered) throws IOException{
		BufferedWriter log = new BufferedWriter(new FileWriter(this.simpleLogFile));
		
		for(int counter = 0; counter < cellsFiltered.size(); counter++){
			Cell cell = cellsFiltered.get(counter);
			
			Box box = cell.getBoundingBox();
			
			Float ratio = null;
			if((box.getWidth()-box.getHeight()) < 0){
				ratio = new Float(((float)box.getWidth()) / ((float)box.getHeight())); 
			} else{
				if(box.getWidth() > 0){
					ratio = new Float(((float)box.getHeight()) / ((float)box.getWidth()));
				}
			}
			
			if(ratio != null){				
				log.append(counter + "\t" +  cell.getPointsAmount() + "\t" + cell.getEmphasis().getX() + "\t" + cell.getEmphasis().getY() + "\t" + ratio + NEWLINE);
			} else{
				log.append(counter + "\t" +  cell.getPointsAmount() + "\t" + cell.getEmphasis().getX() + "\t" + cell.getEmphasis().getY() + "\t" + "-1" + NEWLINE);
			}
			
		}
		
		
		log.flush();
		log.close();
	}
}
