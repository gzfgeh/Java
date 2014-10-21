package com.example.test;

import java.util.ArrayList;
import java.util.List;

import com.example.service.PersonService;
import com.example.sqlitedemo.DBOpenHelper;
import com.example.sqlitedemo.Person;

import android.test.AndroidTestCase;
import android.util.Log;

public class SQLiteTest extends AndroidTestCase {
	public static final String TAG = "SQLiteTest";
	
	public void testOnCreate() throws Exception{
		DBOpenHelper db = new DBOpenHelper(getContext());
		db.getReadableDatabase();
	}
	
	public void testAdd() throws Exception{
		PersonService personService = new PersonService(this.getContext());
		for(int i=0; i<20; i++){
			Person person = new Person("fuge"+i,"1234"+i,0);
			personService.add(person);
		}
	}
	
	public void testDelete() throws Exception{
		PersonService personService = new PersonService(this.getContext());
		for (int i = 0; i < 20; i++) {
			personService.delete(i);
		}
		
	}
	
	public void testUpdate() throws Exception{
		PersonService personService = new PersonService(this.getContext());
		Person person = personService.find(1);
		person.setAmount(10000000);
		personService.update(person);
	}
	
	public void testFind() throws Exception{
		PersonService personService = new PersonService(this.getContext());
		Person person = personService.find(22);
		Log.i(TAG, "find person :" + person.toString());
	}

	public void testGetScroll() throws Exception{
		List<Person> persons = new ArrayList<Person>();
		PersonService personService = new PersonService(this.getContext());
		persons = personService.getScrollData(0, 20);
		for (Person person : persons) {
			Log.i(TAG, person.toString());
		}
	}

	public void testGetCnt() throws Exception{
		PersonService personService = new PersonService(this.getContext());
		long a = personService.getCnt();
		Log.i(TAG,a + " cnt");
	}
	
	public void testSetAmount() throws Exception{
		PersonService personService = new PersonService(this.getContext());
		Person person1 = personService.find(1);
		Person person2 = personService.find(2);
		person1.setAmount(100);
		person2.setAmount(50);
		personService.update(person1);
		personService.update(person2);
	}
	
	public void testPayment() throws Exception{
		PersonService personService = new PersonService(this.getContext());
		personService.payment();
	}
}
