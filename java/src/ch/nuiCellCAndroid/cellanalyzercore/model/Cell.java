/**
 * 
 */
package ch.nuiCellCAndroid.cellanalyzercore.model;

import java.util.ArrayList;

/**
 * @author nicolas baer
 *
 */
public class Cell {

	private ArrayList<Point> points;
	
	
	/**
	 * default constructor
	 */
	public Cell(){
		this.points = new ArrayList<Point>();
	}
	
	
	/**
	 * adds a point to the current cell
	 * @param point
	 */
	public void addPoint(Point point){
		this.points.add(point);
	}
	
	/**
	 * gets the emphasis on the current cell
	 * @return
	 */
	public Point getEmphasis(){
		int[] xminmax = this.getXMinMaxValues();
		int[] yminmax = this.getYMinMaxValues();
		
		Point emphasisPoint = new Point();
		emphasisPoint.setX(xminmax[0] + (xminmax[1] - xminmax[0]) / 2);
		emphasisPoint.setY(yminmax[0] + (yminmax[1] - yminmax[0]) / 2);

		return emphasisPoint;
	}
	
	public int getPointsAmount(){
		return this.points.size();
	}
	
	public Point getPointByIndex(int index){
		return this.points.get(index);
	}
	
	public ArrayList<Point> getPoints(){
		return this.points;
	}
	
	public Box getBoundingBox(){
		int[] xminmax = this.getXMinMaxValues();
		int[] yminmax = this.getYMinMaxValues();
		
		int width = Math.abs(xminmax[0] - xminmax[1]);
		int height = Math.abs(yminmax[0] - yminmax[1]);
		
		return new Box(xminmax[0], yminmax[0], width, height);
	}
	
	
	/**
	 * 
	 * @return
	 */
	private int[] getXMinMaxValues(){
		if(!this.points.isEmpty()){
			int minX = 0;
			int maxX = 0;
			
			boolean first = true;
			for(Point p : this.points){
				if(p.getX() < minX){
					minX = p.getX();
				}
				if(p.getX() > maxX){
					maxX = p.getX();
				}
				
				if(first){
					minX = p.getX();
					maxX = p.getX();
					
					first = false;
				}
			}
			
			return new int[]{minX, maxX};
			
		}
		
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	private int[] getYMinMaxValues(){
		if(!this.points.isEmpty()){
			int minY = 0;
			int maxY = 0;
			
			boolean first = true;
			for(Point p : this.points){
				if(p.getY() < minY){
					minY = p.getY();
				}
				if(p.getY() > maxY){
					maxY = p.getY();
				}
				
				if(first){
					minY = p.getY();
					maxY = p.getY();
					
					first = false;
				}
			}
			
			return new int[]{minY, maxY};
			
		}
		
		return null;
	}
}
