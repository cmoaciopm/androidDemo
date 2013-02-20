package net.cmoaciopm.demo.framework;

import net.cmoaciopm.demo.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ContextMenuActivity extends Activity
{
   private TextView mTextView;
   
   
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.context_menu);
   
      mTextView = (TextView)findViewById(android.R.id.text1);
      
      this.registerForContextMenu(mTextView);
   }

   @Override
   public void
   onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)  // IN
   {
      super.onCreateContextMenu(menu, v, menuInfo);
      MenuInflater inflater = getMenuInflater();
      menu.add("test");
      menu.add("test2");
   }
   
   @Override
   public boolean
   onContextItemSelected(MenuItem item) {
      Toast.makeText(this, item.getTitle() + " is clicked", Toast.LENGTH_LONG).show();
      return true;
   }
}
