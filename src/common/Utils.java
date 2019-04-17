package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

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
	
	
	
}
