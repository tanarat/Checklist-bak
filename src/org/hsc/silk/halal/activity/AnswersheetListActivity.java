package org.hsc.silk.halal.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.hsc.silk.App;
import org.hsc.silk.halal.R;
import org.hsc.silk.halq.bo.AnswersheetBo;


import org.hsc.silk.model.Answersheet;
import org.hsc.silk.model.ListItem;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.tani.app.ui.IconContextMenu;

public class AnswersheetListActivity extends Activity {
	App app;
	private List<ListItem> itemList;
	private ItemListAdapter adapter;

	private ListView listView;
	private IconContextMenu iconContextMenu = null;
	private Resources res;
	
	private AnswersheetBo answersheetBo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		app = (App)getApplication();
		answersheetBo = new AnswersheetBo(app.getDatabase());
		res = getResources();
		// setContentView(R.layout.answersheet_list);
		initContextMenu();
		setContentView(R.layout.answersheet_list);
		setTitle(R.string.title_activity_answersheetlist);
		Intent intent = getIntent();

		listView = (ListView) findViewById(R.id.answersheetList);
		listView.setOnItemLongClickListener(itemLongClickHandler);

	}

	private final int CONTEXT_MENU_ID = 1;
	private final int MENU_SEND = 14;
	private final int MENU_VIEW = 15;
	/**
	 * list item long click handler used to show the context menu
	 */
	private OnItemLongClickListener itemLongClickHandler = new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			setSelectedPosition(position);
			showDialog(CONTEXT_MENU_ID);
			return true;
		}
	};
	
	private void initContextMenu() {
		iconContextMenu = new IconContextMenu(this, CONTEXT_MENU_ID);
		
		
		iconContextMenu.addItem(res, R.string.menu_view, R.drawable.ic_paper, MENU_VIEW);
		
		iconContextMenu.addItem(res, R.string.menu_send2server,
				R.drawable.ic_download, MENU_SEND);
		// set onclick listener for context menu
		iconContextMenu
				.setOnClickListener(new IconContextMenu.IconContextMenuOnClickListener() {
					@Override
					public void onClick(int menuId) {
						switch (menuId) {
						case MENU_SEND:
//							send2server();
							break;
							
						case MENU_VIEW:
							int answerSheetId = (int)adapter.getItemId(getSelectedPosition());
							viewAnswerSheet(answerSheetId);
							break;
						}
						
							
					}

				});
				
	}
	@Override
	protected Dialog onCreateDialog(int id) {
		final EditText inputName = new EditText(this);
		switch (id) {
		case CONTEXT_MENU_ID:
			
			return iconContextMenu.createMenu(res.getString(R.string.menu_title)); 
		
		default:
			return null;
		}
	}
/*
	public void send2server() {
		message("selected answer sheet id : " + adapter.getItemId(getSelectedPosition()));
		int answerSheetId = (int)adapter.getItemId(getSelectedPosition());
		
		try {
			app.uploadAnswerSheet(answerSheetId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			message("upload fail");
			e.printStackTrace();
		}

	}
*/
	private int selectedPosition = -1;

	public int getSelectedPosition() {
		return selectedPosition;
	}

	public void setSelectedPosition(int selectedPosition) {
		this.selectedPosition = selectedPosition;
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
		
		
		itemList = new ArrayList<ListItem>(answersheetBo.getAnswersheetList());
		

		if (itemList == null) {
			message("No item list found");
			return;
		}
		if (itemList.size() == 0) {
			message("No item found : item list size = 0");
		}
		adapter = new ItemListAdapter(itemList, this);
		
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				viewAnswerList((int) id);
			}
		});
		adapter.notifyDataSetChanged();
	}

	
	private void viewAnswerList(int answersheetId) {
		Intent intent = new Intent(AnswersheetListActivity.this, AnswerListActivity.class);
		intent.putExtra("answersheetId", answersheetId);
		startActivity(intent);
//		AnswerSheetListActivity.this.startActivity(myIntent);
	}

	private void message(String msg) {
		Toast toast;
		toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	// Option menu
	static final int MENU_ANSWERSHEET = 0;
	static final int NEW_ANSWERSHEET = 10;
	static final int SEARCH_ANSWERSHEET = 11;
	static final int FILTER_ANSWERSHEET = 12;

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		menu.add(MENU_ANSWERSHEET, NEW_ANSWERSHEET, 0,R.string.lbl_menu_new_answersheet);
//		menu.add(MENU_ANSWERSHEET, SEARCH_ANSWERSHEET, 0,R.string.lbl_menu_search_answersheet);
//		menu.add(MENU_ANSWERSHEET, FILTER_ANSWERSHEET, 0,R.string.lbl_menu_filter_answersheet);
		return true;
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case NEW_ANSWERSHEET:
			newAnswerSheet();
			break;
		}
		return true;
	}
	

	private void viewAnswerSheet(int answersheetId){
		Intent myIntent = new Intent(AnswersheetListActivity.this,
				AnswersheetTabActivity.class);
//		AnswerSheet answerSheet = app.getAnswerSheet(answerSheetId);
		myIntent.putExtra("answersheetId", answersheetId);
		AnswersheetListActivity.this.startActivity(myIntent);
	}
	
	private void newAnswerSheet() {
//		Intent myIntent = new Intent(AnswersheetListActivity.this, AnswersheetActivity.class);
		Intent myIntent = new Intent(AnswersheetListActivity.this, AnswersheetTabActivity.class);
		startActivity(myIntent);
	}
	
}
