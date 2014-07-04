package Utility;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import android.util.Log;

public class Packet {

// uint32 flag;// 0x53535353
// uint32 src_ip;// 源IP
// uint32 dst_ip;// 目的IP
// uint16 sequence; // 包的序列号
// uint16 cmd;// 消息类型
// uint16 total_pack;// 总的包数,按每个包1K字节进行拆分
// uint16 cur_pack;// 当前包数
// uint16 body_len;// 包的数据长度
	
	
//	struct PackTail
//	{
//		uint16 check_sum; //从序列号到包体的校验和
//		uint32 flag; //0x10101010
//	};
	
//	struct ReqClientLogin
//	{
//	    char user[20];
//	    char passwd[20];
//	};

	private byte[] buf = null;
	
	public  char[] getChars () {
	      Charset cs = Charset.forName ("UTF-8");
	      ByteBuffer bb = ByteBuffer.allocate (buf.length);
	      bb.put (buf);
	      bb.flip ();
	      CharBuffer cb = cs.decode (bb);
	  
	   return cb.array();
	}

	private static byte[] int2LH(int n) {
		byte[] b = new byte[4];
		b[0] = (byte) (n & 0xff);
		b[1] = (byte) (n >> 8 & 0xff);
		b[2] = (byte) (n >> 16 & 0xff);
		b[3] = (byte) (n >> 24 & 0xff);
		return b;
	}

	private static byte[] short2LH(int n) {
		byte[] b = new byte[2];
		b[0] = (byte) (n & 0xff);
		b[1] = (byte) (n >> 8 & 0xff);
		return b;
	}

	public Packet(int cmd,String body) {
		byte[] temp = null;
		short checksum=0;
		
		int headLen = 22;
		int bodyLen = body.length();
		int tailLen = 6;

		buf = new byte[headLen+tailLen+bodyLen];
		temp = int2LH(0x53535353);
		System.arraycopy(temp, 0, buf, 0, temp.length);// flag;

		temp = int2LH(0);
		System.arraycopy(temp, 0, buf, 4, temp.length);// src IP

		temp = int2LH(0);
		System.arraycopy(temp, 0, buf, 8, temp.length);// dst IP

		temp = short2LH(0);
		System.arraycopy(temp, 0, buf, 12, temp.length);// 包的序列号

		temp = short2LH(cmd);
		System.arraycopy(temp, 0, buf, 14, temp.length);// 消息类型

		temp = short2LH(1);
		System.arraycopy(temp, 0, buf, 16, temp.length);// 总的包数

		temp = short2LH(1);
		System.arraycopy(temp, 0, buf, 18, temp.length);// 当前包数

		temp = short2LH(bodyLen);
		System.arraycopy(temp, 0, buf, 20, temp.length);// 包的数据长度
		
		System.arraycopy(body.getBytes(), 0, buf, headLen, bodyLen);
		
		for(int i=0;i<(headLen+bodyLen-4);i++)
		{
			short tmp;
			tmp = (short) (0xFF & ((short) buf[4+i]));
			checksum = (short) (checksum + tmp);
		}
		Log.e("check", " "+checksum);
		
		temp = short2LH(checksum);
		System.arraycopy(temp, 0, buf, headLen+bodyLen, temp.length);// checksum
		
		temp = int2LH(0x10101010);
		System.arraycopy(temp, 0, buf, headLen+bodyLen+2, temp.length);// flag;
	}
	
	public Packet(String user,String passWord) {
		byte[] temp = null;
		short checksum=0;
		
		int headLen = 22;
		int bodyLen = 40;
		int tailLen = 6;

		buf = new byte[headLen+tailLen+bodyLen];
		temp = int2LH(0x53535353);
		System.arraycopy(temp, 0, buf, 0, temp.length);// flag;

		temp = int2LH(0);
		System.arraycopy(temp, 0, buf, 4, temp.length);// src IP

		temp = int2LH(0);
		System.arraycopy(temp, 0, buf, 8, temp.length);// dst IP

		temp = short2LH(0);
		System.arraycopy(temp, 0, buf, 12, temp.length);// 包的序列号

		temp = short2LH(1);
		System.arraycopy(temp, 0, buf, 14, temp.length);// 消息类型

		temp = short2LH(1);
		System.arraycopy(temp, 0, buf, 16, temp.length);// 总的包数

		temp = short2LH(1);
		System.arraycopy(temp, 0, buf, 18, temp.length);// 当前包数

		temp = short2LH(bodyLen);
		System.arraycopy(temp, 0, buf, 20, temp.length);// 包的数据长度
		
		System.arraycopy(user.getBytes(), 0, buf, headLen, user.length());
		
		System.arraycopy(passWord.getBytes(), 0, buf, headLen+20, passWord.length());
		
		for(int i=0;i<(headLen+bodyLen-4);i++)
		{
			short tmp;
			tmp = (short) (0xFF & ((short) buf[4+i]));
			checksum = (short) (checksum + tmp);
		}
		Log.e("check", " "+checksum);
		
		temp = short2LH(checksum);
		System.arraycopy(temp, 0, buf, headLen+bodyLen, temp.length);// checksum
		
		temp = int2LH(0x10101010);
		System.arraycopy(temp, 0, buf, headLen+bodyLen+2, temp.length);// flag;

	}

	public byte[] getBuf() {
		return buf;
	}
	
	//return cmd type
	public static int checkRecInfo(char [] RecBuf,int recLength,char [] InfoBuf)
	{
		int headLen = 22;
		int tailLen = 6;
		int InfoLength=0;
        int len;
        int cmd;
		
		if(recLength<headLen+tailLen)
			return -1;
		if(RecBuf[0]!=0x53 && RecBuf[1]!=0x53 && RecBuf[2]!=0x53 && RecBuf[3]!=0x53)
			return -1;
		
		InfoLength = (RecBuf[20]&0xff)+((RecBuf[21]&0xff)<<8);
		
        if((InfoLength+headLen+tailLen)!= recLength)
        {
        	int temp = InfoLength+headLen+tailLen;
        	Log.e("check", recLength+" "+temp);
        	return -1;
        }
        
        cmd = (RecBuf[14]&0xff)+((RecBuf[15]&0xff)<<8);
        
        System.arraycopy(RecBuf, headLen, InfoBuf, 0, InfoLength);		
		return cmd;
	}

}
