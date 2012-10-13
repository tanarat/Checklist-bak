package org.silk.checklist.bo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;


import org.silk.checklist.dao.AnswerChoiceDao;
import org.silk.checklist.dao.AnswerChoiceOptionDao;
import org.silk.checklist.dao.AnswerDao;
import org.silk.checklist.dao.AnswersheetAttendanceDao;
import org.silk.checklist.dao.AnswersheetAuditorDao;
import org.silk.checklist.dao.AnswersheetDao;
import org.silk.checklist.dao.PaperQuestionDao;
import org.silk.checklist.dao.QuestionDao;
import org.silk.checklist.db.DbHelper;
import org.silk.checklist.model.Answer;
import org.silk.checklist.model.Answersheet;
import org.silk.checklist.model.Choice;
import org.silk.checklist.model.ListItem;
import org.silk.checklist.model.Paper;
import org.silk.checklist.model.Question;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AnswersheetBo {
	public String tag  = this.getClass().getName();
	private AnswersheetDao dao;
	private AnswerBo answerBo;
	private PaperBo paperBo;
	private PaperQuestionDao paperQuestionDao;
	private AnswersheetAuditorDao answersheetAuditorDao;
	private AnswersheetAttendanceDao answersheetAttendanceDao;
	private BPartnerBo bpartnerBo;

	public AnswersheetBo(SQLiteDatabase db) {
		super();
		dao = new AnswersheetDao(db);
		answerBo = new AnswerBo(db);
		paperBo = new PaperBo(db);
		paperQuestionDao = new PaperQuestionDao(db);
		answersheetAuditorDao = new AnswersheetAuditorDao(db);
		answersheetAttendanceDao = new AnswersheetAttendanceDao(db);
		bpartnerBo = new BPartnerBo(db);

	}
	
	public void save(Answersheet answersheet){
		
		int answersheetId = answersheet.getAnswersheetId();
		String name = answersheet.getAnswersheetName();
		int paperId = answersheet.getPaper().getPaperId();
		int bpId = answersheet.getBpartner().getBpartnerId();
		Date date = answersheet.getDate();
		Date start = answersheet.getStartTime();
		Date finish = answersheet.getFinishTime();
		
		if(answersheetId != 0){
			dao.update(answersheetId, name, paperId, bpId, date, start, finish);
			
			List<Integer> auditorIds = answersheet.getAuditorIds();
			answersheetAuditorDao.delete(answersheetId);
			answersheetAuditorDao.insert(answersheetId, auditorIds);
			
			List<String> attendances = answersheet.getAttendances();
			answersheetAttendanceDao.delete(answersheetId);
			answersheetAttendanceDao.insert(answersheetId, attendances);
			
		}else{
			
			create(answersheet);
			
		}
		

	}
	public void create(Answersheet answersheet){
		String name = answersheet.getAnswersheetName();
		int paperId = answersheet.getPaper().getPaperId();
		int bpId = answersheet.getBpartner().getBpartnerId();
		Date date = answersheet.getDate();
		Date start = answersheet.getStartTime();
		Date finish = answersheet.getFinishTime();
		
		
		
		int id = dao.insert(name, paperId, bpId, date, start, finish);
		answersheet.setAnswersheetId(id);
		
		List<Integer> auditorIds = answersheet.getAuditorIds();
		answersheetAuditorDao.delete(id);
		answersheetAuditorDao.insert(id, auditorIds);
		
		List<String> attendances = answersheet.getAttendances();
		answersheetAttendanceDao.delete(id);
		answersheetAttendanceDao.insert(id, attendances);
		
		
		// create answer list of this answersheet
		Log.i(tag, "Create list of answers for Answersheet id " + id);
		Cursor cursor = paperQuestionDao.fetchByPaperId(paperId);
		while(cursor.moveToNext()){
			int questionId = cursor.getInt(cursor.getColumnIndex(PaperQuestionDao.COLUMN_QUESTION_ID));
			Answer answer = new Answer(answersheet.getAnswersheetId(), questionId);
			answerBo.save(answer);
		}
		cursor.close();
	}
	
	public AnswersheetDao getDao() {
		return dao;
	}

	public void setDao(AnswersheetDao dao) {
		this.dao = dao;
	}
//	public Answer getAnswer(int id) {
//		// TODO Auto-generated method stub
//		Answer answer = null;
//		Cursor cursor =  dao.fetchById(id);
//		if(cursor.moveToFirst()){
//			int answerSheetId = cursor.getInt(cursor.getColumnIndex(AnswerDao.COLUMN_ANSWERSHEET_ID));
//			int questionId = cursor.getInt(cursor.getColumnIndex(AnswerDao.COLUMN_QUESTION_ID));
//			String remark = cursor.getString(cursor.getColumnIndex(AnswerDao.COLUMN_REMARK));
//			answer = new Answer(answerSheetId, questionId, remark);
//			answer.setAnswerId(cursor.getInt(cursor.getColumnIndex(AnswerDao.COLUMN_ID)));
//		}
//		cursor.close();
//		if(answer == null) return answer;
//		
//		Cursor answerChoiceCursor = answerChoiceDao.fetchByAnswerId(answer.getAnswerId());
//		while(answerChoiceCursor.moveToNext()){
//			int choiceId = answerChoiceCursor.getInt(answerChoiceCursor.getColumnIndex(AnswerChoiceDao.COLUMN_CHOICE_ID));
//			answer.getMapChoices().put(choiceId, new ArrayList<Integer>());
//			int answerChoiceId = answerChoiceCursor.getInt(answerChoiceCursor.getColumnIndex(AnswerChoiceDao.COLUMN_ID));
//			Cursor answerChoiceOptionCursor = answerChoiceOptionDao.fetchByAnswerId(answerChoiceId);
//			while(answerChoiceOptionCursor.moveToNext()){
//				int optionId = answerChoiceOptionCursor.getInt(answerChoiceOptionCursor.getColumnIndex(AnswerChoiceOptionDao.COLUMN_OPTION_ID));
//				answer.getMapChoices().get(choiceId).add(optionId);
//			}
//			answerChoiceOptionCursor.close();
//			
//		}
//		answerChoiceCursor.close();
//		return answer;
//	}
//	public void list() {
//		// TODO Auto-generated method stub
//		List<Answersheet> answersheetList = new ArrayList<Answersheet>();
//		Cursor cursor = dao.fetchAll();
//		while(cursor.moveToNext()){
////			Answesheet answersheet = new Answersheet();
//		}
//	}
	

	public List<Answersheet> getAnswersheetList(){
		List<Answersheet> answersheetList = new ArrayList<Answersheet>();
		Cursor cursor = dao.fetchAll();
		while(cursor.moveToNext()){
			int answersheetId = cursor.getInt(cursor.getColumnIndex(AnswersheetDao.COLUMN_ID));
			String answersheetName = cursor.getString(cursor.getColumnIndex(AnswersheetDao.COLUMN_NAME));
			int paperId = cursor.getInt(cursor.getColumnIndex(AnswersheetDao.COLUMN_PAPER_ID));
			int bpartnerId = cursor.getInt(cursor.getColumnIndex(AnswersheetDao.COLUMN_BP_ID));
			String startTime = cursor.getString(cursor.getColumnIndex(AnswersheetDao.COLUMN_START));
			String finishTime = cursor.getString(cursor.getColumnIndex(AnswersheetDao.COLUMN_FINISH));
			
			List<Integer> auditorIds = new ArrayList<Integer>();
			Cursor cursorX = answersheetAuditorDao.fetchById(answersheetId);
			while(cursorX.moveToNext()){
				int auditorId = cursorX.getInt(cursorX.getColumnIndex(AnswersheetAuditorDao.COLUMN_AUDITOR_ID));
				auditorIds.add(auditorId);
			}
			cursorX.close();
			
			List<String> attendances = new ArrayList<String>();
			Cursor cursorY = answersheetAttendanceDao.fetchById(answersheetId);
			while(cursorY.moveToNext()){
				String attendance = cursorY.getString(cursorY.getColumnIndex(AnswersheetAttendanceDao.COLUMN_ATTENDANCE_NAME));
				attendances.add(attendance);
			}
			cursorY.close();
			
			
			Answersheet answersheet = new Answersheet();
			answersheet.setAnswersheetId(answersheetId);
			answersheet.setAnswersheetName(answersheetName);
			answersheet.setPaper(paperBo.getPaper(paperId));
			answersheet.setBpartner(bpartnerBo.getBPartner(bpartnerId));
			answersheet.setStartTime(new Date(startTime));
			answersheet.setFinishTime(new Date(finishTime));
			answersheet.setAuditorIds(auditorIds);
			answersheet.setAttendances(attendances);
			
			answersheetList.add(answersheet);
		}
		cursor.close();
		return answersheetList;
	}
	public Answersheet getAnswersheet(int answersheetId) {
		// TODO Auto-generated method stub
		Answersheet answersheet = null;

		Cursor cursor = dao.fetchById(answersheetId);
		if(cursor.moveToNext()){
			int id = cursor.getInt(cursor.getColumnIndex(AnswersheetDao.COLUMN_ID));
			String answersheetName = cursor.getString(cursor.getColumnIndex(AnswersheetDao.COLUMN_NAME));
			int paperId = cursor.getInt(cursor.getColumnIndex(AnswersheetDao.COLUMN_PAPER_ID));
			int bpartnerId = cursor.getInt(cursor.getColumnIndex(AnswersheetDao.COLUMN_BP_ID));
			String startTime = cursor.getString(cursor.getColumnIndex(AnswersheetDao.COLUMN_START));
			String finishTime = cursor.getString(cursor.getColumnIndex(AnswersheetDao.COLUMN_FINISH));
			
			List<Integer> auditorIds = new ArrayList<Integer>();
			Cursor cursorX = answersheetAuditorDao.fetchById(answersheetId);
			while(cursorX.moveToNext()){
				int auditorId = cursorX.getInt(cursorX.getColumnIndex(AnswersheetAuditorDao.COLUMN_AUDITOR_ID));
				auditorIds.add(auditorId);
			}
			cursorX.close();
			
			List<String> attendances = new ArrayList<String>();
			Cursor cursorY = answersheetAttendanceDao.fetchById(answersheetId);
			while(cursorY.moveToNext()){
				String attendance = cursorY.getString(cursorY.getColumnIndex(AnswersheetAttendanceDao.COLUMN_ATTENDANCE_NAME));
				attendances.add(attendance);
			}
			cursorY.close();
			
			answersheet = new Answersheet();
			answersheet.setAnswersheetId(id);
			answersheet.setAnswersheetName(answersheetName);
			answersheet.setPaper(paperBo.getPaper(paperId));
			answersheet.setBpartner(bpartnerBo.getBPartner(bpartnerId));
			answersheet.setStartTime(new Date(startTime));
			answersheet.setFinishTime(new Date(finishTime));
			answersheet.setAuditorIds(auditorIds);
			answersheet.setAttendances(attendances);
			
		}
		cursor.close();
		return answersheet;
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
