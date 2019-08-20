package struct;

public class EncodedField {

	/**
	 * struct encoded_field
		{
			uleb128 filed_idx_diff; // index into filed_ids for ID of this filed
			uleb128 access_flags; // access flags like public, static etc.
		}
	 */
	/** 注意：如果同一个方法中该结构体超过一个，后面的field索引值为前面所有索引值相加的结果*/
	public byte[] filed_idx_diff;
	public byte[] access_flags;
	
}
