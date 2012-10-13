package org.hsc.silk.halq.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hsc.silk.dao.ChoiceDao;
import org.hsc.silk.db.DbHelper;
import org.hsc.silk.model.Choice;
import org.hsc.silk.model.Option;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ChoiceBo {
	private ChoiceDao dao;
	private OptionBo optionBo;
	public ChoiceBo(SQLiteDatabase db) {
		super();
		dao = new ChoiceDao(db);
		optionBo = new OptionBo(db);
	}
	
	public void save(Choice choice){
		int choiceId = choice.getChoiceId();
		int questionId = choice.getQuestionId();
		String choiceText = choice.getChoiceText();
		if(dao.exist(choiceId)){
			dao.update(choiceId, questionId, choiceText);
		}else{
			dao.insert(choiceId, questionId, choiceText);
		}
	}
	public void save(List<Choice> list){
		for (Choice choice : list) {
			save(choice);
		}
	}

	public ChoiceDao getDao() {
		return dao;
	}

	public void setDao(ChoiceDao dao) {
		this.dao = dao;
	}
	public void create(int questionId, List<Choice> listChoice) {
		// TODO Auto-generated method stub
		for (Choice choice : listChoice) {
			Map<Integer, Option> mapOptions = choice.getMapOptions();
			List<Option> optionList = new ArrayList<Option>(mapOptions.values());
			optionBo.create(choice.getChoiceId(), optionList);
			dao.insert(choice.getChoiceId(), questionId, choice.getChoiceText());
		}
		
	}
	public List<Choice> getChoiceByQuestion(int questionId){
		List<Choice> choiceList = new ArrayList<Choice>();
		Cursor cursor = dao.fetchByQuestionId(questionId);
		while(cursor.moveToNext()){
			int id = cursor.getInt(cursor.getColumnIndex(ChoiceDao.COLUMN_ID));
			String choiceText = cursor.getString(cursor.getColumnIndex(ChoiceDao.COLUMN_NAME));
			Choice choice = new Choice(id, choiceText);
			choiceList.add(choice);
		}
		cursor.close();
		return choiceList;
	}
	public Choice getChoice(int selectedChoiceId) {
		// TODO Auto-generated method stub
		Choice choice = null;
		Cursor cursor = dao.fetchById(selectedChoiceId);
		if(cursor.moveToNext()){
			int id = cursor.getInt(cursor.getColumnIndex(ChoiceDao.COLUMN_ID));
			String choiceText = cursor.getString(cursor.getColumnIndex(ChoiceDao.COLUMN_NAME));
			choice = new Choice(id, choiceText);
		}
		cursor.close();
		return choice;
	}

}
