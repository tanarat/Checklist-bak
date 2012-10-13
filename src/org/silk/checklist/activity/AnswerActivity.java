package org.silk.checklist.activity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.silk.checklist.App;
import org.silk.checklist.R;
import org.silk.checklist.bo.AnswerBo;
import org.silk.checklist.model.Answer;
import org.silk.checklist.model.Choice;
import org.silk.checklist.model.Option;
import org.silk.checklist.model.Question;
import org.silk.widget.CheckboxGroup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class AnswerActivity extends Activity implements OnCheckedChangeListener{
	App app;
	int answerId = 0;
	Answer answer;
	AnswerBo answerBo;
	
	
	int diff = 1000000; // use to make  choiceId and optionId difference
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		app = (App)getApplication();
		setContentView(R.layout.question);
		setTitle(R.string.title_activity_answersheet);
		Intent intent = getIntent();
		answerId = intent.getIntExtra("answerId", 0);
		answerBo = new AnswerBo(app.getDatabase());
		
		
	}
	private void saveAnswer(){
		
		//choice 
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.choices);
		int selectedChoice = radioGroup.getCheckedRadioButtonId();
		if(selectedChoice == -1){
			return ;
		}
		
		//option
		List<Integer> selectedOptions = null;
		LinearLayout chOptLayout = (LinearLayout) findViewById(R.id.options);
		for(int i = 0; i < chOptLayout.getChildCount(); i++){
			CheckboxGroup chGroup = (CheckboxGroup) chOptLayout.getChildAt(i);
			if(chGroup.getId() == selectedChoice){
				selectedOptions = chGroup.getCheckedItemIds();
				break;
			}
		}
		Map<Integer, List<Integer>> map = new LinkedHashMap<Integer, List<Integer>>();
		map.put(selectedChoice, selectedOptions);
		answer.getMapChoices().clear();
		answer.setMapChoices(map);
		
		//remark
		TextView tvRemark = (TextView)findViewById(R.id.tvRemark);
		String remark = tvRemark.getText().toString();
		answer.setRemark(remark);
		
//		System.out.println("test print save data");
//		System.out.println("answerId : " + answer.getAnswerId());
//		System.out.println("answersheetId : " + answer.getAnswersheetId());
//		System.out.println("questionId : " + answer.getQuestionId());
//		System.out.println("remark : " + answer.getRemark());
//		System.out.println("choice and option : " + answer.getMapChoices());
		
		answerBo.save(answer);
		
	}
	public void onOkButtonClick(View view){
		saveAnswer();
		finish();
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
		
		answer = answerBo.getAnswer(answerId);
		if(answer == null){
			message("Could not find answer with answerId " + answerId);
		}else{
			Question question = answer.getQuestion();
			TextView tvQuestionText = (TextView)findViewById(R.id.tvQuestionText);
			tvQuestionText.setText(question.getQuestionText());
			tvQuestionText.setTextSize(30);
			
			//selected choice
			Map<Integer,List<Integer>> selectedMap = answer.getMapChoices();
			
			//Choice
			RadioGroup radioGroup = (RadioGroup) findViewById(R.id.choices);
			List<Choice> choiceList = new ArrayList<Choice>(question.getMapChoices().values());
			for (Choice choice : choiceList) {
				RadioButton chRadio = new RadioButton(this);
				chRadio.setId(choice.getChoiceId());
				chRadio.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT));
				chRadio.setPadding(50, 0, 100, 0);
				chRadio.setText(choice.getChoiceText());
				chRadio.setTextSize(25);
				
				if(selectedMap.containsKey(choice.getChoiceId())){
					chRadio.setChecked(true);
				}
				
				radioGroup.addView(chRadio);
				
			}
			radioGroup.setOnCheckedChangeListener(this);
			
			//Option
			LinearLayout chOptLayout = (LinearLayout) findViewById(R.id.options);
			chOptLayout.setOrientation(LinearLayout.VERTICAL);
			chOptLayout.setPadding(50, 0, 0, 0);
			for (Choice choice : choiceList) {
				List<Option> optionList = new ArrayList<Option>(choice.getMapOptions().values());
				CheckboxGroup chGroup = new CheckboxGroup(getApplicationContext());
				chGroup.setId(choice.getChoiceId());
				List<Integer> selectOpt = selectedMap.get(choice.getChoiceId());
				for (Option option : optionList) {
						boolean checked = (selectOpt != null)? selectOpt.contains(option.getOptionId()): false;
						chGroup.addCheckbox(option.getOptionId(), option.getOptionText(), checked );
					
				}
				chOptLayout.addView(chGroup);
			}
			
			//Remark
			String remark = answer.getRemark();
			if(remark != null){
				TextView tvRemark = (TextView)findViewById(R.id.tvRemark);
				tvRemark.setText(remark);
			}
		}
	}

	private void message(String msg) {
		Toast toast;
		toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		LinearLayout chOptLayout = (LinearLayout) findViewById(R.id.options);
		for(int i = 0; i < chOptLayout.getChildCount(); i++){
			CheckboxGroup chGroup = (CheckboxGroup) chOptLayout.getChildAt(i);
			if(chGroup.getId() == checkedId){
				System.out.println("enable " + chGroup.getId());
				chGroup.setEnableAllCheckBox(true);
			}else{
				System.out.println("disable " + chGroup.getId());
				chGroup.setEnableAllCheckBox(false);
			}
			
		}
	}
}
