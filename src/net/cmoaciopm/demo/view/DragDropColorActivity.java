package net.cmoaciopm.demo.view;

import net.cmoaciopm.demo.R;
import android.app.Activity;
import android.content.ClipData;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.widget.TextView;

public class DragDropColorActivity extends Activity implements OnLongClickListener {

	private static final String TAG = "DragDropColorActivity";
	
	private View mColorView;
	private TextView mTextViewColorRed;
	private TextView mTextViewColorBlue;
	private TextView mTextViewColorGreen;
	private TextView mTextViewColorYellow;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drag_drop_color);
		
		mColorView = findViewById(android.R.id.background);
		mTextViewColorRed = (TextView)findViewById(R.id.textview_red);
		mTextViewColorBlue = (TextView)findViewById(R.id.textview_blue);
		mTextViewColorGreen = (TextView)findViewById(R.id.textview_green);
		mTextViewColorYellow = (TextView)findViewById(R.id.textview_yellow);
		
		mTextViewColorRed.setOnLongClickListener(this);
		mTextViewColorBlue.setOnLongClickListener(this);
		mTextViewColorGreen.setOnLongClickListener(this);
		mTextViewColorYellow.setOnLongClickListener(this);
		
		mColorView.setOnDragListener(new OnDragListener() {
			@Override
			public boolean onDrag(View v, DragEvent event) {
				final int action = event.getAction();
				switch(action) {
				case DragEvent.ACTION_DRAG_STARTED:
					Log.d(TAG, v.getId() + " Drag started.");
					return true;
				case DragEvent.ACTION_DRAG_ENDED:
					Log.d(TAG, v.getId() + " Drag ended.");
					return true;
				case DragEvent.ACTION_DROP:
					Log.d(TAG, v.getId() + " Drag drop.");
					CharSequence colorText = event.getClipData().getItemAt(0).getText();
					int colorResId = Integer.parseInt(colorText.toString());
					mColorView.setBackgroundColor(getResources().getColor(colorResId));
					return true;
				case DragEvent.ACTION_DRAG_LOCATION:
					Log.d(TAG, v.getId() + " Drag location.");
					return true;
				case DragEvent.ACTION_DRAG_ENTERED:
					Log.d(TAG, v.getId() + " Drag entered.");
					return true;
				case DragEvent.ACTION_DRAG_EXITED:
					Log.d(TAG, v.getId() + " Drag exit.");
					return true;
				}
				return false;
			}
			
		});
	}
	
	class MyDrawShadowBuilder extends DragShadowBuilder {
		private ColorDrawable shadow;
		public MyDrawShadowBuilder(View view) {
			super(view);
			shadow = new ColorDrawable(R.color.gray_b8);
		}
		
		@Override
		public void onDrawShadow(Canvas canvas) {
			shadow.draw(canvas);
			View view = getView();
			if(view instanceof TextView) {
				Paint paint = new Paint();
				paint.setColor(Color.BLUE);
				paint.setTextSize(18);
				canvas.drawText(((TextView)view).getText().toString(), 0, view.getHeight()/2, paint);
				canvas.drawLine(0, 0, view.getWidth(), 0, paint);
				canvas.drawLine(0, view.getHeight()-1, view.getWidth(), view.getHeight()-1, paint);
			}
		}
		
		@Override
		public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
			int width = getView().getWidth();
			int height = getView().getHeight();
			shadowSize.set(width/2, height);
			shadowTouchPoint.set(width/4, height/2);
			shadow.setBounds(0, 0, width, height);
		}
		
	}

	@Override
	public boolean onLongClick(View v) {
		int id = v.getId();
		int colorValue = 0;
		MyDrawShadowBuilder drawShadowBuilder = null;
		switch(id) {
		case R.id.textview_red:
			colorValue = R.color.red;
			drawShadowBuilder = new MyDrawShadowBuilder(mTextViewColorRed);
			break;
		case R.id.textview_blue:
			colorValue = R.color.blue;
			drawShadowBuilder = new MyDrawShadowBuilder(mTextViewColorBlue);
			break;
		case R.id.textview_green:
			colorValue = R.color.green;
			drawShadowBuilder = new MyDrawShadowBuilder(mTextViewColorGreen);
			break;
		case R.id.textview_yellow:
			colorValue = R.color.yellow;
			drawShadowBuilder = new MyDrawShadowBuilder(mTextViewColorYellow);
			break;
		}
		
		if(colorValue!=0 && drawShadowBuilder!=null) {
			v.startDrag(ClipData.newPlainText("color", colorValue+""), drawShadowBuilder, null, 0);
		}
		
		return false;
	}
}
