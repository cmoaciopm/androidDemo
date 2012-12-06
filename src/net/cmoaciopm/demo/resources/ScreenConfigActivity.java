package net.cmoaciopm.demo.resources;

import net.cmoaciopm.demo.R;
import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class ScreenConfigActivity extends Activity {

	private TextView mTextViewScreenDpi;
	private TextView mTextViewScreenCategory;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_config);
		
		mTextViewScreenDpi = (TextView)findViewById(android.R.id.text1);
		mTextViewScreenCategory = (TextView)findViewById(android.R.id.text2);
		
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		switch(dm.densityDpi) {
		case DisplayMetrics.DENSITY_LOW:
			mTextViewScreenDpi.setText(getString(R.string.text_screen_density, "Low"));
			break;
		case DisplayMetrics.DENSITY_MEDIUM:
			mTextViewScreenDpi.setText(getString(R.string.text_screen_density, "Medium"));
			break;
		case DisplayMetrics.DENSITY_HIGH:
			mTextViewScreenDpi.setText(getString(R.string.text_screen_density, "High"));
			break;
		case DisplayMetrics.DENSITY_XHIGH:
			mTextViewScreenDpi.setText(getString(R.string.text_screen_density, "XHigh"));
			break;
		default:
			mTextViewScreenDpi.setText(getString(R.string.text_screen_density, dm.densityDpi));
		}
		
		int screenLayout = this.getResources().getConfiguration().screenLayout;
		int screenCategory = screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
		switch(screenCategory) {
		case Configuration.SCREENLAYOUT_SIZE_SMALL:
			mTextViewScreenCategory.setText(getString(R.string.text_screen_category, "small"));
			break;
		case Configuration.SCREENLAYOUT_SIZE_NORMAL:
			mTextViewScreenCategory.setText(getString(R.string.text_screen_category, "normal"));
			break;
		case Configuration.SCREENLAYOUT_SIZE_LARGE:
			mTextViewScreenCategory.setText(getString(R.string.text_screen_category, "large"));
			break;
		case Configuration.SCREENLAYOUT_SIZE_XLARGE:
			mTextViewScreenCategory.setText(getString(R.string.text_screen_category, "xlarge"));
			break;
		default:
			mTextViewScreenCategory.setText(getString(R.string.text_screen_category, "unknown"));
		}
		
	}
	
	

}
