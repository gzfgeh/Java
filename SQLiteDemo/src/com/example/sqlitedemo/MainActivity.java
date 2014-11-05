package com.example.sqlitedemo;

import com.example.sqlitedata.DBOpenHelper;
import com.example.sqlitedata.Person;
import com.example.sqlitedata.PersonService;
import com.example.sqlitedemo.MyDialogDemo.Builder.GetSureInputData;
import android.R.anim;
import android.R.integer;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private Button simpleButton,arrayButton,myButton,cursorButton,addButton,deleteButton,updateButton,findButton,cntButton,buildButton,existButton;
	private PersonService personService;
	private String dataString = null;
	private static int dialogFlag = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		buildButton = (Button)findViewById(R.id.build);
		buildButton.setOnClickListener(this);
		simpleButton = (Button)findViewById(R.id.simple_display);
		simpleButton.setOnClickListener(this);
		arrayButton = (Button)findViewById(R.id.array_display);
		arrayButton.setOnClickListener(this);
		cursorButton = (Button)findViewById(R.id.cursor_display);
		cursorButton.setOnClickListener(this);
		addButton = (Button)findViewById(R.id.insert);
		addButton.setOnClickListener(this);
		deleteButton = (Button)findViewById(R.id.delete);
		deleteButton.setOnClickListener(this);
		updateButton = (Button)findViewById(R.id.update);
		updateButton.setOnClickListener(this);
		findButton = (Button)findViewById(R.id.find);
		findButton.setOnClickListener(this);
		cntButton = (Button)findViewById(R.id.cnt);
		cntButton.setOnClickListener(this);
		existButton = (Button)findViewById(R.id.exist);
		existButton.setOnClickListener(this);
		myButton = (Button)findViewById(R.id.my_adapt);
		myButton.setOnClickListener(this);
		
		personService = new PersonService(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch(v.getId())
		{
		case R.id.simple_display:
			intent.putExtra("Adapt", "SimpleAdapt");
			intent.setClass(this, DisplayActivity.class);
			startActivity(intent);
			break;
		case R.id.array_display:
			intent.putExtra("Adapt", "ArrayAdapt");
			intent.setClass(this, DisplayActivity.class);
			startActivity(intent);
			break;
		case R.id.cursor_display:
			intent.putExtra("Adapt", "CursorAdapt");
			intent.setClass(this, DisplayActivity.class);
			startActivity(intent);
			break;
		case R.id.my_adapt:
			intent.putExtra("Adapt", "MyAdapt");
			intent.setClass(this, DisplayActivity.class);
			startActivity(intent);
			break;
		case R.id.insert:
			showMyDialog(R.layout.insert_dialog,null,"Insert Item",R.drawable.title);
			break;
		case R.id.delete:
			showMyDialog(R.layout.delete_dialog,null,"Delete Item",R.drawable.title);
			break;
		case R.id.update:
			showMyDialog(R.layout.update_dialog,null,"Updata Item",R.drawable.title);
			break;
		case R.id.find:
			showMyDialog(R.layout.find_dialog,null,"Find Item",R.drawable.title);
			break;
		case R.id.cnt:
			showMyDialog(R.layout.diaplay_dialog,"ListView Item Cnt " + personService.getCnt(),"Get ListView Item Cnt" ,android.R.drawable.ic_dialog_info);
			break;
		case R.id.build:
			buildSQLite();
			showMyDialog(R.layout.diaplay_dialog,"Create Successful","My Dialog display",R.drawable.title);
			break;
		case R.id.exist:
			showMyDialog(R.layout.input_dialog,null,"My Dialog display",android.R.drawable.ic_dialog_info);
			break;
		default:
			break;
		}
	}

	public void showMyDialog(final int layout,String message,String title,int icon) {
		// TODO Auto-generated method stub
		MyDialogDemo.Builder builder;
		final GetSureInputData getSureInputData = new GetSureInputData(){
			@SuppressLint("NewApi")
			@Override
			public void getText(String string) {
				// TODO Auto-generated method stub
				if(layout == R.layout.input_dialog){
					if(string.equals("name") || string.equals("phone") || string.equals("amount"))
						Toast.makeText(MainActivity.this, string+"存在", Toast.LENGTH_SHORT).show();
					else 
						Toast.makeText(MainActivity.this, string+"不存在", Toast.LENGTH_SHORT).show();
		        }else if(layout == R.layout.insert_dialog){
		        	if ((string != null) && (string.length() != 2)){
		    			String[] data = string.split(" ");
		    			if (data.length == 3){
		    				if (personService.find(data[0]) == null) {
								Person person = new Person(data[0], data[1], Integer.valueOf(data[2]));
				    			personService.add(person);
				    			Toast.makeText(MainActivity.this, "Insert " + data[0] + " Successful", Toast.LENGTH_SHORT).show();
			    			}else{
			    				Toast.makeText(MainActivity.this, data[0] + " exist", Toast.LENGTH_SHORT).show();
			    			}
			    		}
		    		}
		        }else if(layout == R.layout.delete_dialog){
		        	if ((string != null) && (string.length() != 0)){
		        		if(personService.find(string) != null){
		        			personService.delete(string);
		        			Toast.makeText(MainActivity.this, "Delete Successful", Toast.LENGTH_SHORT).show();
		        		}else{
		        			Toast.makeText(MainActivity.this, string + " not exist", Toast.LENGTH_SHORT).show();
				        	dialogFlag = 1;
		        		}
		        	}
		        }else if(layout == R.layout.find_dialog){
		        	if ((string != null) && (string.length() != 0)){
		        		if(personService.find(string) != null)
		        			Toast.makeText(MainActivity.this, string + " find", Toast.LENGTH_SHORT).show();
		        		else
		        			Toast.makeText(MainActivity.this, string + " not find", Toast.LENGTH_SHORT).show();
		        	}
		        }else if(layout == R.layout.update_dialog){
		        	if ((string != null) && (string.length() != 2)){
		    			String[] data = dataString.split(" ");
		    			if (data.length == 3){
		    				if (personService.find(data[0]) != null) {
								Person person = new Person(data[0], data[1], Integer.valueOf(data[2]));
				    			personService.update(person);
				    			Toast.makeText(MainActivity.this, "Update " + data[0] + " Successful", Toast.LENGTH_SHORT).show();
			    			}else{
			    				Toast.makeText(MainActivity.this, data[0] + " no exist", Toast.LENGTH_SHORT).show();
			    			}
			    		}
		    		}
		        }
			}
    	};
    	
		if (layout == R.layout.diaplay_dialog){
			builder = new MyDialogDemo.Builder(MainActivity.this);			
		}else {
			builder = new MyDialogDemo.Builder(this,getSureInputData);
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
		}
		
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	if (dialogFlag == 0)
					dialog.dismiss();
            }
		});
		builder.setMessage(message);
		builder.setTitle(title);
		builder.setIcon(icon);
		builder.create(layout).show();
	}
	
	private void buildSQLite() {
		// TODO Auto-generated method stub
		DBOpenHelper dbOpenHelper = new DBOpenHelper(this);
		dbOpenHelper.getReadableDatabase();
	}
	
}
