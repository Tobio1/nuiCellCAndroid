/**
 * 
 */
package ch.nuiCellCAndroid.cellanalyzercore.model;

/**
 * @author nicolas baer
 *
 */
public class Box {
	private int xMin;
	private int yMin;
	
	private int width;
	private int height;
	
	/**
	 * default constructor
	 */
	public Box(){
		
	}

	/**
	 * @param xMin
	 * @param yMin
	 * @param width
	 * @param height
	 */
	public Box(int xMin, int yMin, int width, int height) {
		super();
		this.xMin = xMin;
		this.yMin = yMin;
		this.width = width;
		this.height = height;
	}

	/**
	 * @return the xMin
	 */
	public int getxMin() {
		return xMin;
	}

	/**
	 * @param xMin the xMin to set
	 */
	public void setxMin(int xMin) {
		this.xMin = xMin;
	}

	/**
	 * @return the yMin
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * @param yMin the yMin to set
	 */
	public void setyMin(int yMin) {
		this.yMin = yMin;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	
}
