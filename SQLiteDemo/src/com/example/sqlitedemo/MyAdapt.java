package com.example.sqlitedemo;

import java.util.List;
import java.util.zip.Inflater;

import com.example.sqlitedata.Person;
import com.example.sqlitedata.PersonService;

import android.R.integer;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyAdapt extends BaseAdapter implements OnClickListener {
	private Context context;
	private List<Person> persons;
	private PersonService personService;
	private int mLastPosition;
	private int mLastVisibility;
	private View mLastView;
	private Person person;
	
	

	public MyAdapt(Context context, List<Person> persons) {
		super();
		personService = new PersonService(context);
		this.context = context;
		this.persons = persons;
		mLastPosition = -1;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return persons.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return persons.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder = null;
		if (convertView == null){
			holder = new Holder();
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.mylistview_item, null);
			holder.name = (TextView)convertView.findViewById(R.id.name);
			holder.phone = (TextView)convertView.findViewById(R.id.phone);
			holder.amount = (TextView)convertView.findViewById(R.id.account);
			holder.test = (TextView)convertView.findViewById(R.id.hint_text);
			holder.test.setText(persons.get(position).getNameString());
			holder.test.setOnClickListener(this);
			holder.image1 = (ImageView)convertView.findViewById(R.id.image1);
			holder.image1.setOnClickListener(this);
			holder.image2 = (ImageView)convertView.findViewById(R.id.image2);
			holder.image2.setOnClickListener(this);
			holder.hint = convertView.findViewById(R.id.hint_image);
			convertView.setTag(holder);
		}else{
			holder = (Holder)convertView.getTag();
		}
		
		if (mLastPosition == position){
			holder.hint.setVisibility(mLastVisibility);
			//holder.test.setText(holder.name.getText().toString());
		}else{
			holder.hint.setVisibility(View.GONE);
		}
		
		person = persons.get(position);
		holder.name.setText(person.getNameString());
		holder.phone.setText(person.getPhoneNum());
		holder.amount.setText(person.getAmount()+"");
		
		return convertView;
	}
	
	public final class Holder{
		public TextView name;
		public TextView phone;
		public TextView amount;
		public TextView test;
		public ImageView image1;
		public ImageView image2;
		public View hint;

	}
	
	
	public void changeItemVisibility(View view,int position){
		if ((mLastView != null) && (mLastPosition != position)){
			Holder holder = (Holder)mLastView.getTag();
			switch (holder.hint.getVisibility()) {
			case View.VISIBLE:
				holder.hint.setVisibility(View.GONE);
				mLastVisibility = View.GONE;
				view.setBackgroundColor(0xFFFFFF);
				break;

			default:
				break;
			}
		}
		
		mLastPosition = position;
		mLastView = view;
		Holder holder = (Holder)view.getTag();
		switch(holder.hint.getVisibility()) {
        case View.GONE:
            holder.hint.setVisibility(View.VISIBLE);
            mLastVisibility = View.VISIBLE;
            view.setBackgroundColor(0x0000FF);
            break;
        case View.VISIBLE:
            holder.hint.setVisibility(View.GONE);
            mLastVisibility = View.GONE;
            view.setBackgroundColor(0xFFFFFF);
            break;
        }

	}
	
	public void showMyDialog(String message){
		MyDialogDemo.Builder builder = new MyDialogDemo.Builder(context);
		builder.setIcon(R.drawable.title);
		builder.setTitle("提示信息");
		builder.setMessage(message);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				dialog.dismiss();
			}
		});
		builder.create(R.layout.diaplay_dialog).show();
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.image1:
		case R.id.image2:
		case R.id.hint_text:
			String data = "name = " + person.getNameString() + "\n" + "phone = "+ person.getPhoneNum() + "\n" + "account = " + person.getAmount();
			showMyDialog(data);
			break;
		default:
			break;
		}
	}
	
}
