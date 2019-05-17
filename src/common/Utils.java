package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import struct.HeaderType;

public class Utils {
	
	public static int FT_APK = 0;
	
	public static int FT_DEX = 1;

	
	/**
	 * 读取文件头magic来判断文件类型
	 * 
	 * @param filePath 需要检测文件的路径
	 * @return	返回代表不同文件类型的int值
	 */
	public static int getFileType(String filePath) {
		int ft;
		byte[] bt = new byte[4];
		String fMagic;
		try {
			FileInputStream fileInputStream = new FileInputStream(filePath);
			fileInputStream.read(bt, 0, 4);
			fMagic = new String(bt);
			if(fMagic.startsWith("PK"))
				return FT_APK;
			if(fMagic.startsWith("dex"))
				return FT_DEX;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return FT_DEX;
	}
	
	/**
	 * 在src字节数组中，以start为起始下标，复制长度为len个字节并将其返回
	 * 
	 * @param src	需要被复制的字节数组
	 * @param start	被复制的起始位置
	 * @param len	被复制的字节的长度
	 * @return	被复制的字节数组
	 */
	public static byte[] copyByte(byte[] src, int start, int len){
		if(src == null){
			return null;
		}
		if(start > src.length){
			return null;
		}
		if((start+len) > src.length){
			return null;
		}
		if(start<0){
			return null;
		}
		if(len<=0){
			return null;
		}
		byte[] resultByte = new byte[len];
		for(int i=0;i<len;i++){
			resultByte[i] = src[i+start];
		}
		return resultByte;
	}
	
	/**
	 * 将字节数组转成16进制的字符串
	 * 
	 * @param src	需要转换的字节数组
	 * @return	返回的十六进制字符串
	 */
	public static String bytesToHexString(byte[] src){  
		StringBuilder stringBuilder = new StringBuilder("");  
		if (src == null || src.length <= 0) {  
			return null;  
		}  
		for (int i = 0; i < src.length; i++) {  
			//限制在0-255范围内
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);  
			if (hv.length() < 2) {  
				stringBuilder.append(0);  
			}  
			stringBuilder.append(hv+" "); 
		}  
		return stringBuilder.toString();  
	}  
	
	/**
	 * 将int型数据转成字节数组。
	 * 利用int型除法的商只取正数位(不四舍五入)，所以想要得到一个字节二进制位数必须大于等于8小于16，所以
	 * 计算从左到右有多少个连续的0，来确定这个int型参数的二进制位数，然后再用40(32+8)来减去这个位数，
	 * 即int型参数的二进制位数加8。这样就可以保证如果int参数在0-8位二进制数时，载加上8刚好在8-16区间，
	 * 
	 * @param integer	需要转换的int参数
	 * @return	需要返回的字节数组
	 */
	public static byte[] int2Byte(final int integer) {
		//计算出参数占用几个字节
		int byteNum = (40 -Integer.numberOfLeadingZeros (integer < 0 ? ~integer : integer))/ 8;
		byte[] byteArray = new byte[4];
		for (int n = 0; n < byteNum; n++)
			byteArray[3 - n] = (byte) (integer>>> (n * 8));
		return (byteArray);
	}
	
	/**
	 * 将byte数组的内容转成int型
	 * 
	 * @param res	需要被转化的字节数组
	 * @return	返回转换完成的int型的值
	 */
	public static int byte2int(byte[] res) { 
		int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00)
				| ((res[2] << 24) >>> 8) | (res[3] << 24); 
		return targets; 
	}
	
	/**
	 * 将byte数组的内容转成short型(长度为2字节)
	 * 
	 * @param b
	 * @return
	 */
	public static short byte2Short(byte[] b) { 
        short s = 0; 
        short s0 = (short) (b[0] & 0xff);
        short s1 = (short) (b[1] & 0xff); 
        s1 <<= 8; 
        s = (short) (s0 | s1); 
        return s; 
    }
	
	/**
	 * 将提供的十六进制字符串倒序排放
	 * 
	 * @param hexstr	需要被排放的十六进制字符串
	 * @return	完成排放后的的十六进制字符串
	 */
	public static String reverseOrderHexStr(String hexstr) {
		String[] hexs = hexstr.split(" ");
		int len = hexs.length;
		String result = "";
		for(int i=hexs.length; i>0; i--) {
			int index = i - 1;
			result = result + hexs[index];
		}
		return result;
	}
	
	/**
	 * 根据提供的参数，在srcByte字节流中，从startOff偏移位置开始获取字节流并转成字符串，截至于0x00
	 * 
	 * @param srcByte	被取值的字节流
	 * @param startOff	取值的起始位置
	 * @return	被取的字节数组转换成字符串类型的值
	 */
	public static String getString(byte[] srcByte, int startOff){
		byte size = srcByte[startOff];
		byte[] strByte = Utils.copyByte(srcByte, startOff+1, size);
		String result = "";
		try{
			result = new String(strByte, "UTF-8");
		}catch(Exception e){
		}
		return result;
	}
	
	/**
	 * 读取C语言中的uleb类型,一直读到字节的最高位为1为止，将读取的所有字节返回，通过decodeUleb128方法将其解码成int值
	 * 
	 * 目的是解决整型数值浪费问题
	 * 长度不固定，在1~5个字节中浮动
	 * @param srcByte	需要读取的字节流
	 * @param offset	开始读取的偏移位置
	 * @return	1~5个字节组成的字节数组
	 */
	public static byte[] readUnsignedLeb128(byte[] srcByte, int offset){
		List<Byte> byteAryList = new ArrayList<Byte>();
		byte bytes = Utils.copyByte(srcByte, offset, 1)[0];
		byte highBit = (byte)(bytes & 0x80);
		byteAryList.add(bytes);
		offset ++;
		while(highBit != 0){
			bytes = Utils.copyByte(srcByte, offset, 1)[0];
			highBit = (byte)(bytes & 0x80);
			offset ++;
			byteAryList.add(bytes);
		}
		byte[] byteAry = new byte[byteAryList.size()];
		for(int j=0;j<byteAryList.size();j++){
			byteAry[j] = byteAryList.get(j);
		}
		return byteAry;
	}
	
	/**
	 * 解码leb128数据
	 * 每个字节去除最高位，然后进行拼接，重新构造一个int类型数值，从低位开始
	 * @param byteAry
	 * @return
	 */
	public static int decodeUleb128(byte[] byteAry) {
	    int result = 0;
		
	    if(byteAry.length == 1){
	    	return byteAry[0];
	    }
	    if(byteAry.length == 2){
	        result = (byteAry[0] & 0x7f) | ((byteAry[1] & 0x7f) << 7);
	        return result;
	    }
	    if(byteAry.length == 3){
	        result = (byteAry[0] & 0x7f) | ((byteAry[1] & 0x7f) << 7) | ((byteAry[2] & 0x7f) << 14);
	        return result;
	    }
	    if(byteAry.length == 4){
	    	result = (byteAry[0] & 0x7f) | ((byteAry[1] & 0x7f) << 7) | ((byteAry[2] & 0x7f) << 14) | ((byteAry[3] & 0x7f) << 21);
	        return result;
	    }
        if(byteAry.length == 5){
        	result = (byteAry[0] & 0x7f) | ((byteAry[1] & 0x7f) << 7) | ((byteAry[2] & 0x7f) << 14) | ((byteAry[3] & 0x7f) << 21) | ((byteAry[4] & 0x7f) << 28);
            return result;
        }
        return result;
	}
}
