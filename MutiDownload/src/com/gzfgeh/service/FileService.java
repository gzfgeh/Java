package com.gzfgeh.service;

import java.util.HashMap;
import java.util.Map;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class FileService {
	private DBOpenHelper openHelper;

	public FileService(Context context) {
		openHelper = new DBOpenHelper(context);
	}
	/*
	 * ��ȡÿ���߳��Ѿ����ص��ļ�����
	 */
	@SuppressLint("UseSparseArrays")
	public Map<Integer, Integer> getData(String path){
		SQLiteDatabase db = openHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select threadid, downlength from breakPoint where downpath=?",
					new String[]{path});
		
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		while (cursor.moveToNext()){
			map.put(cursor.getInt(0), cursor.getInt(1));
		}
		cursor.close();
		db.close();
		return map;
	}
	/*
	 * ����ÿ���߳��Ѿ����ص��ļ�����
	 */
	public void save(String path,Map<Integer, Integer> map){
		SQLiteDatabase db = openHelper.getReadableDatabase();
		db.beginTransaction();
		try{
			for(Map.Entry<Integer, Integer> entry: map.entrySet()){
				db.execSQL("insert into breakPoint(downpath,threadid,downlength) values(?,?,?)",
						new Object[]{path,entry.getKey(),entry.getValue()});
			}
			db.setTransactionSuccessful();
		}finally{
			db.endTransaction();
		}
		db.close();
	}
	 /**
     * ʵʱ����ÿ���߳��Ѿ����ص��ļ�����
     * @param path
     * @param map
     */
    public void update(String path, Map<Integer, Integer> map){
        SQLiteDatabase db = openHelper.getWritableDatabase();
        db.beginTransaction();
        
        try{
            for(Map.Entry<Integer, Integer> entry : map.entrySet()){
                db.execSQL("update breakPoint set downlength=? where downpath=? and threadid=?",
                        new Object[]{entry.getValue(), path, entry.getKey()});
            }
            
            db.setTransactionSuccessful();
        }finally{
            db.endTransaction();
        }
        
        db.close();
    }
    
    /**
     * ���ļ�������ɺ�ɾ����Ӧ�����ؼ�¼
     * @param path
     */
    public void delete(String path){
        SQLiteDatabase db = openHelper.getWritableDatabase();
        db.execSQL("delete from breakPoint where downpath=?", new Object[]{path});
        db.close();
    }
	
}
