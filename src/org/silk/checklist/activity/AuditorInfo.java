package org.silk.checklist.activity;

import java.util.ArrayList;
import java.util.List;

import org.silk.checklist.App;
import org.silk.checklist.R;
import org.silk.checklist.bo.AuditorBo;
import org.silk.checklist.model.Answersheet;
import org.silk.checklist.model.Auditor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class AuditorInfo extends Activity {
	App app;
	public String tag = this.getClass().getName();

	private Answersheet answersheet;

	private List<Auditor> auditorList;
	private List<Integer> selectedAuditorIds; // = new HashSet<Integer>();
	ListView listview;
	AuditorBo auditorBo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		app = (App)getApplication();
		setContentView(R.layout.auditor_info);
		listview = (ListView) findViewById(R.id.auditorList);


		auditorBo = new AuditorBo(app.getDatabase());
		Intent intent = getIntent();
		answersheet = (Answersheet) intent.getSerializableExtra("answersheet");
		selectedAuditorIds = answersheet.getAuditorIds();

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
		

		auditorList = auditorBo.list(selectedAuditorIds);

		ArrayAdapter<Auditor> adapter = new ArrayAdapter<Auditor>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				auditorList);
		// auditorAdapter = new ItemListAdapter(auditorList,this);
		listview.setAdapter(adapter);

	}

	public static final int AUDITORS_SELECT = 22;

	public void btnSelectAuditor(View view) {
		Intent intent = new Intent();
		intent.setClass(AuditorInfo.this, SelectAuditors.class);
		intent.putIntegerArrayListExtra("selectedAuditorIds",
				new ArrayList<Integer>(selectedAuditorIds));
		startActivityForResult(intent, AUDITORS_SELECT);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case AUDITORS_SELECT:
			if (resultCode == RESULT_OK) {
				selectedAuditorIds = data
						.getIntegerArrayListExtra("selectedAuditorIds");
				Toast.makeText(this,
						"You have chosen the auditors: " + selectedAuditorIds,
						Toast.LENGTH_SHORT).show();
				answersheet.setAuditorIds(selectedAuditorIds);
				break;
			}
		default:
			break;
		}

	}
	//
	// @Override
	// protected void onListItemClick(ListView l, View v, int position, long id)
	// {
	// // TODO Auto-generated method stub
	// super.onListItemClick(l, v, position, id);
	// // System.out.println("position : " + position + ", id : " + id);
	// CheckedTextView ch = (CheckedTextView)v;
	// // CheckBox ch = (CheckBox)v;
	// if(!ch.isChecked()){
	// auditorIdSet.add((int)id);
	// }else{
	// auditorIdSet.remove((int)id);
	// }
	// System.out.println(auditorIdSet);
	// }

}
