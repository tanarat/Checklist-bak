package org.hsc.silk.halq.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hsc.silk.dao.PaperDao;
import org.hsc.silk.dao.PaperQuestionDao;
import org.hsc.silk.db.DbHelper;
import org.hsc.silk.model.ListItem;
import org.hsc.silk.model.Paper;
import org.hsc.silk.model.Question;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PaperBo {
	private PaperDao dao;
	private PaperQuestionDao paperQuestionDao;
	public PaperBo(SQLiteDatabase db) {
		super();
//		dao = new PaperDao(dbHelper);
		dao = new PaperDao(db);
		paperQuestionDao = new PaperQuestionDao(db);
	}
	
	public void save(Paper paper){
		
		int paperId = paper.getPaperId();
		String name = paper.getName();
		List<Integer> questionIds = new ArrayList<Integer>(paper.getQuestionsSet());
		
		if(dao.exist(paper.getPaperId())){
			dao.update(paperId,name);
		}else{
			dao.insert(paperId, name);
		}
		
		paperQuestionDao.delete(paperId);
		paperQuestionDao.insert(paperId, questionIds);

	}
	public void save(List<Paper> paperList){
		for (Paper paper : paperList) {
			save(paper);
		}
	}
	
	public List<Paper> list(){
		List<Paper> papers = new ArrayList<Paper>();
		Cursor cursor = dao.fetchAll();
		while(cursor.moveToNext()){
			Paper paper = new Paper(cursor.getInt(0),cursor.getString(1));
			papers.add(paper);
		}
		cursor.close();
		return papers;
	}
	public PaperDao getDao() {
		return dao;
	}

	public void setDao(PaperDao dao) {
		this.dao = dao;
	}
	public void loadSampleData(){
		List<Paper> paperList = new ArrayList<Paper>();
		paperList.add(new Paper(1, "แบบประเมิน HAL-Q [มาตรฐาน]"));
		paperList.add(new Paper(2, "แบบประเมิน HAL-Q [โรงงาน]"));
		paperList.add(new Paper(3, "แบบประเมิน HAL-Q [โรงเชือด]"));
		paperList.add(new Paper(4, "แบบประเมิน HALAL [มาตรฐาน]"));
		paperList.add(new Paper(5, "แบบประเมิน HALAL [โรงงาน]"));
		save(paperList);
	}
	public Paper getPaper(int paperId) {
		// TODO Auto-generated method stub
		Paper paper = null;
		Cursor cursor = dao.fetchById(paperId);
		if(cursor.moveToNext()){
			int id = cursor.getInt(cursor.getColumnIndex(PaperDao.COLUMN_ID));
			String name = cursor.getString(cursor.getColumnIndex(PaperDao.COLUMN_NAME));
			paper = new Paper(id, name);
		}
		cursor.close();
		return paper;
	}
	public void create(Paper paper){
		List<Integer> questionIds = new ArrayList<Integer>(paper.getQuestionsSet());
		paperQuestionDao.insert(paper.getPaperId(), questionIds);
		dao.insert(paper.getPaperId(), paper.getName());
	}
	public void create(List<Paper> papers) {
		// TODO Auto-generated method stub
		for (Paper paper : papers) {
			create(paper);
		}
	}

}
