/**
 * 
 */
package ch.nuiCellCAndroid.cellanalyzercore.model;

/**
 * @author nicolas baer
 *
 */
public class Point {
	private int x;
	private int y;
	
	public Point(){
		
	}
	
	/**
	 * @param x
	 * @param y
	 */
	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public Point(int[] coords){
		this.x = coords[0];
		this.y = coords[1];
	}
	
	public int[] getCoords(){
		return new int[]{this.x, this.y};
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	
	
}
