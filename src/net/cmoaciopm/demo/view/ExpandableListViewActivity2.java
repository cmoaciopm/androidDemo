package net.cmoaciopm.demo.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.cmoaciopm.demo.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ExpandableListViewActivity2 extends Activity {

	private String[] mArray;
	private MyAdapter mAdapter;
	
	private Button mButton;
	private ListView mListView;
	
	private int count;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expand_listview2);
		
		mButton = (Button)findViewById(android.R.id.button1);
		mListView = (ListView)findViewById(android.R.id.list);
		
		mArray = getResources().getStringArray(R.array.four_pop);
		//mArray = getResources().getStringArray(R.array.colors);
      
		ArrayList<String> list = new ArrayList<String>();
		for(int i=0; i<mArray.length; i++) {
		   list.add(mArray[i]);
		}
		mAdapter = new MyAdapter(list);
		mListView.setAdapter(mAdapter);
		
		mButton.setOnClickListener(new OnClickListener() {
         @Override
         public void onClick(View v)
         {
            if(count%2 == 0) {
               mAdapter.fold();
            } else {
               mAdapter.unfold();
            }
            count++;
         }
		});
	}
	
	class MyAdapter extends BaseAdapter {
	   private List<String> mList;
	   private List<String> mFoldList = new ArrayList<String>();
	   private final int foldCount = 4;
	   private boolean isFold = false;
	   private boolean foldable = false;
	   
	   public MyAdapter(List<String> list) {
	      mList = list;
	      
	      if(list.size() < foldCount) {
	         isFold = false;
	         foldable = true;
	         return;
	      }
	      
	      while(mList.size() > foldCount) {
	         mFoldList.add(mList.remove(foldCount));
	      }
	   }

	   public void fold() {
	      if(foldable && isFold) {
	         return;
	      }
	      
	      mList.removeAll(mFoldList);
	      isFold = true;
	      notifyDataSetChanged();
	   }
	   
	   public void unfold() {
	      if(foldable && !isFold) {
	         return;
	      }
	      
	      mList.addAll(mFoldList);
	      isFold = false;
	      notifyDataSetChanged();
	   }
	   
      @Override
      public int getCount()
      {
         return mList.size();
      }
      @Override
      public Object getItem(int position)
      {
         return mList.get(position);
      }
      @Override
      public long getItemId(int position)
      {
         return position;
      }
      @Override
      public View getView(int position, View convertView, ViewGroup parent)
      {
         TextView textView = new TextView(parent.getContext());
         textView.setText(mList.get(position));
         return textView;
      }
        
	}
	
}
