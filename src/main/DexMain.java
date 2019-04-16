package main;

import javax.swing.text.View;

import parseFormat.*;

public class DexMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/**
		 * 需要删除的临时变量
		 */
		String fp = "E:\\eclipseProjects\\parse_androiddex-master\\dex\\classes.dex";
		
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
			paseFormatView mView =new paseFormatView();
			mView.showAllStruct(fp);
			break;
		default:
			
			break;
		}
		
		
				
		
	}

}
