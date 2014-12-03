package com.gzfgeh.ui;

import com.gzfgeh.codeui.R;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
	private TextView textView;
	private boolean flag = false;
    @SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LayoutParams layoutParams = new LayoutParams(
        		LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        
        
        textView = new TextView(this);
        textView.setText("hello man");
        LayoutParams textViewParams = new LayoutParams(
        		LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.addView(textView, textViewParams);
        
        View portView = getPortUI();
        linearLayout.addView(portView);
        
        setContentView(linearLayout,layoutParams);
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
    
    private View getPortUI(){
    	LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	return inflater.inflate(R.layout.port, null);
    }
    
    public void Click(View v){
    	if (!flag){
    		textView.setText("hi woman");
    		flag = true;
    	}
    	else{
    		textView.setText("hi man");
    		flag = false;
    	}
    }
}









