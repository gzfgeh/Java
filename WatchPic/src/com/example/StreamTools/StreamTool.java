package com.example.StreamTools;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamTool {

	public static byte[] read(InputStream input) throws Exception{
		// TODO Auto-generated method stub
		byte[] buffer = new byte[1024];
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int len = 0;
		while((len = input.read(buffer)) != -1){
			out.write(buffer, 0, len);
		}
		
		out.close();
		return out.toByteArray();
	}

}
