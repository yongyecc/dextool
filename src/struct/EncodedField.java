package struct;

public class EncodedField {

	/**
	 * struct encoded_field
		{
			uleb128 filed_idx_diff; // index into filed_ids for ID of this filed
			uleb128 access_flags; // access flags like public, static etc.
		}
	 */
	public byte[] filed_idx_diff;
	public byte[] access_flags;
	
}
