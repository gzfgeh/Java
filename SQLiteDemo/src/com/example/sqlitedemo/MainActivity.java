package com.example.sqlitedemo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
	private Button insertBtn,updataGBtn,deleteBtn,displayBtn,selectBtn,closeBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        initMainActivity();
	}

	private void initMainActivity() {
		// TODO Auto-generated method stub
		insertBtn = (Button)findViewById(R.id.insert);
		updataGBtn = (Button)findViewById(R.id.updateG);
		deleteBtn = (Button)findViewById(R.id.delete);
		displayBtn = (Button)findViewById(R.id.display);
		selectBtn = (Button)findViewById(R.id.select);
		closeBtn = (Button)findViewById(R.id.close);
		insertBtn.setOnClickListener(this);
		updataGBtn.setOnClickListener(this);
		deleteBtn.setOnClickListener(this);
		displayBtn.setOnClickListener(this);
		selectBtn.setOnClickListener(this);
		closeBtn.setOnClickListener(this);
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
    	int id = v.getId();
		switch(id)
		{
		case R.id.insert:
			
			break;
		case R.id.display:
			Toast.makeText(this, "test11111111", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(MainActivity.this,Display.class);
			startActivity(intent);
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
