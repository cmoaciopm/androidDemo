package net.cmoaciopm.demo.view;

import net.cmoaciopm.demo.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.ListView;

public class StickyHeaderListView extends ListView {

	public StickyHeaderListView(Context context) {
		super(context);
	}
	
	public StickyHeaderListView(Context context, AttributeSet attrs) {
		super(context, attrs, android.R.attr.listViewStyle);
	}

	@Override
	public void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setAlpha(100);
		
		int width = canvas.getWidth();
		int height = 100;
		
		canvas.drawRect(0, 0, width, height, paint);
		
		int firstVisible = this.getFirstVisiblePosition();
		String firstItem = (String)this.getItemAtPosition(firstVisible);
		
		paint.setTextSize(18);
		paint.setColor(Color.BLACK);
		canvas.drawText("First item is " + firstItem, 0, height/2, paint);
		
		/*
		ColorDrawable shadow = new ColorDrawable(R.color.gray_b8);
		shadow.setAlpha(100);
		shadow.setBounds(0, 0, 100, 100);
		shadow.draw(canvas);*/
	}
}
