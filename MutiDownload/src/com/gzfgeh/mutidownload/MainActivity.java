package com.gzfgeh.mutidownload;

import java.io.File;

import com.gzfgeh.net.download.DownloadProgressListener;
import com.gzfgeh.net.download.FileDownloader;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText downlownPath;
	private static TextView progress;
	private static ProgressBar progressBar;
	private static Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:
				int size = msg.getData().getInt("size");
				progressBar.setProgress(size);
				float num = (float)progressBar.getProgress()/(float)progressBar.getMax();
				int result = (int)(num*100);
				progress.setText(result+ "%");
				if(progressBar.getProgress()==progressBar.getMax()){
					//Toast.makeText(MainActivity.this, R.string.down_success, Toast.LENGTH_SHORT).show();
				}
				break;
			case -1:
				//Toast.makeText(MainActivity.this, R.string.down_fail, Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
		
	};
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (android.os.Build.VERSION.SDK_INT > 9) { 
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); 
			StrictMode.setThreadPolicy(policy); 
		} 
		
		progress = (TextView)findViewById(R.id.progress);
		progress.setText("0%");
		downlownPath = (EditText)findViewById(R.id.path);
		downlownPath.setText("http://192.168.103.104:8080/Web/xxxx.exe");
		progressBar = (ProgressBar)findViewById(R.id.progressbar);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void downLoad(View v){
		String path = downlownPath.getText().toString();
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			File saveFile = Environment.getExternalStorageDirectory();
			downloadFile(path,saveFile);
		}else {
			Toast.makeText(this, R.string.not_found, Toast.LENGTH_SHORT).show();
		}
	}

	private void downloadFile(final String path, final File saveFile) {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				try {
					FileDownloader loader = new FileDownloader(MainActivity.this, path, saveFile, 3);
					progressBar.setMax(loader.getFileSize());
					loader.download(new DownloadProgressListener() {
						
						public void onDownloadSize(int size) {
							// TODO Auto-generated method stub
							Message msg = new Message();
							msg.what = 1;
							msg.getData().putInt("size", size);
							handler.sendMessage(msg);
						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					handler.obtainMessage(-1).sendToTarget();;
					e.printStackTrace();
				}
			}
		}).start();;
	}
	
}
