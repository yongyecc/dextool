package struct;

import common.Utils;

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
	
	public String checksum;
	
	public byte[] siganature = new byte[20];
	
	public int file_size;
	
	public int header_size;
	
	public String endian_tag;
	
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
		return "magic:\t\t\t"+Utils.bytesToHexString(magic)+"\n"
				+ "checksum:\t\t"+checksum + "\n"
				+ "siganature:\t\t"+Utils.bytesToHexString(siganature) + "\n"
				+ "file_size:\t\t"+file_size + "\n"
				+ "header_size:\t\t"+header_size + "\n"
				+ "endian_tag:\t\t"+endian_tag + "\n"
				+ "link_size:\t\t"+link_size + "\n"
				+ "link_off:\t\t"+ link_off + "\n"
				+ "map_off:\t\t"+ map_off + "\n"
				+ "string_ids_size:\t"+string_ids_size + "\n"
				+ "string_ids_off:\t\t"+ string_ids_off+ "\n"
				+ "type_ids_size:\t\t"+type_ids_size + "\n"
				+ "type_ids_off:\t\t"+ type_ids_off + "\n"
				+ "proto_ids_size:\t\t"+proto_ids_size + "\n"
				+ "proto_ids_off:\t\t"+ proto_ids_off + "\n"
				+ "field_ids_size:\t\t"+field_ids_size + "\n"
				+ "field_ids_off:\t\t"+ field_ids_off + "\n"
				+ "method_ids_size:\t"+method_ids_size + "\n"
				+ "method_ids_off:\t\t"+ method_ids_off + "\n"
				+ "class_defs_size:\t"+class_defs_size + "\n"
				+ "class_defs_off:\t\t"+ class_defs_off + "\n"
				+ "data_size:\t\t"+data_size + "\n"
				+ "data_off:\t\t"+ data_off;

	}

	
}
