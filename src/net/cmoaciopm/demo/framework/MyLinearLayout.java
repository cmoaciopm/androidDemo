package net.cmoaciopm.demo.framework;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MyLinearLayout extends LinearLayout {
	private static final String TAG = "MyLinearLayout";
	private Button mButton1;
	private Button mButton2;
	
	public MyLinearLayout(Context context) {
        super(context);
    }
	
	public MyLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public MyLinearLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onFinishInflate() {
		mButton1 = (Button)findViewById(android.R.id.button1);
		mButton2 = (Button)findViewById(android.R.id.button2);
		
		mButton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
	}
	
	@Override
	public void dispatchDraw(Canvas canvas) {
		//super.dispatchDraw(canvas);
		Log.d(TAG, String.format("Canvas width=%d, height=%d",  canvas.getWidth(), canvas.getHeight()));
		
		final long drawingTime = getDrawingTime();
		drawChild(canvas, mButton1, drawingTime);
		
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setTextSize(25);
		
		int screenHeight = getResources().getDisplayMetrics().heightPixels;
		int screenWidth = getResources().getDisplayMetrics().widthPixels;
		
		paint.setColor(Color.RED);
		canvas.drawRect(0, 100, screenWidth, 200, paint);
		
		canvas.save();
		canvas.translate(0, 200);
		canvas.drawText("This text is drawn at dispatchDraw(), translate(0, 200) and draw at (0,100)", 0, 100, paint);
		canvas.restore();
		
		drawChild(canvas, mButton2, drawingTime);
	}
	
	
	
}
