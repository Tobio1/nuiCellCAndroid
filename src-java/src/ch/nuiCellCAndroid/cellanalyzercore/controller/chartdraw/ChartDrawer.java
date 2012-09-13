/**
 * 
 */
package ch.nuiCellCAndroid.cellanalyzercore.controller.chartdraw;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.TreeMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import ch.nuiCellCAndroid.cellanalyzercore.controller.IChartDraw;
import ch.nuiCellCAndroid.cellanalyzercore.model.Cell;

/**
 * Handles the xy-chart for the cell analyzer results.
 * 
 * @author nicolasbar
 */
public class ChartDrawer implements IChartDraw{

	@Override
	public void drawCellBarChart(ArrayList<Cell> cells, File imageFile) throws Exception {
		
		TreeMap<Integer, Integer> cellMap = new TreeMap<Integer, Integer>();
		ArrayList<Integer> numberOfPoints = new ArrayList<Integer>();
		for(Cell cell : cells){
			int points = cell.getPointsAmount();
			
			if(cellMap.containsKey(points)){
				cellMap.put(points, cellMap.get(points) + 1);
			} else{
				cellMap.put(points, 1);
			}
			
			numberOfPoints.add(points);			
		}
		
		int max = cellMap.lastKey() + 3;
		int min = 0;

		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(int counter = min; counter < max; counter++){
			Integer value = cellMap.get(counter);
			if(value != null){
				dataset.setValue(value, "cell count [cells]", ""+counter);
			}
		}
		
		JFreeChart chart = ChartFactory.createBarChart("Cell Size Distribution","cell size [pixel]", "cell count [cells]", dataset, PlotOrientation.VERTICAL, false,true, false);
		chart.setBackgroundPaint(Color.white);
		chart.getTitle().setPaint(Color.black); 
		CategoryPlot p = chart.getCategoryPlot(); 
		p.setRangeGridlinePaint(Color.black); 
		
		try {
			ChartUtilities.writeChartAsPNG(new FileOutputStream(imageFile), chart, 600, 400);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
}
