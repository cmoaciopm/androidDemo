package net.cmoaciopm.demo.view;

import net.cmoaciopm.demo.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpandableListViewActivity extends Activity {

	private ExpandableListView mExpandableListView;
	private MyAdapter mAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expand_listview);
		
		mExpandableListView = (ExpandableListView)findViewById(R.id.expandable_list_view);
		
		mAdapter = new MyAdapter();
		mExpandableListView.setAdapter(mAdapter);
		
		mExpandableListView.expandGroup(0);
	}
	
	class MyAdapter extends BaseExpandableListAdapter {
        private String[] groups = { "People Names", "Dog Names", "Cat Names", "Fish Names" };
        private String[][] children = {
                { "Arnold", "Barry", "Chuck", "David" },
                { "Ace", "Bandit", "Cha-Cha", "Deuce" },
                { "Fluffy", "Snuggles" },
                { "Goldy", "Bubbles" }
        };
        
        public Object getChild(int groupPosition, int childPosition) {
            return children[groupPosition][childPosition];
        }

        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        public int getChildrenCount(int groupPosition) {
            return children[groupPosition].length;
        }

        public TextView getGenericView() {
            // Layout parameters for the ExpandableListView
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 64);

            TextView textView = new TextView(ExpandableListViewActivity.this);
            textView.setLayoutParams(lp);
            // Center the text vertically
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            // Set the text starting position
            textView.setPadding(36, 0, 0, 0);
            return textView;
        }
        
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                View convertView, ViewGroup parent) {
        	View view = View.inflate(parent.getContext(), R.layout.expandable_list_item2, null);
        	
        	TextView textView = (TextView)view.findViewById(android.R.id.text1);
            textView.setText(getChild(groupPosition, childPosition).toString());
            
            return view;
        }

        public Object getGroup(int groupPosition) {
            return groups[groupPosition];
        }

        public int getGroupCount() {
            return groups.length;
        }

        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                ViewGroup parent) {
        	View view = View.inflate(parent.getContext(), R.layout.expandable_list_item, null);
        	
        	TextView textView = (TextView)view.findViewById(android.R.id.text1);
            textView.setText(getGroup(groupPosition).toString());
            
            ImageView imageView = (ImageView)view.findViewById(android.R.id.icon);
			if(isExpanded) {
				imageView.setImageResource(R.drawable.arrow_down);
			} else {
				imageView.setImageResource(R.drawable.arrow);
			}
            return view;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        public boolean hasStableIds() {
            return true;
        }
	}
	
}
