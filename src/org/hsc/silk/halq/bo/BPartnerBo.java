package org.hsc.silk.halq.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hsc.silk.dao.BPartnerDao;
import org.hsc.silk.dao.PaperDao;
import org.hsc.silk.dao.PaperQuestionDao;
import org.hsc.silk.db.DbHelper;
import org.hsc.silk.model.BPartner;
import org.hsc.silk.model.ListItem;
import org.hsc.silk.model.Paper;
import org.hsc.silk.model.Question;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BPartnerBo {
	private BPartnerDao dao;
	
	public BPartnerBo(SQLiteDatabase db) {
		super();
		dao = new BPartnerDao(db);
		
	}
	
	public void save(BPartner bp){
		
		int bpId = bp.getBpartnerId();
		String name = bp.getName();
		String office = bp.getOffice();
		String factory = bp.getFactory();
		int numOfEmp = bp.getNumOfEmployee();
		int numOfMus = bp.getNumOfMuslim();
		int numOfFore = bp.getNumOfForeigner();
		
		if(dao.exist(bpId)){
			dao.update(bpId, name, office, factory, numOfEmp, numOfMus, numOfFore);
		}else{
			dao.insert(bpId, name, office, factory, numOfEmp, numOfMus, numOfFore);
		}
		


	}
	public void save(List<BPartner> bpList){
		for (BPartner bp : bpList) {
			save(bp);
		}
	}
	
	public List<BPartner> list(){
		List<BPartner> bpList = new ArrayList<BPartner>();
		Cursor cursor = dao.fetchAll();
		while(cursor.moveToNext()){
			int bpartnerId = cursor.getInt(cursor.getColumnIndex(BPartnerDao.COLUMN_ID));
			String name = cursor.getString(cursor.getColumnIndex(BPartnerDao.COLUMN_NAME));
			String office = cursor.getString(cursor.getColumnIndex(BPartnerDao.COLUMN_ADDRESS_OFFICE));
			String factory = cursor.getString(cursor.getColumnIndex(BPartnerDao.COLUMN_ADDRESS_FACTORY));
			int numOfEmployee = cursor.getInt(cursor.getColumnIndex(BPartnerDao.COLUMN_NUM_OF_EMPLOYEE));
			int numOfMuslim = cursor.getInt(cursor.getColumnIndex(BPartnerDao.COLUMN_NUM_OF_MUSLIM));
			int numOfForeigner = cursor.getInt(cursor.getColumnIndex(BPartnerDao.COLUMN_NUM_OF_FOREIGNER));
			BPartner bp = new BPartner(bpartnerId, name, office, factory, numOfEmployee, numOfMuslim, numOfForeigner);
			bpList.add(bp);
		}
		cursor.close();
		return bpList;
	}
	public BPartnerDao getDao() {
		return dao;
	}

	public void setDao(BPartnerDao dao) {
		this.dao = dao;
	}
	
	public BPartner getBPartner(int bpId) {
		// TODO Auto-generated method stub
		BPartner bp = null;
		Cursor cursor = dao.fetchById(bpId);
		if(cursor.moveToNext()){
			int bpartnerId = cursor.getInt(cursor.getColumnIndex(BPartnerDao.COLUMN_ID));
			String name = cursor.getString(cursor.getColumnIndex(BPartnerDao.COLUMN_NAME));
			String office = cursor.getString(cursor.getColumnIndex(BPartnerDao.COLUMN_ADDRESS_OFFICE));
			String factory = cursor.getString(cursor.getColumnIndex(BPartnerDao.COLUMN_ADDRESS_FACTORY));
			int numOfEmployee = cursor.getInt(cursor.getColumnIndex(BPartnerDao.COLUMN_NUM_OF_EMPLOYEE));
			int numOfMuslim = cursor.getInt(cursor.getColumnIndex(BPartnerDao.COLUMN_NUM_OF_MUSLIM));
			int numOfForeigner = cursor.getInt(cursor.getColumnIndex(BPartnerDao.COLUMN_NUM_OF_FOREIGNER));
			bp = new BPartner(bpartnerId, name, office, factory, numOfEmployee, numOfMuslim, numOfForeigner);
		}
		cursor.close();
		return bp;
	}
	public void create(BPartner bp){
		int bpId = bp.getBpartnerId();
		String name = bp.getName();
		String office = bp.getOffice();
		String factory = bp.getFactory();
		int numOfEmp = bp.getNumOfEmployee();
		int numOfMus = bp.getNumOfMuslim();
		int numOfFore = bp.getNumOfForeigner();
		dao.insert(bpId, name, office, factory, numOfEmp, numOfMus, numOfFore);
	}
	public void create(List<BPartner> bpList) {
		// TODO Auto-generated method stub
		for (BPartner bp : bpList) {
			create(bp);
		}
	}

}
