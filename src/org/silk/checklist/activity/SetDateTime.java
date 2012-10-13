package org.silk.checklist.activity;

import java.util.Date;

import org.silk.checklist.R;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class SetDateTime extends Activity{

	private Date startDateTime;
	private Date finishDateTime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_date_time);
		Intent intent = getIntent();
		startDateTime = (Date)intent.getSerializableExtra("startDateTime");
		if(startDateTime == null){
			startDateTime = new Date();
		}
		finishDateTime = (Date)intent.getSerializableExtra("finishDateTime");
		if(finishDateTime == null){
			finishDateTime = new Date();
		}
		int startHour = startDateTime.getHours();
		int startMinute = startDateTime.getMinutes();
		int finishHour = finishDateTime.getHours();
		int finishMinute = finishDateTime.getMinutes();
		
		DatePicker dp = (DatePicker)findViewById(R.id.date);
		int year = startDateTime.getYear() + 1900;
		int month = startDateTime.getMonth();
		int date = startDateTime.getDate();
		dp.init(year, month, date, null);
		
		TimePicker startTP = (TimePicker)findViewById(R.id.startTime);
		startTP.setCurrentHour(startHour);
		startTP.setCurrentMinute(startMinute);
		TimePicker finishTP = (TimePicker)findViewById(R.id.finishTime);
		finishTP.setCurrentHour(finishHour);
		finishTP.setCurrentMinute(finishMinute);
		
		
	}
	public void onButtonClick(View view){
		DatePicker dp = (DatePicker)findViewById(R.id.date);
		int day = dp.getDayOfMonth();
		int month = dp.getMonth();
		int year = dp.getYear() - 1900;
		
		TimePicker startTP = (TimePicker)findViewById(R.id.startTime);
		int startHour = startTP.getCurrentHour();
		int startMinute = startTP.getCurrentMinute();
		Date startDateTime = new Date(year, month, day, startHour, startMinute);
		System.out.println(startDateTime.toString());
		
		TimePicker finishTP = (TimePicker)findViewById(R.id.finishTime);
		int finishHour = finishTP.getCurrentHour();
		int finishMinute = finishTP.getCurrentMinute();
		Date finishDateTime = new Date(year, month, day, finishHour, finishMinute);
		System.out.println(finishDateTime.toString());
		
		Intent returnIntent = new Intent();
		returnIntent.putExtra("startDateTime", startDateTime);
		returnIntent.putExtra("finishDateTime", finishDateTime);
		setResult(RESULT_OK, returnIntent);
		finish();
	}

}
