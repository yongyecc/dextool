package struct;

import java.util.ArrayList;
import java.util.List;

public class ProtoIdsItem {

	/**
	 * struct proto_id_item
		{
		uint shorty_idx;
		uint return_type_idx;
		uint parameters_off;
		}
	 */
	
	public int shorty_idx;
	public int return_type_idx;
	public int parameters_off;
	
	public static int getSize(){
		return 4 + 4 + 4;
	}
	
	//存有参数信息的结构：参数的数量、DexTypeItem结构体
	//DexTypeItem结构体只有一个属性：大小为2字节的Type_ids区段的索引，最终指向的字符串就是参数的类型，如果parameters_off代表的偏移为0，则没有该结构体
	public List<String> parametersList = new ArrayList<String>();
	public int parameterCount;
	
	
}
