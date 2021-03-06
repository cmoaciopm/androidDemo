package net.cmoaciopm.demo.view;

import java.util.ArrayList;
import java.util.List;

import net.cmoaciopm.demo.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseAdapter
{
   private List<Object> mList = new ArrayList<Object>();
   private List<SectionIndex> mSectionIndexList = new ArrayList<SectionIndex>();

   class SectionIndex
   {
      public int start;
      public int count;
      public int end;
      public SectionAdapter adapter;
   }

   class FoldableSection
   {
      public String header;
      public SectionAdapter adapter;

      public FoldableSection(String header, SectionAdapter adapter)
      {
         super();
         this.header = header;
         this.adapter = adapter;
      }
   }

   public void onItemClick(int position)
   {
      if (mList.get(position) instanceof FoldableSection) {
         FoldableSection fs = (FoldableSection) mList.get(position);
         if (fs.adapter.foldable && fs.adapter.isFold) {
            fs.adapter.unfold();
         } else if (fs.adapter.foldable && !fs.adapter.isFold) {
            fs.adapter.fold();
         }

      }
   }

   public void add(Object obj)
   {
      synchronized (mList) {
         mList.add(obj);
      }
   }

   public void addSection(String header, SectionAdapter adapter)
   {
      synchronized (mList) {
         mList.add(new FoldableSection(header, adapter));

         SectionIndex si = new SectionIndex();
         si.start = mList.size();
         si.count = adapter.getCount();
         si.end = si.start + si.count;
         si.adapter = adapter;
         mSectionIndexList.add(si);

         for (int i = 0; i < adapter.getCount(); i++) {
            mList.add(adapter.getItem(i));
         }
      }
   }

   @Override
   public int getCount()
   {
      return mList.size();
   }

   @Override
   public Object getItem(int position)
   {
      SectionIndex si;
      for (int i = 0; i < mSectionIndexList.size(); i++) {
         si = mSectionIndexList.get(i);
         if (position >= si.start && position < si.end) {
            return si.adapter.getItem(position - si.start);
         }
      }

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
      SectionIndex si;
      for (int i = 0; i < mSectionIndexList.size(); i++) {
         si = mSectionIndexList.get(i);
         if (position >= si.start && position < si.end) {
            return si.adapter.getView(position - si.start, convertView, parent);
         }
      }

      if (mList.get(position) instanceof FoldableSection) {
         FoldableSection foldableSection = (FoldableSection) mList
               .get(position);

         LayoutInflater inflater = LayoutInflater.from(parent.getContext());
         View view = inflater.inflate(R.layout.expandable_list_header, null);

         TextView tv = (TextView) view.findViewById(android.R.id.text1);
         tv.setText("Expand section header");

         ImageView iv = (ImageView) view.findViewById(android.R.id.icon);

         if (foldableSection.adapter.foldable
               && !foldableSection.adapter.isFold) {
            iv.setImageResource(R.drawable.arrow_down);
         } else {
            iv.setImageResource(R.drawable.arrow);
         }
         return view;
      }

      TextView tv = new TextView(parent.getContext());
      tv.setText("haha");
      return tv;
   }

   @Override
   public int getItemViewType(int position)
   {
      SectionIndex si;
      for (int i = 0; i < mSectionIndexList.size(); i++) {
         si = mSectionIndexList.get(i);
         if (position >= si.start && position < si.end) {
            return si.adapter.getItemViewType(position - si.start);
         }
      }
      return 0;
   }

   @Override
   public int getViewTypeCount()
   {
      int count = 0;
      for (int i = 0; i < mSectionIndexList.size(); i++) {
         count += mSectionIndexList.get(i).adapter.getViewTypeCount();
      }
      return count;
   }

   public class SectionAdapter extends BaseAdapter
   {
      private List<String> mSectionList;
      private List<String> mFoldList = new ArrayList<String>();
      private final int foldCount = 4;
      private boolean isFold = false;
      private boolean foldable = false;

      private BaseAdapter parentAdapter;

      public SectionAdapter(BaseAdapter parentAdapter, List<String> list)
      {
         this.parentAdapter = parentAdapter;
         mSectionList = list;

         if (list.size() <= foldCount) {
            isFold = false;
            foldable = false;
            return;
         }

         while (mSectionList.size() > foldCount) {
            mFoldList.add(mSectionList.remove(foldCount));
         }
         isFold = true;
         foldable = true;
      }

      public void fold()
      {
         if (foldable && isFold) {
            return;
         }

         mSectionList.removeAll(mFoldList);
         isFold = true;

         for (int i = 0; i < mSectionIndexList.size(); i++) {
            SectionIndex si = mSectionIndexList.get(i);
            if (this == si.adapter) {
               mList.removeAll(mFoldList);
               si.count -= mFoldList.size();
               si.end -= mFoldList.size();
               
               if(i < mSectionIndexList.size()-1) {
                  for(int j=i+1; j<mSectionIndexList.size(); j++) {
                     SectionIndex temp = mSectionIndexList.get(j);
                     temp.start -= mFoldList.size();
                     temp.end -= mFoldList.size();
                  }
               }
            }
         }

         parentAdapter.notifyDataSetChanged();
      }

      public void unfold()
      {
         if (foldable && !isFold) {
            return;
         }

         mSectionList.addAll(mFoldList);
         isFold = false;

         for (int i = 0; i < mSectionIndexList.size(); i++) {
            SectionIndex si = mSectionIndexList.get(i);
            if (this == si.adapter) {
               mList.addAll(si.end, mFoldList);
               si.count += mFoldList.size();
               si.end += mFoldList.size();
               
               if(i < mSectionIndexList.size()-1) {
                  for(int j=i+1; j<mSectionIndexList.size(); j++) {
                     SectionIndex temp = mSectionIndexList.get(j);
                     temp.start += mFoldList.size();
                     temp.end += mFoldList.size();
                  }
               }
            }
         }

         parentAdapter.notifyDataSetChanged();
      }

      @Override
      public int getCount()
      {
         return mSectionList.size();
      }

      @Override
      public Object getItem(int position)
      {
         return mSectionList.get(position);
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
         View view = inflater.inflate(R.layout.expandable_list_item2, null);

         TextView tv = (TextView) view.findViewById(android.R.id.text1);
         tv.setText(mSectionList.get(position));
         return view;
      }

   }
}
