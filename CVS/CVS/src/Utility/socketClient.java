package Utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class socketClient extends Thread {
	private String ip = "210.32.159.42";
	private int port = 8123;
	private String TAG = "socketthread";
	private int timeout = 10000;
	InetSocketAddress endpoint = new InetSocketAddress(ip , port); 
	public Socket client = null;
	DataOutputStream output;
	BufferedReader in;
	public boolean isRun = true;
	Handler loginHandler;
	private static socketClient soC;

	 public static socketClient getInstance() {  
	       if (soC == null) {  
	    	   soC = new socketClient();  
	       }  
	       return soC;  
	    } 
	 
	 public static void destroyInstance() {  
	       if (soC != null) {  
	    	   soC = null;  
	       }
	    } 
	 
	 public void setLoginHandler(Handler han)
	 {
		 loginHandler = han;
	 } 

	/**
	 * 连接socket服务器
	 */
	public void conn() {

		try {
			Log.i(TAG, "连接中……");
			client = new Socket();
			client.setSoTimeout(timeout);// 设置阻塞时间
			client.connect(endpoint);
			in = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			output = new DataOutputStream(client.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
			conn();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 实时接受数据
	 */
	@Override
	public void run() {
		conn();
		String line = "";
		char[] charBuf = new char[2048];  
		char[] infoBuf = new char[2048];  
		while (isRun) {
			try {
				if (client != null) {
					int val;					
					//while((val = in.read(charBuf))!=-1)
					if((val = in.read(charBuf))!=-1)
					{
						int rval = Packet.checkRecInfo(charBuf,val,infoBuf);
						switch (rval)
						{
						case 1:
							break;
						case 2:
							int flag = (infoBuf[0]&0xff)+((infoBuf[1]&0xff)<<8)+((infoBuf[1]&0xff)<<16)+((infoBuf[1]&0xff)<<24);
							if(flag == 0)
							{
								loginHandler.sendEmptyMessage(0);
								
							}else
							{
								loginHandler.sendEmptyMessage(0);
							}
							Log.e("check","get: "+rval+" "+flag+" "+Arrays.toString(infoBuf));
							break;
						default:
							break;
						}
						
						
					}
				} else {
					conn();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 发送字节流
	 * 
	 * @param mess
	 */
	public void Send(byte [] byteArr) {
		try {
			if (client != null) {
                output.write(byteArr);
                output.flush();

			} else {
				conn();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}
	


	/**
	 * 关闭连接
	 */
	public void close() {
		try {
			if (client != null) {
				in.close();;
				output.close();
				client.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
