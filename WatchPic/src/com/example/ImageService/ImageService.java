package com.example.ImageService;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.example.StreamTools.StreamTool;

public class ImageService {

	public static byte[] getImage(String path) throws Exception{
		// TODO Auto-generated method stub
		URL url = new URL(path);
		//建立连接
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == 200){
			InputStream input = conn.getInputStream();
			return StreamTool.read(input);
		}
		return null;
	}

}
