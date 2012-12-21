package net.cmoaciopm.demo.view;

import net.cmoaciopm.demo.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SideBarScrollActivity extends Activity {

    private SideBarScrollView mSideBarScrollView;
    private Button mButtonScrollLeft;
    private Button mButtonScrollRight;
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.side_bar_scroll_view);
       
       mSideBarScrollView = (SideBarScrollView)findViewById(R.id.side_bar_scroll_view);
       mButtonScrollLeft = (Button)findViewById(android.R.id.button1);
       mButtonScrollRight = (Button)findViewById(android.R.id.button2);
       
       View sideBarView = getLayoutInflater().inflate(R.layout.side_bar, null);
       ListView listView = (ListView)sideBarView.findViewById(android.R.id.list);
       ArrayAdapter adapter = new ArrayAdapter<String>(this,
             android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.colors));
       listView.setAdapter(adapter);
       listView.setOnItemClickListener(new OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view,
               int position, long id)
         {
            Toast.makeText(SideBarScrollActivity.this, position + " is clicked", Toast.LENGTH_SHORT).show();
         }
       });
       
       View contentView = getLayoutInflater().inflate(R.layout.drag_drop_color, null);
       TextView buttonRed = (TextView)contentView.findViewById(R.id.textview_red);
       TextView buttonGreen = (TextView)contentView.findViewById(R.id.textview_green);
       TextView buttonBlue = (TextView)contentView.findViewById(R.id.textview_blue);
       TextView buttonYellow = (TextView)contentView.findViewById(R.id.textview_yellow);
       
       OnClickListener listener = new OnClickListener() {
         @Override
         public void onClick(View v)
         {
            Toast.makeText(SideBarScrollActivity.this, v.getId()+"", Toast.LENGTH_SHORT).show();
         }
       };
       buttonRed.setOnClickListener(listener);
       buttonGreen.setOnClickListener(listener);
       buttonBlue.setOnClickListener(listener);
       buttonYellow.setOnClickListener(listener);
       
       mSideBarScrollView.initViews(new View[]{sideBarView, contentView}, 1, new SideBarScrollView.SizeCallback() {
         
         @Override
         public int[] getViewSize(int idx, int w, int h)
         {
            switch(idx) {
            case 0:
               return new int[]{300, h};
            case 1:
               return new int[]{w, h};
            }
            return null;
         }
       });
       
       mButtonScrollLeft.setOnClickListener(new OnClickListener() {
         @Override
         public void onClick(View v)
         {
            mSideBarScrollView.open();
         }
       });
       
       mButtonScrollRight.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v)
          {
             mSideBarScrollView.close();
          }
        });
       
       
    }
    
}
