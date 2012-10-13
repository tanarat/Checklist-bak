package org.silk.checklist.db;

import org.silk.checklist.dao.AnswerChoiceDao;
import org.silk.checklist.dao.AnswerChoiceOptionDao;
import org.silk.checklist.dao.AnswerDao;
import org.silk.checklist.dao.AnswersheetAttendanceDao;
import org.silk.checklist.dao.AnswersheetAuditorDao;
import org.silk.checklist.dao.AnswersheetDao;
import org.silk.checklist.dao.AuditorDao;
import org.silk.checklist.dao.BPartnerDao;
import org.silk.checklist.dao.ChoiceDao;
import org.silk.checklist.dao.OptionDao;
import org.silk.checklist.dao.PaperDao;
import org.silk.checklist.dao.PaperQuestionDao;
import org.silk.checklist.dao.QuestionDao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
	public String tag = this.getClass().getName();
	static String DATABASE_NAME = "halaldb";
	public static int DATABASE_VERSION = 1;
	private boolean needUpdate = false;
	
	static String DATABASE_CREATE = "";

	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	public boolean isNeedUpdate(){
		return needUpdate;
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.i(tag, "Create Database " + DATABASE_NAME);
		Log.i(tag, AuditorDao.CREATE_TABLE);
		db.execSQL(AuditorDao.CREATE_TABLE);
		Log.i(tag, PaperDao.CREATE_TABLE);
		db.execSQL(PaperDao.CREATE_TABLE);
		Log.i(tag, QuestionDao.CREATE_TABLE);
		db.execSQL(QuestionDao.CREATE_TABLE);
		Log.i(tag, PaperQuestionDao.CREATE_TABLE);
		db.execSQL(PaperQuestionDao.CREATE_TABLE);
		Log.i(tag, ChoiceDao.CREATE_TABLE);
		db.execSQL(ChoiceDao.CREATE_TABLE);
		Log.i(tag, OptionDao.CREATE_TABLE);
		db.execSQL(OptionDao.CREATE_TABLE);
		
		Log.i(tag, AnswersheetDao.CREATE_TABLE);
		db.execSQL(AnswersheetDao.CREATE_TABLE);
		Log.i(tag, AnswerDao.CREATE_TABLE);
		db.execSQL(AnswerDao.CREATE_TABLE);
		Log.i(tag, AnswerChoiceDao.CREATE_TABLE);
		db.execSQL(AnswerChoiceDao.CREATE_TABLE);
		Log.i(tag, AnswerChoiceOptionDao.CREATE_TABLE);
		db.execSQL(AnswerChoiceOptionDao.CREATE_TABLE);
		
		Log.i(tag, BPartnerDao.CREATE_TABLE);
		db.execSQL(BPartnerDao.CREATE_TABLE);
		
		Log.i(tag, AnswersheetAuditorDao.CREATE_TABLE);
		db.execSQL(AnswersheetAuditorDao.CREATE_TABLE);
		
		Log.i(tag, AnswersheetAttendanceDao.CREATE_TABLE);
		db.execSQL(AnswersheetAttendanceDao.CREATE_TABLE);
		needUpdate = true;
	}
	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(tag, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL(AuditorDao.DROP_TABLE);
        db.execSQL(PaperDao.DROP_TABLE);
        db.execSQL(QuestionDao.DROP_TABLE);
        db.execSQL(PaperQuestionDao.DROP_TABLE);
        db.execSQL(ChoiceDao.DROP_TABLE);
        db.execSQL(OptionDao.DROP_TABLE);
        db.execSQL(AnswersheetDao.DROP_TABLE);
        db.execSQL(AnswerDao.DROP_TABLE);
        db.execSQL(AnswerChoiceDao.DROP_TABLE);
        db.execSQL(AnswerChoiceOptionDao.DROP_TABLE);
        db.execSQL(BPartnerDao.DROP_TABLE);
        db.execSQL(AnswersheetAuditorDao.DROP_TABLE);
        db.execSQL(AnswersheetAttendanceDao.DROP_TABLE);
        
        onCreate(db);
	}

}
