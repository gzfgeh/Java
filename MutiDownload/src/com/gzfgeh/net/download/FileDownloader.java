package com.gzfgeh.net.download;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gzfgeh.service.FileService;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

@SuppressLint("DefaultLocale")
public class FileDownloader {
	private static final String TAG = "FileDownloader";
	
	private Context context;
	private int downloadSize = 0;
	private int fileSize = 0;
	private DownloadThread[] threads;
	
	private FileService fileService;
	private boolean isStop;
	private File saveFile;
	private Map<Integer, Integer> data = new ConcurrentHashMap<Integer, Integer>();
	private int block;
	private String downloadUrl;
	
	public int getTreadSize(){
		return threads.length;
	}
	
	public void exit(){
		this.isStop = true;
	}
	
	public boolean getExit(){
		return this.isStop;
	}
	
	public int getFileSize(){
		return this.fileSize;
	}
	
	protected synchronized void append(int size){
		downloadSize += size;
	}
	
	protected synchronized void update(int threadId,int pos){
		this.data.put(threadId, pos);
		this.fileService.update(this.downloadUrl, this.data);
	}
	
	/*
	 * 构建文件下载器
	 */
	public FileDownloader(Context context,String downloadUrl,File fileSaveDir,int threadNum){
		try{
			this.context = context;
			this.downloadUrl = downloadUrl;
			if (!fileSaveDir.exists()) fileSaveDir.mkdirs();
			this.threads = new DownloadThread[threadNum];
			
			fileService = new FileService(this.context);
			
			URL url = new URL(downloadUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, "
					+ "image/pjpeg, application/x-shockwave-flash, application/xaml+xml, "
					+ "application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, "
					+ "application/vnd.ms-excel, "
					+ "application/vnd.ms-powerpoint, application/msword, */*");
			
			conn.setRequestProperty("Accept-Language", "zh-CN");
			conn.setRequestProperty("Referer", downloadUrl);
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; "
					+ ".NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.connect();
			//printResponseHeader(conn);
			
			if (conn.getResponseCode()==200) {
		        this.fileSize = conn.getContentLength();//根据响应获取文件大小
		        if (this.fileSize <= 0) throw new RuntimeException("Unkown file size ");
		                
		        String filename = getFileName(conn);//获取文件名称
		        this.saveFile = new File(fileSaveDir, filename);//构建保存文件
		        Map<Integer, Integer> logdata = fileService.getData(downloadUrl);//获取下载记录
		        
		        if(logdata.size()>0){//如果存在下载记录
		            for(Map.Entry<Integer, Integer> entry : logdata.entrySet())
		                data.put(entry.getKey(), entry.getValue());//把各条线程已经下载的数据长度放入data中
		        }
		        //print(this.data.size() + "--" + this.threads.length);
		        if(this.data.size()==this.threads.length){//下面计算所有线程已经下载的数据长度
		            for (int i = 0; i < this.threads.length; i++) {
		                this.downloadSize += this.data.get(i+1);
		            }
		            
		            print("已经下载的长度"+ this.downloadSize);
		        }
		        
		        //计算每条线程下载的数据长度
		        this.block = (this.fileSize % this.threads.length)==0? this.fileSize / this.threads.length : this.fileSize / this.threads.length + 1;
		    }else{
		        throw new RuntimeException("server no response ");
		    }
	    } catch (Exception e) {
	        print(e.toString());
	        throw new RuntimeException("don't connection this url");
	    }
	}

	/**
     * 获取文件名
     * @param conn
     * @return
     */
    @SuppressLint("DefaultLocale")
	private String getFileName(HttpURLConnection conn) {
        String filename = this.downloadUrl.substring(this.downloadUrl.lastIndexOf('/') + 1);
        
        if(filename==null || "".equals(filename.trim())){//如果获取不到文件名称
            for (int i = 0;; i++) {
                String mine = conn.getHeaderField(i);
                
                if (mine == null) break;
                
                if("content-disposition".equals(conn.getHeaderFieldKey(i).toLowerCase())){
                	Matcher m = Pattern.compile(".*filename=(.*)").matcher(mine.toLowerCase());
					if(m.find()) return m.group(1);
				}
            }
            
            filename = UUID.randomUUID()+ ".tmp";//默认取一个文件名
        }
        
        return filename;
    }
	
    /**
     *  开始下载文件
     * @param listener 监听下载数量的变化,如果不需要了解实时下载的数量,可以设置为null
     * @return 已下载文件大小
     * @throws Exception
     */
    public int download(DownloadProgressListener listener) throws Exception{
        try {
            RandomAccessFile randOut = new RandomAccessFile(this.saveFile, "rw");
            if(this.fileSize>0) randOut.setLength(this.fileSize);
            randOut.close();
            URL url = new URL(this.downloadUrl);
            
            if(this.data.size() != this.threads.length){
                this.data.clear();
                
                for (int i = 0; i < this.threads.length; i++) {
                    this.data.put(i+1, 0);//初始化每条线程已经下载的数据长度为0
                }
            }
            
            for (int i = 0; i < this.threads.length; i++) {//开启线程进行下载
                int downLength = this.data.get(i+1);
                
                if(downLength < this.block && this.downloadSize<this.fileSize){//判断线程是否已经完成下载,否则继续下载    
                    this.threads[i] = new DownloadThread(this, url, this.saveFile, this.block, this.data.get(i+1), i+1);
                    this.threads[i].setPriority(7);
                    this.threads[i].start();
                }else{
                    this.threads[i] = null;
                }
            }
            
            this.fileService.save(this.downloadUrl, this.data);
            boolean notFinish = true;//下载未完成
            
            while (notFinish) {// 循环判断所有线程是否完成下载
                Thread.sleep(900);
                notFinish = false;//假定全部线程下载完成
                
                for (int i = 0; i < this.threads.length; i++){
                    if (this.threads[i] != null && !this.threads[i].isFinish()) {//如果发现线程未完成下载
                        notFinish = true;//设置标志为下载没有完成
                        
                        if(this.threads[i].getDownLength() == -1){//如果下载失败,再重新下载
                            this.threads[i] = new DownloadThread(this, url, this.saveFile, this.block, this.data.get(i+1), i+1);
                            this.threads[i].setPriority(7);
                            this.threads[i].start();
                        }
                    }
                }    
                
                if(listener!=null) listener.onDownloadSize(this.downloadSize);//通知目前已经下载完成的数据长度
            }
            
            fileService.delete(this.downloadUrl);
        } catch (Exception e) {
            print(e.toString());
            throw new Exception("file download fail");
        }
        return this.downloadSize;
    }
    
    
    /**
     * 获取Http响应头字段
     * @param http
     * @return
     */
    public static Map<String, String> getHttpResponseHeader(HttpURLConnection http) {
        Map<String, String> header = new LinkedHashMap<String, String>();
        
        for (int i = 0;; i++) {
            String mine = http.getHeaderField(i);
            if (mine == null) break;
            header.put(http.getHeaderFieldKey(i), mine);
        }
        
        return header;
    }
    
    /**
     * 打印Http头字段
     * @param http
     */
    public static void printResponseHeader(HttpURLConnection http){
        Map<String, String> header = getHttpResponseHeader(http);
        
        for(Map.Entry<String, String> entry : header.entrySet()){
            String key = entry.getKey()!=null ? entry.getKey()+ ":" : "";
            print(key+ entry.getValue());
        }
    }

    private static void print(String msg){
        Log.i(TAG, msg);
    }
	
}
