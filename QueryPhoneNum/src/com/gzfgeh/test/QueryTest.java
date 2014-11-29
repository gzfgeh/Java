package com.gzfgeh.test;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.test.AndroidTestCase;

import com.gzfgeh.StreamTool.StreamToByteArray;

public class QueryTest extends AndroidTestCase{
	public void testXml() throws Exception{
		String path = "http://192.168.103.104:8080/Web/XmlServlet";
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("Soap.xml");
		byte[] data = StreamToByteArray.read(inputStream);
		
		HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
		conn.setReadTimeout(5000);
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		
		conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
		conn.setRequestProperty("Content-Length", String.valueOf(data.length));
		conn.getOutputStream().write(data);
		if (conn.getResponseCode() == 200){
			System.out.println("KOKOKO");
		}
		//System.out.println("error");
	}
}
