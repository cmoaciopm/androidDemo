package net.cmoaciopm.demo.view;

import net.cmoaciopm.demo.R;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditTextActivity extends Activity
{
   
   private EditText mEditText;
   private Button mButton1;
   private Button mButton2;
   
   private static final InputFilter[] DISABLED_INPUT_FILTERS = new InputFilter[]{new InputFilter() {
      @Override
      public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
         //return dest.subSequence(dstart, dend);
         return "";
      }
   }};
   private InputFilter[] oldInputFilters;
   
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.edit_text);
      
      
      mEditText = (EditText)findViewById(android.R.id.edit);
      mButton1 = (Button)findViewById(android.R.id.button1);
      mButton2 = (Button)findViewById(android.R.id.button2);
      
      oldInputFilters = mEditText.getFilters();
      
      mEditText.addTextChangedListener(new TextWatcher() {

         @Override
         public void afterTextChanged(Editable s) {
            Log.d("EditTextActivity", "afterTextChanged");
         }

         @Override
         public void beforeTextChanged(CharSequence s, int start, int count,
               int after) {
            Log.d("EditTextActivity", "beforeTextChanged s =" + s.toString());
         }

         @Override
         public void onTextChanged(CharSequence s, int start, int before,
               int count)
         {
            Log.d("EditTextActivity", "onTextChanged s =" + s.toString());
         }
         
      });
      
      mButton1.setOnClickListener(new OnClickListener() {

         @Override
         public void onClick(View v)
         {
            mEditText.setFilters(oldInputFilters);
         }
         
      });
      
      mButton2.setOnClickListener(new OnClickListener() {

         @Override
         public void onClick(View v)
         {
            mEditText.setFilters(DISABLED_INPUT_FILTERS);
         }
         
      });
   }

}
