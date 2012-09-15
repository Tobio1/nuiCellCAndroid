/**
 * 
 */
package ch.nuiCellCAndroid.cellanalyzercore.controller;

import java.util.ArrayList;

import ch.nuiCellCAndroid.cellanalyzercore.model.Cell;

/**
 * @author nicolas baer
 *
 */
public interface ICellFinder {

	/**
	 * Finds all cells in the provided image.
	 * @param image image with cd4 cells
	 * @return list of cells found
	 */
	public ArrayList<Cell> findCells(ImageProcessor imageProcessor);
}
