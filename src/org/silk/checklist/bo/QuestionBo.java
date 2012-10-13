package org.silk.checklist.bo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


import org.silk.checklist.dao.PaperQuestionDao;
import org.silk.checklist.dao.QuestionDao;
import org.silk.checklist.db.DbHelper;
import org.silk.checklist.model.Choice;
import org.silk.checklist.model.ListItem;
import org.silk.checklist.model.Option;
import org.silk.checklist.model.Paper;
import org.silk.checklist.model.Question;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class QuestionBo {
	public String tag  = this.getClass().getName();
	private QuestionDao dao;
	private PaperQuestionDao paperQuestionDao;
	private ChoiceBo choiceBo;
	private OptionBo optionBo;
	public QuestionBo(SQLiteDatabase db) {
		super();
		dao = new QuestionDao(db);
		paperQuestionDao = new PaperQuestionDao(db);
		choiceBo = new ChoiceBo(db);
		optionBo = new OptionBo(db);
	}
	
	public void save(Question question){
		
		int questionId = question.getId();
		String name = question.getQuestionText();
		String groupName = question.getGroupName();
//		List<Choice> choices = question.getChoices();
		
		if(dao.exist(questionId)){
			dao.update(questionId,name,groupName);
		}else{
			dao.insert(questionId, name, groupName);
		}
		
//		choiceBo.save(choices);

	}
	public void save(List<Question> list){
		for (Question question : list) {
			save(question);
		}
	}
	public void create(Question question){
		Map<Integer, Choice> mapChoices = question.getMapChoices();
		List<Choice> listChoice = new ArrayList<Choice>(mapChoices.values());
		choiceBo.create(question.getId(),listChoice);
		dao.insert(question.getId(), question.getQuestionText(),question.getGroupName());
	}
	public void create(List<Question> questionList){
		for (Question question : questionList) {
			create(question);
		}
	}

	public QuestionDao getDao() {
		return dao;
	}

	public void setDao(QuestionDao dao) {
		this.dao = dao;
	}
	public void loadSampleData(){
		List<Question> questions = new ArrayList<Question>();
		questions.add(new Question(1,"มีคู่มือคุณภาพ (QM) ครอบคลุมการผลิตอาหารฮาลาล"));
		questions.add(new Question(2,"มีคู่มือการปฏิบัติงานครอบคลุมการผลิตอาหารฮาลาล"));
		questions.add(new Question(3,"มีการควบคุมเอกสารและบันทึกการปฏิบัติในการผลิตอาหารฮาลาล"));
		questions.add(new Question(4,"มีนโยบายการผลิตอาหารฮาลาลเป็นลายลักษณ์อักษร/ประกาศรับทราบทั่วกัน"));
		save(questions);
	}
	public List<Question> listByPaperId(int paperId) {
		// TODO Auto-generated method stub
		
		
		Cursor cursor = paperQuestionDao.fetchByPaperId(paperId);
		List<Integer> questionIds = new ArrayList<Integer>();
		while(cursor.moveToNext()){
			questionIds.add(cursor.getInt(cursor.getColumnIndex(PaperQuestionDao.COLUMN_QUESTION_ID)));
		}
		cursor.close();
		return list(questionIds);
		
	}
	public List<Question> list(List<Integer> questionIds){
		Log.i(tag, "question id : " + questionIds);
		List<Question> questions = new ArrayList<Question>();
		Cursor cursor = dao.fetchByIds(questionIds);
		while(cursor.moveToNext()){
			int id = cursor.getInt(cursor.getColumnIndex(QuestionDao.COLUMN_ID));
			String questionText = cursor.getString(cursor.getColumnIndex(QuestionDao.COLUMN_NAME));
			String questionGroup = cursor.getString(cursor.getColumnIndex(QuestionDao.COLUMN_GROUP_NAME));
			Question question = new Question(id, questionText, questionGroup);
			questions.add(question);
		}
		cursor.close();
		return questions;
	}
	public Question getQuestion(int questionId){
		Question question = null;
		Cursor cursor = dao.fetchById(questionId);
		if(cursor.moveToNext()){
			int id = cursor.getInt(cursor.getColumnIndex(QuestionDao.COLUMN_ID));
			String questionText = cursor.getString(cursor.getColumnIndex(QuestionDao.COLUMN_NAME));
			String questionGroup = cursor.getString(cursor.getColumnIndex(QuestionDao.COLUMN_GROUP_NAME));
			question = new Question(id, questionText, questionGroup );
		}
		cursor.close();
		
		if(question == null) return question;
		List<Choice> choiceList = choiceBo.getChoiceByQuestion(question.getId());
		for (Choice choice : choiceList) {
			List<Option> optionList = optionBo.getOptionByChoice(choice.getChoiceId());
			for (Option option : optionList) {
				choice.addOption(option);
			}
			question.addChoice(choice);
		}
		
		return question;
	}

}
