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
 * CD4 Analyzer: handles the analysis of blood cells (see analyze method)
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
	 * CD4 Analyzis:
	 * 1. crops the image to the corresponding properties
	 * 2. loads the color image
	 * 3. loads the threshold image (otsu)
	 * 4. finds all possible cells
	 * 5. filters the cells by size and circularity
	 * 6. writes a log
	 * 7. writes the resulting cell image
	 * 8. creates a chart with the pixel size / amount
	 * 
	 * @param properties settings to use for the analyzis
	 * @throws IOException src or dst file problems
	 * @return cell count result
	 */
	public float analyze(Properties properties) throws IOException{
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
			for(Cell cell : cellsFiltered){
				imageProcessorColor.markCell(cell);
			}
			
			// write logs
			Logger logger = new Logger(properties.getLogFile());
			logger.writeLog(cellsPossible, cellsFiltered);
			
			// write charts
			try{
				if(properties.getChartDraw() != null){
					properties.getChartDraw().drawCellBarChart(cellsFiltered, properties.getImageChart());
				}
			} catch (Exception e){
				System.out.println("Could not write chart file\n" + e.getMessage());
			}
			
			// save results
			imageProcessorColor.saveImage(properties.getImageCells().getCanonicalPath());
			imageProcessorThreshold.saveImage(properties.getImageThreshold().getCanonicalPath());
			

			// calculate results
			float result = cellsFiltered.size() / properties.getCellCountCoefficient();
		
			// print results
			System.out.println(result +" cells per \u00B5L found |Êtotal amount of cells = " + cellsFiltered.size());
			
			return result;
			
		} else{
			// no cells found
			return 0f;
		}
		
	}
	
}
