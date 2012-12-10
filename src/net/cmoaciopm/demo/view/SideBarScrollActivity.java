package net.cmoaciopm.demo.view;

import net.cmoaciopm.demo.R;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
       
       View leftView = new View(this);
       ViewGroup.LayoutParams lp1 = new ViewGroup.LayoutParams(300, ViewGroup.LayoutParams.MATCH_PARENT);
       leftView.setLayoutParams(lp1);
       leftView.setBackgroundColor(Color.BLUE);
       
       View rightView = new View(this);
       ViewGroup.LayoutParams lp2 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
       rightView.setLayoutParams(lp2);
       rightView.setBackgroundColor(Color.RED);
       
       mSideBarScrollView.initViews(new View[]{leftView, rightView}, 1, new SideBarScrollView.SizeCallback() {
         
         @Override
         public int[] getViewSize(int idx, int w, int h)
         {
            switch(idx) {
            case 0:
               return new int[]{300, ViewGroup.LayoutParams.MATCH_PARENT};
            case 1:
               return new int[]{w, h};
            }
            return null;
         }
      });
       
    }
}
