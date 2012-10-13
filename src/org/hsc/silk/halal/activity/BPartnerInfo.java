package org.hsc.silk.halal.activity;

import java.util.List;


import org.hsc.silk.App;
import org.hsc.silk.halal.R;
import org.hsc.silk.halq.bo.BPartnerBo;
import org.hsc.silk.model.Answersheet;
import org.hsc.silk.model.BPartner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class BPartnerInfo extends Activity{
	App app;
//	int selectedBpId = 0;
	Answersheet answersheet = null;
	int selectedBPartnerId = 0;
	BPartner selectedBPartner = null;
	BPartnerBo bpbo;
	
	public static final int FORM_SELECT = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		app = (App)getApplication();
		setContentView(R.layout.bpartner_info);
		Intent intent = getIntent();
//		selectedBpId = intent.getIntExtra("selectedBpId", 0);
		answersheet = (Answersheet)intent.getSerializableExtra("answersheet");
		
//		selectedBPartner = (BPartner)intent.getSerializableExtra("selectedBPartner");
		selectedBPartner = answersheet.getBpartner();
		if(selectedBPartner != null){
			selectedBPartnerId = selectedBPartner.getBpartnerId();
		}
		
		
		bpbo = new BPartnerBo(app.getDatabase());
		
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		bpbo.open();
//		if(selectedBpId != 0){
//			selectedBPartner = bpbo.getBPartner(selectedBpId);
//		}
//		selectedForm = formAnswerSheetManager.getForm(answerSheet.getAnswerSheetId());
//		System.out.println("on resume "+ selectedForm.getFormId());
		if(selectedBPartner != null){
			TextView tvHalqForm = (TextView)findViewById(R.id.halqForm);
			tvHalqForm.setText(selectedBPartner.getName());
			
			TextView tvOfficeAddr = (TextView)findViewById(R.id.officeAddr);
			tvOfficeAddr.setText(selectedBPartner.getOffice());
			
			TextView tvFactoryAddr = (TextView)findViewById(R.id.factoryAddr);
			tvFactoryAddr.setText(selectedBPartner.getFactory());
			
			TextView tvNumOfEmp = (TextView)findViewById(R.id.numOfEmp);
			tvNumOfEmp.setText(String.valueOf(selectedBPartner.getNumOfEmployee()));
			
			
			TextView tvNumOfMuslim = (TextView)findViewById(R.id.numOfMuslim);
			tvNumOfMuslim.setText(String.valueOf(selectedBPartner.getNumOfMuslim()));
			
			TextView tvNumOfForeigner = (TextView)findViewById(R.id.numOfForeigner);
			tvNumOfForeigner.setText(String.valueOf(selectedBPartner.getNumOfForeigner()));
			
//			
//			List<RegisterProduct> registerProducts = selectedForm.getRegisterProducts();
//			ArrayAdapter<RegisterProduct> adapter = new ArrayAdapter<RegisterProduct>(this.getApplicationContext(), 
//					android.R.layout.simple_list_item_1, 
//					registerProducts);
//			ListView lvProduct = (ListView)findViewById(R.id.productList);
//			lvProduct.setAdapter(adapter);
//			
//			List<RegisterStandard> registerStandards = selectedForm.getRegisterStandards();
//			ArrayAdapter<RegisterStandard> adapter2 = new ArrayAdapter<RegisterStandard>(this.getApplicationContext(), 
//					android.R.layout.simple_list_item_1, 
//					registerStandards);
//			ListView lvStandard = (ListView)findViewById(R.id.standardList);
//			lvStandard.setAdapter(adapter2);
			
		}
	}


	public void btnSelectHalqForm(View view){
		Intent halqFormIntent = new Intent();
		halqFormIntent.setClass(BPartnerInfo.this,SelectBPartner.class);
		halqFormIntent.putExtra("selectedBPartnerId", selectedBPartnerId);
		startActivityForResult(halqFormIntent,FORM_SELECT);
	}
	public void onButtonClick(View view){
//		if(bpartner == null){
//			Toast.makeText(this, "กรุณา" , Toast.LENGTH_LONG).show();
//			return;
//		}
		Intent returnIntent = new Intent();
		returnIntent.putExtra("selectedBPartner", selectedBPartner);
		setResult(RESULT_OK, returnIntent);
		finish();
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
    	switch(requestCode) {
    	case FORM_SELECT: 
            if (resultCode == RESULT_OK) {
            	selectedBPartner = (BPartner)data.getSerializableExtra("selectedBPartner");
            	selectedBPartnerId = data.getIntExtra("selectedBPartnerId", 0);
            	answersheet.setBpartner(selectedBPartner);
//            	System.out.println("*********" + selectedBPartnerId);
//            	formAnswerSheetManager.saveFormAnswerSheet(selectedForm.getFormId(), answerSheet.getAnswerSheetId());
//                answerSheet.setPaper(form);
//                Toast.makeText(this, "You have chosen the Halq Form of : " + " " + selectedForm.getFormId() + " " + selectedForm.getCompanyName(), Toast.LENGTH_SHORT).show();
                break;
            }
    	}
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		bpbo.close();
//		if(selectedForm != null){
//			formAnswerSheetManager.saveFormAnswerSheet(selectedForm.getFormId(), answerSheet.getAnswerSheetId());
//			System.out.println("on pause, formId=" + selectedForm.getFormId()  );
//		}
	}
	
}
