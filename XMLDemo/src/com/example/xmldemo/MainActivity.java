package com.example.xmldemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.gzfgeh.domain.News;
import com.gzfgeh.parsedata.ParseDataService;

import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private ListView listView;
	private EditText editText;
	private Button button;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (android.os.Build.VERSION.SDK_INT > 9) { 
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); 
			StrictMode.setThreadPolicy(policy); 
		} 
		
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		listView = (ListView)findViewById(R.id.listview);
		editText = (EditText)findViewById(R.id.path);
		editText.setText("http://192.168.1.100:8080/VideoNews/ListServlet");
		button = (Button)findViewById(R.id.display);
		button.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		String path = editText.getText().toString();
		List<News> videos = null;
		boolean b = path.contains("json");
		try{
			if (b){
				videos = ParseDataService.getNewsJson(path);
			}else{
				videos = ParseDataService.getNewsXml(path);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
			
		if (videos == null){
			Toast.makeText(this, "µÿ÷∑¥ÌŒÛ", Toast.LENGTH_SHORT).show();
			return;
		}
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
		
		for (News news : videos) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("id", news.getId());
			hashMap.put("title", getResources().getString(R.string.video_name)+ news.getTitle());
			hashMap.put("timelength", getResources().getString(R.string.time)+
					news.getTimelength()+getResources().getString(R.string.minute));
			data.add(hashMap);
		}
		SimpleAdapter adapter = new SimpleAdapter(this,data,R.layout.item,
				new String[]{"title","timelength"},new int[]{R.id.title,R.id.timelength});
		
		listView.setAdapter(adapter);
	}
}
