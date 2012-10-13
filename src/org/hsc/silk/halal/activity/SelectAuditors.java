package org.hsc.silk.halal.activity;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


import org.hsc.silk.App;
import org.hsc.silk.halal.R;
import org.hsc.silk.halq.bo.AuditorBo;
import org.hsc.silk.adapter.AuditorAdapter;
import org.hsc.silk.model.Auditor;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SelectAuditors extends Activity {
	App app;
	public String tag = this.getClass().getName();
	
	private AuditorBo auditorBo;
	private ArrayList<Integer> selectedAuditorIds;
	private Set<Integer> set = new LinkedHashSet<Integer>();

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		

		List<Auditor> auditorList = auditorBo.list();
		AuditorAdapter adapter = new AuditorAdapter(this, auditorList);
		ListView lv = (ListView) findViewById(R.id.itemList);
		lv.setAdapter(adapter);

		Intent intent = getIntent();
		selectedAuditorIds = intent
				.getIntegerArrayListExtra("selectedAuditorIds");
		if (selectedAuditorIds == null) {
			selectedAuditorIds = new ArrayList<Integer>();
		}
		set = new LinkedHashSet<Integer>(selectedAuditorIds);
		for (Integer auditorId : selectedAuditorIds) {
			int position = adapter.getPositionById(auditorId);
			if (position != -1) {
				lv.setItemChecked(position, true);
			}
		}

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) { // TODO Auto-generated method stub
				CheckedTextView ch = (CheckedTextView)view;
				
				if(ch.isChecked()){
					set.remove((int) id);
					System.out.println("remove : " + id);
				}else{
					set.add((int) id);
					System.out.println("add : " + id);
				}
			}
		});

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		app = (App)getApplication();
		setContentView(R.layout.select_auditors);
		
		auditorBo = new AuditorBo(app.getDatabase());
		
	}

	public void onButtonClick(View view) {
		Intent returnIntent = new Intent();
		
		returnIntent.putIntegerArrayListExtra("selectedAuditorIds",new ArrayList<Integer>(set));
		setResult(RESULT_OK, returnIntent);
		finish();
	}
}
