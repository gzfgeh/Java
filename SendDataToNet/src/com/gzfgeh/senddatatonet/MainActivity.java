package com.gzfgeh.senddatatonet;

import java.io.File;

import com.gzfgeh.service.NewsService;

import android.R.anim;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText titleText;
	private EditText timeText;
	private EditText addrText;
	private EditText fileText;
	private RadioGroup mode;
	private String path = "http://192.168.103.104:8080/Web/ManageServlet";
	private int modeValue = 0;
	private File file;
	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}
	
	@SuppressLint("NewApi") private void initView() {
		// TODO Auto-generated method stub
    	
    	if (android.os.Build.VERSION.SDK_INT > 9) { 
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); 
			StrictMode.setThreadPolicy(policy); 
		} 
		titleText = (EditText)findViewById(R.id.title);
		titleText.setText("xxx");
		timeText = (EditText)findViewById(R.id.time);
		timeText.setText("200");
		addrText = (EditText)findViewById(R.id.addr);
		addrText.setText(path);
		fileText = (EditText)findViewById(R.id.filename);
		fileText.setText("1.gif");
		mode = (RadioGroup)findViewById(R.id.mode);
		mode.setOnCheckedChangeListener(new OnCheckedChangeListenerImp());
	}
	
	private class OnCheckedChangeListenerImp implements OnCheckedChangeListener{

		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.get:
				modeValue = 0;
				break;
			case R.id.post:
				modeValue = 1;
				break;
			case R.id.httpclient:
				modeValue = 2;
			case R.id.socket:
				modeValue = 3;
			default:
				break;
			}
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void save(View v) {
		// TODO Auto-generated method stub
		path = addrText.getText().toString();
		final String title = titleText.getText().toString();
		final String time = timeText.getText().toString();
		final String filename = fileText.getText().toString();
		progressDialog = ProgressDialog.show(this,"请稍等", "文件上传中...");
		Thread thread = new Thread(){
			 public void run() {
				 if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ||
							Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY)){
						file = new File(Environment.getExternalStorageDirectory(),filename);
						if (file.exists()){
							try{
								boolean result = NewsService.save(path,title,time,"UTF-8",modeValue,file);
								if (result){
									Toast.makeText(MainActivity.this, R.string.send_ok, Toast.LENGTH_SHORT).show();
								}else{
									Toast.makeText(MainActivity.this, R.string.send_fail, Toast.LENGTH_SHORT).show();
								}
							}catch(Exception e){
								e.printStackTrace();
							}
						}else{
							Toast.makeText(MainActivity.this, R.string.nofile, Toast.LENGTH_SHORT).show();
						}
					}
				 progressDialog.dismiss();
			 }
		};
		thread.start();
	}

}
