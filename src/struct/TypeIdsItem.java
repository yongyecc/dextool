package struct;

import common.Utils;

public class TypeIdsItem {
	
	/**
	 * struct type_ids_item
		{
		uint descriptor_idx;
		}
	 */
	//类名在StringIds区段里位置的下标
	public int descriptor_idx;

	public static int getSize(){
		return 4;
	}
	
	@Override
	public String toString(){
		return Utils.bytesToHexString(Utils.int2Byte(descriptor_idx));
	}
}
