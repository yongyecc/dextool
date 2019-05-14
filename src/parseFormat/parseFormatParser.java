package parseFormat;

import java.util.ArrayList;
import java.util.List;

import common.Utils;
import struct.HeaderType;
import struct.ProtoIdsItem;
import struct.StringIdsItem;
import struct.TypeIdsItem;

public class parseFormatParser {
	
	private static int stringIdsSize = 0;
	private static int stringIdOffset = 0;
	private static int typeIdsSize = 0;
	private static int typeIdsOffset = 0;
	private static int protoIdsSize = 0;
	private static int protoIdsOffset = 0;
	private static int fieldIdsSize = 0;
	private static int fieldIdsOffset = 0;
	private static int methodIdsSize = 0;
	private static int methodIdsOffset = 0;
	private static int classIdsSize = 0;
	private static int classIdsOffset = 0;
	private static int dataIdsSize = 0;
	private static int dataIdsOffset = 0;
	private static int mapListOffset = 0;
	private static List<StringIdsItem> stringIdsList = new ArrayList<StringIdsItem>();
	private static List<String> stringList = new ArrayList<String>();
	private static List<TypeIdsItem> typeIdsList = new ArrayList<TypeIdsItem>();
	private static List<ProtoIdsItem> protoIdsList = new ArrayList<ProtoIdsItem>();
	
	private static List<String> parameterList;
	
	/**
	 * 根据传递的byte流dex文件数据，解析dex文件头，并打印出来
	 * 
	 * @param byteSrc	被解析的dex文件的字节流数据
	 */
	public static void showDexHeader(byte[] byteSrc){
		HeaderType headerType = new HeaderType();
		//magic：8字节
		byte[] magic = Utils.copyByte(byteSrc, 0, 8);
		headerType.magic = magic;
		
		//checksum：4字节
		byte[] checksumByte = Utils.copyByte(byteSrc, 8, 4);
		String checksum_hex = Utils.reverseOrderHexStr(Utils.bytesToHexString(checksumByte));
		headerType.checksum = Utils.bytesToHexString(checksumByte) + "(" + checksum_hex + "h)";
		
		//signature：20字节
		byte[] siganature = Utils.copyByte(byteSrc, 12, 20);
		headerType.siganature = siganature;
		
		//file_size：4字节
		byte[] fileSizeByte = Utils.copyByte(byteSrc, 32, 4);
		headerType.file_size = Utils.byte2int(fileSizeByte);
		
		//header_size：4字节
		byte[] headerSizeByte = Utils.copyByte(byteSrc, 36, 4);
		headerType.header_size = Utils.byte2int(headerSizeByte);
		
		//endian_tag：4字节
		byte[] endianTagByte = Utils.copyByte(byteSrc, 40, 4);
		String endian_tag_hexstr = Utils.reverseOrderHexStr(Utils.bytesToHexString(endianTagByte));
		headerType.endian_tag = Utils.bytesToHexString(endianTagByte) + "(" + endian_tag_hexstr + "h)";
		
		//静态链接link_size： 4字节
		byte[] linkSizeByte = Utils.copyByte(byteSrc, 44, 4);
		headerType.link_size = Utils.byte2int(linkSizeByte);
		
		//link_off： 4字节
		byte[] linkOffByte = Utils.copyByte(byteSrc, 48, 4);
		headerType.link_off = Utils.byte2int(linkOffByte);
		
		//文件开头到 data 区段的偏移量,map_off： 4字节
		byte[] mapOffByte = Utils.copyByte(byteSrc, 52, 4);
		headerType.map_off = Utils.byte2int(mapOffByte);
		
		//string_ids_size: 
		byte[] stringIdsSizeByte = Utils.copyByte(byteSrc, 56, 4);
		headerType.string_ids_size = Utils.byte2int(stringIdsSizeByte);
		
		//string_ids_off
		byte[] stringIdsOffByte = Utils.copyByte(byteSrc, 60, 4);
		headerType.string_ids_off = Utils.byte2int(stringIdsOffByte);
		
		//所有类型：type_ids_size
		byte[] typeIdsSizeByte = Utils.copyByte(byteSrc, 64, 4);
		headerType.type_ids_size = Utils.byte2int(typeIdsSizeByte);
		
		//type_ids_off
		byte[] typeIdsOffByte = Utils.copyByte(byteSrc, 68, 4);
		headerType.type_ids_off = Utils.byte2int(typeIdsOffByte);
		
		//方法原型proto_ids_size
		byte[] protoIdsSizeByte = Utils.copyByte(byteSrc, 72, 4);
		headerType.proto_ids_size = Utils.byte2int(protoIdsSizeByte);
		
		//proto_ids_off
		byte[] protoIdsOffByte = Utils.copyByte(byteSrc, 76, 4);
		headerType.proto_ids_off = Utils.byte2int(protoIdsOffByte);
		
		//field_ids_size
		byte[] fieldIdsSizeByte = Utils.copyByte(byteSrc, 80, 4);
		headerType.field_ids_size = Utils.byte2int(fieldIdsSizeByte);
		
		//field_ids_off
		byte[] fieldIdsOffByte = Utils.copyByte(byteSrc, 84, 4);
		headerType.field_ids_off = Utils.byte2int(fieldIdsOffByte);
		
		//method_ids_size
		byte[] methodIdsSizeByte = Utils.copyByte(byteSrc, 88, 4);
		headerType.method_ids_size = Utils.byte2int(methodIdsSizeByte);
		
		//method_ids_off
		byte[] methodIdsOffByte = Utils.copyByte(byteSrc, 92, 4);
		headerType.method_ids_off = Utils.byte2int(methodIdsOffByte);
		
		//每个类的各种信息class_defs_size
		byte[] classDefsSizeByte = Utils.copyByte(byteSrc, 96, 4);
		headerType.class_defs_size = Utils.byte2int(classDefsSizeByte);
		
		//class_defs_off
		byte[] classDefsOffByte = Utils.copyByte(byteSrc, 100, 4);
		headerType.class_defs_off = Utils.byte2int(classDefsOffByte);
		
		//data_size
		byte[] dataSizeByte = Utils.copyByte(byteSrc, 104, 4);
		headerType.data_size = Utils.byte2int(dataSizeByte);
		
		//data_off
		byte[] dataOffByte = Utils.copyByte(byteSrc, 108, 4);
		headerType.data_off = Utils.byte2int(dataOffByte);
		
		stringIdsSize = headerType.string_ids_size;
		stringIdOffset = headerType.string_ids_off;
		typeIdsSize = headerType.type_ids_size;
		typeIdsOffset = headerType.type_ids_off;
		protoIdsSize = headerType.proto_ids_size;
		protoIdsOffset = headerType.proto_ids_off;
		fieldIdsSize = headerType.field_ids_size;
		fieldIdsOffset = headerType.field_ids_off;
		methodIdsSize = headerType.method_ids_size;
		methodIdsOffset = headerType.method_ids_off;
		classIdsSize = headerType.class_defs_size;
		classIdsOffset = headerType.class_defs_off;
		mapListOffset = headerType.map_off;
		
		System.out.println(headerType.toString());
	}
	
	/**
	 * 解析dex文件格式中的stringids段，获取string_ids_item结构体。
	 * StringIdsItem对象对应string_ids_item结构体，使用StringIdsItem列表将dex文件中所有string_ids_item结构体保存。
	 * string_ids_item结构体只存有StringDataItem结构体的偏移地址，具体数据存放在数据区域
	 * 
	 * @param byteSrc 被解析的dex文件的字节流数据
	 */
	public static void parseStringIds(byte[] byteSrc){
		byte[] idsByte;
		int idSize = StringIdsItem.getSize();
		int countIds = stringIdsSize;
		System.out.println("Total " + String.valueOf(countIds) + " strings\n");
		for(int i=0;i<countIds;i++){
			StringIdsItem item = new StringIdsItem();
			idsByte = Utils.copyByte(byteSrc, stringIdOffset+i*idSize, idSize);
			item.string_data_off = Utils.byte2int(idsByte);
			stringIdsList.add(item);
		}
	}
	//解析StringDataItem结构体,获取里面的字符串数据,并打印出来
	public static void parseStringList(byte[] srcByte){
		int i=1;
		for(StringIdsItem item : stringIdsList){
			String str = Utils.getString(srcByte, item.string_data_off);
			System.out.println(str);
			i++;
			stringList.add(str);
		}
	}
	
	/**
	 * 解析出dex文件中用到的所有类型，这个区段保存有DexTypeId结构体，这个结构体只有一个属性，是一个索引值，指向stringids段的字符串
	 * 
	 * @param srcByte	被解析的dex文件的字节流数据
	 */
	public static void parseTypeIds(byte[] srcByte){
		int idSize = TypeIdsItem.getSize();
		int countIds = typeIdsSize;
		System.out.println("Total " + String.valueOf(countIds) + " types\n");
		for(int i=0;i<countIds;i++){
			TypeIdsItem item = new TypeIdsItem();
			byte[] descriptorIdxByte = Utils.copyByte(Utils.copyByte(srcByte, typeIdsOffset+i*idSize, idSize), 0, 4);
			item.descriptor_idx = Utils.byte2int(descriptorIdxByte);
			typeIdsList.add(item);
		}
		for(TypeIdsItem item : typeIdsList){
			System.out.println(String.valueOf(item.descriptor_idx) + " -->\tString_id[" + 
		String.valueOf(item.descriptor_idx) + "] -->\t" + stringList.get(item.descriptor_idx));
		}
	}
	
	/**
	 * 解析出所有方法原型，由DexProtoId 结构体组成。
	 * DexProtoId结构体包含：
	 * 		1. 指向Stringids段的索引值，指向的字符串内容为：返回类型+参数类型(eg: II 原型为:int (int int); L 原型为: Ljava/lang/String()),
	 * 		2. 指向Type_ids段的索引值，最终指向的字符串保存的内容为：返回类型
	 * 		3. 指向存有参数信息的结构体(DexTypeItem)的偏移
	 * @param srcByte	被解析的dex文件的字节流数据
	 */
	public static void parseProtoIds(byte[] srcByte){
		int idSize = ProtoIdsItem.getSize();
		int countIds = protoIdsSize;
		System.out.println("Total " + String.valueOf(countIds) + " 方法原型\n");
		for(int i=0;i<countIds;i++){
			ProtoIdsItem item = new ProtoIdsItem();
			byte[] itemByte = Utils.copyByte(srcByte, protoIdsOffset+i*idSize, idSize);
			byte[] shortyIdxByte = Utils.copyByte(itemByte, 0, 4);
			item.shorty_idx = Utils.byte2int(shortyIdxByte);
			byte[] returnTypeIdxByte = Utils.copyByte(itemByte, 4, 8);
			item.return_type_idx = Utils.byte2int(returnTypeIdxByte);
			byte[] parametersOffByte = Utils.copyByte(itemByte, 8, 4);
			item.parameters_off = Utils.byte2int(parametersOffByte);
			protoIdsList.add(item);
		}
		for(ProtoIdsItem item : protoIdsList){
			if(item.parameters_off != 0) {
				item = parseParameterTypeList(srcByte, item.parameters_off, item);
				System.out.println(String.valueOf(item.shorty_idx) + "," + String.valueOf(item.return_type_idx) 
				+ "," + item.parameters_off + " -->\tString_id[" + String.valueOf(item.shorty_idx)  + "],Type_id[" + String.valueOf(item.return_type_idx) + "],"
				+ "{" + String.valueOf(item.parameterCount) + ", {" + parameterList.toString() + "} -->\t"
				+ stringList.get(item.shorty_idx) + ", " + stringList.get(typeIdsList.get(item.return_type_idx).descriptor_idx) + ", -->\t"
				+ stringList.get(item.shorty_idx) + " ()");
			}else {
				System.out.println(String.valueOf(item.shorty_idx) + "," + String.valueOf(item.return_type_idx) 
				+ ",0 -->\tString_id[" + String.valueOf(item.shorty_idx)  + "],Type_id[" + String.valueOf(item.return_type_idx) + "],0 parameters -->\t"
				+ stringList.get(item.shorty_idx) + ", " + stringList.get(typeIdsList.get(item.return_type_idx).descriptor_idx) + ", -->\t"
				+ stringList.get(item.shorty_idx) + " ()");
			}
		}
	}
	//如果存在参数，用parseParameterTypeList来解析参数
	private static ProtoIdsItem parseParameterTypeList(byte[] srcByte, int startOff, ProtoIdsItem item){
		byte[] sizeByte = Utils.copyByte(srcByte, startOff, 4);
		int size = Utils.byte2int(sizeByte);
		List<String> parametersList = new ArrayList<String>();
		List<Short> typeList = new ArrayList<Short>(size);
		for(int i=0;i<size;i++){
			byte[] typeByte = Utils.copyByte(srcByte, startOff+4+2*i, 2);
			typeList.add(Utils.byte2Short(typeByte));
		}
		parameterList = new ArrayList<>();
		for(int i=0;i<typeList.size();i++){
			int index = typeIdsList.get(typeList.get(i)).descriptor_idx;
			if(i == typeList.size()-1) {
				parameterList.add("Type_id["+String.valueOf(index) + "]");
			}else {
				parameterList.add("Type_id["+String.valueOf(index) + "],");
			}
			parametersList.add(stringList.get(index));
		}
		
		item.parameterCount = size;
		item.parametersList = parametersList;
		return item;
	}
}
