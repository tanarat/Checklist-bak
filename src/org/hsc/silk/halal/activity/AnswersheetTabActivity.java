package org.hsc.silk.halal.activity;


import org.hsc.silk.App;
import org.hsc.silk.halal.R;
import org.hsc.silk.halq.bo.AnswersheetBo;
import org.hsc.silk.model.Answersheet;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;

public class AnswersheetTabActivity extends TabActivity{
	App app;
	Resources res;
	TabHost tabHost = null;
	int answersheetId = 0;
	Answersheet answersheet = null;
	AnswersheetBo answersheetBo;
	
	private static final int NEW = 0;
	private static final int VIEW = 1;
	private static int MODE = NEW;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setTitle(R.string.title_activity_answersheettab);
	    res = getResources();
	    app = (App)getApplication();
	    answersheetBo = new AnswersheetBo(app.getDatabase());
	    
	    setContentView(R.layout.tab);
	    
	    
	    Intent intent = getIntent();
		answersheetId = intent.getIntExtra("answersheetId", 0);
		if(answersheetId == 0){
			//create new AnswerSheet object
			answersheet = new Answersheet();
			MODE = NEW;
		}else{
			answersheet = answersheetBo.getAnswersheet(answersheetId);
			MODE = VIEW;
		}
	    
		createTab();
	    
	}
	 //Option menu
  	static final int MENU_ANSWERSHEET = 0;
  	
  	static final int CREATE_ANSWER_LIST = 10;
  	static final int VIEW_ANSWER_LIST = 11;
  	static final int SAVE = 12;
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		menu.add(MENU_ANSWERSHEET, SAVE, 0, R.string.lbl_menu_save);

		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){

		case SAVE:
			save();
			break;
		}
		return true;
	}
	private void save(){
		String msg = validate();
		if(!"OK".equals(validate())){
			message(msg);
			return;
		}
		
//		answersheet.setAnswersheetName("xx");
		if(answersheet.getAnswersheetName() == null || answersheet.getAnswersheetName().length() == 0){
			String name = "รายการตรวจประเมิน " + answersheet.getPaper().getName() + "  [ " +
					answersheet.getBpartner().getName() +" ]";
			answersheet.setAnswersheetName(name);
			
		}
		answersheetBo.save(answersheet);
		System.out.println("=== Save new Answersheet===");
		System.out.println("Paper : " + answersheet.getPaper().getName());
		System.out.println("BPartner : " + answersheet.getBpartner().getName());
		System.out.println("Auditor : " + answersheet.getAuditorIds());
		System.out.println("Attendance : " + answersheet.getAttendances());
		
		message("บันทึกรายการเรียบร้อย");
		finish();
		
		
	}
	private String validate(){
		String message = "OK";
		if(answersheet.getPaper() == null){
			message = "กรุณากำหนดแบบประเมิน";
			return message;
		}
		if(answersheet.getBpartner() == null){
			message = "กรุณากำหนดผู้ประกอบการ";
			return message;
		}
		return message;
	}
	private void createTab(){
		tabHost = getTabHost();
	    TabHost.TabSpec spec;
	    
	    Intent intent1 = new Intent();
	    intent1.setClass(this,new AnswersheetInfo().getClass());
	    intent1.putExtra("answersheet", answersheet);
	    intent1.putExtra("mode", MODE);
	    spec = tabHost.newTabSpec("AnswersheetInfo");
	    spec.setIndicator(res.getString(R.string.tab_name_answersheet));
	    spec.setContent(intent1);
	    tabHost.addTab(spec);
	    
	    Intent intent2 = new Intent();
	    intent2.setClass(this,new BPartnerInfo().getClass());
	    intent2.putExtra("answersheet", answersheet);
	    spec = tabHost.newTabSpec("BPartnerInfo");
	    spec.setIndicator(res.getString(R.string.tab_name_bpartner));
	    spec.setContent(intent2);
	    tabHost.addTab(spec);
	    
	    //Auditor
	    Intent intent3 = new Intent();
	    intent3.setClass(this,new AuditorInfo().getClass());
	    intent3.putExtra("answersheet", answersheet);
	    spec = tabHost.newTabSpec("AuditorInfo");
	    spec.setIndicator(res.getString(R.string.tab_name_auditor));
	    spec.setContent(intent3);
	    tabHost.addTab(spec);
	    
	    //Attendance
	    Intent intent4 = new Intent();
	    intent4.setClass(this,new AttendanceList().getClass());
	    intent4.putExtra("answersheet", answersheet);
	    spec = tabHost.newTabSpec("AttendanceInfo");
	    spec.setIndicator(res.getString(R.string.tab_name_attendance));
	    spec.setContent(intent4);
	    tabHost.addTab(spec);
	    
	    tabHost.setCurrentTab(0);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
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
