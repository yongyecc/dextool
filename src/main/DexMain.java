package main;	

import javax.swing.text.View;

import common.Utils;
import parseFormat.*;

public class DexMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/**
		 * 需要删除的临时变量
		 */
//		String fp = "E:\\eclipseProjects\\parse_androiddex-master\\dex\\classes.dex";
		if(args.length < 1) {
			System.out.printf("\n\tjava -jar readdex.jar <dex file>\n");
			System.exit(0);
		}
		String fp = args[0];
		/**
		 * 打印dex文件所有结构体信息
		 */
		
		int ftype;
		ftype = Utils.getFileType(fp);
		switch (ftype) {
		//file type：APK
		case 0:
			
			break;
		//file type：dex
		case 1:
			paseFormatView mView = new paseFormatView();
			mView.showAllStruct(fp);
			break;
		default:
			
			break;
		}	
	}

}
