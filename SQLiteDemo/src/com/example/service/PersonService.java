package com.example.service;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sqlitedemo.DBOpenHelper;
import com.example.sqlitedemo.Person;

public class PersonService {
	private DBOpenHelper db;
	
	public PersonService(Context context) {
		this.db = new DBOpenHelper(context);
	}
	
	public void add(Person person){
		SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(DBOpenHelper.NAME, person.getNameString());
		cv.put(DBOpenHelper.PHONE, person.getPhoneNum());
		cv.put(DBOpenHelper.AMOUNT, person.getAmount());
		sqLiteDatabase.insert(DBOpenHelper.TABLE, null, cv);
	}
	
	public void delete(Integer id){
		SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
		sqLiteDatabase.delete(DBOpenHelper.TABLE, "personid=?", new String[]{id.toString()});
	}
	
	public void update(Person person){
		SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBOpenHelper.NAME, person.getNameString());
		values.put(DBOpenHelper.PHONE, person.getPhoneNum());
		values.put(DBOpenHelper.AMOUNT, person.getAmount());
		sqLiteDatabase.update(DBOpenHelper.TABLE, values, "personid=?", 
				new String[]{String.valueOf(person.getId())});
	}
	
	public Person find(Integer id){
		SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
		Cursor cursor = sqLiteDatabase.query(DBOpenHelper.TABLE, null, "personid=?", 
				new String[]{id.toString()}, null, null, null);
		
		if(cursor.moveToFirst()){
			int personid = cursor.getInt(cursor.getColumnIndex("personid"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String phone = cursor.getString(cursor.getColumnIndex("phone"));
			int amount = cursor.getInt(cursor.getColumnIndex("amount"));
			return new Person(name,personid,phone,amount);
		}
		cursor.close();
		return null;
	}
	
	public List<Person> getScrollData(int offset,int limit){
		List<Person> persons = new ArrayList<Person>();
		SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
		
		Cursor cursor = sqLiteDatabase.query(DBOpenHelper.TABLE, null, null, 
				null, null, null, "personid asc",offset+","+limit);
		
		while(cursor.moveToNext()){
			int personid = cursor.getInt(cursor.getColumnIndex("personid"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String phone = cursor.getString(cursor.getColumnIndex("phone"));
			int amount = cursor.getInt(cursor.getColumnIndex("amount"));
			persons.add(new Person(name,personid,phone,amount));
		}
		cursor.close();
		return persons;
	}
	
	public int getCnt(){
		SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
		Cursor cursor = sqLiteDatabase.query(DBOpenHelper.TABLE, new String[]{"count(*)"}, 
				null, null, null, null, null);
		
		cursor.moveToFirst();
		int cnt = cursor.getInt(0);
		cursor.close();
		return cnt;
	}
	
	public void payment(){
		SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
		try {
			sqLiteDatabase.beginTransaction();
			sqLiteDatabase.execSQL("update student set amount=amount-10 where personid=1");
			sqLiteDatabase.execSQL("update student set amount=amount+10 where personid=2");
			sqLiteDatabase.setTransactionSuccessful();
		}finally{
			sqLiteDatabase.endTransaction();
		}
	}
}
