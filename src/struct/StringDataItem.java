package struct;

import java.util.ArrayList;
import java.util.List;

public class StringDataItem {

	/**
	 * struct string_data_item
		{
		uleb128 utf16_size;
		ubyte data;
		}
	 */
	public List<Byte> utf16_size = new ArrayList<Byte>();
	public byte data;
	
}
