package org.hsc.silk.dao;

import java.util.List;



import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AuditorDao {
	public String tag  = this.getClass().getName();
//	private DbHelper dbHelper;
	private SQLiteDatabase db;
	
	public AuditorDao(SQLiteDatabase db){
//		this.db = App.getDatabase();
		this.db = db;
	}	
	public void insert(int id, String name){
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, id);
		values.put(COLUMN_NAME, name);
		db.insert(TABLE_NAME, null, values);
		Log.i(tag, "Insert Auditor : " + values);
	}
	public void update(int id, String name){
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, id);
		values.put(COLUMN_NAME, name);
		db.update(TABLE_NAME, values, COLUMN_ID + " = " + id, null);
		Log.i(tag, "Update Auditor : " + values);
	}
	public Cursor fetchAll(){
		return db.query(TABLE_NAME, COLUMNS, null, null, null, null, null);
	}
	public Cursor fetchById(int id){
		return db.query(TABLE_NAME, COLUMNS, COLUMN_ID + " = " + id, null, null, null, null);

	}
	public static String TABLE_NAME = "AUDITOR";
	public static String COLUMN_ID = "ID";
	public static String COLUMN_NAME = "NAME";
	public static String[] COLUMNS = { COLUMN_ID, COLUMN_NAME };

	public static String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME + "("
			+ COLUMN_ID + " INTEGER PRIMARY KEY, "
			+ COLUMN_NAME + " TEXT NOT NULL)";
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
	public Cursor fetchByIds(List<Integer> auditorIds) {
		// TODO Auto-generated method stub
		StringBuilder args = new StringBuilder();
		for (int i = 0; i < auditorIds.size(); i++) {
			args.append(auditorIds.get(i));
			if(i != auditorIds.size() -1)
				args.append(",");
		}
		String selection = String.format(COLUMN_ID+" in ( %s )" , args.toString());
		return db.query(TABLE_NAME, COLUMNS, selection, null, null, null, null);
	}
	
}
