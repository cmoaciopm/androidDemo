package net.cmoaciopm.demo.view;

import net.cmoaciopm.demo.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.SlidingDrawer;

public class SlidingDrawerActivity extends Activity {
	
	private SlidingDrawer mSlidingDrawer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sliding_drawer);
		
		mSlidingDrawer = (SlidingDrawer)findViewById(R.id.sliding_drawer);
		
	}

}
