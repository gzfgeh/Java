package com.gzfgeh.parsedata;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import android.util.Xml;

import com.gzfgeh.StreamTool.StreamToByteArray;
import com.gzfgeh.domain.News;

public class ParseDataService {
	//private static final String PATH = "http://192.168.1.100:8080/VideoNews/ListServlet";
	public static List<News> getNewsXml(String path) throws Exception{
		URL uri = new URL(path);
		HttpURLConnection conn = (HttpURLConnection)uri.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == 200){
			InputStream inputStream = conn.getInputStream();
			return parseXml(inputStream);
		}
		return null;
	}
	
	public static List<News> getNewsJson(String path) throws Exception{
		URL uri = new URL(path);
		HttpURLConnection conn = (HttpURLConnection)uri.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == 200){
			InputStream inputStream = conn.getInputStream();
			return parseJson(inputStream);
		}
		return null;
	}
	
	private static List<News> parseJson(InputStream inputStream) throws Exception{
		// TODO Auto-generated method stub
		List<News> list = new ArrayList<News>();
		byte[] array = StreamToByteArray.read(inputStream);
		String data = new String(array);
		JSONArray jsonArray = new JSONArray(data);
		News news = null;
		for (int i = 0; i < jsonArray.length(); i++) {
			news = new News();
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			news.setId(jsonObject.getInt("id"));
			news.setTitle(jsonObject.getString("title"));
			news.setTimelength(jsonObject.getInt("timelength"));
			list.add(news);
		}
		return list;
	}

	private static List<News> parseXml(InputStream inputStream) throws Exception{
		// TODO Auto-generated method stub
		News news = null;
		List<News> newes = new ArrayList<News>();
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(inputStream, "UTF-8");
		int event = parser.getEventType();
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			case XmlPullParser.START_TAG:
				if("news".equals(parser.getName())){
					int id = new Integer(parser.getAttributeValue(0));
					news = new News();
					news.setId(id);
				}else if("title".equals(parser.getName())){
					news.setTitle(parser.nextText());
				}else if ("timelength".equals(parser.getName())){
					news.setTimelength(new Integer(parser.nextText()));
				}
				break;
			case XmlPullParser.END_TAG:
				if ("news".equals(parser.getName())){
					newes.add(news);
					news = null;
				}
			default:
				break;
			}
			event = parser.next();	
		}
		return newes;
	}
	
}
