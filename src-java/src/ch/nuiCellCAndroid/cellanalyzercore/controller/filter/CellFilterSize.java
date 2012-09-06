/**
 * 
 */
package ch.nuiCellCAndroid.cellanalyzercore.controller.filter;

import java.util.ArrayList;

import ch.nuiCellCAndroid.cellanalyzercore.controller.ICellFilter;
import ch.nuiCellCAndroid.cellanalyzercore.model.Cell;
import ch.nuiCellCAndroid.cellanalyzercore.model.Properties;

/**
 * @author nicolas baer
 *
 */
public class CellFilterSize implements ICellFilter {

	/**
	 * deprecated
	 */
	private static final int MINIMAL_NR_OF_POINTS_PER_CELL_FILTER_VALUE = 7;
	private static final int MAXIMAL_NR_OF_POINTS_PER_CELL_FILTER_VALUE = 42;
	
	
	private ArrayList<Cell> rejectedCells;
	private Properties properties;
	
	
	/**
	 * default constructor
	 */
	public CellFilterSize(){
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
			boolean rejected = false;
			// check if minimal size
			if(cell.getPointsAmount() > properties.getFilterSizeMaxNumberOfPoints()){
				this.rejectedCells.add(cell);
				rejected = true;
			}
			
			if(cell.getPointsAmount() < properties.getFilterSizeMinNumberOfPoints()){
				this.rejectedCells.add(cell);
				rejected = true;
			}
			
			if(!rejected){
				acceptedCells.add(cell);
			}
			
		}
		
		return acceptedCells;		
	}

	public void setProperties(Properties properties){
		this.properties = properties;
	}
	
	
	/* (non-Javadoc)
	 * @see ch.nuiCellCAndroid.cellanalyzer.controller.ICellFilter#getRejectedCells()
	 */
	@Override
	public ArrayList<Cell> getRejectedCells() {
		// TODO Auto-generated method stub
		return null;
	}

}
