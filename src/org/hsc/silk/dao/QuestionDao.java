package org.hsc.silk.dao;

import java.util.List;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



public class QuestionDao {
	public String tag  = this.getClass().getName();
//	private DbHelper dbHelper;
	private SQLiteDatabase db;

	public static String TABLE_NAME = "QUESTION";
	public static String COLUMN_ID = "ID";
	public static String COLUMN_NAME = "QUESTION";
	public static String COLUMN_GROUP_NAME = "GROUP_NAME";
	
	public static String[] COLUMNS = {COLUMN_ID, COLUMN_NAME, COLUMN_GROUP_NAME};
	
	public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"(" +
			COLUMN_ID + " INTEGER PRIMARY KEY , " +
			COLUMN_NAME + " TEXT NOT NULL , " +
			COLUMN_GROUP_NAME + " TEXT " +
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
//	public QuestionDao(DbHelper dbHelper) {
//		super();
//		this.dbHelper = dbHelper;
//	}
	public QuestionDao(SQLiteDatabase db) {
		super();
//		this.db = App.getDatabase();
		this.db = db;
	}
	public void insert(int id, String name, String groupName){
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, id);
		values.put(COLUMN_NAME, name);
		values.put(COLUMN_GROUP_NAME, groupName);
		db.insert(TABLE_NAME, null, values);
		Log.i(tag, "Insert Question : " + values);
	}
	public void update(int id, String name, String groupName){
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, id);
		values.put(COLUMN_NAME, name);
		values.put(COLUMN_GROUP_NAME, groupName);
		db.update(TABLE_NAME, values, COLUMN_ID + " = " + id, null);
		Log.i(tag, "Update Question : " + values);
	}
	public Cursor fetchAll(){
		return db.query(TABLE_NAME, COLUMNS, null, null, null, null, null);
	}
	public Cursor fetchById(int id){
		return db.query(TABLE_NAME, COLUMNS, COLUMN_ID + " = " + id, null, null, null, null);

	}
	public Cursor fetchByIds(List<Integer> questionIds) {
		// TODO Auto-generated method stub
		StringBuilder args = new StringBuilder();
		for (int i = 0; i < questionIds.size(); i++) {
			args.append(questionIds.get(i));
			if(i != questionIds.size() -1)
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
