/**
 * 
 */
package ch.nuiCellCAndroid.cellanalyzercore.model;

import java.io.File;

/**
 * Offers persistence of the properties during one program cycle.
 * 
 * @author nicolas baer
 */
public class Properties {
	// image file properties
	private File imageSrc;
	private File imageCells;
	private File imageThreshold;
	private File imageGray;
	private File imageCrop;
	
	// log properties
	private File logFile;

	// crop properties
	private int cropTop = 0;
	private int cropBottom = 0;
	private int cropLeft = 0;
	private int cropRight = 0;
	
	// filter criterion
	private int filterSizeMinNumberOfPoints = 7;
	private int filterSizeMaxNumberOfPoints = 42;
	private int filterShapeMinDeviationCellAspectRationPercent = 30;
	
	
	// cell count properties
	private float cellCountCoefficient = 2f;

	
	
	/**
	 * default constructor
	 */
	public Properties(){
		
	}



	public File getImageSrc() {
		return imageSrc;
	}



	public void setImageSrc(File imageSrc) {
		this.imageSrc = imageSrc;
	}



	public File getImageCells() {
		return imageCells;
	}



	public void setImageCells(File imageCells) {
		this.imageCells = imageCells;
	}



	public File getImageThreshold() {
		return imageThreshold;
	}



	public void setImageThreshold(File imageThreshold) {
		this.imageThreshold = imageThreshold;
	}



	public File getImageGray() {
		return imageGray;
	}



	public void setImageGray(File imageGray) {
		this.imageGray = imageGray;
	}



	public File getImageCrop() {
		return imageCrop;
	}



	public void setImageCrop(File imageCrop) {
		this.imageCrop = imageCrop;
	}



	public int getCropTop() {
		return cropTop;
	}



	public void setCropTop(int cropTop) {
		this.cropTop = cropTop;
	}



	public int getCropBottom() {
		return cropBottom;
	}



	public void setCropBottom(int cropBottom) {
		this.cropBottom = cropBottom;
	}



	public int getCropLeft() {
		return cropLeft;
	}



	public void setCropLeft(int cropLeft) {
		this.cropLeft = cropLeft;
	}



	public int getCropRight() {
		return cropRight;
	}



	public void setCropRight(int cropRight) {
		this.cropRight = cropRight;
	}



	public int getFilterSizeMinNumberOfPoints() {
		return filterSizeMinNumberOfPoints;
	}



	public void setFilterSizeMinNumberOfPoints(int filterSizeMinNumberOfPoints) {
		this.filterSizeMinNumberOfPoints = filterSizeMinNumberOfPoints;
	}



	public int getFilterSizeMaxNumberOfPoints() {
		return filterSizeMaxNumberOfPoints;
	}



	public void setFilterSizeMaxNumberOfPoints(int filterSizeMaxNumberOfPoints) {
		this.filterSizeMaxNumberOfPoints = filterSizeMaxNumberOfPoints;
	}



	public int getFilterShapeMinDeviationCellAspectRationPercent() {
		return filterShapeMinDeviationCellAspectRationPercent;
	}



	public void setFilterShapeMinDeviationCellAspectRationPercent(
			int filterShapeMinDeviationCellAspectRationPercent) {
		this.filterShapeMinDeviationCellAspectRationPercent = filterShapeMinDeviationCellAspectRationPercent;
	}



	public float getCellCountCoefficient() {
		return cellCountCoefficient;
	}



	public void setCellCountCoefficient(float cellCountCoefficient) {
		this.cellCountCoefficient = cellCountCoefficient;
	}


	public File getLogFile() {
		return logFile;
	}



	public void setLogFile(File logFile) {
		this.logFile = logFile;
	}
	
}
