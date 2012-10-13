package org.silk.checklist.adapter;

import java.util.ArrayList;
import java.util.List;

import org.silk.checklist.App;
import org.silk.checklist.R;
import org.silk.checklist.bo.ChoiceBo;
import org.silk.checklist.model.Answer;
import org.silk.checklist.model.Choice;
import org.silk.checklist.model.ListItem;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AnswerListAdapter extends BaseAdapter {
	App app;
//	private Activity activity;
	private List<ListItem> answerList;
	private static LayoutInflater inflater = null;
	private ChoiceBo choiceBo;

	// private Activity activity;

	public AnswerListAdapter(Activity activity,List<ListItem> answerList) {
		super();
//		this.activity = activity;
		app = (App)activity.getApplication();
		choiceBo = new ChoiceBo(app.getDatabase());
	
		this.answerList = answerList;
		
		// this.activity = activity;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
//		Set<Integer> key  = mapAnswer.keySet();
//		for (Integer integer : key) {
//			Answer answer = mapAnswer.get(integer);
////			System.out.println("------"+answer.getSelectedChoiceId());
//		}
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return answerList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return answerList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub

		return (answerList.get(position)).getItemId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		View view = convertView;
		if (convertView == null)
			view = inflater.inflate(R.layout.question_list_row, null);
		
		ImageView thumb_image=(ImageView)view.findViewById(R.id.list_image); // thumb image

		TextView tvQuestionItemText = (TextView) view
				.findViewById(R.id.questionText);
		TextView tvQuestionGroup = (TextView) view.findViewById(R.id.questionGroup);

		ListItem item = answerList.get(position);
		

		tvQuestionItemText.setText(item.getItemName());
		Answer answer = (Answer)item;
		
		tvQuestionGroup.setText(answer.getQuestion().getGroupName());
		
		List<Integer> selectedChoices = new ArrayList<Integer>(answer.getMapChoices().keySet());
		if(selectedChoices.size() > 0){
			int selectedChoiceId = selectedChoices.get(0);
			
			Choice selectedChoice = choiceBo.getChoice(selectedChoiceId);
			
			if(selectedChoice != null){
				String choiceText = selectedChoice.getChoiceText();
				if(choiceText.equals("มี") || choiceText.equals("ถูกต้อง") || choiceText.equals("ดี") || choiceText.equals("ใช้"))
					thumb_image.setImageResource(R.drawable.ic_yes);
				else if(choiceText.equals("ไม่มี") || choiceText.equals("ไม่ถูกต้อง") || choiceText.equals("ต้องปรับปรุง") || choiceText.equals("ไม่ใช้"))
					thumb_image.setImageResource(R.drawable.ic_no);
				else
					thumb_image.setImageResource(R.drawable.ic_notcheckd);
				
			}
		}else{
//			thumb_image.setImageResource(R.drawable.ic_notcheckd);
		}
				
		return view;
	}

}
