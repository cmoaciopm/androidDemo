/*********************************************************
 * Copyright (C) 2010-2012 VMware, Inc. All rights reserved.
 *
 * This file is part of VMware View Open Client.
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation version 2.1 and no later version.
 *
 * This program is released with an additional exemption that
 * compiling, linking, and/or using the OpenSSL libraries with this
 * program is allowed.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA.
 *
 *********************************************************/
package net.cmoaciopm.demo.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.HorizontalScrollView;

public class SideBarScrollView extends HorizontalScrollView implements OnGestureListener, OnGlobalLayoutListener
{
   private SizeCallback mSizeCallback;
   private int mScrollToViewIdx;
   private int mScrollToPos;
   private View[] mChildren;
   private GestureDetector mGestureDetector = new GestureDetector(this.getContext(), this);
   private boolean hasMeasured = false;
   
   public SideBarScrollView(Context context)
   {
      super(context);
      init(context);
   }
   
   public SideBarScrollView(Context context, AttributeSet attrs)
   {
      super(context, attrs);
      init(context);
   }
   
   public SideBarScrollView(Context context, AttributeSet attrs, int defStyle)
   {
      super(context, attrs, defStyle);
      init(context);
   }

   /*
    * 
    *-----------------------------------------------------------------------------
    * init --
    *
    *      Init this ScrollView
    *
    * Results:
    *      None
    *
    * Side effects:
    *      Fading edge will be disabled
    *
    *-----------------------------------------------------------------------------
    */
   private void
   init(Context context)
   {
      setHorizontalFadingEdgeEnabled(false);
      setVerticalFadingEdgeEnabled(false);
   }
   
   /*
    * 
    *-----------------------------------------------------------------------------
    * initViews --
    *
    *      Init child views of this ScrollView.
    *      param children 
    *            Child views of this ScrollView
    *      param scrollToViewIdx
    *            The default visible view index when this view init
    *      param sizeCallback
    *            A callback which is used for specifying child view's dimension
    * Results:
    *      None
    *
    * Side effects:
    *      None
    *
    *-----------------------------------------------------------------------------
    */
   public void
   initViews(View[] children,
             int scrollToViewIdx,
             SizeCallback sizeCallback)
   {
      this.mChildren = children;
      this.mScrollToViewIdx = scrollToViewIdx;
      this.mSizeCallback = sizeCallback;
      
      this.getViewTreeObserver().addOnGlobalLayoutListener(this);
   }
   
   public void
   setScrollToPos(int scrollToPos)
   {
      this.mScrollToPos = scrollToPos;
   }

   public void
   scrollToRight()
   {
      this.smoothScrollTo(0, 0);
   }
   
   public void
   scrollToLeft() {
      this.smoothScrollTo(mScrollToPos, 0);
   }
   
   public boolean isExpand() {
      return getScrollX()==0;
   }
   
   @Override
   public boolean
   onInterceptTouchEvent(MotionEvent event)
   {
      if(isExpand()
            && event.getRawX()>mScrollToPos) {
         //If is expaned, do not respond touch event which x is greater than mScrollToPos
         return true;
      }
      
      if(!isExpand()
            && event.getAction()==MotionEvent.ACTION_DOWN
            && event.getRawX()<this.getContext().getResources().getDisplayMetrics().widthPixels*2/10) {
         //TODO If is not expanded, respond touch event only when x is smaller than some config value
         return true;
      }
      
      return false;
   }
   
   @Override
   public boolean
   onTouchEvent(MotionEvent event)
   {
      if(!isExpand()
            && event.getAction()==MotionEvent.ACTION_DOWN
            && event.getRawX()>this.getContext().getResources().getDisplayMetrics().widthPixels*2/10) {
         //TODO
         return false;
      }
      
      boolean result = mGestureDetector.onTouchEvent(event);
      
      if(event.getAction()==MotionEvent.ACTION_UP) {
         if(Math.abs(getScrollX()) < mScrollToPos/2) {
            scrollToRight();
         } else {
            scrollToLeft();
         }
         return true;
      }
      
      return result;
   }
   
   @Override
   public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
      int width = getMeasuredWidth();
      int height = getMeasuredHeight();
      synchronized(this) {
         if(!hasMeasured && width!=0 && height!=0) {
            Log.d("agong", "onLayout");
            hasMeasured = true;
            ((ViewGroup)this.getChildAt(0)).removeAllViews();
            
            final int w = getMeasuredWidth();
            final int h = getMeasuredHeight();
            for (int i = 0; i < mChildren.length; i++) {
               int[] dim = mSizeCallback.getViewSize(i, w, h);
               mChildren[i].setVisibility(View.VISIBLE);
               ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(dim[0], dim[1]);
               ((ViewGroup)this.getChildAt(0)).addView(mChildren[i], params);
               if (i < mScrollToViewIdx) {
                  mScrollToPos += dim[0];
               }
            }
            
         }
      }
      
   }
   
   public interface
   SizeCallback
   {
      /*
       * 
       *-----------------------------------------------------------------------------
       * getViewSize --
       *
       *      Used by clients to specify the View dimensions. 
       *      param idx
       *            Index of the View
       *      param w
       *            Width of the parent view
       *      param h
       *            Height of the parent view
       *            
       * Results:
       *      dims[0] should be set to View width. dims[1] should be set to View height.
       *
       * Side effects:
       *      None
       *
       *-----------------------------------------------------------------------------
       */
      public int[] getViewSize(int idx, int w, int h);
   }

   @Override
   public boolean onDown(MotionEvent e)
   {
      Log.d("agong", "onDown");
      return true;
   }

   @Override
   public void onShowPress(MotionEvent e)
   {
      Log.d("agong", "onShowPress");
   }

   @Override
   public boolean onSingleTapUp(MotionEvent e)
   {
      Log.d("agong", "onSingleTapUp");
      return false;
   }

   @Override
   public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
         float distanceY)
   {
      Log.d("agong", "onScroll");
      scrollBy((int)distanceX, 0);
      return true;
   }

   @Override
   public void onLongPress(MotionEvent e)
   {
      Log.d("agong", "onLongPress");
   }

   @Override
   public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
         float velocityY)
   {
      Log.d("agong", "onFling");
      return false;
   }
   
   
   @Override
   public void onGlobalLayout()
   {
      /*
      if(getMeasuredWidth()!=0 && getMeasuredHeight()!=0) {
         this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
         
         new Handler().post(new Runnable() {
            @Override
            public void
            run()
            {
               scrollTo(mScrollToPos, 0);
            }
         });
      }*/
   }
}
