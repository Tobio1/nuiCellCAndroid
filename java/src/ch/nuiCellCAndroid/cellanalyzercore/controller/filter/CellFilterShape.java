/**
 * 
 */
package ch.nuiCellCAndroid.cellanalyzercore.controller.filter;

import java.util.ArrayList;

import ch.nuiCellCAndroid.cellanalyzercore.controller.ICellFilter;
import ch.nuiCellCAndroid.cellanalyzercore.model.Box;
import ch.nuiCellCAndroid.cellanalyzercore.model.Cell;
import ch.nuiCellCAndroid.cellanalyzercore.model.Properties;

/**
 * @author nicolas baer
 *
 */
public class CellFilterShape implements ICellFilter {
		
	ArrayList<Cell> rejectedCells;

	private Properties properties;
	
	/**
	 * default constructor
	 */
	public CellFilterShape(){
		this.properties = null;
	}
	

	/* (non-Javadoc)
	 * @see ch.nuiCellCAndroid.cellanalyzer.controller.ICellFilter#filterCells(java.util.ArrayList)
	 */
	@Override
	public ArrayList<Cell> filterCells(ArrayList<Cell> cells) {
		// check properties
		if(this.properties == null){
			this.properties = new Properties();
		}
		
		ArrayList<Cell> acceptedCells = new ArrayList<Cell>();
		this.rejectedCells = new ArrayList<Cell>();
		
		for(Cell cell : cells){
			Box box = cell.getBoundingBox();
			
			float ratio;
			if((box.getWidth()-box.getHeight()) < 0){
				ratio = box.getWidth() / box.getHeight(); 
			} else{
				ratio = box.getHeight() / box.getWidth();
			}
			
			ratio = (100 - ratio * 100);
			if(ratio < properties.getFilterShapeMinDeviationCellAspectRationPercent()){
				acceptedCells.add(cell);
			} else{
				this.rejectedCells.add(cell);
			}
		}
		

		return acceptedCells;		
	}

	/* (non-Javadoc)
	 * @see ch.nuiCellCAndroid.cellanalyzer.controller.ICellFilter#getRejectedCells()
	 */
	@Override
	public ArrayList<Cell> getRejectedCells() {
		return this.rejectedCells;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.nuiCellCAndroid.cellanalyzercore.controller.ICellFilter#setProperties(ch.nuiCellCAndroid.cellanalyzercore.model.Properties)
	 */
	public void setProperties(Properties properties){
		this.properties = properties;
	}
}
