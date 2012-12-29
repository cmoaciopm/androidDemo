package net.cmoaciopm.demo.other;

import java.util.List;

import net.cmoaciopm.demo.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ExpandableListAdapter extends AbsExpandableListAdapter
{
   
   public ExpandableListAdapter(List list) {
      super.addAll(list);
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
      LayoutInflater inflater = LayoutInflater.from(parent.getContext());
      Object obj = mList.get(position);
      
      if(obj instanceof String) {
         View view = inflater.inflate(R.layout.expandable_list_item2, null);

         TextView tv = (TextView) view.findViewById(android.R.id.text1);
         tv.setText((String)obj);
         
         return view;
      } else {
         View view = inflater.inflate(R.layout.expandable_list_item2, null);

         TextView tv = (TextView) view.findViewById(android.R.id.text1);
         tv.setText("This is a header");
         
         return view;
      }
   }
   
   @Override
   public int getItemViewType(int position) {
      return 0;
   }

   @Override
   public int getViewTypeCount() {
      return 1;
   }
}
