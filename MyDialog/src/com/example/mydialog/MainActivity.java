package com.example.mydialog;

import com.example.mydialog.MyDialogDemo.Builder.GetSureInputData;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button button;
	private String dataString;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		button = (Button)findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				final GetSureInputData getSureInputData = new GetSureInputData(){
					public void getText(String string) {
						// TODO Auto-generated method stub
						dataString = string;
						if(dataString.equals("name") || dataString.equals("phone") || dataString.equals("account"))
							Toast.makeText(MainActivity.this, dataString+"存在", Toast.LENGTH_SHORT).show();
						else 
							Toast.makeText(MainActivity.this, dataString+"不存在", Toast.LENGTH_SHORT).show();
		            }
		    	};
		    	
				MyDialogDemo.Builder builder = new MyDialogDemo.Builder(MainActivity.this,
						getSureInputData);
				
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int which) {
		                    dialog.dismiss();
		            }
				});
				//builder.setMessage("message");
				builder.setTitle("title");
				builder.setIcon(R.drawable.ic_launcher);
				builder.create(R.layout.input_dialog).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
