package org.hsc.silk.halal.activity;

import java.util.List;

import org.hsc.silk.halal.R;
import org.hsc.silk.model.ListItem;




import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemListAdapter extends BaseAdapter{
	private List<ListItem> itemList;
	private Activity activity;
	private static LayoutInflater inflater = null;
	public ItemListAdapter(List<ListItem> itemList,Activity activity) {
		super();
		this.itemList = itemList;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itemList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return itemList.get(position).getItemId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
//		TextView tv = (TextView)inflater.inflate(android.R.layout.simple_list_item_1,null);
//		tv.setText(itemList.get(position).toString());
//		return tv;
		

		View view=convertView;
        if(convertView==null)
            view = inflater.inflate(R.layout.list_row, null);
        
        TextView tvItemName = (TextView)view.findViewById(R.id.itemName);
        TextView tvItemDesc = (TextView)view.findViewById(R.id.itemDescription);
        TextView tvItemDesc2 = (TextView)view.findViewById(R.id.itemDescription2);
        ListItem item = itemList.get(position);
        
        
        tvItemName.setText(item.getItemName());
        if(item.getItemDescription() != null)
        	tvItemDesc.setText(item.getItemDescription());
        if(item.getItemDescription2() != null)
        	tvItemDesc2.setText(item.getItemDescription2());
        
        
		return view;
	}

}
