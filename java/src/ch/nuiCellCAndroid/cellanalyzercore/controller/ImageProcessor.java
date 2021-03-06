/**
 * 
 */
package ch.nuiCellCAndroid.cellanalyzercore.controller;

import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import ch.nuiCellCAndroid.cellanalyzercore.model.Box;
import ch.nuiCellCAndroid.cellanalyzercore.model.Cell;
import ch.nuiCellCAndroid.cellanalyzercore.model.Point;

import com.googlecode.javacv.cpp.opencv_core.CvRect;
import com.googlecode.javacv.cpp.opencv_core.CvScalar;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

/**
 * The image processor handles all opencv functionality that is needed for the cd4 analysis.
 * Since javacv is not object oriented, it makes sense to keep all functionality in one class.
 * 
 * @author nicolas baer
 */
public class ImageProcessor {
	// threshold vars	
	private static final int THRESHOLD_VALUE = 81;
	public static final int MAX_THRESHOLD_IMAGE_VALUE = 255;
	private static final int THRESHOLD_TYPE = CV_THRESH_OTSU;
	
	// color vars
	private static final int GRAY = CV_LOAD_IMAGE_GRAYSCALE;
	private static final int COLOR = CV_LOAD_IMAGE_COLOR;
	
	// file vars
	private String filePath;
	private IplImage image;
	
	
	/**
	 * default constructor
	 * @param filePath path to image file
	 */
	public ImageProcessor(String filePath){
		this.filePath = filePath;
		this.image = cvLoadImage(filePath);
	}
	
	/**
	 * default constructor
	 * @param image image to process
	 */
	public ImageProcessor(IplImage image){
		this.image = image;
	}
	
	/**
	 * Defines the region of interest within the image.
	 * All further operations will be based on this region.
	 * 
	 * @param rectangle
	 */
	public void crop(int top, int bottom, int right, int left){
		// create rectangle
		CvRect rectangle = cvRect(right, top, (this.image.width()-right-left), (this.image.height()-top-bottom));
		
		// set region of interest
		cvSetImageROI(this.image, rectangle);
		
		// copy image 
		IplImage temp = cvCreateImage(cvGetSize(this.image), this.image.depth(), this.image.nChannels());
		cvCopy(this.image, temp);
		
		this.image = temp;
	}
	
	/**
	 * loads the image in gray scale
	 * @return image
	 */
	public IplImage loadGrayImage(){
		this.image = cvLoadImage(this.filePath, ImageProcessor.GRAY);
		return this.image;
	}
	
	/**
	 * loads the image in colors
	 * @return image
	 */
	public IplImage loadColorImage(){
		this.image = cvLoadImage(this.filePath, ImageProcessor.COLOR);
		return this.image;
	}
	
	/**
	 * loads the threshold image corresponding to the given settings:
	 * - threshold value
	 * - max threshold image value
	 * - threshold type
	 * @return image
	 */
	public double loadThresholdImage(){
		IplImage thresholdImage = cvCreateImage(cvGetSize(this.image), IPL_DEPTH_8U, CV_8UC1);
		double threshold = cvThreshold(this.image, thresholdImage, THRESHOLD_VALUE, MAX_THRESHOLD_IMAGE_VALUE, THRESHOLD_TYPE);
		this.image = thresholdImage;
		
		return threshold;
	}
	
	/**
	 * Gets the point at the given height and width within the image as 2d array.
	 * @param height
	 * @param width
	 * @return the specified array element
	 */
	public CvScalar get2D(int height, int width){
		return cvGet2D(this.image, height, width);
	}
	
	/**
	 * draws a cell by the given box model.
	 * @param box box to draw
	 */
	public void drawCell(Box box){
		
		// draw horizontal bouding box lines
		for(int width = box.getxMin(); width < box.getxMin()+box.getWidth(); width++){
			cvSet2D(this.image, box.getyMin(), width, CvScalar.WHITE);
			cvSet2D(this.image, box.getyMin()+box.getHeight(), width, CvScalar.WHITE);
		}
		
		// draw vertical bounding box lines
		for(int height = box.getyMin(); height < box.getyMin()+box.getHeight(); height++){
			cvSet2D(this.image, height, box.getxMin(), CvScalar.WHITE);
			cvSet2D(this.image, height, box.getxMin()+box.getWidth(), CvScalar.WHITE);
		}
	}

	/**
	 * marks the given cell in the image red by painting all points of the cell. 
	 * @param cell cell to mark
	 */
	public void markCell(Cell cell) {
		for(Point point : cell.getPoints()){
			cvSet2D(this.image, point.getY(), point.getX(), CvScalar.RED);
		}
	}
		
	/**
	 * saves the image to the given path
	 * @paramĘpath path to save image
	 */
	public void saveImage(String path){
		// reset roi
		cvResetImageROI(this.image);
		
		// save image
		cvSaveImage(path, this.image);
		cvReleaseImage(this.image);
	}

	/**
	 * TODO
	 */
	public void watershed() {
		
		
	}
	
	
	/**
	 * @return image
	 */
	public IplImage getImage() {
		return this.image;
	}
}
