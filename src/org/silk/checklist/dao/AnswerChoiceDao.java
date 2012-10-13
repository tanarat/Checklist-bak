package org.silk.checklist.dao;




import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



public class AnswerChoiceDao {
	public String tag  = this.getClass().getName();
//	private DbHelper dbHelper;
	private SQLiteDatabase db;

	public static String TABLE_NAME = "ANSWER_CHOICE";
	public static String COLUMN_ID = "ID";
	public static String COLUMN_ANSWER_ID = "ANSWER_ID";
	public static String COLUMN_CHOICE_ID = "CHOICE_ID";
	
	public static String[] COLUMNS = {	COLUMN_ID
		, COLUMN_ANSWER_ID
		, COLUMN_CHOICE_ID};
	
	public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"(" +
			COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + // Id
		   	COLUMN_ANSWER_ID + " INTEGER NOT NULL , " +   // AnswerId
			COLUMN_CHOICE_ID + " INTEGER NOT NULL , " +   // ChoiceId
			"FOREIGN KEY("+ COLUMN_ANSWER_ID +") REFERENCES "+ AnswerDao.TABLE_NAME +"("+ AnswerDao.COLUMN_ID +") , " +
			"FOREIGN KEY("+ COLUMN_CHOICE_ID +") REFERENCES "+ ChoiceDao.TABLE_NAME +"("+ ChoiceDao.COLUMN_ID +")" +
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
//	public AnswerChoiceDao(DbHelper dbHelper) {
//		super();
//		this.dbHelper = dbHelper;
//	}
	public AnswerChoiceDao(SQLiteDatabase db){
//		db = App.getDatabase();
		this.db = db;
	}
	public int insert(int answerId, int choiceId){
		ContentValues values = new ContentValues();
		values.put(COLUMN_ANSWER_ID, answerId);
		values.put(COLUMN_CHOICE_ID, choiceId);
		int id = (int)db.insert(TABLE_NAME, null, values);
		Log.i(tag, "Insert AnswerChoice : " + values);
		return id;
	}

	public void update(int answerChoiceId, int answerId, int choiceId){
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, answerChoiceId);
		values.put(COLUMN_ANSWER_ID, answerId);
		values.put(COLUMN_CHOICE_ID, choiceId);
		db.update(TABLE_NAME, values, COLUMN_ID + " = " + answerChoiceId, null);
		Log.i(tag, "Update AnswerChoice : " + values);
	}
	public void delete(int answerId){
		int rows = db.delete(TABLE_NAME, COLUMN_ANSWER_ID + " = " + answerId, null);
		Log.i(tag, "Delete AnswerChoice for Answer : " + answerId + " (" + rows + " rows)");
	}
	public Cursor fetchAll(){
		return db.query(TABLE_NAME, COLUMNS, null, null, null, null, null);
	}
	//fetch by answerChoiceId
	public Cursor fetchById(int id){
		return db.query(TABLE_NAME, COLUMNS, COLUMN_ID + " = " + id, null, null, null, null);
	}
	//fetch by answer id
	public Cursor fetchByAnswerId(int answerId){
		return db.query(TABLE_NAME, COLUMNS, COLUMN_ANSWER_ID + " = " + answerId, null, null, null, null);
	}
	
	public boolean exist(int answerChoiceId){
		Cursor cursor = fetchById(answerChoiceId);
		boolean ret = cursor.moveToFirst();
		cursor.close();
		return ret;
	}
}
