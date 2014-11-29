package com.gzfgeh.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
	private static final String DBNAME = "download.db";
	private static int version = 1;
	
	public DBOpenHelper(Context context) {
		super(context,DBNAME,null,version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE IF NOT EXISTS breakPoint (id integer primary key autoincrement,downpath verchar(100),threadid INTEGER,downlength INTEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS breakPoint");
		onCreate(db);
	}

}
