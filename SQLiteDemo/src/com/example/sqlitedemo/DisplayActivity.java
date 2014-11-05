package com.example.sqlitedemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.sqlitedata.Person;
import com.example.sqlitedata.PersonService;
import com.example.sqlitedemo.MyDialogDemo.Builder.GetSureInputData;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Notification.Action;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayActivity extends Activity implements OnItemClickListener, OnItemLongClickListener, OnClickListener {
	private String getData;
	private ListView listView;
	private PersonService personService;
	private MyAdapt myAdapt;
	private List<Person> persons;
	private Button searchBtn;
	private EditText editText;
	
	@SuppressLint("NewApi") 
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		Intent intent = getIntent();
		getData = intent.getStringExtra("Adapt");
		
		editText = (EditText)findViewById(R.id.edit);
		searchBtn = (Button)findViewById(R.id.search);
		searchBtn.setOnClickListener(this);
		listView = (ListView)findViewById(R.id.listview);
		listView.setOnItemClickListener(this);
		//listView.setOnItemLongClickListener(this); //自己添加长按事件
		registerForContextMenu(listView);
		
		personService = new PersonService(this);
		persons = personService.getScrollData(0, personService.getCnt());
		
		if (getData.equals("SimpleAdapt"))
			showSimpleAdaptView();
		else if (getData.equals("CursorAdapt"))
			showCursorAdaptView();
		else if (getData.equals("ArrayAdapt"))
			showArrayAdaptView();
		else if (getData.equals("MyAdapt"))
			showMyAdapt();
		
		setTitle(getData);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP, ActionBar.DISPLAY_HOME_AS_UP);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_menu, menu);
		return true;
	}
	@Override  
    public boolean onOptionsItemSelected(MenuItem item) {  
        switch (item.getItemId()) {  
        case android.R.id.home:  
            // 当ActionBar图标被点击时调用  
            Intent intent = new Intent(this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
            startActivity(intent);
            break;
        case R.id.add_menu:
        	showMyDialog(R.layout.insert_dialog, "添加Item",null,null);
        	break;
        default:
        		break;
        
        }  
        return super.onOptionsItemSelected(item);  
    }  
	
	private void showMyAdapt() {
		// TODO Auto-generated method stub
		myAdapt = new MyAdapt(this, persons);
		listView.setAdapter(myAdapt);
	}

	private void showCursorAdaptView() {
		// TODO Auto-generated method stub
		Cursor cursor = personService.getCursor(0, personService.getCnt());
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.listview_item, cursor,
				new String[]{"name","phone","amount"}, 
				new int[]{R.id.name,R.id.phone,R.id.account});
		
		listView.setAdapter(adapter);
	}

	private void showArrayAdaptView() {
		// TODO Auto-generated method stub
		PersonAdapt personAdapt = new PersonAdapt(this, persons, R.layout.listview_item);
		listView.setAdapter(personAdapt);
	}

	private void showSimpleAdaptView() {
		// TODO Auto-generated method stub
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
		for (Person person : persons) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("name", person.getNameString());
			hashMap.put("phone", person.getPhoneNum());
			hashMap.put("amount", person.getAmount());
			hashMap.put("personid", person.getId());
			data.add(hashMap);
		}
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.listview_item,
				new String[]{"name","phone","amount"}, 
				new int[]{R.id.name,R.id.phone,R.id.account});
		
		listView.setAdapter(simpleAdapter);
	}

	@SuppressLint("NewApi")
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		myAdapt.changeItemVisibility(view, position);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		//myAdapt.itemLongClick(view,position);
		return true;
	}
	
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {  
        menu.setHeaderTitle("请选择操作");  
        menu.add(0, 1, 0, "编辑");  
        menu.add(0, 2, 1, "删除");  
    } 
	
	// 响应编辑和删除事件处理  
    public boolean onContextItemSelected(MenuItem item) {  
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item  
                .getMenuInfo();
        String name = ((TextView)info.targetView.findViewById(R.id.name)).getText().toString();
        String phone = ((TextView)info.targetView.findViewById(R.id.phone)).getText().toString();
        String amount = ((TextView)info.targetView.findViewById(R.id.account)).getText().toString();
        //((EditText)info.targetView.findViewById(R.id.insert_name)).setText(name);
		//((EditText)info.targetView.findViewById(R.id.insert_name)).setText(phone);
		//((EditText)info.targetView.findViewById(R.id.insert_name)).setText(amount);
        switch (item.getItemId()) {  
        case 1:  
        	showMyDialog(R.layout.update_dialog, "编辑Item",name,null);
            break;  
        case 2:
        	showMyDialog(R.layout.diaplay_dialog, "删除Item",name,null);
            break;  
        default:  
            break;  
        }
        return false;  
    }  
    
    public void showMyDialog(final int layout,String title,final String data,String message){
    	GetSureInputData getSureInputData = new GetSureInputData() {
			
			@Override
			public void getText(String string) {
				// TODO Auto-generated method stub
				if(layout == R.layout.insert_dialog){
		        	if ((string != null) && (string.length() != 2)){
		    			String[] data = string.split(" ");
		    			if (data.length == 3){
		    				if (personService.find(data[0]) == null) {
								Person person = new Person(data[0], data[1], Integer.valueOf(data[2]));
				    			personService.add(person);
				    			Toast.makeText(DisplayActivity.this, "Insert " + data[0] + " Successful", Toast.LENGTH_SHORT).show();
			    			}else{
			    				Toast.makeText(DisplayActivity.this, data[0] + " exist", Toast.LENGTH_SHORT).show();
			    			}
			    		}
		    		}
		        }else if(layout == R.layout.update_dialog){
		        	if ((string != null) && (string.length() != 2)){
		    			String[] data = string.split(" ");
		    			if (data.length == 3){
		    				if (personService.find(data[0]) != null) {
								Person person = new Person(data[0], data[1], Integer.valueOf(data[2]));
				    			personService.update(person);
				    			Toast.makeText(DisplayActivity.this, "Update " + data[0] + " Successful", Toast.LENGTH_SHORT).show();
			    			}else{
			    				Toast.makeText(DisplayActivity.this, data[0] + " no exist", Toast.LENGTH_SHORT).show();
			    			}
			    		}
		    		}
		        }
			}
		};
    	MyDialogDemo.Builder builder = new MyDialogDemo.Builder(this,getSureInputData);
		builder.setIcon(R.drawable.title);
		builder.setTitle(title);
		if(message != null)
			builder.setMessage(message);
		
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				// TODO Auto-generated method stub
				if (layout == R.layout.diaplay_dialog)
					personService.delete(data);
				dialog.dismiss();
			}
		});
		
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		builder.create(layout).show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.search:
			String data = editText.getText().toString();
			Person person = personService.find(data);
			if(person != null){
				data = "name = " + person.getNameString() + "\n" + "phone = "+ person.getPhoneNum() + "\n" + "account = " + person.getAmount();
				showMyDialog(R.layout.diaplay_dialog, "提示消息" ,null,data);
			}
			else
				showMyDialog(R.layout.diaplay_dialog, "提示消息",null,"没有找到");
			break;
		default:
			break;
		}
	}
}
