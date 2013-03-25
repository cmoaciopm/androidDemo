package net.cmoaciopm.demo.framework;

import net.cmoaciopm.demo.R;
import android.app.Activity;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class PopupWindowActivity extends Activity
{

   private Button mButton;
   private PopupWindow mPopupWindow;

   private LayoutInflater mInflater;

   @Override
   public void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.popup_window);

      mInflater = getLayoutInflater();

      mButton = (Button) findViewById(android.R.id.button1);

      mButton.setOnClickListener(new OnClickListener() {
         @Override
         public void onClick(View v)
         {
            View view = mInflater.inflate(R.layout.popup_window_content, null);
            mPopupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
                  LayoutParams.WRAP_CONTENT);
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            mPopupWindow.setOutsideTouchable(true);
            // Show at center of screen
            // mPopupWindow.showAtLocation(mButton, Gravity.CENTER, 0, 0);
            mPopupWindow.showAsDropDown(mButton, 3000, 0);

            TextView textViewInPopup = (TextView) view
                  .findViewById(android.R.id.text1);
            textViewInPopup.setOnClickListener(new OnClickListener() {
               @Override
               public void onClick(View v)
               {
                  Toast.makeText(PopupWindowActivity.this, "You clicked me...",
                        Toast.LENGTH_SHORT).show();
               }
            });
         }

      });
   }
}
