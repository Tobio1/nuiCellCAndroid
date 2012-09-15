package ch.nuiCellCAndroid.cellanalyzercore.controller;

import java.io.File;
import java.util.ArrayList;

import ch.nuiCellCAndroid.cellanalyzercore.model.Cell;

/**
 * Interface for chart drawer
 * @author nicolasbar
 *
 */
public interface IChartDraw {

	/**
	 * draws a bar chart for the given cells, regarding the size and distribution.
	 * @param cells analyzed cells
	 * @param imageFile file to store chart image
	 */
	public void drawCellBarChart(ArrayList<Cell> cells, File imageFile) throws Exception;
	
	
}
