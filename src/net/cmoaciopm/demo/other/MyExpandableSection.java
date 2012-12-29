package net.cmoaciopm.demo.other;

import java.util.List;

import android.content.Context;

public class MyExpandableSection<E> extends ExpandableSection<E>
{

   private Context mContext;
   private Header header = new Header();
   private int expandCount = 4;
   
   public MyExpandableSection(Context context) {
      mContext = context;
   }
   
   @Override
   public List getData()
   {
      return this;
   }

   @Override
   public int getExpandCount()
   {
      return expandCount;
   }
   
   public void updateExpandCount(int from, int to) {
      super.updateExpandCount(from, to);
      expandCount = to;
   }
   
   @Override
   public Object getHeaderData()
   {
      return header;
   }

   @Override
   public void onExpand()
   {
      header.isExpand = true;
   }

   @Override
   public void onUnexpand()
   {
      header.isExpand = false;
   }
}
