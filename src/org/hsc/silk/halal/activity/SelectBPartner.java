package org.hsc.silk.halal.activity;

import java.util.List;


import org.hsc.silk.App;
import org.hsc.silk.halal.R;
import org.hsc.silk.halq.bo.BPartnerBo;
import org.hsc.silk.adapter.BPartnerAdapter;
import org.hsc.silk.model.BPartner;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

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
