package org.hsc.silk.halq.bo;

import java.util.ArrayList;
import java.util.List;

import org.hsc.silk.dao.AuditorDao;
import org.hsc.silk.dao.QuestionDao;
import org.hsc.silk.db.DbHelper;
import org.hsc.silk.model.Auditor;
import org.hsc.silk.model.ListItem;
import org.hsc.silk.model.Paper;
import org.hsc.silk.model.Question;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AuditorBo {
	private AuditorDao dao;
	public AuditorBo(SQLiteDatabase db) {
		super();
		dao = new AuditorDao(db);
	}
	
	public void save(Auditor auditor){
		
		int id = auditor.getAuditorId();
		String name = auditor.getName();
		
		if(dao.exist(id)){
			dao.update(id,name);
		}else{
			dao.insert(id, name);
		}

	}
	public void save(List<Auditor> list){
		for (Auditor auditor : list) {
			save(auditor);
		}
	}
	public AuditorDao getDao() {
		return dao;
	}
	public void setDao(AuditorDao dao) {
		this.dao = dao;
	}
	public void loadSampleData(){
		List<Auditor> list = new ArrayList<Auditor>();
		list.add(new Auditor(1, "นายอาณัฐ เด่นยิ่งโยชน์"));
		list.add(new Auditor(2, "นายธรรมนูญ ดีสวัสดิ์"));
		list.add(new Auditor(3, "นางสาววริวรรณ เครืออินทร์"));
		save(list);
	}
	public List<Auditor> list() {
		// TODO Auto-generated method stub
		List<Auditor> auditors = new ArrayList<Auditor>();
		Cursor cursor = dao.fetchAll();
		while(cursor.moveToNext()){
			int auditorId = cursor.getInt(cursor.getColumnIndex(AuditorDao.COLUMN_ID));
			String name = cursor.getString(cursor.getColumnIndex(AuditorDao.COLUMN_NAME));
			Auditor auditor = new Auditor(auditorId, name);
			auditors.add(auditor);
		}
		cursor.close();
		return auditors;
	}
	public List<Auditor> list(List<Integer> auditorIds) {
		// TODO Auto-generated method stub
		
		List<Auditor> auditors = new ArrayList<Auditor>();
		Cursor cursor = dao.fetchByIds(auditorIds);
		while(cursor.moveToNext()){
			int auditorId = cursor.getInt(cursor.getColumnIndex(AuditorDao.COLUMN_ID));
			String name = cursor.getString(cursor.getColumnIndex(AuditorDao.COLUMN_NAME));
			Auditor auditor = new Auditor(auditorId, name);
			auditors.add(auditor);
		}
		cursor.close();
		return auditors;
	}
	

}
