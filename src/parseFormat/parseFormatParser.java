package parseFormat;

import common.Utils;
import struct.HeaderType;

public class parseFormatParser {

	public static void showDexHeader(byte[] byteSrc){
		HeaderType mHeaderType = new HeaderType();
		byte[] magic = Utils.copyByte(byteSrc, 0, 8);
		mHeaderType.magic = magic;
		
		System.out.println(mHeaderType.toString());
	}
	
	
}
