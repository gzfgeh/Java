package com.gzfgeh.service;

import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.SocketHttpClientConnection;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.apache.http.message.BasicNameValuePair;

import com.gzfgeh.senddatatonet.MainActivity;
import com.gzfgeh.utils.FormFile;
import com.gzfgeh.utils.SocketHttpRequester;

import android.content.Context;
import android.widget.MultiAutoCompleteTextView.CommaTokenizer;
import android.widget.Toast;

public class NewsService {

	public static boolean save(String path,String title, String time,
			String encodeMothod,int modeValue,File file) throws Exception{
		// TODO Auto-generated method stub
		Map<String,String> params = new HashMap<String,String>();
		params.put("title", title);
		params.put("timelength", time);
		FormFile formFile = new FormFile(file, "do not know", "do not know");
		
		if (modeValue == 1)
			return SendDataPostMode(path,params,encodeMothod);
		else if(modeValue == 2)
			return SendDataHttpClientMode(path,params,encodeMothod);
		else if (modeValue == 3)
			return SocketHttpRequester.post(path, params, formFile);
		else 
			return SendDataGetMode(path,params,encodeMothod);
	}

	private static boolean SendDataHttpClientMode(String path,
			Map<String, String> params, String encodeMothod) throws Exception{
		// TODO Auto-generated method stub
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		if ((params != null) && !params.isEmpty()){
			for (Map.Entry<String, String> entry : params.entrySet()) {
				list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,encodeMothod);
		HttpPost httpPost = new HttpPost(path);
		httpPost.setEntity(entity);
		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(httpPost);
		if (response.getStatusLine().getStatusCode() == 200){
			return true;
		}
		return false;
	}

	private static boolean SendDataPostMode(String path,
			Map<String, String> params, String encodeMothod) throws Exception{
		// TODO Auto-generated method stub
		StringBuilder data = new StringBuilder();
		if ((params != null) && !params.isEmpty()){
			for (Map.Entry<String, String> entry : params.entrySet()) {
				data.append(entry.getKey()).append('=').append(URLEncoder.encode(entry.getValue(),encodeMothod));
				data.append('&');
			}
			data.deleteCharAt(data.length() - 1);
		}
		
		byte[] entity = data.toString().getBytes();
		HttpURLConnection conn = (HttpURLConnection)new URL(path).openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("POST");
		conn.setDoOutput(true); //must be 
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
		OutputStream outputStream = conn.getOutputStream();
		outputStream.write(entity);
		if (conn.getResponseCode() == 200){
			return true;
		}
		return false;
	}

	private static boolean SendDataGetMode(String path, Map<String, String> params,String encodeMothod) throws Exception{
		// TODO Auto-generated method stub
		StringBuilder url = new StringBuilder(path);
		url.append('?');
		for (Map.Entry<String, String> entry : params.entrySet()) {
			url.append(entry.getKey()).append('=').append(URLEncoder.encode(entry.getValue(),encodeMothod));
			url.append('&');
		}
		url.deleteCharAt(url.length() - 1);
		
		URL urlAll = new URL(url.toString());
		HttpURLConnection conn = (HttpURLConnection)urlAll.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == 200){
			return true;
		}
		return false;
	}
}
