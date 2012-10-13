package org.hsc.silk.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hsc.silk.model.Auditor;


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

public class AuditorAdapter extends BaseAdapter {
	
	private List<Auditor> auditorList = new ArrayList<Auditor>();
	private static LayoutInflater inflater = null;
	

	// private Activity activity;

	public AuditorAdapter(Activity activity, List<Auditor> auditorList) {
		super();
		this.auditorList = auditorList;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return auditorList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub

		return auditorList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		
		return auditorList.get(position).getAuditorId();
	}
	
	public int getPositionById(long itemId){
		for (int i = 0; i < auditorList.size(); i++) {
			if(getItemId(i) == itemId)
				return i;
		}
		return -1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
//		if (convertView == null)
//			view = inflater.inflate(R.layout.question_list_row, null);
		CheckedTextView ch = (CheckedTextView)inflater.inflate(android.R.layout.simple_list_item_multiple_choice,null);
//		CheckBox ch = (CheckBox)inflater.inflate(android.R.layout.simple_list_item_multiple_choice,null);
		ch.setText(auditorList.get(position).getName());
		ch.setChecked(true);
		
		
		return ch;
	}

}
