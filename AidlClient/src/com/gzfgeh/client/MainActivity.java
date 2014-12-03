package com.gzfgeh.client;

import com.gzfgeh.aidl.StudentQuery;
import com.gzfgeh.aidlclient.R;

import android.support.v7.app.ActionBarActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
	private EditText input;
	private TextView display;
	private StudentQuery studentQuery;
	private StudentConnection conn = new StudentConnection();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        input = (EditText)findViewById(R.id.number);
        input.setText("1");
        display = (TextView)findViewById(R.id.display);
        
        Intent service = new Intent("com.gzfgeh.student.query");
    	bindService(service, conn, BIND_AUTO_CREATE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void BtnClick(View v){
    	String name = input.getText().toString();
    	
    	try {
			display.setText(studentQuery.studentQuery(Integer.valueOf(name)));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			display.setText("fail");
			e.printStackTrace();
		}
    }
    
    
    
    public final class StudentConnection implements ServiceConnection{

		@Override
		public void onServiceConnected(ComponentName name, IBinder ibinder) {
			// TODO Auto-generated method stub
			studentQuery = StudentQuery.Stub.asInterface(ibinder);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			studentQuery = null;
		}
    	
    }
}





