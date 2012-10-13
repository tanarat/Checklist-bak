package org.hsc.silk.halal.activity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import org.hsc.silk.App;
import org.hsc.silk.halal.R;
import org.hsc.silk.halq.bo.AnswerBo;
import org.hsc.silk.halq.bo.AnswersheetBo;
import org.hsc.silk.adapter.AnswerListAdapter;
import org.hsc.silk.model.Answer;
import org.hsc.silk.model.Answersheet;
import org.hsc.silk.model.ListItem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;



public class AnswerListActivity extends Activity{
	App app;
	private Answersheet answersheet;
//	private AnswersheetBo answersheetBo;
	private AnswerBo answerBo;
	private List<Answer> answerList;
	//<questionId,answerObj>
	private Map<Integer,Answer> mapAnswer = new LinkedHashMap<Integer, Answer>();
	private List<ListItem> listItem;
	AnswerListAdapter answerListAdapter;
	int answersheetId = -1;
	ListView listView;
	public static final int ANSWER = 21;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		app = (App)getApplication();
		setContentView(R.layout.question_list);
		setTitle(R.string.title_activity_questionlist);
//		setContentView(R.layout.item_list);
		
//		answersheetBo = new AnswersheetBo(app.getDbHelper());
		answerBo = new AnswerBo(app.getDatabase());
		
		
		Intent intent = this.getIntent();
		answersheetId = intent.getIntExtra("answersheetId", -1);
//		answersheet = answersheetBo.getAnswersheet(answersheetId);
		
		
//		questionList = new ArrayList<ListItem>(app.questionList(answersheet.getPaper().getPaperId()));
//		answerList = app.answerList(answerSheetId);
//		for (Answer answer : answerList) {
//			mapAnswer.put(answer.getQuestionId(), answer);
//		}

//		ItemListAdapter answerListAdapter = new ItemListAdapter(questionList, this);

		
		
		
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
		
		listItem = new ArrayList<ListItem>(answerBo.getAnswersForAnswersheet(answersheetId));
		answerListAdapter = new AnswerListAdapter(this, listItem);
		listView = (ListView)findViewById(R.id.list); 
		listView.setAdapter(answerListAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long answerId) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AnswerListActivity.this, AnswerActivity.class);
				intent.putExtra("answerId", (int)answerId);
				startActivity(intent);
//				AnswerListActivity.this.startActivityForResult(intent, ANSWER);
			}
		});
		
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
    	switch(requestCode) {
    	case ANSWER: 
            if (resultCode == RESULT_OK) {
            	Answer answer = (Answer)data.getSerializableExtra("answer");
                mapAnswer.put(answer.getQuestionId(), answer);
//                System.out.println("return values ...");
//                System.out.println("selectedChoiceId : " + answer.getSelectedChoiceId());
//                System.out.println("selectedOptionIds : " + answer.getSelectedOptionIds());
                break;
            }
    	}
	}
	 //Option menu
  	static final int MENU_ANSWERSHEET = 0;

  	static final int SAVE = 12;
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

//		menu.add(MENU_ANSWERSHEET, SAVE, 0, R.string.menuSave);

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
		List<Answer> answerList = new ArrayList<Answer>( mapAnswer.values());
//		app.save(answersheet,answerList);
		finish();
	}
}
