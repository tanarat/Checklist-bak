package org.silk.checklist.dao;




import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



public class OptionDao {
	public String tag  = this.getClass().getName();
//	private DbHelper dbHelper;
	private SQLiteDatabase db;

	public static String TABLE_NAME = "OPTION";
	public static String COLUMN_ID = "OPTION_ID";
	public static String COLUMN_CHOICE_ID = "CHOICE_ID";
	public static String COLUMN_NAME = "OPTION";
	
	public static String[] COLUMNS = {COLUMN_ID, COLUMN_CHOICE_ID, COLUMN_NAME};
	
	public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"(" +
			COLUMNS[0] + " INTEGER PRIMARY KEY , " + // OptionId
		   	COLUMNS[1] + " INTEGER NOT NULL , " +   // ChoiceId
			COLUMNS[2] + " TEXT NOT NULL)";			// OptionText
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
//	public OptionDao(DbHelper dbHelper) {
//		super();
//		this.dbHelper = dbHelper;
//	}
	public OptionDao(SQLiteDatabase db) {
		super();
//		this.db = App.getDatabase();
		this.db = db;
	}
	public void insert(int optionId, int choiceId, String optionText){
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, optionId);
		values.put(COLUMN_CHOICE_ID, choiceId);
		values.put(COLUMN_NAME, optionText);
		db.insert(TABLE_NAME, null, values);
		Log.i(tag, "Insert Option : " + values);
	}

	public void update(int optionId, int choiceId, String optionText){
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, optionId);
		values.put(COLUMN_CHOICE_ID, choiceId);
		values.put(COLUMN_NAME, optionText);
		db.update(TABLE_NAME, values, COLUMN_ID + " = " + optionId, null);
		Log.i(tag, "Update Option : " + values);
	}
	public void delete(int choiceId){
		int rows = db.delete(TABLE_NAME, COLUMN_CHOICE_ID + " = " + choiceId, null);
		Log.i(tag, "Delete Option for Choice : " + choiceId + " (" + rows + " rows)");
	}
	public Cursor fetchAll(){
		return db.query(TABLE_NAME, COLUMNS, null, null, null, null, null);
	}
	//fetch by optionId
	public Cursor fetchById(int optionId){
		return db.query(TABLE_NAME, COLUMNS, COLUMN_ID + " = " + optionId, null, null, null, null);

	}
	//fetch by choiceId
	public Cursor fetchByChoiceId(int choiceId){
		return db.query(TABLE_NAME, COLUMNS, COLUMN_CHOICE_ID + " = " + choiceId, null, null, null, null);

	}
	
	public boolean exist(int optionId){
		Cursor cursor = fetchById(optionId);
		boolean ret = cursor.moveToFirst();
		cursor.close();
		return ret;
	}
}
