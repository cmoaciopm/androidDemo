package net.cmoaciopm.demo.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.util.Log;
import android.widget.BaseAdapter;

public abstract class AbsExpandableListAdapter extends BaseAdapter
{

   protected List mList = new ArrayList();
   private DefaultDataObserver mDataObserver = new DefaultDataObserver();
   
   public static interface DataObserver {
      public void onAdd(ExpandableSection section, Object obj);
      public void onAdd(ExpandableSection section, int index, Object obj);
      public void onAddAll(ExpandableSection section, List list);
      public void onClear(ExpandableSection section);
      public void onRemove(ExpandableSection section, int index);
      public void onRemove(ExpandableSection section, Object obj);
      
      public void onExpandCountChanged(ExpandableSection section, int from, int to);
   }
   
   class DefaultDataObserver implements DataObserver {

      @Override
      public void onAdd(ExpandableSection section, Object obj)
      {
         synchronized(this) {
            Iterator it = expandableItems.keySet().iterator();
            while(it.hasNext()) {
               Object key = it.next();
               if(expandableItems.get(key).section == section) {
                  ExpandableItem item = expandableItems.get(key);
                  if(!item.expandable && item.visibleList.size()+1<=item.expandCount) {
                     int headerIndex = mList.indexOf(item.rawHeader);
                     mList.add(headerIndex+item.visibleList.size()+1, obj);
                     item.visibleList.add(obj);
                  } else if(!item.expandable && item.visibleList.size()+1>item.expandCount) {
                     item.hiddenList.add(obj);
                     item.expandable = true;
                  } else if(item.expandable && !item.isExpand) {
                     item.hiddenList.add(obj);
                  } else if(item.expandable && item.isExpand) {
                     int headerIndex = mList.indexOf(item.rawHeader);
                     mList.add(headerIndex+item.visibleList.size()+item.hiddenList.size()+1, obj);
                     item.hiddenList.add(obj);
                  }
                  Log.d("", String.format("List size=%d, visible size=%d, hidden size=%d ", mList.size(), item.visibleList.size(), item.hiddenList.size()));
                  break;
               }
            }       
         }

      }

      @Override
      public void onAdd(ExpandableSection section, int index, Object obj)
      {
         // TODO
      }

      @Override
      public void onAddAll(ExpandableSection section, List list)
      {
         Iterator it = expandableItems.keySet().iterator();
         while(it.hasNext()) {
            Object key = it.next();
            if(expandableItems.get(key).section == section) {
               ExpandableItem item = expandableItems.get(key);
               if(!item.expandable && item.visibleList.size()+list.size()<=item.expandCount) {
                  int headerIndex = mList.indexOf(item.rawHeader);
                  for(int i=0; i<list.size(); i++) {
                     mList.add(headerIndex+item.visibleList.size()+(i+1), list.get(i));
                  }
                  item.visibleList.addAll(list);
               } else if(!item.expandable && item.visibleList.size()+list.size()>item.expandCount) {
                  for(int i=0; i<item.expandCount-item.visibleList.size(); i++) {
                     item.visibleList.add(list.remove(i));
                  }
                  item.hiddenList.add(list);
                  item.expandable = true;
               } else if(item.expandable && !item.isExpand) {
                  item.hiddenList.add(list);
               } else if(item.expandable && item.isExpand) {
                  int headerIndex = mList.indexOf(item.rawHeader);
                  for(int i=0; i<list.size(); i++) {
                     mList.add(headerIndex+item.visibleList.size()+item.hiddenList.size()+(i+1), list.get(i));
                  }
                  item.hiddenList.addAll(list);
               }
               break;
            }
         }
      }

      @Override
      public void onClear(ExpandableSection section)
      {
         // TODO Auto-generated method stub
         
      }

      @Override
      public void onRemove(ExpandableSection section, int index)
      {
         // TODO Auto-generated method stub
         Iterator it = expandableItems.keySet().iterator();
         while(it.hasNext()) {
            Object key = it.next();
            if(expandableItems.get(key).section == section) {
               ExpandableItem item = expandableItems.get(key);
               int headerIndex = mList.indexOf(item.rawHeader);
               if(!item.expandable) {
                  item.visibleList.remove(index);
                  mList.remove(headerIndex+index+1);
               } else if(item.expandable) {
                  if(index<item.visibleList.size()) {
                     item.visibleList.remove(index);
                     Object obj = item.hiddenList.remove(0);
                     item.visibleList.add(obj);
                     mList.remove(headerIndex+index+1);
                     mList.add(headerIndex+item.visibleList.size(), obj);
                  } else {
                     item.hiddenList.remove(index-item.visibleList.size());
                     if(item.isExpand) {
                        mList.remove(headerIndex+index+1);
                     }
                  }
                  
                  if(item.hiddenList.size()==0) {
                     item.expandable = false;
                     item.isExpand = false;
                     item.section.onUnexpand();
                  }
               }
               break;
            }
         }
      }

      @Override
      public void onRemove(ExpandableSection section, Object obj)
      {
         // TODO Auto-generated method stub
         
      }

      @Override
      public void onExpandCountChanged(ExpandableSection section, int from, int to)
      {
         if(from == to) {
            return;
         }
         
         if(from<0 || to<0) {
            return;
         }
         
         Iterator it = expandableItems.keySet().iterator();
         while(it.hasNext()) {
            Object key = it.next();
            if(expandableItems.get(key).section == section) {
               ExpandableItem item = expandableItems.get(key);
               if(!item.expandable && from < to) {
                  // If not expandable and expandCount increase, no need to handle
               } else if(!item.expandable && from > to) {
                  // If not expandable and expandCount decrease
                  if(item.visibleList.size() <= to) {
                     // If visible count is less than target expandCount, no need to handle
                  } else {
                     // If visible count is more than target expandCount, section become expandable
                     int visibleCount = item.visibleList.size();
                     for(int i=0; i<visibleCount-to; i++) {
                        // Hide element which exceed target expandCount
                        int headerIndex = mList.indexOf(item.rawHeader);
                        mList.remove(headerIndex+to+1);
                        // Move exceeded element from visible to hidden, append at the head of hidden area
                        item.hiddenList.add(i, item.visibleList.remove(to));
                     }
                     // Reset item
                     item.expandable = true;
                     item.isExpand = false;
                     // Notify section unexpand event
                     item.section.onUnexpand();
                  }
               } else if (item.expandable && from < to) {
                  // If expandable and expandCount increase
                  int oldHiddenCount = item.hiddenList.size();
                  int oldVisibleCount = item.visibleList.size();
                  int sectionSize = item.visibleList.size()+item.hiddenList.size();
                  int nowHiddenCount = (sectionSize<=to)?0:sectionSize-to;
                  for(int i=0; i<oldHiddenCount-nowHiddenCount; i++) {
                     // Move element from hidden to visible, and append at the end of visible area
                     item.visibleList.add(item.hiddenList.remove(0));
                  }
                  
                  int headerIndex = mList.indexOf(item.rawHeader);
                  int visibleCount = item.visibleList.size();
                  int hiddenCount = item.hiddenList.size();
                  if(item.isExpand) {
                     for(int i=0; i<visibleCount+hiddenCount; i++) {
                        mList.remove(headerIndex+1);
                     }
                  } else {
                     for(int i=0; i<oldVisibleCount; i++) {
                        mList.remove(headerIndex+1);
                     }
                  }
                  for(int i=0; i<visibleCount; i++) {
                     mList.add(headerIndex+i+1, item.visibleList.get(i));
                  }
                  
                  if(nowHiddenCount==0) {
                     // Reset item
                     item.expandable = false;
                     item.isExpand = false;
                     // Notify section unexpand event
                     item.section.onUnexpand();
                  }
               } else if(item.expandable && from > to) {
                  // If expandable and expandCount decrease
                  int headerIndex = mList.indexOf(item.rawHeader);
                  int oldVisibleCount = item.visibleList.size();
                  int moveStartIndex;
                  if(!item.isExpand) {
                     moveStartIndex = from;
                  } else {
                     moveStartIndex = item.visibleList.size();
                  }
                  for(int i=0; i<moveStartIndex-to; i++) {
                     item.hiddenList.add(i, item.visibleList.remove(to));
                  }
                  
                  int visibleCount = item.visibleList.size();
                  int hiddenCount = item.hiddenList.size();
                  if(!item.isExpand) {
                     for(int i=0; i<oldVisibleCount; i++) {
                        mList.remove(headerIndex+1);
                     }
                  } else {
                     for(int i=0; i<visibleCount+hiddenCount; i++) {
                        mList.remove(headerIndex+1);
                     }
                  }
                  for(int i=0; i<visibleCount; i++) {
                     mList.add(headerIndex+i+1, item.visibleList.get(i));
                  }
                  
                  if(item.isExpand) {
                     // Reset item
                     item.expandable = true;
                     item.isExpand = false;
                     // Notify section unexpand event
                     item.section.onUnexpand();
                  }
               }
               item.expandCount = to;
            }
         }
      }
   }
   
   class ExpandableItem {
      public List visibleList = new ArrayList();
      public List hiddenList = new ArrayList();
      public boolean isExpand = false;
      public boolean expandable = false;
      public Object rawHeader;
      public int expandCount;
      public ExpandableSection section;
   }
   
   private Map<Object, ExpandableItem> expandableItems = new HashMap<Object, ExpandableItem>();
   
   public void expand(Object obj) {
      // TODO
      ExpandableItem expandableItem = expandableItems.get(obj);
      if(expandableItem.expandable && !expandableItem.isExpand) {
         int headerIndex = mList.indexOf(obj);
         // TODO ?
         int insertStartIndex = headerIndex + expandableItem.expandCount + 1;
         for(int i=0; i<expandableItem.hiddenList.size(); i++) {
            mList.add(insertStartIndex+i, expandableItem.hiddenList.get(i));
         }
         expandableItem.isExpand = true;
         expandableItem.section.onExpand();
         notifyDataSetChanged();
      }
   }
   
   public void unexpand(Object obj) {
      // TODO
      ExpandableItem expandableItem = expandableItems.get(obj);
      
      if(expandableItem.expandable && expandableItem.isExpand) {
         int headerIndex = mList.indexOf(obj);
         int removeIndex = headerIndex + expandableItem.expandCount + 1;
         for(int i=0; i<expandableItem.hiddenList.size(); i++) {
            mList.remove(removeIndex);
         }
         expandableItem.isExpand = false;
         expandableItem.section.onUnexpand();
         notifyDataSetChanged();
      }
   }
   
   public void addAll(List list) {
      for(int i=0; i<list.size(); i++) {
         add(list.get(i));
      }
   }
   
   public void add(Object obj) {
      if(obj instanceof ExpandableSection) {
         ExpandableSection section = ((ExpandableSection)obj);
         section.setDataObserver(mDataObserver);
         
         ExpandableItem item = new ExpandableItem();
         mList.add(section.getHeaderData());
         item.section = section;
         item.rawHeader = section.getHeaderData();
         item.expandCount = section.getExpandCount();
         
         int expandCount = section.getExpandCount();
         List sectionData = new ArrayList();
         sectionData.addAll(section.getData());
         if(expandCount < 0) {
            // TODO
         } else if(expandCount == 0) {
            // TODO
         } else {
            if (sectionData.size() <= expandCount) {
               item.expandable = false;
               item.isExpand = false;
            } else {
               while (sectionData.size() > expandCount) {
                  item.hiddenList.add(sectionData.remove(expandCount));
               }
               item.expandable = true;
               item.isExpand = false;
               section.onUnexpand();
            }
         }
         
         item.visibleList = sectionData;
         mList.addAll(sectionData);
         expandableItems.put(item.rawHeader, item);
         return;
      }
      
      mList.add(obj);
      
      // TODO update index
   }
   

}
