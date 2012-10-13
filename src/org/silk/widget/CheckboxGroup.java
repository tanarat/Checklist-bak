//package com.va.feedback.widget;
package org.silk.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import com.va.feedback.FeedbackDroidActivity;

/**
 * The CheckboxGroup is a custom component to create multiple textboxes with checkboxes.
 * This is not a very configurable option, but it uses less space than the normal ListView
 * approach.
 * <p>
 * This class may be deprecated in the future, and ListView approach may be taken
 * </p>
 * @author varun
 *
 */
public class CheckboxGroup extends RelativeLayout {

        private final int CHECKBOX_TICK_ID = 128;
        private final int CHECKBOX_TEXT_ID = 129;
//        private int groupId;
        private Context context;
        int currentCheckbox = 1;

        /**
         * Constructor for CheckboxGroup
         * @param context
         */
        public CheckboxGroup(Context context) {
                super(context);
                this.context = context;
        }
        
        /**
         * Adds a checkbox item to the group
         * @param text The item string
         */
        public void addCheckbox(int id, String text, boolean checked) {
                RelativeLayout checkbox = new RelativeLayout(context);
                RelativeLayout.LayoutParams checkboxLayout = new RelativeLayout.LayoutParams(
                                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                checkbox.setId(currentCheckbox);
                
                CheckBox cb = new CheckBox(context);
                RelativeLayout.LayoutParams cbLayout = new RelativeLayout.LayoutParams(
                                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                cb.setId(CHECKBOX_TICK_ID);
                cb.setChecked(checked);
                checkbox.addView(cb, cbLayout);
                
                TextView tv = new TextView(context);
                tv.setText(text);
                tv.setTextSize(20);
                RelativeLayout.LayoutParams tvLayout = new RelativeLayout.LayoutParams(
                                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//                tv.setId(CHECKBOX_TEXT_ID);
                tv.setId(id);
                tvLayout.addRule(RelativeLayout.ALIGN_BOTTOM, CHECKBOX_TICK_ID);
                tvLayout.addRule(RelativeLayout.RIGHT_OF, CHECKBOX_TICK_ID);
//                int margin = (int) (FeedbackDroidActivity.getScale() * 10 + 0.5);
                int margin = 10;
                tvLayout.bottomMargin = margin;
                checkbox.addView(tv, tvLayout);
                
                if (currentCheckbox > 1) {
                        checkboxLayout.addRule(RelativeLayout.BELOW, currentCheckbox-1);
                }
                this.addView(checkbox, checkboxLayout);
                currentCheckbox++;
        }
        
        /**
         * @return Returns the checked items from the group
         */
        public List<String> getCheckedItems() {
                List<String> checkedItems = new ArrayList<String>();
                int numChildren = this.getChildCount();
                RelativeLayout child;
                CheckBox childCheckBox;
                for (int i=0; i<numChildren; i++) {
                        child = (RelativeLayout) this.getChildAt(i);
                        childCheckBox = getCheckboxFromItem(child);
                        if (childCheckBox == null)
                                continue;
                        if (childCheckBox.isChecked())
                                checkedItems.add(getTextFromItem(child));
                }
                return checkedItems;
        }
        public List<Integer> getCheckedItemIds(){
        	List<Integer> checkedItemIds = new ArrayList<Integer>();
        	int numChildren = this.getChildCount();
            RelativeLayout child;
            CheckBox childCheckBox;
            for (int i=0; i<numChildren; i++) {
                    child = (RelativeLayout) this.getChildAt(i);
                    childCheckBox = getCheckboxFromItem(child);
                    if (childCheckBox == null)
                            continue;
                    if (childCheckBox.isChecked())
//                            checkedItems.add(getTextFromItem(child));
                    	checkedItemIds.add(getIdFromItem(child));
            }
        	return checkedItemIds;
        }
        private int getIdFromItem(RelativeLayout child) {
            int count = child.getChildCount();
            for (int i=0; i<count; i++) {
                    if (child.getChildAt(i) instanceof TextView && !(child.getChildAt(i) instanceof CheckBox))
//            	if (!(child.getChildAt(i) instanceof TextView) && (child.getChildAt(i) instanceof CheckBox))
//                            return ((TextView) child.getChildAt(i)).getText().toString();
                    	return child.getChildAt(i).getId();
            }
            return -1;
    }
        private String getTextFromItem(RelativeLayout child) {
                int count = child.getChildCount();
                for (int i=0; i<count; i++) {
                        if (child.getChildAt(i) instanceof TextView && !(child.getChildAt(i) instanceof CheckBox))
                                return ((TextView) child.getChildAt(i)).getText().toString();
                }
                return null;
        }

        private CheckBox getCheckboxFromItem(RelativeLayout child) {
                int count = child.getChildCount();
                for (int i=0; i<count; i++) {
                        if (child.getChildAt(i) instanceof CheckBox)
                                return (CheckBox) child.getChildAt(i);
                }
                return null;
        }
        public void setEnableAllCheckBox(boolean enabled){
        	int numChildren = this.getChildCount();
            RelativeLayout child;
            CheckBox childCheckBox;
            for (int i=0; i<numChildren; i++) {
                    child = (RelativeLayout) this.getChildAt(i);
                    childCheckBox = getCheckboxFromItem(child);
                    if (childCheckBox == null)
                            continue;
                    childCheckBox.setEnabled(enabled);
                    System.out.println(enabled + " " + child.getId());
                    childCheckBox.setClickable(enabled);
            }
        	
        }
//        public void setSelectedItem(){
//        	int numChildren = this.getChildCount();
//            RelativeLayout child;
//            CheckBox childCheckBox;
//            for (int i=0; i<numChildren; i++) {
//                    child = (RelativeLayout) this.getChildAt(i);
//                    childCheckBox = getCheckboxFromItem(child);
//                    if (childCheckBox == null)
//                            continue;
//                    
//            }
//        }

//		public int getGroupId() {
//			return groupId;
//		}
//
//		public void setGroupId(int groupId) {
//			this.groupId = groupId;
//		}
		
}
