package com.example.sqlitedata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public class DBOpenHelper extends SQLiteOpenHelper {
		public static final String DB_NAME="studentdata.db"; 
		public static final String TABLE = "student";
		public static final String NAME = "name";
		public static final String PHONE = "phone";
		public static final String PERSONID = "personid";
		public static final String AMOUNT = "amount";
		
		public DBOpenHelper(Context context) {
			super(context, DB_NAME, null, 1);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE student(personid integer primary key autoincrement,name verchar(20),phone verchar(12),amount integer)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
			// TODO Auto-generated method stub
			db.execSQL("ALTER TABLE student ADD amount integer");
		}

}
