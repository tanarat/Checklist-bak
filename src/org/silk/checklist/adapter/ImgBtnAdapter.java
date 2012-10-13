package org.silk.checklist.adapter;

import org.silk.checklist.R;
import org.silk.checklist.activity.MenuButton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImgBtnAdapter extends BaseAdapter {
	private Context context;
//	private final String[] values;
	private final MenuButton[] values;
//	public ImgBtnAdapter(Context context, String[] values){
//		this.context = context;
//		this.values = values;
//	}
	public ImgBtnAdapter(Context context, MenuButton[] values){
		this.context = context;
		this.values = values;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return values.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return values[position].getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 
			View gridView;
	 
			if (convertView == null) {
	 
				gridView = new View(context);
				
				gridView = inflater.inflate(R.layout.img_btn, null);
	 
				// set value into textview
				TextView textView = (TextView) gridView
						.findViewById(R.id.grid_item_label);
				textView.setText(values[position].getName());
	 
				// set image based on selected text
				ImageView imageView = (ImageView) gridView
						.findViewById(R.id.grid_item_image);
	

				imageView.setImageResource(values[position].getImgResource());

	 
			} else {
				gridView = (View) convertView;
			}
	 
			return gridView;
	}

}
