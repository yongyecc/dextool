package struct;

import java.util.ArrayList;
import java.util.List;

public class TypeListItem {
	
//	struct DexTypeList {
//	    u4 size;             /* DexTypeItem的个数，即参数个数 */
//	    DexTypeItem list[size]; /* size个TypeList结构体 */
//	};
	
	public int typeItemCount;
	
	public List<TypeItem> typeitemList = new ArrayList<TypeItem>();

}
