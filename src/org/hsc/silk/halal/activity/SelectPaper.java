package org.hsc.silk.halal.activity;

import java.util.List;


import org.hsc.silk.App;
import org.hsc.silk.halal.R;
import org.hsc.silk.halq.bo.PaperBo;
import org.hsc.silk.adapter.PaperAdapter;
import org.hsc.silk.model.Paper;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

public class SelectPaper extends Activity{
	App app;
	public String tag = this.getClass().getName();

	private PaperBo paperBo;
	private Paper selectedPaper = null;
	private int selectedPaperId = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		app = (App)getApplication();
		setContentView(R.layout.select_paper);

		paperBo = new PaperBo(app.getDatabase());
		
		
		
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		List<Paper> paperList = paperBo.list();

		Intent intent = getIntent();
		
		selectedPaperId = intent.getIntExtra("selectedPaperId", 0);
		

		PaperAdapter adapter = new PaperAdapter(this, paperList , selectedPaperId);
		
		ListView lv = (ListView)findViewById(R.id.itemList);
		lv.setAdapter(adapter);
		lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		int position = adapter.getPositionById(selectedPaperId);
		if(position != -1){
			lv.setItemChecked(position, true);
		}
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				selectedPaper = (Paper)adapter.getItemAtPosition(position);
				selectedPaperId = (int) adapter.getItemIdAtPosition(position);
			}
		});
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}

	public void onButtonClick(View view){
//		if(selectedPaper == null){
//			Toast.makeText(this, "กรุณาเลือกแบบประเมิน" , Toast.LENGTH_LONG).show();
//			return;
//		}
		Intent returnIntent = new Intent();
		returnIntent.putExtra("selectedPaperId", selectedPaperId);
		returnIntent.putExtra("selectedPaper", selectedPaper);
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
