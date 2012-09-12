/**
 * 
 */
package ch.nuiCellCAndroid.cellanalyzercore.controller;

import java.io.IOException;
import java.util.ArrayList;

import ch.nuiCellCAndroid.cellanalyzercore.controller.filter.CellFilterShape;
import ch.nuiCellCAndroid.cellanalyzercore.controller.filter.CellFilterSize;
import ch.nuiCellCAndroid.cellanalyzercore.controller.finder.CellFinderNeighbour;
import ch.nuiCellCAndroid.cellanalyzercore.model.Cell;
import ch.nuiCellCAndroid.cellanalyzercore.model.Properties;

/**
 * 
 * 
 * 
 * 
 * 
 * 
 * @author nicolas baer
 */
public class Analyzer {
	
	/**
	 * default constructor
	 */
	public Analyzer(){
		
	}
	
	/**
	 * @throws IOException src or dst file problems
	 * 
	 */
	public void analyze(Properties properties) throws IOException{
		// crop image to given size
		ImageProcessor imageProcessorCrop = new ImageProcessor(properties.getImageSrc().getCanonicalPath());
		imageProcessorCrop.crop(properties.getCropTop(), properties.getCropBottom(), properties.getCropRight(), properties.getCropLeft());
		imageProcessorCrop.saveImage(properties.getImageCrop().getCanonicalPath());
				
		// init color image processor
		ImageProcessor imageProcessorColor = new ImageProcessor(properties.getImageCrop().getCanonicalPath());
		imageProcessorColor.loadColorImage();
		
		// process threshold image
		ImageProcessor imageProcessorThreshold = new ImageProcessor(properties.getImageCrop().getCanonicalPath());
		imageProcessorThreshold.loadGrayImage();
		imageProcessorThreshold.loadThresholdImage();
		
		// watershed threshold image TODO
		
		
		// find cells by neighboring algorithm
		ICellFinder cellFinder = new CellFinderNeighbour();
		ArrayList<Cell> cellsPossible = cellFinder.findCells(imageProcessorThreshold);
		
		if(cellsPossible != null && !cellsPossible.isEmpty()){
			// mark all cells with a bounding box
			for(Cell cell : cellsPossible){
				imageProcessorColor.drawCell(cell.getBoundingBox());
			}
			
			// filter cells by size
			ICellFilter filterSize = new CellFilterSize();
			filterSize.setProperties(properties);
			ArrayList<Cell> cellsFiltered = filterSize.filterCells(cellsPossible);
			
			// filter cells by shape
			ICellFilter filterShape = new CellFilterShape();
			filterShape.setProperties(properties);
			cellsFiltered = filterShape.filterCells(cellsFiltered);
			
			// mark interesting cells colored
			for(Cell cell : cellsPossible){
				imageProcessorColor.markCell(cell);
			}
			
			// TODO write logs and histograms
			Logger logger = new Logger(properties.getLogFile());
			logger.writeLog(cellsPossible, cellsFiltered);
			
			// save results
			imageProcessorColor.saveImage(properties.getImageCells().getCanonicalPath());
			imageProcessorThreshold.saveImage(properties.getImageThreshold().getCanonicalPath());
			//imageProcessorHistogram.saveImage(properties.getImageHistogram().getCanonicalPath());

			
		} else{
			// no cells found
		}
		
	}
	
}
