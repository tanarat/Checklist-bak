package org.silk.checklist.dao;


import java.util.List;



import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



public class AnswerChoiceOptionDao {
	public String tag  = this.getClass().getName();
//	private DbHelper dbHelper;
	private SQLiteDatabase db;

	public static String TABLE_NAME = "ANSWER_CHOICE_OPTION";
	public static String COLUMN_ID = "ID";
	public static String COLUMN_ANSWER_CHOICE_ID = "ANSWER_CHOICE_ID";
	public static String COLUMN_OPTION_ID = "OPTION_ID";
	
	public static String[] COLUMNS = {	COLUMN_ID
		, COLUMN_ANSWER_CHOICE_ID
		, COLUMN_OPTION_ID};
	
	public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"(" +
			COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + // Id
			COLUMN_ANSWER_CHOICE_ID + " INTEGER NOT NULL , " +    // AnswerChoiceId
			COLUMN_OPTION_ID + " INTEGER NOT NULL , " +			  // OptionId
			"FOREIGN KEY("+ COLUMN_ANSWER_CHOICE_ID +") REFERENCES "+ AnswerChoiceDao.TABLE_NAME +"("+ AnswerChoiceDao.COLUMN_ID +") , " +
			"FOREIGN KEY("+ COLUMN_OPTION_ID +") REFERENCES "+ OptionDao.TABLE_NAME +"("+ OptionDao.COLUMN_ID +")" +
					")";	  
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
//	public AnswerChoiceOptionDao(DbHelper dbHelper) {
//		super();
//		this.dbHelper = dbHelper;
//	}
	public AnswerChoiceOptionDao(SQLiteDatabase db) {
		super();
//		this.db = App.getDatabase();
		this.db = db;
	}
	public void insert(int answerChoiceId, List<Integer> optionIds) {
		// TODO Auto-generated method stub
		for (Integer optionId : optionIds) {
			insert(answerChoiceId, optionId);
		}
	}
	public void insert(int answerChoiceId, int optionId){
		ContentValues values = new ContentValues();
		values.put(COLUMN_ANSWER_CHOICE_ID, answerChoiceId);
		values.put(COLUMN_OPTION_ID, optionId);
		db.insert(TABLE_NAME, null, values);
		Log.i(tag, "Insert AnswerChoiceOption : " + values);
	}

	public void update(int answerChoiceOptionId, int answerChoiceId, int optionId){
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, answerChoiceOptionId);
		values.put(COLUMN_ANSWER_CHOICE_ID, answerChoiceId);
		values.put(COLUMN_OPTION_ID, optionId);
		db.update(TABLE_NAME, values, COLUMN_ID + " = " + answerChoiceId, null);
		Log.i(tag, "Update AnswerChoiceOption : " + values);
	}
	//delete record for AnswerChoice
	public void delete(int answerChoiceId){
		int rows = db.delete(TABLE_NAME, COLUMN_ANSWER_CHOICE_ID + " = " + answerChoiceId, null);
		Log.i(tag, "Delete AnswerChoiceOption for AnswerChoice : " + answerChoiceId + " (" + rows + " rows)");
	}
	public Cursor fetchAll(){
		return db.query(TABLE_NAME, COLUMNS, null, null, null, null, null);
	}
	//fetch by answerChoiceOptionId
	public Cursor fetchById(int answerChoiceOptionId){
		return db.query(TABLE_NAME, COLUMNS, COLUMN_ID + " = " + answerChoiceOptionId, null, null, null, null);
	}
	//fetch by answerChoiceId
	public Cursor fetchByAnswerId(int answerChoiceId){
		return db.query(TABLE_NAME, COLUMNS, COLUMN_ANSWER_CHOICE_ID + " = " + answerChoiceId, null, null, null, null);
	}
	
	public boolean exist(int answerChoiceOptionId){
		Cursor cursor = fetchById(answerChoiceOptionId);
		boolean ret = cursor.moveToFirst();
		cursor.close();
		return ret;
	}
	
}
