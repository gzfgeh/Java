package com.gzfgeh.queryphonenum;

import com.gzfgeh.service.QueryService;

import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	private EditText inputText;
	private TextView display;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

    	if (android.os.Build.VERSION.SDK_INT > 9) { 
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); 
			StrictMode.setThreadPolicy(policy); 
		}
		
		inputText = (EditText)findViewById(R.id.number);
		inputText.setText("18501917154");
		display = (TextView)findViewById(R.id.address);
	}
	
	public void queryNum(View v) throws Exception{
		String address = QueryService.getPNAddress(inputText.getText().toString());
		if (address != null)
			display.setText(address);
		else
			display.setText("fail");
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
