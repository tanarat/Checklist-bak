package org.silk.checklist.activity;

import java.util.ArrayList;
import java.util.List;


import org.silk.checklist.App;
import org.silk.checklist.bo.PaperBo;
import org.silk.checklist.model.ListItem;



import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class PaperListActivity extends ListActivity{
	App app;
	private List<ListItem> itemList;
	private ItemListAdapter adapter;

	private PaperBo paperBo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		app = (App)getApplication();
		paperBo = new PaperBo(app.getDatabase());
		

		
		
		/*
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				Intent itemIntent = new Intent(this, QuestionListActivity.class);
				itemIntent.putExtra("paperId", id);
			}
		});
		*/
	}
	
	

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Intent itemIntent = new Intent(this, QuestionListActivity.class);
		itemIntent.putExtra("paperId", (int)id);
		startActivity(itemIntent);
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		itemList = new ArrayList<ListItem>(paperBo.list());
		
		if(itemList == null){
			message("No item list found or item list length is 0");
			return;
		}
		adapter = new ItemListAdapter(itemList,this);
		this.setListAdapter(adapter);
	}



	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}



	private void message(String msg) {
		Toast toast;
		toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
}
