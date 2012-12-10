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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.HorizontalScrollView;

public class SideBarScrollView extends HorizontalScrollView
{
   private SizeCallback mSizeCallback;
   private int mScrollToViewIdx;
   private int mScrollToPos;
   private View[] mChildren;
   
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
      
      MyOnGlobalLayoutListener listener = 
            new MyOnGlobalLayoutListener(children,
                                         scrollToViewIdx,
                                         sizeCallback);
      this.getViewTreeObserver().addOnGlobalLayoutListener(listener);
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
   
   @Override
   public boolean
   onInterceptTouchEvent(MotionEvent ev)
   {
      //Do not intercept touch event
      return false;
   }
   
   @Override
   public boolean
   onTouchEvent(MotionEvent ev)
   {
      //Do not handle touch event here
      return false;
   }
   
   class MyOnGlobalLayoutListener
   implements OnGlobalLayoutListener
   {
      private View[] children;
      private int scrollToViewIdx;
      private int scrollToViewPos;
      private SizeCallback sizeCallback;
      
      public MyOnGlobalLayoutListener(View[] children,
                                      int scrollToViewIdx,
                                      SizeCallback sizeCallback)
      {
         this.children = children;
         this.scrollToViewIdx = scrollToViewIdx;
         this.sizeCallback = sizeCallback;
      }
      
      @Override
      public void
      onGlobalLayout()
      {
         final SideBarScrollView view = SideBarScrollView.this;
         view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
         
         ((ViewGroup)view.getChildAt(0)).removeAllViews();
         
         final int w = view.getMeasuredWidth();
         final int h = view.getMeasuredHeight();
         for (int i = 0; i < children.length; i++) {
            int[] dim = sizeCallback.getViewSize(i, w, h);
            children[i].setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(dim[0], dim[1]);
            ((ViewGroup)view.getChildAt(0)).addView(children[i], params);
            if (i < scrollToViewIdx) {
               scrollToViewPos += dim[0];
            }
            
            // For some reason we need to post this action, rather than call
            // immediately.
            // If we try immediately, it will not scroll.
            new Handler().post(new Runnable() {
               @Override
               public void
               run()
               {
                  view.scrollTo(scrollToViewPos, 0);
               }
            });
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
}
