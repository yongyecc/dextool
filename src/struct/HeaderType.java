package struct;

public class HeaderType {
	
	/**
	 * struct header_item
		{
		ubyte[8] magic;
		unit checksum;
		ubyte[20] siganature;
		uint file_size;
		uint header_size;
		unit endian_tag;
		uint link_size;
		uint link_off;
		uint map_off;
		uint string_ids_size;
		uint string_ids_off;
		uint type_ids_size;
		uint type_ids_off;
		uint proto_ids_size;
		uint proto_ids_off;
		uint method_ids_size;
		uint method_ids_off;
		uint class_defs_size;
		uint class_defs_off;
		uint data_size;
		uint data_off;
		}
	 */
	public byte[] magic = new byte[8];
	
	public int checksum;
	
	public byte[] siganature = new byte[20];
	
	public int file_size;
	
	public int header_size;
	
	public int endian_tag;
	
	public int link_size;
	
	public int link_off;
	
	public int map_off;
	
	public int string_ids_size;
	
	public int string_ids_off;
	
	public int type_ids_size;
	
	public int type_ids_off;
	
	public int proto_ids_size;
	
	public int proto_ids_off;
	
	public int field_ids_size;
	
	public int field_ids_off;
	
	public int method_ids_size;
	
	public int method_ids_off;
	
	public int class_defs_size;
	
	public int class_defs_off;
	
	public int data_size;
	
	public int data_off;

	@Override
	public String toString(){
			
		return null;
	}

	
}
