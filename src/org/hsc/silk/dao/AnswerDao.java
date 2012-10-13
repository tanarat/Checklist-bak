package org.hsc.silk.dao;

import java.util.List;



import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



public class AnswerDao {
	public String tag  = this.getClass().getName();
//	private DbHelper dbHelper;
	private SQLiteDatabase db;

	public static String TABLE_NAME = "ANSWER";
	public static String COLUMN_ID = "ID";
	public static String COLUMN_ANSWERSHEET_ID = "ANSWERSHEET_ID";
	public static String COLUMN_QUESTION_ID = "QUESTION_ID";
	public static String COLUMN_REMARK = "REMARK";
	
	public static String[] COLUMNS = { COLUMN_ID, COLUMN_ANSWERSHEET_ID, COLUMN_QUESTION_ID,
		COLUMN_REMARK };
	
	public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_ANSWERSHEET_ID + " INTEGER NOT NULL, " 
			+ COLUMN_QUESTION_ID + " INTEGER NOT NULL, " 
			+ COLUMN_REMARK + " TEXT)";
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
//	public AnswerDao(DbHelper dbHelper) {
//		super();
//		this.dbHelper = dbHelper;
//	}
	public AnswerDao(SQLiteDatabase db) {
		super();
//		this.db = App.getDatabase();
		this.db = db;
	}
	public int insert(int answersheetId, int questionId,String remark){
		ContentValues values = new ContentValues();
		values.put(COLUMN_ANSWERSHEET_ID, answersheetId);
		values.put(COLUMN_QUESTION_ID, questionId);
		values.put(COLUMN_REMARK, remark);
		int id = (int)db.insert(TABLE_NAME, null, values);
		Log.i(tag, "Insert Answer : " + values);
		return id;
	}
	public void update(int answerId, int answersheetId, int questionId, String remark){
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, answerId);
		values.put(COLUMN_ANSWERSHEET_ID, answersheetId);
		values.put(COLUMN_QUESTION_ID, questionId);
		values.put(COLUMN_REMARK, remark);
		db.update(TABLE_NAME, values, COLUMN_ID + " = " + answerId, null);
		Log.i(tag, "Update Answer : " + values);
	}
	public Cursor fetchAll(){
		return db.query(TABLE_NAME, COLUMNS, null, null, null, null, null);
	}
	public Cursor fetchById(int id){
		return db.query(TABLE_NAME, COLUMNS, COLUMN_ID + " = " + id, null, null, null, null);

	}
	public Cursor fetchByIds(List<Integer> answerIds) {
		// TODO Auto-generated method stub
		StringBuilder args = new StringBuilder();
		for (int i = 0; i < answerIds.size(); i++) {
			args.append(answerIds.get(i));
			if(i != answerIds.size() -1)
				args.append(",");
		}
		String selection = String.format(COLUMN_ID+" in ( %s )" , args.toString());
		return db.query(TABLE_NAME, COLUMNS, selection, null, null, null, null);
	}
	
	public boolean exist(int id){
		Cursor cursor = fetchById(id);
		boolean ret = cursor.moveToFirst();
		cursor.close();
		return ret;
	}
	public Cursor fetchByAnswersheetId(int answersheetId) {
		// TODO Auto-generated method stub
		return db.query(TABLE_NAME, COLUMNS, COLUMN_ANSWERSHEET_ID + " = " + answersheetId, null, null, null, null);
	}
	
}
