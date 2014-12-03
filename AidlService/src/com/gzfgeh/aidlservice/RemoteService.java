package com.gzfgeh.aidlservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class RemoteService extends Service {
	private IBinder binder = new StudentBinder();
	private String[] students = {"张三","李四","王五"};
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return binder;
	}
	
	public String StudentQuery(int number){
		if (number >0 && number < 4){
			return students[number-1];
		}
		return null;
	}
	
	public final class StudentBinder extends com.gzfgeh.aidl.StudentQuery.Stub{

		@Override
		public String studentQuery(int number) throws RemoteException {
			// TODO Auto-generated method stub
			return StudentQuery(number);
		}
		
	}
	
}
