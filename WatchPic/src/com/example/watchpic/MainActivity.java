package com.example.watchpic;

import com.example.ImageService.ImageService;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements OnClickListener {
	private EditText imagePath;
	private Button getImage;
	private ImageView imageView;
    private byte[] value;
    
    @SuppressLint("NewApi") protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        
        imagePath = (EditText)findViewById(R.id.image_path);
        getImage = (Button)findViewById(R.id.btn_look_image);
        getImage.setOnClickListener(this);
        imageView = (ImageView)findViewById(R.id.image);	
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


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String getImagePathString = imagePath.getText().toString();
		try {
			byte[] buffer = ImageService.getImage(getImagePathString);
			Bitmap bitmap = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
			//Looper.prepare();
			imageView.setImageBitmap(bitmap);
			Toast.makeText(this, "获取图片成功", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(this, "获取图片错误", Toast.LENGTH_SHORT).show();
		}
	}
}
















