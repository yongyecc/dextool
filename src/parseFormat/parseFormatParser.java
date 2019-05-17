package parseFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.rmi.CORBA.Util;

import common.Utils;
import struct.ClassCodeItem;
import struct.ClassDataItem;
import struct.ClassDefItem;
import struct.EncodedField;
import struct.EncodedMethod;
import struct.FieldIdsItem;
import struct.HeaderType;
import struct.MapItem;
import struct.MapList;
import struct.MethodIdsItem;
import struct.ProtoIdsItem;
import struct.StringIdsItem;
import struct.TypeIdsItem;
import struct.TypeItem;
import struct.TypeListItem;

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
	private static List<FieldIdsItem> fieldIdsList = new ArrayList<FieldIdsItem>();
	private static List<MethodIdsItem> methodIdsList = new ArrayList<MethodIdsItem>();
	private static List<ClassDefItem> classIdsList = new ArrayList<ClassDefItem>(); 
	
	private static List<String> parameterList;
	private static HashMap<String, ClassDefItem> classDataMap = new HashMap<String, ClassDefItem>();
	
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
	 * 解析dex文件格式中的stringids段，获取string_ids_item结构体，然后将解析的内容打印出来。
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
		int i=0;
		for(StringIdsItem item : stringIdsList){
			String str = Utils.getString(srcByte, item.string_data_off);
			System.out.println("String_id[" + String.valueOf(i) + "]\t-->\t" + str);
			i++;
			stringList.add(str);
		}
	}
	
	/**
	 * 解析出dex文件中用到的所有类型的类名。
	 * 这个区段保存有DexTypeId结构体，这个结构体只有一个属性，是一个索引值，指向stringids段的字符串，然后将解析的内容打印出来
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
		int index = 0;
		for(TypeIdsItem item : typeIdsList){
			System.out.println("Type_id[" + String.valueOf(index) + "]\t-->\t" +String.valueOf(item.descriptor_idx) + "\t-->\tString_id[" + 
		String.valueOf(item.descriptor_idx) + "]\t-->\t" + stringList.get(item.descriptor_idx));
			index++;
		}
	}
	
	/**
	 * 解析出所有方法原型，这个区段的方法原型信息由DexProtoId 结构体组成。
	 * DexProtoId结构体包含：
	 * 		1. 指向Stringids段的索引值，指向的字符串内容为：返回类型+参数类型(eg: II 原型为:int (int int); L 原型为: Ljava/lang/String()),
	 * 		2. 指向Type_ids段的索引值，最终指向的字符串保存的内容为：返回类型
	 * 		3. 指向存有参数信息的结构体(DexTypeItem)的偏移
	 * 
	 * 然后将解析的内容打印出来
	 * 
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
		int j = 0;
		for(ProtoIdsItem item : protoIdsList){
			if(item.parameters_off != 0) {
				item = parseParameterTypeList(srcByte, item.parameters_off, item);
				System.out.println("Proto_id[" + String.valueOf(j) + "]\t-->\t" 
				+ String.valueOf(item.shorty_idx) + "," + String.valueOf(item.return_type_idx) 
				+ "," + item.parameters_off + " -->\tString_id[" + String.valueOf(item.shorty_idx)  + "],Type_id[" + String.valueOf(item.return_type_idx) + "],"
				+ "{" + String.valueOf(item.parameterCount) + " parameters, {" + parameterList.toString() + "}");
			}else {
				System.out.println("Proto_id[" + String.valueOf(j) + "]\t-->\t" 
				+ String.valueOf(item.shorty_idx) + "," + String.valueOf(item.return_type_idx) 
				+ ",0 -->\tString_id[" + String.valueOf(item.shorty_idx)  + "],Type_id[" + String.valueOf(item.return_type_idx) + "],0 parameters");
			}
			j++;
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
				parameterList.add("Type_id["+typeList.get(i) + "]");
			parametersList.add(stringList.get(index));
		}
		item.parameterCount = size;
		item.parametersList = parametersList;
		return item;
	}
	
	/**
	 * 解析出所有类的属性内容，这个区段的类的属性由结构体 DexFieldId 组成，包含以下三个字段：
	 * 	1. classIdx，字段所属类
	 * 	2. typeIdx，字段类型
	 * 	3. nameIdx，字段名
	 * 
	 * 然后将解析的内容打印出来
	 * 
	 * @param srcByte	被解析的dex文件的字节流数据
	 */
	public static void parseFieldIds(byte[] srcByte){
		int idSize = FieldIdsItem.getSize();
		int countIds = fieldIdsSize;
		System.out.println("Total " + String.valueOf(countIds) + " fields(类的属性)\n");
		for(int i=0;i<countIds;i++){
			FieldIdsItem item = new FieldIdsItem();
			byte[] fieldItemByte = Utils.copyByte(srcByte, fieldIdsOffset+i*idSize, idSize); 
			byte[] classIdxByte = Utils.copyByte(fieldItemByte, 0, 2);
			item.class_idx = Utils.byte2Short(classIdxByte);
			byte[] typeIdxByte = Utils.copyByte(fieldItemByte, 2, 2);
			item.type_idx = Utils.byte2Short(typeIdxByte);
			byte[] nameIdxByte = Utils.copyByte(fieldItemByte, 4, 4);
			item.name_idx = Utils.byte2int(nameIdxByte);
			fieldIdsList.add(item);
		}
		int i = 0;
		for(FieldIdsItem item : fieldIdsList){
			int classIndex = typeIdsList.get(item.class_idx).descriptor_idx;
			int typeIndex = typeIdsList.get(item.type_idx).descriptor_idx;
			System.out.println("Field_id[" + String.valueOf(i)  + "]\t-->\t"
					+ String.valueOf(item.class_idx) + "," + String.valueOf(item.type_idx) + "," + String.valueOf(item.name_idx)
					+ "\t-->\tType_id[" +  String.valueOf(item.class_idx) + "],Type_id[" + String.valueOf(item.type_idx) + "],String_id[" + String.valueOf(item.name_idx) + "]\t-->\t"
					+ "class:"+stringList.get(classIndex)+"=>type:"+stringList.get(typeIndex)+"=>name:"+stringList.get(item.name_idx));
			i++;
		}
	}

	/**
	 * 解析出每个类的每个方法,这个区段的方法信息由结构体 DexMethodId 存储，主要包含以下三个属性：
	 * 	1. classIdx：方法所在类(指向DexTypeId列表的索引)
	 * 	2. protoIdx：方法原型(指向DexProtoId列表的索引)
	 *  3. nameIdx：方法名(指向DexStringId列表的索引)
	 * 
	 * 然后将解析的内容打印出来
	 * 
	 * @param srcByte	被解析的dex文件的字节流数据
	 */
	public static void parseMethodIds(byte[] srcByte){
		int idSize = MethodIdsItem.getSize();
		int countIds = methodIdsSize;
		System.out.println("Total " + String.valueOf(countIds) + " methods(类方法)\n");
		for(int i=0;i<countIds;i++){
			MethodIdsItem item = new MethodIdsItem();
			byte[] methodItemByte =  Utils.copyByte(srcByte, methodIdsOffset+i*idSize, idSize);
			byte[] classIdxByte = Utils.copyByte(methodItemByte, 0, 2);
			item.class_idx = Utils.byte2Short(classIdxByte);
			byte[] protoIdxByte = Utils.copyByte(methodItemByte, 2, 2);
			item.proto_idx = Utils.byte2Short(protoIdxByte);
			byte[] nameIdxByte = Utils.copyByte(methodItemByte, 4, 4);
			item.name_idx = Utils.byte2int(nameIdxByte);
			methodIdsList.add(item);
		}
		int i=0;
		for(MethodIdsItem item : methodIdsList){
			int classIndex = typeIdsList.get(item.class_idx).descriptor_idx;
			int returnIndex = protoIdsList.get(item.proto_idx).return_type_idx;
			String returnTypeStr = stringList.get(typeIdsList.get(returnIndex).descriptor_idx);
			int shortIndex = protoIdsList.get(item.proto_idx).shorty_idx;
			String shortStr = stringList.get(shortIndex);
			List<String> paramList = protoIdsList.get(item.proto_idx).parametersList;
			StringBuilder parameters = new StringBuilder();
			parameters.append(returnTypeStr+"(");
			for(String str : paramList){
				parameters.append(str+",");
			}
			parameters.append(")");
			System.out.println("Method_id[" + String.valueOf(i) + "]\t-->\t"
					+ String.valueOf(item.class_idx) + "," + String.valueOf(item.proto_idx) + "," + String.valueOf(item.name_idx) + "\t-->\t"
					+ "Type_id[" + String.valueOf(item.class_idx) + "],Proto_id[" + String.valueOf(item.proto_idx) + "],String_id[" + String.valueOf(item.name_idx) + "]\t-->\t"
					+ "class:"+stringList.get(classIndex)+"=>proto:"+parameters+"=>name:"+stringList.get(item.name_idx));
			i++;
		}
		
	}

	/**
	 * 解析出每个自定义类（不包含java、android的内置类，如：int、string、android.app.Activity等）的详细信息，这个区段的每个类的详细信息由结构体 ClassDefItem 组成，
	 * 主要包含以下属性：
	 * 	1. classIdx：类名，指向DexTypeId列表的索引 
	 *  2. accessFlags：访问标志，具体参考ClassDefItem内部参数
	 *  3. superclassIdx：父类类名，指向DexTypeId列表的索引 
	 *  4. interfacesOff：接口，指向DexTypeList结构体的偏移
	 *  5. sourceFileIdx：源代码文件信息，指向DexStringId列表的索引。如果此项缺失，则用0xFFFFFFFF表示NO_INDEX
	 *  6. annotationsOff：注解，指向DexAnnotationsDirectoryItem结构体的偏移
	 *  7. classDataOff：类的字段、方法的信息，指向DexClassData结构体的偏移 
	 * 	8. staticValuesOff：指向data区段的DexEncodedArray结构体的偏移
	 * 
	 * @param srcByte	被解析的dex文件的字节流数据
	 */
	public static void parseClassIds(byte[] srcByte){
		int idSize = ClassDefItem.getSize();
		int countIds = classIdsSize;
		System.out.println("Total " + String.valueOf(countIds) + " classes(自定义类)\n");
		for(int i=0;i<countIds;i++){
			ClassDefItem item = new ClassDefItem();
			byte[] classItemByte = Utils.copyByte(srcByte, classIdsOffset+i*idSize, idSize);
			byte[] classIdxByte = Utils.copyByte(classItemByte, 0, 4);
			item.class_idx = Utils.byte2int(classIdxByte);
			byte[] accessFlagsByte = Utils.copyByte(classItemByte, 4, 4);
			item.access_flags = Utils.byte2int(accessFlagsByte);
			byte[] superClassIdxByte = Utils.copyByte(classItemByte, 8, 4);
			item.superclass_idx = Utils.byte2int(superClassIdxByte);
			byte[] iterfacesOffByte = Utils.copyByte(classItemByte, 12, 4);
			item.iterfaces_off = Utils.byte2int(iterfacesOffByte);
			byte[] sourceFileIdxByte = Utils.copyByte(classItemByte, 16, 4);
			item.source_file_idx = Utils.byte2int(sourceFileIdxByte);
			byte[] annotationsOffByte = Utils.copyByte(classItemByte, 20, 4);
			item.annotations_off = Utils.byte2int(annotationsOffByte);
			byte[] classDataOffByte = Utils.copyByte(classItemByte, 24, 4);
			item.class_data_off = Utils.byte2int(classDataOffByte);
			byte[] staticValueOffByte = Utils.copyByte(classItemByte, 28, 4);
			item.static_value_off = Utils.byte2int(staticValueOffByte);
			classIdsList.add(item);
		}
		int index = 0;
		for(ClassDefItem item : classIdsList){
			//classid
			System.out.printf("Class #%d\n", index);
			int classIdx = item.class_idx;
			TypeIdsItem typeItem = typeIdsList.get(classIdx);
			String classIdxString = stringList.get(typeItem.descriptor_idx);
			System.out.printf("\tClass descriptor:%s\n", classIdxString);
			//access fla
			System.out.printf("\tAccess flags\t:0x%x\n", item.access_flags);
			//superclass id
			int superClassIdx = item.superclass_idx;
			TypeIdsItem superTypeItem = typeIdsList.get(superClassIdx);
			String superTypeString = stringList.get(superTypeItem.descriptor_idx);
			System.out.printf("\tSuperClass\t:%s\n", superTypeString);
			//Interfaces
			System.out.printf("\tInterfaces\t-\n");
			if(item.iterfaces_off != 0) {
				TypeListItem mTypeListItem = new TypeListItem();
				mTypeListItem.typeItemCount = Utils.byte2int(Utils.copyByte(srcByte, item.iterfaces_off, 4));
				byte[] typeListItemByte = Utils.copyByte(srcByte, item.iterfaces_off, 4+mTypeListItem.typeItemCount*2);
				for(int i=0; i<mTypeListItem.typeItemCount; i++) {
					TypeItem mTypeItem = new TypeItem();
					mTypeItem.typeidIndex = Utils.byte2Short(Utils.copyByte(typeListItemByte, 4+i*2, 2));
					mTypeListItem.typeitemList.add(mTypeItem);
					System.out.printf("\t\t#%s\t:%s\n", String.valueOf(i), stringList.get(typeIdsList.get(mTypeItem.typeidIndex).descriptor_idx));
				}
				
			}
			int sourceIdx = item.source_file_idx;
			String sourceFile = null;
			if(sourceIdx == -1) {
				sourceFile = "NO_INDEX";
			}else {
				sourceFile = stringList.get(sourceIdx);
			}
			if(sourceIdx == -1) {
				System.out.printf("\tSource File\t:(0x%X)%s\n", item.source_file_idx, sourceFile);
			}else if(item.source_file_idx<10) {
				System.out.printf("\tSource File\t:(0x000%x)%s\n", item.source_file_idx, sourceFile);
			}else if(item.source_file_idx <= 255) {
				System.out.printf("\tSource File\t:(0x00%x)%s\n", item.source_file_idx, sourceFile);
			}else if (item.source_file_idx <= 4095) {
				System.out.printf("\tSource File\t:(0x0%x)%s\n", item.source_file_idx, sourceFile);
			}else {
				System.out.printf("\tSource File\t:(0x%x)%s\n", item.source_file_idx, sourceFile);
			}
			index++;
			parseClassDataItem(srcByte, item);
			System.out.printf("\n");
		}	
	}
	//解析class
	public static void parseClassDataItem(byte[] srcByte, ClassDefItem mClassDefItem){
			int dataOffset = mClassDefItem.class_data_off;
			ClassDataItem item = new ClassDataItem();
			for(int i=0;i<4;i++){
				byte[] byteAry = Utils.readUnsignedLeb128(srcByte, dataOffset);
				dataOffset += byteAry.length;
				int size = Utils.decodeUleb128(byteAry);
				if(i == 0){
					item.static_fields_size = size;
				}else if(i == 1){
					item.instance_fields_size = size;
				}else if(i == 2){
					item.direct_methods_size = size;
				}else if(i == 3){
					item.virtual_methods_size = size;
				}
			}
			//staticFields
			EncodedField[] staticFieldAry = new EncodedField[item.static_fields_size];
			for(int i=0;i<item.static_fields_size;i++){
				/**
				 *  public int filed_idx_diff;
					public int access_flags;
				 */
				EncodedField staticField = new EncodedField();
				staticField.filed_idx_diff = Utils.readUnsignedLeb128(srcByte, dataOffset);
				dataOffset += staticField.filed_idx_diff.length;
				staticField.access_flags = Utils.readUnsignedLeb128(srcByte, dataOffset);
				dataOffset += staticField.access_flags.length;
				staticFieldAry[i] = staticField;
			}
			//instanceFields
			EncodedField[] instanceFieldAry = new EncodedField[item.instance_fields_size];
			for(int i=0;i<item.instance_fields_size;i++){
				/**
				 *  public int filed_idx_diff;
					public int access_flags;
				 */
				EncodedField instanceField = new EncodedField();
				instanceField.filed_idx_diff = Utils.readUnsignedLeb128(srcByte, dataOffset);
				dataOffset += instanceField.filed_idx_diff.length;
				instanceField.access_flags = Utils.readUnsignedLeb128(srcByte, dataOffset);
				dataOffset += instanceField.access_flags.length;
				instanceFieldAry[i] = instanceField;
			}
			//directMethods
			EncodedMethod[] staticMethodsAry = new EncodedMethod[item.direct_methods_size];
			for(int i=0;i<item.direct_methods_size;i++){
				/**
				 *  public byte[] method_idx_diff;
					public byte[] access_flags;
					public byte[] code_off;
				 */
				EncodedMethod directMethod = new EncodedMethod();
				directMethod.method_idx_diff = Utils.readUnsignedLeb128(srcByte, dataOffset);
				dataOffset += directMethod.method_idx_diff.length;
				directMethod.access_flags = Utils.readUnsignedLeb128(srcByte, dataOffset);
				dataOffset += directMethod.access_flags.length;
				directMethod.code_off = Utils.readUnsignedLeb128(srcByte, dataOffset);
				dataOffset += directMethod.code_off.length;
				staticMethodsAry[i] = directMethod;
			}
			//virtualMethods
			EncodedMethod[] instanceMethodsAry = new EncodedMethod[item.virtual_methods_size];
			for(int i=0;i<item.virtual_methods_size;i++){
				/**
				 *  public byte[] method_idx_diff;
					public byte[] access_flags;
					public byte[] code_off;
				 */
				EncodedMethod instanceMethod = new EncodedMethod();
				instanceMethod.method_idx_diff = Utils.readUnsignedLeb128(srcByte, dataOffset);
				dataOffset += instanceMethod.method_idx_diff.length;
				instanceMethod.access_flags = Utils.readUnsignedLeb128(srcByte, dataOffset);
				dataOffset += instanceMethod.access_flags.length;
				instanceMethod.code_off = Utils.readUnsignedLeb128(srcByte, dataOffset);
				dataOffset += instanceMethod.code_off.length;
				instanceMethodsAry[i] = instanceMethod;
			}
			item.static_fields = staticFieldAry;
			item.instance_fields = instanceFieldAry;
			item.direct_methods = staticMethodsAry;
			item.virtual_methods = instanceMethodsAry;
			//打印classDataItem结构体
			System.out.printf("\tstatic fields size\t:%d\n", item.static_fields_size);
			System.out.printf("\tinstance fields size\t:%d\n", item.instance_fields_size );
			System.out.printf("\tdirect fields size\t:%d\n", item.direct_methods_size);
			System.out.printf("\tvirutal fields size\t:%d\n", item.virtual_methods_size);
			System.out.printf("\tStatic fields\t-\n");
			if(item.static_fields.length != 0) {
				for(int i=0; i<item.static_fields.length; i++) {
					int fieldIndex = Utils.decodeUleb128(item.static_fields[i].filed_idx_diff);
					int accessflag = Utils.decodeUleb128(item.static_fields[i].access_flags);
					System.out.printf("\t\t#%s\n", String.valueOf(i));
					System.out.printf("\t\t  name\t:%s\n", stringList.get(fieldIdsList.get(fieldIndex).name_idx));
					System.out.printf("\t\t  type\t:%s\n", stringList.get(typeIdsList.get(fieldIdsList.get(fieldIndex).type_idx).descriptor_idx));
					System.out.printf("\t\t  access\t:0x%x\n", accessflag);
				}
			}
			System.out.printf("\tInstance fields\t-\n");
			if(item.instance_fields.length != 0) {
				for(int i=0; i<item.instance_fields.length; i++) {
					int fieldIndex = Utils.decodeUleb128(item.instance_fields[i].filed_idx_diff);
					int accessflag = Utils.decodeUleb128(item.instance_fields[i].access_flags);
					System.out.printf("\t\t#%s\n", String.valueOf(i));
					System.out.printf("\t\t  name\t:%s\n", stringList.get(fieldIdsList.get(fieldIndex).name_idx));
					System.out.printf("\t\t  type\t:%s\n", stringList.get(typeIdsList.get(fieldIdsList.get(fieldIndex).type_idx).descriptor_idx));
					System.out.printf("\t\t  access\t:0x%x\n", accessflag);
				}
			}
			System.out.printf("\tDirect methods\t-\n");
			if(item.direct_methods.length != 0) {
				for(int i=0; i<item.direct_methods.length; i++) {
					int methodIndex = Utils.decodeUleb128(item.direct_methods[i].method_idx_diff);
					int accessflag = Utils.decodeUleb128(item.direct_methods[i].access_flags);
					int code_off = Utils.decodeUleb128(item.direct_methods[i].code_off);
					System.out.printf("\t\t#%s\n", String.valueOf(i));
					System.out.printf("\t\t  name\t:%s\n", stringList.get(methodIdsList.get(methodIndex).name_idx));
					System.out.printf("\t\t  proto\t:%s\n", stringList.get(protoIdsList.get(methodIdsList.get(methodIndex).proto_idx).shorty_idx));
					System.out.printf("\t\t  access:0x%x\n", accessflag);
					
					//解析code_item结构体
					System.out.printf("\t\t  code\t-\n");
					byte[] codeItemByte = Utils.copyByte(srcByte, code_off, 16);
					ClassCodeItem mClassCodeItem = new ClassCodeItem();
					mClassCodeItem.registersSize = Utils.byte2Short(Utils.copyByte(codeItemByte, 0, 2));
					mClassCodeItem.insSize = Utils.byte2Short(Utils.copyByte(codeItemByte, 2, 2));
					mClassCodeItem.outsSize = Utils.byte2Short(Utils.copyByte(codeItemByte, 4, 2));
					mClassCodeItem.triesSize = Utils.byte2Short(Utils.copyByte(codeItemByte, 6, 2));
					mClassCodeItem.debugInfoOff = Utils.byte2int(Utils.copyByte(codeItemByte, 8, 4));
					mClassCodeItem.insnsSize = Utils.byte2int(Utils.copyByte(codeItemByte, 12, 4));
					byte[] instruction_byte = Utils.copyByte(srcByte, code_off+16, mClassCodeItem.insnsSize*2);
					for(int j=0; j<mClassCodeItem.insnsSize; j++) {
						mClassCodeItem.insns.add(Utils.byte2Short(Utils.copyByte(instruction_byte, 2*j, 2)));
					}
					System.out.printf("\t\t    regis_size:%h\n", mClassCodeItem.registersSize);
					System.out.printf("\t\t    params_size:%h\n", mClassCodeItem.insSize);
					System.out.printf("\t\t    o_params_size:%h\n", mClassCodeItem.outsSize);
					System.out.printf("\t\t    try/catch_size:%h\n", mClassCodeItem.triesSize);
					System.out.printf("\t\t    instruction_size:%d\n", mClassCodeItem.insnsSize);
					System.out.printf("\t\t    instructions:%s\n", mClassCodeItem.insns.toString());
				}
			}
			System.out.printf("\tVirtual methods\t-\n");
			if(item.virtual_methods.length != 0) {
				for(int i=0; i<item.virtual_methods.length; i++) {
					int methodIndex = Utils.decodeUleb128(item.virtual_methods[i].method_idx_diff);
					int accessflag = Utils.decodeUleb128(item.virtual_methods[i].access_flags);
					int code_off = Utils.decodeUleb128(item.virtual_methods[i].code_off);
					System.out.printf("\t\t#%s\n", String.valueOf(i));
					System.out.printf("\t\t  name\t:%s\n", stringList.get(methodIdsList.get(methodIndex).name_idx));
					System.out.printf("\t\t  proto\t:%s\n", stringList.get(protoIdsList.get(methodIdsList.get(methodIndex).proto_idx).shorty_idx));
					System.out.printf("\t\t  access\t:0x%x\n", accessflag);
					
					//解析code_item结构体
					System.out.printf("\t\t  code\t-\n");
					byte[] codeItemByte = Utils.copyByte(srcByte, code_off, 16);
					ClassCodeItem mClassCodeItem = new ClassCodeItem();
					mClassCodeItem.registersSize = Utils.byte2Short(Utils.copyByte(codeItemByte, 0, 2));
					mClassCodeItem.insSize = Utils.byte2Short(Utils.copyByte(codeItemByte, 2, 2));
					mClassCodeItem.outsSize = Utils.byte2Short(Utils.copyByte(codeItemByte, 4, 2));
					mClassCodeItem.triesSize = Utils.byte2Short(Utils.copyByte(codeItemByte, 6, 2));
					mClassCodeItem.debugInfoOff = Utils.byte2int(Utils.copyByte(codeItemByte, 8, 4));
					mClassCodeItem.insnsSize = Utils.byte2int(Utils.copyByte(codeItemByte, 12, 4));
					byte[] instruction_byte = Utils.copyByte(srcByte, code_off+16, mClassCodeItem.insnsSize*2);
					for(int j=0; j<mClassCodeItem.insnsSize; j++) {
						mClassCodeItem.insns.add(Utils.byte2Short(Utils.copyByte(instruction_byte, 2*j, 2)));
					}
					System.out.printf("\t\t    regis_size:%h\n", mClassCodeItem.registersSize);
					System.out.printf("\t\t    params_size:%h\n", mClassCodeItem.insSize);
					System.out.printf("\t\t    o_params_size:%h\n", mClassCodeItem.outsSize);
					System.out.printf("\t\t    try/catch_size:%h\n", mClassCodeItem.triesSize);
					System.out.printf("\t\t    instruction_size:%d\n", mClassCodeItem.insnsSize);
					System.out.printf("\t\t    instructions:%s\n", mClassCodeItem.insns.toString());
				}
			}
		}

	/**
	 * 解析dex文件的最后一个区段，data段。
	 * 
	 * @param srcByte	被解析的dex文件的字节流数据
	 */
	public static void parseMapItemList(byte[] srcByte){
		MapList mapList = new MapList();
		byte[] mapsizeByte = Utils.copyByte(srcByte, mapListOffset, 4);
		int size = Utils.byte2int(mapsizeByte);
		for(int i=0;i<size;i++){
			MapItem item = new MapItem();
			byte[] mapItemByte = Utils.copyByte(srcByte, mapListOffset+4+i*MapItem.getSize(), MapItem.getSize());
			byte[] typeByte = Utils.copyByte(mapItemByte, 0, 2);
			item.type = Utils.byte2Short(typeByte);
			byte[] unuseByte = Utils.copyByte(mapItemByte, 2, 2);
			item.unuse = Utils.byte2Short(unuseByte);
			byte[] sizeByte = Utils.copyByte(mapItemByte, 4, 4);
			item.size = Utils.byte2int(sizeByte);
			byte[] offsetByte = Utils.copyByte(mapItemByte, 8, 4);
			item.offset = Utils.byte2int(offsetByte);
			mapList.map_item.add(item);
		}
		Map<Short, String> mapItemType = new HashMap<>();
		mapItemType.put((short) 0x0000, "Header Item");
		mapItemType.put((short) 0x0001, "StringId Item");
		mapItemType.put((short) 0x0002, "TypeId Item");
		mapItemType.put((short) 0x0003, "ProtoId Item");
		mapItemType.put((short) 0x0004, "FieldId Item");
		mapItemType.put((short) 0x0005, "MethodId Item");
		mapItemType.put((short) 0x0006, "ClassDef Item");
		mapItemType.put((short) 0x1000, "MapList");
		mapItemType.put((short) 0x1001, "TypeList");
		mapItemType.put((short) 0x1002, "AnnotationSetRefList");
		mapItemType.put((short) 0x1003, "AnnotationSet Item");
		mapItemType.put((short) 0x2000, "ClassData Item");
		mapItemType.put((short) 0x2001, "Code Item");
		mapItemType.put((short) 0x2002, "StringData Item");
		mapItemType.put((short) 0x2003, "DebugInfo Item");
		mapItemType.put((short) 0x2004, "Annotation Item");
		mapItemType.put((short) 0x2005, "EncodedArray Item");
		mapItemType.put((short) 0x2006, "AnnotationsDirectory Item");
		//打印MapList结构体
		System.out.printf("Total %d MapItem struct\n\n", mapList.size);
		for(int i=0; i<size; i++) {
			MapItem mMapItem = mapList.map_item.get(i);
			System.out.printf("#%d\n", i);
			System.out.printf("  type\t:%s\n", mapItemType.get(mMapItem.type));
			System.out.printf("  size\t:%d\n", mMapItem.size);
			System.out.printf("  offset:%d\n", mMapItem.offset);
			System.out.printf("\n");
		}
	}
	
}
