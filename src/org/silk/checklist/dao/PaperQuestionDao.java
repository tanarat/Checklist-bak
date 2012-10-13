package org.silk.checklist.dao;

import java.util.List;



import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



public class PaperQuestionDao {
	public String tag  = this.getClass().getName();
//	private DbHelper dbHelper;
	private SQLiteDatabase db;

	public static String TABLE_NAME = "PAPER_QUESTION";
	public static String COLUMN_PAPER_ID = "PAPER_ID";
	public static String COLUMN_QUESTION_ID = "QUESTION_ID";
	
	public static String[] COLUMNS = {	COLUMN_PAPER_ID
										, COLUMN_QUESTION_ID};
	
	public static String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME + "("
			+ COLUMN_PAPER_ID + " INTEGER NOT NULL, "  //PaperId
			+ COLUMN_QUESTION_ID + " INTEGER NOT NULL, "  //QuestionId
			+ "FOREIGN KEY("+ COLUMN_PAPER_ID +") REFERENCES "+ PaperDao.TABLE_NAME +"("+ PaperDao.COLUMN_ID +") , "
			+ "FOREIGN KEY("+ COLUMN_QUESTION_ID +") REFERENCES "+ QuestionDao.TABLE_NAME +"("+ QuestionDao.COLUMN_ID +") "
			+ ")";  
	
	public static String DROP_TABLE = "DROP TABLE IF EXISTS "+ TABLE_NAME; 
	public void createTable(){
		Log.i(tag, "Create table " + TABLE_NAME);
		db.execSQL(CREATE_TABLE);
	}
	public void dropTable(){
		Log.i(tag, "Drop table " + TABLE_NAME);
		db.execSQL(DROP_TABLE);
	}
//	public void open(){
//		db = dbHelper.getWritableDatabase();
//	}
//	public void close(){
//		dbHelper.close();
//	}
//	public PaperQuestionDao(DbHelper dbHelper) {
//		super();
//		this.dbHelper = dbHelper;
//	}
	public PaperQuestionDao(SQLiteDatabase db) {
		super();
//		this.db = App.getDatabase();
		this.db = db;
	}
	public void insert(int paperId, List<Integer> questionIds){
		ContentValues values = new ContentValues();
		for (Integer questionId : questionIds) {
			values.put(COLUMN_PAPER_ID, paperId);
			values.put(COLUMN_QUESTION_ID, questionId);
			db.insert(TABLE_NAME, null, values);
			Log.i(tag, "Insert Paper_Question : " + values);
		}
		
	}
	public void delete(int paperId){
		int rows = db.delete(TABLE_NAME, COLUMN_PAPER_ID + " = " + paperId, null);
		Log.i(tag, "Delete Paper_Question for Paper : " + paperId + " (" + rows + " rows)");
	}
	public Cursor fetchByPaperId(int paperId){
		return db.query(TABLE_NAME, COLUMNS, COLUMN_PAPER_ID + " = " + paperId, null, null, null, null);
		
	}
}
