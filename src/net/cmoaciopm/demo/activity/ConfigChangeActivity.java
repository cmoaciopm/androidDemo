package net.cmoaciopm.demo.activity;

import net.cmoaciopm.demo.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class ConfigChangeActivity extends Activity {

	private static final String TAG = "ConfigChangeActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config_change);
		
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Log.d(TAG, "===" + this.toString() + " onStart()===");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "===" + this.toString() + "onResume()===");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "===" + this.toString() + "onPause()===");
	}
	
	@Override
	public void onStop() {
		super.onStop();
		Log.d(TAG, "===" + this.toString() + "onStop()===");
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "===" + this.toString() + "onDestroy()===");
	}
}
