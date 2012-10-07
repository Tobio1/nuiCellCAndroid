package ch.nuiCellCAndroid.cellanalyzer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import ch.nuiCellCAndroid.cellanalyzercore.controller.Analyzer;
import ch.nuiCellCAndroid.cellanalyzercore.model.Properties;

public class MainActivity extends Activity implements Runnable{

	// ui variables
	private EditText textTargetUri;
	private EditText pxTopEditText;
	private EditText pxBottomEditText;
	private EditText pxLeftEditText;
	private EditText pxRightEditText;
	private EditText pixelMinSizeEditText;
	private EditText pixelMaxSizeEditText;
	private EditText circularityEditText;
	private EditText conversionFactorEditText;
	
	// progress dialog
	private ProgressDialog progressDialog;
	private AlertDialog.Builder alertDialogBuilder;
	
	private String picturePath;
	private Properties properties;
	private String resultMessage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// bind vars
		this.textTargetUri = (EditText) findViewById(R.id.imageName);
		this.pxTopEditText = (EditText) findViewById(R.id.pxTop);
		this.pxBottomEditText = (EditText) findViewById(R.id.pxBottom);
		this.pxLeftEditText = (EditText) findViewById(R.id.pxLeft);
		this.pxRightEditText = (EditText) findViewById(R.id.pxRight);
		this.pixelMinSizeEditText = (EditText) findViewById(R.id.pixelMinSize);
		this.pixelMaxSizeEditText = (EditText) findViewById(R.id.pixelMaxSize);
		this.circularityEditText = (EditText) findViewById(R.id.circularity);
		this.conversionFactorEditText = (EditText) findViewById(R.id.conversionFactor);
		
		// init properties
		this.properties = new Properties();
		this.pxTopEditText.setText(new Integer(properties.getCropTop()).toString());
		this.pxBottomEditText.setText(new Integer(properties.getCropBottom()).toString());
		this.pxLeftEditText.setText(new Integer(properties.getCropLeft()).toString());
		this.pxRightEditText.setText(new Integer(properties.getCropRight()).toString());
		this.pixelMinSizeEditText.setText(new Integer(properties.getFilterSizeMinNumberOfPoints()).toString());
		this.pixelMaxSizeEditText.setText(new Integer(properties.getFilterSizeMaxNumberOfPoints()).toString());
		this.circularityEditText.setText(new Integer(properties.getFilterShapeMinDeviationCellAspectRationPercent()).toString());
		this.conversionFactorEditText.setText(new Float(properties.getCellCountCoefficient()).toString());
	}

	/**
	 * Image selection intend call
	 * @param arg0
	 */
	public void selectImage(View arg0) {
		Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, 0);
	}

	/**
	 * Image selection callback method
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			this.picturePath = cursor.getString(columnIndex);

			String[] path = this.picturePath.split(System
					.getProperty("file.separator"));
			this.textTargetUri.setText(path[path.length - 1]);

			cursor.close();
		}
	}

	public void analyzeImage(View arg0) {

		// show progress dialog
		this.progressDialog = ProgressDialog.show(this, "Please wait", "Analyzing image...", true, false);
		
		// alert box
		alertDialogBuilder = new AlertDialog.Builder(this);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// read path and filename of current image
		String[] uri = picturePath.split(System.getProperty("file.separator"));
		String path = picturePath.substring(0, picturePath.length() - uri[uri.length - 1].length());
		String fileName = uri[uri.length - 1];
		fileName = fileName.substring(0, fileName.lastIndexOf('.'));
		String fileEnding = this.picturePath.substring(this.picturePath.lastIndexOf('.') + 1,this.picturePath.length());
		
		// create directory for results
		String resultDir = path + new SimpleDateFormat("yyMMddHHmm").format(new Date()) + fileName + System.getProperty("file.separator");
		new File(resultDir).mkdir();

		// initialize properties
		properties.setImageSrc(new File(this.picturePath));
		properties.setImageCells(new File(resultDir + fileName + "-cells." + fileEnding));
		properties.setImageThreshold(new File(resultDir + fileName + "-threshold." + fileEnding));
		properties.setImageCrop(new File(resultDir + fileName + "-crop." + fileEnding));
		properties.setImageGray(new File(resultDir + fileName + "-gray." + fileEnding));
		properties.setImageChart(new File(resultDir + fileName + "-chart.png"));
		properties.setLogFile(new File(resultDir + fileName + "-log.txt"));
		properties.setLogSimpleFile(new File(resultDir + fileName + "-simplelog.txt"));
		
		// read properties from ui
		properties.setCropTop(new Integer(this.pxTopEditText.getText().toString()));
		properties.setCropBottom(new Integer(this.pxBottomEditText.getText().toString()));
		properties.setCropLeft(new Integer(this.pxLeftEditText.getText().toString()));
		properties.setCropRight(new Integer(this.pxRightEditText.getText().toString()));
		properties.setFilterSizeMinNumberOfPoints(new Integer(this.pixelMinSizeEditText.getText().toString()));
		properties.setFilterSizeMaxNumberOfPoints(new Integer(this.pixelMaxSizeEditText.getText().toString()));
		properties.setFilterShapeMinDeviationCellAspectRationPercent(new Integer(this.circularityEditText.getText().toString()));
		properties.setCellCountCoefficient(new Float(this.conversionFactorEditText.getText().toString()));
		
		// set chart drawer correctly
		// -> really important, since the default drawer of cellanalyzercore will fail on android
		//properties.setChartDraw(new ChartDrawAndroid(getApplicationContext()));
		properties.setChartDraw(null);  // do not draw the chart...
		
		// start analyzer
		Thread thread = new Thread(this);
        thread.start();
		
	}

	@Override
	public void run() {
		// start analyzing
		float result = 0f;
		
		Analyzer analyzer = new Analyzer(properties);
		try {
			result = analyzer.analyze();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		resultMessage = "";
		if(result != 0){
			resultMessage = result +" cells per \u00B5L found";
		} else{
			resultMessage = "no cells found";
		}
		
		// call handler
		handler.sendEmptyMessage(0);
		
	}
	
	private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // stop progress bar    
        	progressDialog.cancel();
                
        	// show dialog
    		alertDialogBuilder.setTitle("CD4 Analysis Result").setMessage(resultMessage).setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    		           public void onClick(DialogInterface dialog, int id) {
    		                dialog.cancel();
    		           }
    		       });
    		AlertDialog alert = alertDialogBuilder.create();
    		alert.show();
        }
	};
	
}
