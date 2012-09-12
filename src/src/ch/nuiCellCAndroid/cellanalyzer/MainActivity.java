package ch.nuiCellCAndroid.cellanalyzer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import ch.nuiCellCAndroid.cellanalyzercore.controller.Analyzer;
import ch.nuiCellCAndroid.cellanalyzercore.model.Properties;

public class MainActivity extends Activity {

	// ui variables
	private EditText textTargetUri;
	private EditText pxTopEditText;
	private EditText pxBottomEditText;
	private EditText pxLeftEditText;
	private EditText pxRightEditText;
	private EditText pixelSizeEditText;
	private EditText circularityEditText;
	private EditText conversionFactorEditText;
	
	private String picturePath;
	private Properties properties;

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
		this.pixelSizeEditText = (EditText) findViewById(R.id.pixelSize);
		this.circularityEditText = (EditText) findViewById(R.id.circularity);
		this.conversionFactorEditText = (EditText) findViewById(R.id.conversionFactor);
		
		// init properties
		this.properties = new Properties();
		this.pxTopEditText.setText(new Integer(properties.getCropTop()).toString());
		this.pxBottomEditText.setText(new Integer(properties.getCropBottom()).toString());
		this.pxLeftEditText.setText(new Integer(properties.getCropLeft()).toString());
		this.pxRightEditText.setText(new Integer(properties.getCropRight()).toString());
		this.pixelSizeEditText.setText(new Integer(properties.getFilterSizeMinNumberOfPoints()).toString());
		this.circularityEditText.setText(new Integer(properties.getFilterShapeMinDeviationCellAspectRationPercent()).toString());
		this.conversionFactorEditText.setText(new Float(properties.getCellCountCoefficient()).toString());
	}

	public void selectImage(View arg0) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, 0);

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
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
		/*
		 * // get bitmap from media store Bitmap bitmap =
		 * BitmapFactory.decodeFile(this.picturePath);
		 */

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
		Properties properties = new Properties();
		properties.setImageSrc(new File(this.picturePath));
		properties.setImageCells(new File(resultDir + fileName + "-cells." + fileEnding));
		properties.setImageThreshold(new File(resultDir + fileName + "-threshold." + fileEnding));
		properties.setImageCrop(new File(resultDir + fileName + "-crop." + fileEnding));
		properties.setImageGray(new File(resultDir + fileName + "-gray." + fileEnding));
		properties.setImageHistogram(new File(resultDir + fileName + "-histogram." + fileEnding));
		properties.setLogFile(new File(resultDir + fileName + ".log"));
		
		// read properties from ui
		properties.setCropTop(new Integer(this.pxTopEditText.getText().toString()));
		properties.setCropBottom(new Integer(this.pxBottomEditText.getText().toString()));
		properties.setCropLeft(new Integer(this.pxLeftEditText.getText().toString()));
		properties.setCropRight(new Integer(this.pxRightEditText.getText().toString()));
		properties.setFilterSizeMinNumberOfPoints(new Integer(this.pixelSizeEditText.getText().toString()));
		properties.setFilterShapeMinDeviationCellAspectRationPercent(new Integer(this.circularityEditText.getText().toString()));
		properties.setCellCountCoefficient(new Float(this.conversionFactorEditText.getText().toString()));
		
		// start analyzing
		try {
			Analyzer analyzer = new Analyzer();
			analyzer.analyze(properties);
		} catch (IOException e) {
			Log.e("cellanalyzer", e.getMessage());
		}

	}
}
