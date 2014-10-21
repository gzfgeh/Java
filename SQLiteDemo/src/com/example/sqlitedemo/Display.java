package com.example.sqlitedemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.service.PersonService;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Display extends Activity{
	private ListView listView;
	private PersonService personService;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display);
		
		//personService = new PersonService(this);
		//listView = (ListView)findViewById(R.id.display);
		//show();
	}
	private void show() {
		// TODO Auto-generated method stub
		List<Person> persons = personService.getScrollData(0, personService.getCnt());
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
		for (Person person : persons) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("name", person.getNameString());
			hashMap.put("phone", person.getPhoneNum());
			hashMap.put("personid", person.getId());
			hashMap.put("amount", person.getAmount());
			data.add(hashMap);
		}
		SimpleAdapter simpleAdapter = new SimpleAdapter(this,data,R.layout.display_itemr
				,new String[]{"name","phone","amount"},
				new int[]{R.id.name,R.id.phone,R.id.account});
		
		listView.setAdapter(simpleAdapter);
	}
}
