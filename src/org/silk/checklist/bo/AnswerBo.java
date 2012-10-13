package org.silk.checklist.bo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


import org.silk.checklist.dao.AnswerChoiceDao;
import org.silk.checklist.dao.AnswerChoiceOptionDao;
import org.silk.checklist.dao.AnswerDao;
import org.silk.checklist.dao.PaperQuestionDao;
import org.silk.checklist.dao.QuestionDao;
import org.silk.checklist.db.DbHelper;
import org.silk.checklist.model.Answer;
import org.silk.checklist.model.Choice;
import org.silk.checklist.model.ListItem;
import org.silk.checklist.model.Paper;
import org.silk.checklist.model.Question;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AnswerBo {
	public String tag  = this.getClass().getName();
	private AnswerDao dao;
	private AnswerChoiceDao answerChoiceDao;
	private AnswerChoiceOptionDao answerChoiceOptionDao;
	private QuestionBo questionBo;
//	private ChoiceBo choiceBo;
	public AnswerBo(SQLiteDatabase db) {
		super();
		dao = new AnswerDao(db);
		answerChoiceDao = new AnswerChoiceDao(db);
		answerChoiceOptionDao = new AnswerChoiceOptionDao(db);
		questionBo = new QuestionBo(db);
	}
	
	
	public void save(Answer answer){
		
		int answerId = answer.getAnswerId();
		int answersheetId = answer.getAnswersheetId();
		int questionId = answer.getQuestionId();
		String remark = answer.getRemark();

		
		if(answerId != 0){
			dao.update(answerId, answersheetId, questionId, remark);
			
			//delete all selected choices and options
			Cursor cursor = answerChoiceDao.fetchByAnswerId(answerId);
			while(cursor.moveToNext()){
				int answerChoiceId = cursor.getInt(cursor.getColumnIndex(AnswerChoiceDao.COLUMN_ID));
				answerChoiceOptionDao.delete(answerChoiceId);
			}
			cursor.close();
			answerChoiceDao.delete(answerId);
			
			//save selected choices and options for each choice
			List<Integer> selectChoiceList = new ArrayList<Integer>(answer.getMapChoices().keySet());
			for (Integer choiceId : selectChoiceList) {
				int answerChoiceId = answerChoiceDao.insert(answerId, choiceId);
				List<Integer> optionIds = answer.getMapChoices().get(choiceId);
				answerChoiceOptionDao.insert(answerChoiceId, optionIds);
			}
			
		}else{
			int id = dao.insert(answersheetId, questionId, remark);
			answer.setAnswerId(id);
		}
		
		
		
		
		
		

	}
	public void save(List<Answer> list){
		for (Answer answer : list) {
			save(answer);
		}
	}

	public AnswerDao getDao() {
		return dao;
	}

	public void setDao(AnswerDao dao) {
		this.dao = dao;
	}
	public Answer getAnswer(int id) {
		// TODO Auto-generated method stub
		Answer answer = null;
		Cursor cursor =  dao.fetchById(id);
		if(cursor.moveToFirst()){
			int answerSheetId = cursor.getInt(cursor.getColumnIndex(AnswerDao.COLUMN_ANSWERSHEET_ID));
			int questionId = cursor.getInt(cursor.getColumnIndex(AnswerDao.COLUMN_QUESTION_ID));
			String remark = cursor.getString(cursor.getColumnIndex(AnswerDao.COLUMN_REMARK));
			answer = new Answer(answerSheetId, questionId, remark);
			answer.setAnswerId(cursor.getInt(cursor.getColumnIndex(AnswerDao.COLUMN_ID)));
			Question question = questionBo.getQuestion(questionId);
			answer.setQuestionText(question.getQuestionText());
			answer.setQuestion(questionBo.getQuestion(questionId));
		}
		cursor.close();
		if(answer == null) return answer;
		
		Cursor answerChoiceCursor = answerChoiceDao.fetchByAnswerId(answer.getAnswerId());
		while(answerChoiceCursor.moveToNext()){
			int choiceId = answerChoiceCursor.getInt(answerChoiceCursor.getColumnIndex(AnswerChoiceDao.COLUMN_CHOICE_ID));
			answer.getMapChoices().put(choiceId, new ArrayList<Integer>());
			int answerChoiceId = answerChoiceCursor.getInt(answerChoiceCursor.getColumnIndex(AnswerChoiceDao.COLUMN_ID));
			Cursor answerChoiceOptionCursor = answerChoiceOptionDao.fetchByAnswerId(answerChoiceId);
			while(answerChoiceOptionCursor.moveToNext()){
				int optionId = answerChoiceOptionCursor.getInt(answerChoiceOptionCursor.getColumnIndex(AnswerChoiceOptionDao.COLUMN_OPTION_ID));
				answer.getMapChoices().get(choiceId).add(optionId);
			}
			answerChoiceOptionCursor.close();
			
		}
		answerChoiceCursor.close();
		return answer;
	}
	public List<Answer> getAnswersForAnswersheet(int answersheetId) {
		// TODO Auto-generated method stub
		List<Answer> answerList = new ArrayList<Answer>();
		Cursor cursor = dao.fetchByAnswersheetId(answersheetId);
		while(cursor.moveToNext()){
			int answerId = cursor.getInt(cursor.getColumnIndex(AnswerDao.COLUMN_ID));
			Answer answer = getAnswer(answerId);
			/*
			int questionId = cursor.getInt(cursor.getColumnIndex(AnswerDao.COLUMN_QUESTION_ID));
			Question question = questionBo.getQuestion(questionId);
			String remark = cursor.getString(cursor.getColumnIndex(AnswerDao.COLUMN_REMARK));
			Answer answer = new Answer(answersheetId, questionId, remark);
			answer.setAnswerId(answerId);
			answer.setQuestionText(question.getQuestionText());
			answer.setQuestion(question);
			*/
			answerList.add(answer);
		}
		cursor.close();
		return answerList;
	}
	
//	public List<Answer> listByAnswerSheetId(int answersheetId) {
//		// TODO Auto-generated method stub
//		
//		
//		Cursor cursor = paperQuestionDao.fetchByPaperId(answersheetId);
//		List<Integer> questionIds = new ArrayList<Integer>();
//		while(cursor.moveToNext()){
//			questionIds.add(cursor.getInt(cursor.getColumnIndex(PaperQuestionDao.COLUMN_QUESTION_ID)));
//		}
//		cursor.close();
//		return list(questionIds);
//		
//	}
//	public List<Question> list(List<Integer> questionIds){
//		Log.i(tag, "question id : " + questionIds);
//		List<Question> questions = new ArrayList<Question>();
//		Cursor cursor = dao.fetchByIds(questionIds);
//		while(cursor.moveToNext()){
//			int id = cursor.getInt(cursor.getColumnIndex(QuestionDao.COLUMN_ID));
//			String questionText = cursor.getString(cursor.getColumnIndex(QuestionDao.COLUMN_NAME));
//			Question question = new Question(id, questionText);
//			questions.add(question);
//		}
//		cursor.close();
//		return questions;
//	}

}
