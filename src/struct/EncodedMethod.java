package struct;

public class EncodedMethod {

	/**
	 * struct encoded_method
		{
			uleb128 method_idx_diff;
			uleb128 access_flags;
			uleb128 code_off;
		}
	 */
	/** 注意：如果一个类中包含直接方法或者虚方法超过一个，那么每个方法的索引值method_idx_diff是前面所有方法索引值的和*/
	public byte[] method_idx_diff;
	public byte[] access_flags;
	public byte[] code_off;
	
}
