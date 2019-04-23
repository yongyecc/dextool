package parseFormat;

import common.Utils;
import struct.HeaderType;

public class parseFormatParser {

	public static void showDexHeader(byte[] byteSrc){
		HeaderType headerType = new HeaderType();
		byte[] magic = Utils.copyByte(byteSrc, 0, 8);
		headerType.magic = magic;
		byte[] checksumByte = Utils.copyByte(byteSrc, 8, 4);
		headerType.checksum = Utils.bytesToHexString(checksumByte);
		System.out.println(headerType.toString());
	}
	
	
}
