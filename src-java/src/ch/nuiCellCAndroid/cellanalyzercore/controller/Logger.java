/**
 * 
 */
package ch.nuiCellCAndroid.cellanalyzercore.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import ch.nuiCellCAndroid.cellanalyzercore.model.Cell;
import ch.nuiCellCAndroid.cellanalyzercore.model.Point;

/**
 * Handles the login output of the cell analyzer.
 * 
 * @author nicolas baer
 */
public class Logger {
	
	private final static String NEWLINE = "\n";
	
	private File logFile;
	
	/**
	 * default constructor
	 */
	public Logger(File logFile){
		this.logFile = logFile;
	}
	
	/**
	 * Writes the cell analyzer statistics to the given log file.
	 * @param cellsPossible possible list of cells
	 * @param cellsFiltered filtered list of cells
	 * @throws IOException 
	 */
	public void writeLog(ArrayList<Cell> cellsPossible, ArrayList<Cell> cellsFiltered) throws IOException{
		BufferedWriter log = new BufferedWriter(new FileWriter(this.logFile));
		
		// write overall statistic
		log.append("Cell Analyzer Log:" + NEWLINE);
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
		
		/*
        ##TODO: add this stuff to the facts
        #file name 
        #used filters
        #used image filter functions (config)
        #used threshold alg...
        #date
        #script revision
		 */
	}
}
