package org.silk.checklist.dao;

import java.util.List;



import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AnswersheetAuditorDao {
	public String tag  = this.getClass().getName();
//	private DbHelper dbHelper;
	private SQLiteDatabase db;
	
	public AnswersheetAuditorDao(SQLiteDatabase db){
//		this.db = App.getDatabase();
		this.db = db;
	}
//	public AnswersheetAuditorDao(DbHelper dbHelper){
//		this.dbHelper = dbHelper;
//	}
//	public void open(){
//		db = dbHelper.getWritableDatabase();
//	}
//	public void close(){
//		dbHelper.close();
//	}
	public void delete(int answersheetId){
		int rows = db.delete(TABLE_NAME, COLUMN_ANSWERSHEET_ID + " = " + answersheetId, null);
		Log.i(tag, "Delete Answersheet_Auditor (" + rows + " rows)");
	}
	public void insert(int answersheetId, List<Integer> auditorIds) {
		// TODO Auto-generated method stub
		for (Integer auditorId : auditorIds) {
			insert(answersheetId, auditorId);
		}
	}
	public void insert(int answersheetId, int auditorId){
		ContentValues values = new ContentValues();
		values.put(COLUMN_ANSWERSHEET_ID, answersheetId);
		values.put(COLUMN_AUDITOR_ID, auditorId);
		db.insert(TABLE_NAME, null, values);
		Log.i(tag, "Insert Answersheet_Auditor : " + values);
	}
	public void update(int answersheetId, int auditorId){
		ContentValues values = new ContentValues();
		values.put(COLUMN_ANSWERSHEET_ID, answersheetId);
		values.put(COLUMN_AUDITOR_ID, auditorId);
		db.update(TABLE_NAME, values, COLUMN_ANSWERSHEET_ID + " = " + answersheetId, null);
		Log.i(tag, "Update Answersheet_Auditor : " + values);
	}
	public Cursor fetchAll(){
		return db.query(TABLE_NAME, COLUMNS, null, null, null, null, null);
	}
	public Cursor fetchById(int answersheetId){
		return db.query(TABLE_NAME, COLUMNS, COLUMN_ANSWERSHEET_ID + " = " + answersheetId, null, null, null, null);

	}
	public static String TABLE_NAME = "ANSWERSHEET_AUDITOR";
	public static String COLUMN_ANSWERSHEET_ID = "ANSWERSHEET_ID";
	public static String COLUMN_AUDITOR_ID = "AUDITOR_ID";
	public static String[] COLUMNS = { COLUMN_ANSWERSHEET_ID, COLUMN_AUDITOR_ID };

	public static String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME + "("
			+ COLUMN_ANSWERSHEET_ID + " INTEGER NOT NULL , "
			+ COLUMN_AUDITOR_ID + " INTEGER NOT NULL)";
	public static String DROP_TABLE = "DROP TABLE IF EXISTS "+ TABLE_NAME; 

	public void createTable(){
		Log.i(tag, "Create table " + TABLE_NAME);
		db.execSQL(CREATE_TABLE);
	}
	public void dropTable(){
		Log.i(tag, "Drop table " + TABLE_NAME);
		db.execSQL(DROP_TABLE);
	}
	public boolean exist(int id){
		Cursor cursor = fetchById(id);
		boolean ret = cursor.moveToFirst();
		cursor.close();
		return ret;
	}
	
	public Cursor fetchByIds(List<Integer> answersheetIds) {
		// TODO Auto-generated method stub
		StringBuilder args = new StringBuilder();
		for (int i = 0; i < answersheetIds.size(); i++) {
			args.append(answersheetIds.get(i));
			if(i != answersheetIds.size() -1)
				args.append(",");
		}
		String selection = String.format(COLUMN_ANSWERSHEET_ID+" in ( %s )" , args.toString());
		return db.query(TABLE_NAME, COLUMNS, selection, null, null, null, null);
	}

	
}
