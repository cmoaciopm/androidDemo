package net.cmoaciopm.demo.framework;

import net.cmoaciopm.demo.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.TextView;

public class BitmapActivity extends Activity {

	private TextView textView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_text);
		
		textView = (TextView)findViewById(android.R.id.text1);
		Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
		textView.setText("BitmapFactory.decodeResource() create a bitmap which is " + (bitmap1.isMutable()?"mutable":"immutable"));
		
		
	}
}
