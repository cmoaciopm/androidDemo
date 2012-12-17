package net.cmoaciopm.demo.framework;

import net.cmoaciopm.demo.R;
import net.cmoaciopm.demo.framework.MyLinearLayout2.OnMeasuredListener;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ViewMeasureActivity extends Activity
{

   private Button mButton;
   private TextView mTextView1;
   private TextView mTextView2;
   private TextView mTextView3;
   private TextView mTextView4;
   
   private View mTestView;
   private MyLinearLayout2 myLinearLayout2;
   
   private long mShowTime;
   
   private Handler mHandler = new Handler() {
      @Override
      public void handleMessage(Message msg) {
         mTextView3.setText(
               String.format("Delay 3 sec,\nView measured width=%d, height=%d", mTestView.getMeasuredWidth(), mTestView.getMeasuredHeight()));
      }
   };
   
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.view_measure);
      
      mButton = (Button)findViewById(android.R.id.button1);
      mTextView1 = (TextView)findViewById(android.R.id.text1);
      mTextView2 = (TextView)findViewById(android.R.id.text2);
      mTextView3 = (TextView)findViewById(R.id.textview_delay_measure_result);
      mTextView4 = (TextView)findViewById(R.id.textview_delay_measure_result2);
      myLinearLayout2 = (MyLinearLayout2)findViewById(R.id.layout_custom);
      
      mTestView = findViewById(R.id.view_test);
      mTestView.setVisibility(View.GONE);
      myLinearLayout2.setVisibility(View.GONE);
      mHandler.sendMessageDelayed(new Message(), 3*1000);
      
      mTextView1.setText(String.format("onCreate, View measured width=%d, height=%d", mTestView.getMeasuredWidth(), mTestView.getMeasuredHeight()));
      mButton.setOnClickListener(new OnClickListener() {
         @Override
         public void onClick(View v)
         {
            mTestView.setVisibility(View.VISIBLE);
            mTextView2.setText(String.format("After set visible,\nView measured width=%d, height=%d", mTestView.getMeasuredWidth(), mTestView.getMeasuredHeight()));
            mHandler.sendMessageDelayed(new Message(), 3*1000);
            
            myLinearLayout2.setVisibility(View.VISIBLE);
            
            mShowTime = System.currentTimeMillis();
         }
      });
      
      myLinearLayout2.setOnMeasuredListener(new OnMeasuredListener() {
         @Override
         public void notify(int width, int height)
         {
            long sizeGetTime = System.currentTimeMillis();
            mTextView4.setText(String.format("Custom View measured width=%d, height=%d\nShowTime=%d, SizeOkTime=%d", 
                  myLinearLayout2.getMeasuredWidth(), myLinearLayout2.getMeasuredHeight(), mShowTime, sizeGetTime));
            
         }
      });
   }
   
}
