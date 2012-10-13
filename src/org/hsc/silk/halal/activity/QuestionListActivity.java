package org.hsc.silk.halal.activity;

import java.util.ArrayList;
import java.util.List;


import org.hsc.silk.App;
import org.hsc.silk.halq.bo.PaperBo;
import org.hsc.silk.halq.bo.QuestionBo;
import org.hsc.silk.model.ListItem;
import org.hsc.silk.model.Paper;



import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class QuestionListActivity extends ListActivity{
	App app;
	private List<ListItem> itemList;
	private ItemListAdapter adapter;

	private QuestionBo questionBo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		app = (App)getApplication();

		questionBo = new QuestionBo(app.getDatabase());
		
		/*
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			}
		});
		*/
	}
	
	

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, QuestionDetailActivity.class);
		intent.putExtra("questionId", (int)id);
		startActivity(intent);
	}



	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
		Intent intent = getIntent();
		int paperId = intent.getIntExtra("paperId", 0);
		itemList = new ArrayList<ListItem>(questionBo.listByPaperId(paperId));
		
		if(itemList == null){
			message("No item list found or item list length is 0");
			return;
		}
		adapter = new ItemListAdapter(itemList,this);
		this.setListAdapter(adapter);
	}



	private void message(String msg) {
		Toast toast;
		toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
}
