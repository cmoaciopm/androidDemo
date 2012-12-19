package net.cmoaciopm.demo.framework;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ViewCallbackActivity extends Activity {

	private ScrollView mScrollView;
	private TextView mLogTextView;
	private StringBuffer mLogText = new StringBuffer();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 
		LinearLayout ll = new LinearLayout(this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
		ll.setLayoutParams(lp);
		ll.setOrientation(LinearLayout.VERTICAL);
		
		mScrollView = new ScrollView(this);
		LinearLayout.LayoutParams scrollViewLp = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT, 0);
		scrollViewLp.weight = 3.0f;
		mScrollView.setLayoutParams(scrollViewLp);
		
		mLogTextView = new TextView(this);
		LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		mScrollView.addView(mLogTextView, lp2);
		
		//------
		MyView myView = new MyView(this);
		LinearLayout.LayoutParams myViewLp = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT, 0);
		myViewLp.weight = 1.0f;
		myView.setLayoutParams(myViewLp);
		myView.setBackgroundColor(Color.BLUE);
		
		ll.addView(mScrollView);
		ll.addView(myView);
		setContentView(ll);
	}
	
	@Override
	public void onConfigurationChanged (Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Log.d("", "onConfigurationChanged() ...");
		// do nothing
	}
	
	class MyView extends View {

		public MyView(Context context) {
			super(context);
		}
		
		@Override
		public void onAttachedToWindow() {
			super.onAttachedToWindow();
			if(true) {
				mLogText = mLogText.append(timestamp()).append(" : ").append("onAttachedToWindow()\n");
				mLogTextView.setText(mLogText.toString());
			}
		}

		@Override
		public void onDetachedFromWindow() {
			super.onDetachedFromWindow();
			if(true) {
				mLogText = mLogText.append(timestamp()).append(" : ").append("onDetachedFromWindow()\n");
				mLogTextView.setText(mLogText.toString());
			}
		}
		
		@Override
		public void onConfigurationChanged(Configuration newConfig) {
			super.onConfigurationChanged(newConfig);
			if(true) {
				mLogText = mLogText.append(timestamp()).append(" : ").append("onConfigurationChanged()\n");
				mLogTextView.setText(mLogText.toString());
			}
		}
		
		@Override
		public void onFinishInflate() {
			super.onFinishInflate();
			if(true) {
				mLogText = mLogText.append(timestamp()).append(" : ").append("onFinishInflate()\n");
				mLogTextView.setText(mLogText.toString());
			}
		}

		@Override
		public void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
			super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
			if(true) {
				mLogText = mLogText.append(timestamp()).append(" : ").append("onFocusChanged()\n");
				mLogTextView.setText(mLogText.toString());
			}
		}
		
		@Override
		public void onLayout(boolean changed, int left, int top, int right, int bottom) {
			super.onLayout(changed, left, top, right, bottom);
			if(true) {
				mLogText = mLogText.append(timestamp()).append(" : ")
						.append(String.format("onLayout(changed=%s, l=%d, t=%d, r=%d, b=%d)\n", changed+"", left, top, right, bottom));
				mLogTextView.setText(mLogText.toString());
			}
		}
		
		@Override
		public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			if(true) {
				mLogText = mLogText.append(timestamp()).append(" : ").append("onMeasure()\n");
				mLogTextView.setText(mLogText.toString());
			}
		}
		
		@Override
		public void onSizeChanged(int w, int h, int oldw, int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);
			if(true) {
				mLogText = mLogText.append(timestamp()).append(" : ")
						.append(String.format("onSizeChanged(w=%d, h=%d, oldw=%d, oldh=%d)\n", w, h,oldw, oldh));
				mLogTextView.setText(mLogText.toString());
			}
		}
	}
	
	private String timestamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("mm:ss.SSSZ");
		return sdf.format(new Date());
	}
}
