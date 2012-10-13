package org.hsc.silk.halal.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


import org.hsc.silk.App;
import org.hsc.silk.halal.R;
import org.hsc.silk.halq.bo.PaperBo;
import org.hsc.silk.model.Answersheet;
import org.hsc.silk.model.BPartner;
import org.hsc.silk.model.Paper;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class AnswersheetInfo extends Activity implements OnFocusChangeListener{
	App app;
	private Answersheet answersheet;
	private int answersheetId = 0;
	private Paper paper;
	
	private int selectedPaperId = 0;
	private BPartner selectedBPartner;
	private int selectedBpId = 0;

	private static final int NEW = 0;
	private static final int VIEW = 1;
	private static int MODE = NEW;
	
	public static final int PAPER_SELECT = 21;
	public static final int AUDITORS_SELECT = 22;
	public static final int SET_DATE_TIME = 23;
	public static final int BP_SELECT = 24;
	public static final int SET_ATTENDANCE = 25;
	
	private PaperBo paperBo;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		app = (App)getApplication();
		paperBo = new PaperBo(app.getDatabase());
		
		setContentView(R.layout.answersheet);
		
		Intent intent = getIntent();
		answersheet = (Answersheet)intent.getSerializableExtra("answersheet");
		MODE = intent.getIntExtra("mode", NEW);
		EditText etAnswersheetName = (EditText)findViewById(R.id.tvAnswersheetName);
		etAnswersheetName.setOnFocusChangeListener(this);
//		answersheetId = intent.getIntExtra("answersheetId", 0);
//		if(answersheetId == 0){
//			//create new AnswerSheet object
//			answersheet = new Answersheet();
//			
//		}
		
	}
	ArrayList<CharSequence> attendanceNames = new ArrayList<CharSequence>();
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
		if(answersheet.getAnswersheetName() != null ){
			EditText etAnswersheetName = (EditText)findViewById(R.id.tvAnswersheetName);
			etAnswersheetName.setText(answersheet.getAnswersheetName());
		}
		
		if(answersheet.getPaper() == null){
			Paper paper = paperBo.getPaper(1); // defalt paper
			selectedPaperId = paper.getPaperId();
			answersheet.setPaper(paper);
		}
		
		
		if(answersheet.getPaper() != null){
			TextView tvPaperName = (TextView)findViewById(R.id.tvPaperName);
			tvPaperName.setText(answersheet.getPaper().getName());
			if(MODE == VIEW){
				ImageButton btnSelectPaper = (ImageButton)findViewById(R.id.btnSelectPaper);
				btnSelectPaper.setEnabled(false);
			}
		}
		
//		if(answersheet.getBpartner() != null){
//			TextView tvBp = (TextView)findViewById(R.id.txtBp);
//			tvBp.setText(answersheet.getBpartner().getName());
//		}
		/*
		
//		if(selectedAuditorIds != null){
		if(answerSheet.getAuditorIds() != null){
			List<Integer> auditorIds = answerSheet.getAuditorIds();
			selectedAuditorIds = new ArrayList<Integer>(auditorIds);
			TextView tvAuditor = (TextView)findViewById(R.id.auditors);
			tvAuditor.setText(Arrays.toString(auditorIds.toArray(new Integer[auditorIds.size()])));
		}
		
		
//		if(attendances != null){
		if(answerSheet.getAttendances() != null){
			List<Attendance> a = answerSheet.getAttendances();
			attendanceNames = new ArrayList<CharSequence>();
			for (int i = 0; i < a.size(); i++) {
				attendanceNames.add(a.get(i).getName());
			}
			
			TextView tvAttendances = (TextView)findViewById(R.id.attendances);
			tvAttendances.setText(Arrays.toString(attendanceNames.toArray(new String[attendanceNames.size()])));
		}
		*/
		
		Date startDateTime, finishDateTime;
		startDateTime = answersheet.getStartTime();
		if(startDateTime == null){
			startDateTime = new Date();
		}
		finishDateTime = answersheet.getFinishTime();
		if(finishDateTime == null){
			finishDateTime = new Date();
		}
		if( startDateTime != null && finishDateTime != null){
			TextView tvEvalDateTime = (TextView)findViewById(R.id.tvDate);
			String dt = String.format("%02d-%02d-%4d  %02d:%02d - %02d:%02d", 
										startDateTime.getDate(),
										startDateTime.getMonth() + 1,
										startDateTime.getYear() + 1900,
										startDateTime.getHours(),
										startDateTime.getMinutes(),
										finishDateTime.getHours(),
										finishDateTime.getMinutes());
			tvEvalDateTime.setText(dt);
		}
		
	}

	public void onButtonClick(View view){
		int viewId = view.getId();
		switch(viewId){
		case R.id.btnSelectPaper:
			selectPaper();
			break;
//		case R.id.btnSelectBp:
//			selectBp();
//			break;
		case R.id.btnSetDateTime:
			setDateTime();
			break;
//		case R.id.btnSelectAuditor:
////			selectAuditor();
//			break;
//		case R.id.btnAttendance:
////			setAttendance();
//			break;
		}
	}
//	
//	private void setAttendance(){
//		Intent intent = new Intent(this, AttendanceList.class);
//        intent.putExtra("attendances", attendanceNames);
//        startActivityForResult(intent, SET_ATTENDANCE);
//	}
//	ArrayList<Integer> selectedAuditorIds;
//	private void selectAuditor(){
//		Intent intent = new Intent();
//		intent.setClass(AnswersheetActivity.this,SelectAuditors.class);
//		intent.putIntegerArrayListExtra("selectedAuditorIds", selectedAuditorIds);
//		startActivityForResult(intent,AUDITORS_SELECT);
//	}


	private void selectPaper(){
		Intent paperIntent = new Intent();
		paperIntent.setClass(AnswersheetInfo.this,SelectPaper.class);
		paperIntent.putExtra("selectedPaperId", selectedPaperId);
		startActivityForResult(paperIntent,PAPER_SELECT);

	}
	
//	private void selectBp(){
//		Intent intent = new Intent();
//		intent.setClass(AnswersheetInfo.this,BPartnerInfo.class);
////		intent.putExtra("selectedBpId", selectedBpId);
//		intent.putExtra("selectedBPartner", selectedBPartner);
//		startActivityForResult(intent,BP_SELECT);
//	}
	private void setDateTime(){
		Intent dateTimeIntent = new Intent();
		dateTimeIntent.setClass(AnswersheetInfo.this,SetDateTime.class);
		dateTimeIntent.putExtra("startDateTime", answersheet.getStartTime());
		dateTimeIntent.putExtra("finishDateTime", answersheet.getFinishTime());
		startActivityForResult(dateTimeIntent,SET_DATE_TIME);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
    	switch(requestCode) {
    	case PAPER_SELECT: 
            if (resultCode == RESULT_OK) {
            	selectedPaperId = data.getIntExtra("selectedPaperId", 0);
            	answersheet.setPaper((Paper)data.getSerializableExtra("selectedPaper"));
                break;
            }
            
//    	case BP_SELECT: 
//            if (resultCode == RESULT_OK) {
//            	selectedBPartner = (BPartner)data.getSerializableExtra("selectedBPartner");
//            	selectedBpId = data.getIntExtra("selectedBpId", 0);
//                answersheet.setBpartner(selectedBPartner);
//                System.out.println("select bpartner " + answersheet.getBpartner().getName());
////                Toast.makeText(this, "BPartner " + answersheet.getBpartner().getName() , Toast.LENGTH_SHORT).show();
//                break;
//            }
//    	case AUDITORS_SELECT:
//            if (resultCode == RESULT_OK) {
//            	selectedAuditorIds = data.getIntegerArrayListExtra("selectedAuditorIds");
//                Toast.makeText(this, "You have chosen the auditors: " + selectedAuditorIds , Toast.LENGTH_SHORT).show();
//                answerSheet.setAuditorIds(selectedAuditorIds);
//                break;
//            }
    	case SET_DATE_TIME:
    		if(resultCode == RESULT_OK){
    			answersheet.setStartTime((Date)data.getSerializableExtra("startDateTime"));
    			answersheet.setFinishTime((Date)data.getSerializableExtra("finishDateTime"));
    			
    			break;
    		}
//    	case SET_ATTENDANCE:
//    		if(resultCode == RESULT_OK){
//    			attendanceNames = data.getCharSequenceArrayListExtra("attendances");
//    			System.out.println("Attendance name size : " + attendanceNames.size());
//    			List<Attendance> y = new ArrayList<Attendance>();
//    			for (int i = 0; i < attendanceNames.size(); i++) {
//					y.add(new Attendance(attendanceNames.get(i).toString()));
//					System.out.println("add " + attendanceNames.get(i) + " into attendance list");
//				}
//    			answerSheet.setAttendances(y);
//    			break;
//    		}
    	}
    	
    		
	} 
	
//	private boolean newAnswerSheet = false;
	
	 //Option menu
  	static final int MENU_ANSWERSHEET = 0;
  	
  	static final int CREATE_ANSWER_LIST = 10;
  	static final int VIEW_ANSWER_LIST = 11;
  	static final int SAVE = 12;
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// TODO Auto-generated method stub
//
//		menu.add(MENU_ANSWERSHEET, SAVE, 0, R.string.lbl_menu_save);
//
//		return true;
//	}
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// TODO Auto-generated method stub
//		switch(item.getItemId()){
//
//		case SAVE:
//			save();
//			break;
//		}
//		return true;
//	}
//	private boolean valid(){
//		
//		return true;
//	}
//	
//	private void save(){
//		if(!valid()){
//			message("not valid");
//			return;
//		}
//		//save answerSheet
////		IAnswerSheetManager answerSheetManager = my.getAnswerSheetManager();
////		answerSheetManager.save(answerSheet);
//		finish();
//		
//		
//	}

	private void message(String msg) {
		Toast toast;
		toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		if(v instanceof EditText){
			if(v.getId() == R.id.tvAnswersheetName){
				if(!hasFocus){
					System.out.println("answersheet name : "+ ((EditText)v).getText());
					answersheet.setAnswersheetName(((EditText)v).getText().toString());
				}else{
					System.out.println("has focus");
				}
			}
		}
	}
	
}
