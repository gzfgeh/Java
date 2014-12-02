package com.gzfgeh.studentquery;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class StudentService extends Service {
	private String[] names = {"张飞","刘备","关羽","美女"};
	private IBinder binder = new StudentBinder(); 
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return binder;
	}
	
	public String queryName(int num){
		if (num>0 && num<4){
			return names[num];
		}
		return null;
	}
	
	private class StudentBinder extends Binder implements IStudent{

		public String queryStudent(int num) {
			// TODO Auto-generated method stub
			return queryName(num);
		}
		
	}
}
