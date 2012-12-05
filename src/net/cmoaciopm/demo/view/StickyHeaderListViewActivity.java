package net.cmoaciopm.demo.view;

import net.cmoaciopm.demo.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class StickyHeaderListViewActivity extends Activity {

	private StickyHeaderListView mListView;
	private String[] mStrings = new String[]{
			"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
			"11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sticky_header_list_view);
		
		mListView = (StickyHeaderListView)findViewById(android.R.id.list);
		ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mStrings);
		mListView.setAdapter(adapter);
	}
	
	
}
