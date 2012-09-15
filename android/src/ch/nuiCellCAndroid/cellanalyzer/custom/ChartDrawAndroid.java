package ch.nuiCellCAndroid.cellanalyzer.custom;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import ch.nuiCellCAndroid.cellanalyzercore.controller.IChartDraw;
import ch.nuiCellCAndroid.cellanalyzercore.model.Cell;

/**
 * 
 * @author nicolas baer
 */
public class ChartDrawAndroid implements IChartDraw {
	
	public ChartDrawAndroid(Context context){

	}
	
	@Override
	public void drawCellBarChart(ArrayList<Cell> cells, File imageFile) throws Exception {

		// TODO implement chart view with achartengine (http://www.achartengine.org/)!
		
	}
}
