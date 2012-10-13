package org.hsc.silk.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hsc.silk.model.Paper;



import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

public class PaperAdapter extends BaseAdapter {
	
	private List<Paper> paperList = new ArrayList<Paper>();
	private static LayoutInflater inflater = null;
	private int selectedItemId = -1;

	// private Activity activity;

	public PaperAdapter(Activity activity, List<Paper> paperList) {
		super();
		this.paperList = paperList;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	public PaperAdapter(Activity activity, List<Paper> paperList, int selectedItemId) {
		super();
		this.paperList = paperList;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.selectedItemId = selectedItemId;
		System.out.println("selectedItemId = " + selectedItemId);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return paperList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub

		return paperList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		
		return paperList.get(position).getPaperId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
//		if (convertView == null)
//			view = inflater.inflate(R.layout.question_list_row, null);
//		CheckedTextView ch = (CheckedTextView)inflater.inflate(android.R.layout.simple_list_item_multiple_choice,null);
		CheckedTextView ch = (CheckedTextView)inflater.inflate(android.R.layout.simple_list_item_single_choice,null);
		ch.setText(paperList.get(position).getName());
		if(getItemId(position) == selectedItemId){
			ch.setChecked(true);
		}
		return ch;
	}
	
	public int getPositionById(long itemId){
		for (int i = 0; i < paperList.size(); i++) {
			if(getItemId(i) == itemId)
				return i;
		}
		return -1;
	}
}
