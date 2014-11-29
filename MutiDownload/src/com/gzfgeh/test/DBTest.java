package com.gzfgeh.test;

import java.util.HashMap;
import java.util.Map;

import com.gzfgeh.service.DBOpenHelper;
import com.gzfgeh.service.FileService;

import android.test.AndroidTestCase;

public class DBTest extends AndroidTestCase {
	
	public void testCreateDB() throws Exception{
		DBOpenHelper db = new DBOpenHelper(getContext());
		db.getReadableDatabase();
	}
	
	public void testSave() throws Exception{
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(10, 11);
		map.put(12, 13);
		map.put(14, 15);
		FileService service = new FileService(getContext());
		service.save("/test", map);
		
	}
	
	public void testUpdate() throws Exception{
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(10, 111);
		FileService service = new FileService(getContext());
		service.update("/test", map);
	}
	
	public void testDelete() throws Exception{
		FileService service = new FileService(getContext());
		service.delete("/test");
	}
}
