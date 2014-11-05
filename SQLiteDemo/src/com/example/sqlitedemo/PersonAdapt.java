package com.example.sqlitedemo;

import java.util.List;

import com.example.sqlitedata.Person;

import android.R.integer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PersonAdapt extends BaseAdapter {
	private List<Person> persons;	//绑定的数据
	private int resource;			//显示的界面
	private LayoutInflater inflater;
	
	public PersonAdapt(Context context,List<Person> persons, int resource) {
		this.persons = persons;
		this.resource = resource;
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return persons.size();
	}

	@Override
	public Object getItem(int i) {
		// TODO Auto-generated method stub
		return persons.get(i);
	}

	@Override
	public long getItemId(int i) {
		// TODO Auto-generated method stub
		return i;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null){
			convertView = inflater.inflate(resource, null);
		}
		
		TextView nameView = (TextView)convertView.findViewById(R.id.name);
		TextView phoneView = (TextView)convertView.findViewById(R.id.phone);
		TextView amountView = (TextView)convertView.findViewById(R.id.account);
		Person person = persons.get(position);
		nameView.setText(person.getNameString());
		phoneView.setText(person.getPhoneNum());
		amountView.setText(person.getAmount()+"");
		
		return convertView;
	}

}
