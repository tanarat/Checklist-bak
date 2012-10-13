package org.silk.checklist.activity;

import java.util.List;

import org.silk.checklist.R;
import org.silk.checklist.model.Answersheet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class AttendanceList extends Activity {
	public String tag = this.getClass().getName();
	private EditText etInput;
	private Button btnAdd;
	private ListView lvItem;
	List<String> attendanceList;
	ArrayAdapter<String> attendanceAdapter;

	Answersheet answersheet = null;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.attendance_list);
		Intent intent = this.getIntent();
		
		answersheet = (Answersheet)intent.getSerializableExtra("answersheet");
		attendanceList = answersheet.getAttendances();
		
		setUpView();

	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		setUpView();
		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		attendanceManager.save(answerSheet, attendances);
	}

	private void setUpView() {
		// TODO Auto-generated method stub
		etInput = (EditText) this.findViewById(R.id.editText_input);
		btnAdd = (Button) this.findViewById(R.id.button_add);
		lvItem = (ListView) this.findViewById(R.id.listView_items);
		

		attendanceAdapter = new ArrayAdapter<String>(	getApplicationContext(), 
															android.R.layout.simple_list_item_1, 
															attendanceList);
		lvItem.setAdapter(attendanceAdapter);


		btnAdd.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				addItemList();
			}
		});

		etInput.setOnKeyListener(new View.OnKeyListener() {

			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub

				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					addItemList();
				}
				return true;
			}
		});
		
		lvItem.setOnItemLongClickListener(itemLongClickHandler);

	}
//	private Resources res;
//	private IconContextMenu iconContextMenu = null;
//	private final int CONTEXT_MENU_ID = 1;
//	private final int MENU_DELETE = 14;
	private OnItemLongClickListener itemLongClickHandler = new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			deleteItemList(position);
			return true;
		}
	};
//	private void initContextMenu() {
//		// init the menu
//		iconContextMenu = new IconContextMenu(this, CONTEXT_MENU_ID);
//		iconContextMenu.addItem(res, R.string.menu_delete, R.drawable.icon_photos_tab,
//				MENU_DELETE);
//	}
	protected void deleteItemList(int position){
		attendanceList.remove(position);
		attendanceAdapter.notifyDataSetChanged();
	}
	protected void addItemList() {

//		etInput.setText("hello");
		if (isInputValid(etInput)) {
			int index = attendanceList.size();
			attendanceList.add(index,etInput.getText().toString());
			etInput.setText("");
			attendanceAdapter.notifyDataSetChanged();
		}

	}

	protected boolean isInputValid(EditText etInput2) {
		// TODO Auto-generatd method stub
		if (etInput2.getText().toString().trim().length() < 1) {
			etInput2.setError("Please Enter Item");
			return false;
		} else {
			return true;
		}

	}
//	public void onButtonClick(View view){
//		
//		Intent returnIntent = new Intent();
//		returnIntent.putExtra("attendances", itemArrey);
//		setResult(RESULT_OK, returnIntent);
//		finish();
//	}
}
