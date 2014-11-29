package com.gzfgeh.StreamTool;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamToByteArray {
	public static byte[] read(InputStream inputStream) throws Exception{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int length = 0;
		while ((length = inputStream.read(data)) != -1){
			outputStream.write(data, 0, length);
		}
		inputStream.close();
		return outputStream.toByteArray();
	}
}
