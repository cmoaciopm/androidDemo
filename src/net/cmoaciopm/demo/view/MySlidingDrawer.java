package net.cmoaciopm.demo.view;

import net.cmoaciopm.demo.R;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class MySlidingDrawer extends LinearLayout {

	private View mHandle;
	private View mContent;
	
	private int mHandleLeft;
	
	private int mContentWidth;
	private int mContentHeight;
	
	private int mContentVisibleX = 0; //0-mContentWidth
	
	private float mTouchLastX;
	
	//handle is being draged?
	private boolean mDraging = false;
	//handle has move some distance?
	private boolean mHandleMoved = false;
	
	private boolean mExpanded = false;
	
	
	public MySlidingDrawer(Context context) {
        super(context);
    }
	
	public MySlidingDrawer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public MySlidingDrawer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		//TODO use a fix id now
		mHandle = findViewById(R.id.handle);
		mContent = findViewById(R.id.content);
		
		removeView(mHandle);
		removeView(mContent);
		
		addView(mContent);
		addView(mHandle);
		mContent.setVisibility(View.GONE);
		
		mHandle.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();
				float nowX = event.getRawX();
				switch(action) {
				case MotionEvent.ACTION_DOWN:
					mDraging = true;
					mTouchLastX = event.getRawX();
					return true;
				case MotionEvent.ACTION_MOVE:
					mContent.setVisibility(View.VISIBLE);
					int distance = (int)(nowX - mTouchLastX);
					mTouchLastX = nowX;
					
					if(mContentVisibleX+distance >= mContentWidth) {
						mContentVisibleX = mContentWidth;
					} else if(mContentVisibleX+distance <= 0) {
						mContentVisibleX = 0;
					} else {
						mContentVisibleX += distance;
					}
					
					mHandleMoved = true;
					invalidate();
					break;
				case MotionEvent.ACTION_UP:
					//TODO only click on handle
					
					if(nowX > mContentWidth/2) {
						//TODO
						mHandler.obtainMessage(1).sendToTarget();
					} else {
						mHandler.obtainMessage(0).sendToTarget();
					}
					mHandleMoved = false;
					break;
				}
				return false;
			}			
		});
		
		/*mHandle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mDraging = true;
				if(mExpanded) {
					mContent.setVisibility(View.VISIBLE);
					mHandler.obtainMessage(1).sendToTarget();
				} else {
					mHandler.obtainMessage(0).sendToTarget();
				}
				
			}
		});*/
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		mContentWidth = mContent.getWidth();
		mContentHeight = mContent.getHeight();
	}
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
		//TODO only support slide from left to right
		if(mDraging) {
			canvas.translate(-(mContentWidth-mContentVisibleX), 0);
			Log.d("", String.format("Content view x=%d", mContentVisibleX));
		}
		super.dispatchDraw(canvas);
	}
	
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case 0:
				if(mContentVisibleX < 10) {
					mContent.setVisibility(View.GONE);
					mContentVisibleX = 0;
					mDraging = false;
					mExpanded = false;
				} else {
					mContentVisibleX -= 10;
					mHandler.sendMessageAtTime(mHandler.obtainMessage(0), 1000/60);
				}
				break;
			case 1:
				if(mContentVisibleX+10>mContentWidth) {
					mContentVisibleX = mContentWidth;
					mDraging = false;
					mExpanded = true;
				} else {
					mContentVisibleX += 10;
					mHandler.sendMessageAtTime(mHandler.obtainMessage(1), 1000/60);
				}
				break;
			}
			
			
			invalidate();
		}
	};
	
}
