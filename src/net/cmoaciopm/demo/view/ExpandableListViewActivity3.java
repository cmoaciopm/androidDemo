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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ExpandableListViewActivity3 extends Activity {

	private String[] mArray;
	private String[] mArray2;
	
	private Button mButton;
	private ListView mListView;
	
	private int count;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expand_listview3);
		
		mButton = (Button)findViewById(android.R.id.button1);
		mListView = (ListView)findViewById(android.R.id.list);
		
		mArray2 = getResources().getStringArray(R.array.four_pop);
		mArray = getResources().getStringArray(R.array.colors);
      
		ArrayList<String> list = new ArrayList<String>();
		for(int i=0; i<mArray.length; i++) {
		   list.add(mArray[i]);
		}
		ArrayList<String> list2 = new ArrayList<String>();
		for(int i=0; i<mArray2.length; i++) {
		   list2.add(mArray2[i]);
		}
		
		
		
		final ExpandableListAdapter adapter = new ExpandableListAdapter();
		ExpandableListAdapter.SectionAdapter mAdapter = adapter.new SectionAdapter(adapter, list);
		ExpandableListAdapter.SectionAdapter mAdapter2 = adapter.new SectionAdapter(adapter, list2);
		adapter.add("haha");
		adapter.addSection("Section1", mAdapter);
		adapter.add("haha2");
		adapter.addSection("Section2", mAdapter2);
		adapter.add("haha3");
		mListView.setAdapter(adapter);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				adapter.onItemClick(position);
			}
		});
		
	}
	

	
}
