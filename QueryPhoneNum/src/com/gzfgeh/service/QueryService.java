package com.gzfgeh.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.gzfgeh.StreamTool.StreamToByteArray;

public class QueryService {
	public static String getPNAddress(String phone) throws Exception{
		String data = getData();
		data = data.replaceAll("\\$phone", phone);
		byte[] entity = data.getBytes();
		
		String path = "http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx";
		HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
		conn.setReadTimeout(5000);
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
		conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
		conn.getOutputStream().write(entity);
		if (conn.getResponseCode() == 200){
			return parseXml(conn.getInputStream());
		}
		return null;
	}

	private static String parseXml(InputStream inputStream) throws Exception{
		// TODO Auto-generated method stub
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(inputStream, "UTF-8");
		int event = parser.getEventType();
		while (event != XmlPullParser.END_DOCUMENT){
			switch (event) {
			case XmlPullParser.START_TAG:
				if ("getMobileCodeInfoResult".equals(parser.getName())){
					return parser.nextText();
				}
				break;
				
			default:
				break;
			}
			event = parser.next();
		}
		return null;
	}

	private static String getData() throws Exception{
		// TODO Auto-generated method stub
		InputStream inputStream = QueryService.class.getClassLoader().getResourceAsStream("Soap.xml");
		byte[] data = StreamToByteArray.read(inputStream);
		return new String(data);
	}
}
