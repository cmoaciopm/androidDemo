package net.cmoaciopm.demo.other;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.cmoaciopm.demo.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ExpandableListViewActivity extends Activity {

	private Button mButton;
	private Button mButton2;
	private ListView mListView;
	
	private int count;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.another_expand_listview);
		
		mButton = (Button)findViewById(android.R.id.button1);
		mButton2 = (Button)findViewById(android.R.id.button2);
		mListView = (ListView)findViewById(android.R.id.list);
		
		List list = new ArrayList();
		
		final MyExpandableSection section = new MyExpandableSection(this);
		section.add("All programs");
		section.add("My documents");
		section.add("3");
		section.add("4");
		section.add("5");
		section.add("6");
		//section.add("My documents2");
		//section.add("My documents3");
		//section.add("My documents4");
		//section.add("My documents5");
		//section.add("My documents6");
		
		final MyExpandableSection section2 = new MyExpandableSection(this);
		section2.add("1");
		section2.add("2");
		section2.add("3");
		section2.add("4");
		section2.add("5");
		section2.add("6");
		
		list.add("haha");
		list.add(section);
		list.add("hehe");
		list.add(section2);
		
		final ExpandableListAdapter adapter = new ExpandableListAdapter(list);
		mListView.setAdapter(adapter);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO
			   Object obj = adapter.getItem(position);
			   if(obj instanceof Header) {
			      Header header = (Header)obj;
			      if(header.isExpand) {
			         adapter.unexpand(obj);
			      } else {
			         adapter.expand(obj);
			      }
			      
			   }
			}
		});
		
		mButton.setOnClickListener(new OnClickListener() {
         @Override
         public void onClick(View v)
         {
            //section.add(new Date().toString());
            section.remove(section.size()-1);
            adapter.notifyDataSetChanged();
         }
		});
		
		mButton2.setOnClickListener(new OnClickListener() {
         @Override
         public void onClick(View v)
         {
            
            int expandCount = section2.getExpandCount();
            if(expandCount > 1) {
               section2.updateExpandCount(expandCount, expandCount-6);
            }
            adapter.notifyDataSetChanged();
         }
      });
	}
}
