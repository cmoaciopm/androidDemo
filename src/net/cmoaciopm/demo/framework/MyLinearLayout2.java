package net.cmoaciopm.demo.framework;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

public class MyLinearLayout2 extends LinearLayout {
	public MyLinearLayout2(Context context) {
        super(context);
    }
	
	public MyLinearLayout2(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public MyLinearLayout2(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onLayout(boolean changed, int left, int top, int right, int bottom) {
	   Log.d("agong", "onLayout");
	   int width = right-left;
	   int height = bottom - top;
	   if(width!=0 && height!=0) {
	      mOnMeasuredListener.notify(width, height);
	   } 
	   super.onLayout(changed, left, top, right, bottom);
	}
	
	private OnMeasuredListener mOnMeasuredListener;
	public static interface OnMeasuredListener {
	   public void notify(int width, int height);
	}
   public void setOnMeasuredListener(OnMeasuredListener mOnMeasuredListener)
   {
      this.mOnMeasuredListener = mOnMeasuredListener;
   };
}
