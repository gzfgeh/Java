package com.example.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.StreamTools.StreamTool;

public class PageService {

	public static String getPageContent(String path) throws Exception{
		// TODO Auto-generated method stub
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == 200){
			InputStream in = conn.getInputStream();
			byte[] data = StreamTool.read(in);
			String html = new String(data,"UTF-8");
			
			return html;
		}
		return null;
	}

}
