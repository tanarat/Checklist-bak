package org.hsc.silk.dao;

import java.util.Date;
import java.util.List;



import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



public class AnswersheetDao {
	public String tag  = this.getClass().getName();
//	private DbHelper dbHelper;
	private SQLiteDatabase db;

	public static String TABLE_NAME = "ANSWERSHEET";
	public static String COLUMN_ID = "ID"; //AnswersheetId
	public static String COLUMN_NAME = "NAME"; //AnswersheetId
	public static String COLUMN_PAPER_ID = "PAPER_ID";
	public static String COLUMN_BP_ID = "BP_ID";
	public static String COLUMN_DATE = "DATE";
	public static String COLUMN_START = "START";
	public static String COLUMN_FINISH = "FINISH";
	
	public static String[] COLUMNS = { 	COLUMN_ID, 
										COLUMN_NAME,
										COLUMN_PAPER_ID, 
										COLUMN_BP_ID,
										COLUMN_DATE,
										COLUMN_START,
										COLUMN_FINISH };
	
	public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_NAME + " TEXT NOT NULL, "
			+ COLUMN_PAPER_ID + " INTEGER NOT NULL, " 
			+ COLUMN_BP_ID + " INTEGER NOT NULL, " 
			+ COLUMN_DATE + " TEXT, "
			+ COLUMN_START + " TEXT, "
			+ COLUMN_FINISH + " TEXT "
				+	")";
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
//	public AnswersheetDao(DbHelper dbHelper) {
//		super();
//		this.dbHelper = dbHelper;
//	}
	public AnswersheetDao(SQLiteDatabase db) {
		super();
//		this.db = App.getDatabase();
		this.db = db;
	}
	public int insert(String name, int paperId, int bpId, Date date, Date start, Date finish){
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, name);
		values.put(COLUMN_PAPER_ID, paperId);
		values.put(COLUMN_BP_ID, bpId);
		values.put(COLUMN_DATE, date.toString());
		values.put(COLUMN_START, start.toString());
		values.put(COLUMN_FINISH, finish.toString());
		int id = (int)db.insert(TABLE_NAME, null, values);
		Log.i(tag, "Insert Answersheet : " + values);
		return id;
	}
	public void update(int answersheetId, String name, int paperId, int bpId, Date date, Date start, Date finish){
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, answersheetId);
		values.put(COLUMN_NAME, name);
		values.put(COLUMN_PAPER_ID, paperId);
		values.put(COLUMN_BP_ID, bpId);
		values.put(COLUMN_DATE, date.toString());
		values.put(COLUMN_START, start.toString());
		values.put(COLUMN_FINISH, finish.toString());
		db.update(TABLE_NAME, values, COLUMN_ID + " = " + answersheetId, null);
		Log.i(tag, "Update Answersheet : " + values);
	}
	public Cursor fetchAll(){
		return db.query(TABLE_NAME, COLUMNS, null, null, null, null, null);
	}
	public Cursor fetchById(int id){
		return db.query(TABLE_NAME, COLUMNS, COLUMN_ID + " = " + id, null, null, null, null);

	}
	public Cursor fetchByIds(List<Integer> answersheetIds) {
		// TODO Auto-generated method stub
		StringBuilder args = new StringBuilder();
		for (int i = 0; i < answersheetIds.size(); i++) {
			args.append(answersheetIds.get(i));
			if(i != answersheetIds.size() -1)
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
	
}
