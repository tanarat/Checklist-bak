package org.silk.checklist.activity;

import java.util.List;

import org.silk.checklist.App;
import org.silk.checklist.R;
import org.silk.checklist.adapter.BPartnerAdapter;
import org.silk.checklist.bo.BPartnerBo;
import org.silk.checklist.model.BPartner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SelectBPartner extends Activity{
	App app;
	public String tag = this.getClass().getName();

	private BPartnerBo bpbo;

	private List<BPartner> bpList;
	
	private int selectedBPartnerId = 0;
	private BPartner selectedBPartner = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		app = (App)getApplication();
		setContentView(R.layout.select_paper);

		bpbo = new BPartnerBo(app.getDatabase());
		

		Intent intent = getIntent();

		selectedBPartnerId = intent.getIntExtra("selectedBPartnerId", selectedBPartnerId);
		
		
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		bpList = bpbo.list();
		
		

		BPartnerAdapter adapter = new BPartnerAdapter(this, bpList , selectedBPartnerId);
		
		ListView lv = (ListView)findViewById(R.id.itemList);
		lv.setAdapter(adapter);
		lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		int position = adapter.getPositionById(selectedBPartnerId);
		if(position != -1){
			lv.setItemChecked(position, true);
		}
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				selectedBPartner = (BPartner)adapter.getItemAtPosition(position);
				selectedBPartnerId = (int)adapter.getItemIdAtPosition(position);
				
			}
		});
		
		
	}

	public void onButtonClick(View view){
		
		System.out.println("============ " + selectedBPartnerId);
		Intent returnIntent = new Intent();
		returnIntent.putExtra("selectedBPartner", selectedBPartner);
		returnIntent.putExtra("selectedBPartnerId", selectedBPartnerId);
		setResult(RESULT_OK, returnIntent);
		finish();
	}

//	@Override
//	protected void onListItemClick(ListView l, View v, int position, long id) {
//		// TODO Auto-generated method stub
//		super.onListItemClick(l, v, position, id);
//		Paper selectedPaper = (Paper)this.getListAdapter().getItem(position);
//		Intent returnIntent = new Intent();
//		returnIntent.putExtra("selectedPaper", selectedPaper);
//		setResult(RESULT_OK, returnIntent);
//		finish();
//	}
	
	
}
