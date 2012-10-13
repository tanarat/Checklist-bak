package org.hsc.silk.halq.bo;

import java.util.ArrayList;
import java.util.List;

import org.hsc.silk.dao.ChoiceDao;
import org.hsc.silk.dao.OptionDao;
import org.hsc.silk.db.DbHelper;
import org.hsc.silk.model.Choice;
import org.hsc.silk.model.Option;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class OptionBo {
	private OptionDao dao;
	public OptionBo(SQLiteDatabase db) {
		super();
		dao = new OptionDao(db);
	}
	
	public void save(Option option){
		int optionId = option.getOptionId();
		int choiceId = option.getChoiceId();
		String optionText = option.getOptionText();
		if(dao.exist(optionId)){
			dao.update(optionId, choiceId, optionText);
		}else{
			dao.insert(optionId, choiceId, optionText);
		}
	}
	public void save(List<Option> list){
		for (Option option : list) {
			save(option);
		}
	}

	public OptionDao getDao() {
		return dao;
	}

	public void setDao(OptionDao dao) {
		this.dao = dao;
	}
	public void create(int choiceId, List<Option> optionList) {
		// TODO Auto-generated method stub
		for (Option option : optionList) {
			dao.insert(option.getOptionId(), choiceId, option.getOptionText());
		}
	}
	public List<Option> getOptionByChoice(int choiceId) {
		
		List<Option> optionList = new ArrayList<Option>();
		Cursor cursor = dao.fetchByChoiceId(choiceId);
		while(cursor.moveToNext()){
			int id = cursor.getInt(cursor.getColumnIndex(OptionDao.COLUMN_ID));
			String optionText = cursor.getString(cursor.getColumnIndex(OptionDao.COLUMN_NAME));
			Option option = new Option(id, optionText);
			optionList.add(option);
		}
		cursor.close();
		return optionList;
	}
	

}
