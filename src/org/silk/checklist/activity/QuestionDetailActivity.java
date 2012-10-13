package org.silk.checklist.activity;

import java.util.ArrayList;
import java.util.List;


import org.silk.checklist.App;
import org.silk.checklist.R;
import org.silk.checklist.bo.QuestionBo;
import org.silk.checklist.model.Choice;
import org.silk.checklist.model.Option;
import org.silk.checklist.model.Question;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class QuestionDetailActivity extends Activity{
	App app;
	Question question;
	QuestionBo questionBo;

	
	int diff = 1000000; // use to make  choiceId and optionId difference
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		app = (App)getApplication();
		setContentView(R.layout.question);
		Intent intent = getIntent();
		int questionId = intent.getIntExtra("questionId", 0);
		questionBo = new QuestionBo(app.getDatabase());
		
		question = questionBo.getQuestion(questionId);
		if(question != null){
			TextView tvQuestionText = (TextView)findViewById(R.id.tvQuestionText);
			tvQuestionText.setText(question.getQuestionText());
			
			//Choice
			RadioGroup radioGroup = (RadioGroup) findViewById(R.id.choices);
			List<Choice> choiceList = new ArrayList<Choice>(question.getMapChoices().values());
			for (Choice choice : choiceList) {
				RadioButton chRadio = new RadioButton(this);
				chRadio.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT));
				chRadio.setPadding(50, 0, 100, 0);
				chRadio.setText(choice.getChoiceText());
				radioGroup.addView(chRadio);
			}
			
			//Option
			LinearLayout chOptLayout = (LinearLayout) findViewById(R.id.options);
			chOptLayout.setOrientation(LinearLayout.VERTICAL);
			chOptLayout.setPadding(50, 0, 0, 0);
			for (Choice choice : choiceList) {
				List<Option> optionList = new ArrayList<Option>(choice.getMapOptions().values());		
				for (Option option : optionList) {
					CheckBox chk = new CheckBox(this);
					chk.setId(option.getOptionId() + diff);
					chk.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
					chk.setText(option.getOptionText());
					chOptLayout.addView(chk);
				}
			}
			
			
		}
	}

}
