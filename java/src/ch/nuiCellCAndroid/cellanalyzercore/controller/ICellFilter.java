/**
 * 
 */
package ch.nuiCellCAndroid.cellanalyzercore.controller;

import java.util.ArrayList;

import ch.nuiCellCAndroid.cellanalyzercore.model.Cell;
import ch.nuiCellCAndroid.cellanalyzercore.model.Properties;

/**
 * @author nicolas baer
 *
 */
public interface ICellFilter {

	/**
	 * Filters the cell given cell list.
	 * @return returns filtered cell list
	 */
	public ArrayList<Cell> filterCells(ArrayList<Cell> cells);
	
	
	/**
	 * Keeps track of all rejected cells
	 * @return rejected cells by filter
	 */
	public ArrayList<Cell> getRejectedCells();
	
	/**
	 * Hook to include custom properties
	 * @param properties
	 */
	public void setProperties(Properties properties);
	
}
