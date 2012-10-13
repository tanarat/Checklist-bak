package org.hsc.silk.dao;




import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



public class ChoiceDao {
	public String tag  = this.getClass().getName();
//	private DbHelper dbHelper;
	private SQLiteDatabase db;

	public static String TABLE_NAME = "CHOICE";
	public static String COLUMN_ID = "ID";
	public static String COLUMN_QUESTION_ID = "QUESTION_ID";
	public static String COLUMN_NAME = "CHOICE";
	
	public static String[] COLUMNS = {COLUMN_ID, COLUMN_QUESTION_ID, COLUMN_NAME};
	
	public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"(" +
			COLUMNS[0] + " INTEGER PRIMARY KEY , " + // ChoiceId
		   	COLUMNS[1] + " INTEGER NOT NULL , " +   // QuestionId
			COLUMNS[2] + " TEXT NOT NULL)";			// ChoiceText
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
//	public ChoiceDao(DbHelper dbHelper) {
//		super();
//		this.dbHelper = dbHelper;
//	}
	public ChoiceDao(SQLiteDatabase db) {
		super();
//		this.db = App.getDatabase();
		this.db = db;
	}
	public void insert(int choiceId, int questionId, String choiceText){
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, choiceId);
		values.put(COLUMN_QUESTION_ID, questionId);
		values.put(COLUMN_NAME, choiceText);
		db.insert(TABLE_NAME, null, values);
		Log.i(tag, "Insert Choice : " + values);
	}

	public void update(int choiceId, int questionId, String choiceText){
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, choiceId);
		values.put(COLUMN_QUESTION_ID, questionId);
		values.put(COLUMN_NAME, choiceText);
		db.update(TABLE_NAME, values, COLUMN_ID + " = " + choiceId, null);
		Log.i(tag, "Update Choice : " + values);
	}
	public void delete(int questionId){
		int rows = db.delete(TABLE_NAME, COLUMN_QUESTION_ID + " = " + questionId, null);
		Log.i(tag, "Delete Choice for Question : " + questionId + " (" + rows + " rows)");
	}
	public Cursor fetchAll(){
		return db.query(TABLE_NAME, COLUMNS, null, null, null, null, null);
	}
	//fetch by choiceId
	public Cursor fetchById(int id){
		return db.query(TABLE_NAME, COLUMNS, COLUMN_ID + " = " + id, null, null, null, null);

	}
	//fetch by questionId
	public Cursor fetchByQuestionId(int questionId){
		return db.query(TABLE_NAME, COLUMNS, COLUMN_QUESTION_ID + " = " + questionId, null, null, null, null);

	}
	
	public boolean exist(int choiceId){
		Cursor cursor = fetchById(choiceId);
		boolean ret = cursor.moveToFirst();
		cursor.close();
		return ret;
	}
}
