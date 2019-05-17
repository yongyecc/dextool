package struct;

public class ClassDataItem {

	/**
	 *  uleb128 unsigned little-endian base 128
		struct class_data_item
		{
			uleb128 static_fields_size;
			uleb128 instance_fields_size;
			uleb128 direct_methods_size;
			uleb128 virtual_methods_size;
			encoded_field static_fields [ static_fields_size ];
			encoded_field instance_fields [ instance_fields_size ];
			encoded_method direct_methods [ direct_method_size ];
			encoded_method virtual_methods [ virtual_methods_size ];
		}
	 */
	//uleb128值用来表示int
	public int static_fields_size;
	public int instance_fields_size;
	public int direct_methods_size;
	public int virtual_methods_size;
	
	public EncodedField[] static_fields;
	public EncodedField[] instance_fields;
	public EncodedMethod[] direct_methods;
	public EncodedMethod[] virtual_methods;
}
