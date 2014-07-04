package Utility;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import android.util.Log;

public class Packet {

// uint32 flag;// 0x53535353
// uint32 src_ip;// ԴIP
// uint32 dst_ip;// Ŀ��IP
// uint16 sequence; // �������к�
// uint16 cmd;// ��Ϣ����
// uint16 total_pack;// �ܵİ���,��ÿ����1K�ֽڽ��в��
// uint16 cur_pack;// ��ǰ����
// uint16 body_len;// �������ݳ���
	
	
//	struct PackTail
//	{
//		uint16 check_sum; //�����кŵ������У���
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
		System.arraycopy(temp, 0, buf, 12, temp.length);// �������к�

		temp = short2LH(cmd);
		System.arraycopy(temp, 0, buf, 14, temp.length);// ��Ϣ����

		temp = short2LH(1);
		System.arraycopy(temp, 0, buf, 16, temp.length);// �ܵİ���

		temp = short2LH(1);
		System.arraycopy(temp, 0, buf, 18, temp.length);// ��ǰ����

		temp = short2LH(bodyLen);
		System.arraycopy(temp, 0, buf, 20, temp.length);// �������ݳ���
		
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
		System.arraycopy(temp, 0, buf, 12, temp.length);// �������к�

		temp = short2LH(1);
		System.arraycopy(temp, 0, buf, 14, temp.length);// ��Ϣ����

		temp = short2LH(1);
		System.arraycopy(temp, 0, buf, 16, temp.length);// �ܵİ���

		temp = short2LH(1);
		System.arraycopy(temp, 0, buf, 18, temp.length);// ��ǰ����

		temp = short2LH(bodyLen);
		System.arraycopy(temp, 0, buf, 20, temp.length);// �������ݳ���
		
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
