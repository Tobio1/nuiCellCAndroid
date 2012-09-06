/**
 * 
 */
package ch.nuiCellCAndroid.cellanalyzercore.controller.finder;

import java.util.ArrayList;

import ch.nuiCellCAndroid.cellanalyzercore.controller.ICellFinder;
import ch.nuiCellCAndroid.cellanalyzercore.controller.ImageProcessor;
import ch.nuiCellCAndroid.cellanalyzercore.model.Cell;
import ch.nuiCellCAndroid.cellanalyzercore.model.Point;

import com.googlecode.javacv.cpp.opencv_core.IplImage;

/**
 * @author nicolas baer
 *
 */
public class CellFinderNeighbour implements ICellFinder{

	/* (non-Javadoc)
	 * @see ch.nuiCellCAndroid.cellanalyzer.controller.ICellFinder#findCells(com.googlecode.javacv.cpp.opencv_core.IplImage)
	 */
	@Override
	public ArrayList<Cell> findCells(ImageProcessor imageProcessor) {
		// get image from processor
		IplImage image = imageProcessor.getImage();
		
		ArrayList<Cell> cells = new ArrayList<Cell>();
		
		// inititalize cell map
		int[][] cellMap = new int[image.height()][image.width()];
		
		for(int width = 0; width < image.width(); width++){
			for(int height = 0; height < image.height(); height++){
				if(cellMap[height][width] == 0){
					
					// find neighbors recursively
					Cell cell = new Cell();
					this.findNeighbors(imageProcessor, new Point(width, height), cell, cellMap);
					
					// add to found cells
					if(cell.getPointsAmount() > 0){
						cells.add(cell);
					}
				}
			}
		}
		
		return cells;
	}
	
	
	/**
	 * 
	 * @param imageProcessor
	 * @param point
	 * @param cell
	 * @param cellMap
	 */
	private void findNeighbors(ImageProcessor imageProcessor, Point point, Cell cell, int[][] cellMap){
		// check for max threshold value in pixel
		if(imageProcessor.get2D(point.getY(), point.getX()).getVal(0) == ImageProcessor.MAX_THRESHOLD_IMAGE_VALUE){
			// set cell as visited
			cellMap[point.getY()][point.getX()] = 1;
			
			// add point to cell
			cell.addPoint(point);
			
			// check field one down
			if(point.getY()+1 < (imageProcessor.getImage().height()) && cellMap[point.getY()+1][point.getX()] == 0){
				this.findNeighbors(imageProcessor, new Point(point.getX(),point.getY()+1), cell, cellMap);
			}
			
			// check field one right
			if(point.getX()+1 < (imageProcessor.getImage().width()) && cellMap[point.getY()][point.getX()+1] == 0){
				this.findNeighbors(imageProcessor, new Point(point.getX()+1,point.getY()), cell, cellMap);
			}
			
			// check field one up
			if(point.getY()-1 >= 0 && cellMap[point.getY()-1][point.getX()] == 0){
				this.findNeighbors(imageProcessor, new Point(point.getX(),point.getY()-1), cell, cellMap);
			}	
		}
	}

}
