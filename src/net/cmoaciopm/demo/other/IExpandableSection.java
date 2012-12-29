/* ****************************************************************************
 * Copyright (C) 2012 VMware, Inc.  All rights reserved. -- VMware Confidential
 * ****************************************************************************/

package net.cmoaciopm.demo.other;

import java.util.List;

/**
 * Used for expandable list view.
 */
public interface IExpandableSection
{
   /* Should return all section data here */
   public List getData();

   /* If section data count is greater than this return value, then section will be expandable */
   public int getExpandCount();
   
   // TODO
   public Object getHeaderData();
   
   public void onExpand();
   
   public void onUnexpand();
}
