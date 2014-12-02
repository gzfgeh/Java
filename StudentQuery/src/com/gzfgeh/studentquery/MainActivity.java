package com.gzfgeh.studentquery;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView display;
	private EditText input;
	private ServiceConnection conn = new StudentServiceConnection();
	private IStudent iStudent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		input = (EditText)findViewById(R.id.number);
		input.setText("1");
		
		display = (TextView)findViewById(R.id.display);
		
		Intent service = new Intent(this,StudentService.class);
		bindService(service, conn, BIND_AUTO_CREATE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void ButtonClick(View v){
		String number = input.getText().toString();
		String name = iStudent.queryStudent(Integer.valueOf(number));
		if (name != null)
			display.setText(name);
		else
			display.setText(" ‰»Î¥ÌŒÛ");
	}
	
	public class StudentServiceConnection implements ServiceConnection{

		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			iStudent = (IStudent)service;
		}

		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			iStudent = null;
		}
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unbindService(conn);
		super.onDestroy();
	}
	
	
}
