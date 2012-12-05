package net.cmoaciopm.demo.view;

import net.cmoaciopm.demo.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;

public class MoveInTouchActivity extends Activity implements OnClickListener {

	private static final String TAG = "MoveInTouchActivity";
	
	private Button mButtonMoveLeft;
	private Button mButtonMoveRight;
	private Button mButtonMoveUp;
	private Button mButtonMoveDown;
	private ImageView mImageView;
	
	private float mImageLastX;
	private float mImageLastY;
	
	private VelocityTracker mVelocityTracker;
	private float mXVelocity;
	private float mYVelocity;
	
	private final int MSG_FLING = 1;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message m) {
            switch (m.what) {
            case MSG_FLING:
            	//TODO
            	int maxWidth = getResources().getDisplayMetrics().widthPixels;
            	int maxHeight = getResources().getDisplayMetrics().heightPixels;
            	if(mXVelocity>0 && mImageView.getLeft()>0) {
            		mImageView.offsetLeftAndRight(-10);
            		mHandler.obtainMessage(MSG_FLING).sendToTarget();
            	} else if(mXVelocity<0 && mImageView.getRight()<maxWidth) {
            		mImageView.offsetLeftAndRight(10);
            		mHandler.obtainMessage(MSG_FLING).sendToTarget();
            	}
            	
            	if(mYVelocity<0 && mImageView.getBottom()<maxHeight) {
            		mImageView.offsetTopAndBottom(10);
            		mHandler.obtainMessage(MSG_FLING).sendToTarget();
            	} else if(mYVelocity>0 && mImageView.getTop()>0) {
            		mImageView.offsetTopAndBottom(-10);
            		mHandler.obtainMessage(MSG_FLING).sendToTarget();
            	}
            	
            	break;
            }
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.move_in_touch);
		
		mButtonMoveLeft = (Button)findViewById(android.R.id.button1);
		mButtonMoveRight = (Button)findViewById(android.R.id.button2);
		mButtonMoveUp = (Button)findViewById(android.R.id.button3);
		mButtonMoveDown = (Button)findViewById(R.id.button_move_down);
		mImageView = (ImageView)findViewById(android.R.id.icon);
		
		mButtonMoveLeft.setOnClickListener(this);
		mButtonMoveRight.setOnClickListener(this);
		mButtonMoveUp.setOnClickListener(this);
		mButtonMoveDown.setOnClickListener(this);
		
		mImageView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();

				switch(action) {
				case MotionEvent.ACTION_DOWN:
					mImageLastX = event.getRawX();
					mImageLastY = event.getRawY();
					
					mVelocityTracker = VelocityTracker.obtain();
					
					break;
				case MotionEvent.ACTION_MOVE:
					float x = event.getRawX();
					float y = event.getRawY();
					mImageView.offsetLeftAndRight((int)(x-mImageLastX));
					mImageView.offsetTopAndBottom((int)(y-mImageLastY));
					mImageLastX = x;
					mImageLastY = y;
					
					mVelocityTracker.addMovement(event);
					break;
				case MotionEvent.ACTION_UP:
					mHandler.obtainMessage(MSG_FLING).sendToTarget();
					
					mVelocityTracker.computeCurrentVelocity(1000);
					mXVelocity = mVelocityTracker.getXVelocity();
					mYVelocity = mVelocityTracker.getYVelocity();
					mVelocityTracker.recycle();
					Log.d(TAG,  String.format("X velocity = %f, Y velocity = %f", mXVelocity, mYVelocity));
					break;
				}
				return true;
			}
		});
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch(id) {
		case android.R.id.button1:
			mImageView.offsetLeftAndRight(-10);
			break;
		case android.R.id.button2:
			mImageView.offsetLeftAndRight(10);
			break;
		case android.R.id.button3:
			mImageView.offsetTopAndBottom(-10);
			break;
		case R.id.button_move_down:
			mImageView.offsetTopAndBottom(10);
			break;
		}
	}
}
