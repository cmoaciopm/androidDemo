package net.cmoaciopm.demo.other;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.cmoaciopm.demo.other.AbsExpandableListAdapter.DataObserver;
import android.view.View;

public abstract class ExpandableSection<E> extends ArrayList<E> implements IExpandableSection
{
   private DataObserver mObserver;
   
   public void setDataObserver(DataObserver observer) {
      this.mObserver = observer;
   }
   
   @Override
   public boolean add(E e) {
      boolean result = super.add(e);
      if(mObserver != null) {
         mObserver.onAdd(this, e);
      }
      return result;
   }
   
   @Override
   public void add(int index, E e) {
      super.add(index, e);
      if(mObserver != null) {
         mObserver.onAdd(this, index, e);
      }
   }

   @Override
   public boolean addAll(Collection<? extends E> collection) {
      // TODO not support yet
      return false;
   }
   
   public boolean addAll(List list) {
      boolean result = super.addAll(list);
      if(mObserver != null) {
         mObserver.onAddAll(this, list);
      }
      return result;
   }
   
   @Override
   public void clear() {
      super.clear();
      if(mObserver != null) {
         mObserver.onClear(this);
      }
   }
   
   @Override
   public E remove(int index) {
      E result = super.remove(index);
      if(mObserver != null) {
         mObserver.onRemove(this, index);
      }
      return result;
   }
   
   @Override
   public boolean remove(Object obj) {
      boolean result = super.remove(obj);
      if(mObserver != null) {
         mObserver.onRemove(this, obj);
      }
      return result;
   }

   public void updateExpandCount(int from, int to) {
      if(mObserver != null) {
         mObserver.onExpandCountChanged(this, from, to);
      }
   }
}
