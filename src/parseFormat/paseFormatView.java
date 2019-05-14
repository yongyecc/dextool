package parseFormat;

import java.util.ArrayList;
import java.util.HashMap;

import parseFormat.paseFormatContract.Presenter;

public class paseFormatView implements paseFormatContract.View{
	
	private static String mFilePath;
	
	private static parseFormatPresenter mParseFormatPresenter;
	
	public void showAllStruct(String fp) {
		mFilePath = fp;
		parseFormatPresenter mFormatPresenter = new parseFormatPresenter(mFilePath, this);
		
	}

	@Override
	public void setPresenter(Presenter mPresenter) {
		// TODO Auto-generated method stub
		mParseFormatPresenter = (parseFormatPresenter) mPresenter;
		mParseFormatPresenter.start();
	}

	@Override
	public void showAllStructData(Object mObject) {
		// TODO Auto-generated method stub
		System.out.println("Dex File header:");
		parseFormatParser.showDexHeader((byte[])mObject);
		System.out.println("++++++++++++++++++++++++++++++++++++++++");
		parseFormatParser.parseStringIds((byte[])mObject);
		parseFormatParser.parseStringList((byte[])mObject);
		System.out.println("++++++++++++++++++++++++++++++++++++++++");
		parseFormatParser.parseTypeIds((byte[])mObject);
		System.out.println("++++++++++++++++++++++++++++++++++++++++");
		parseFormatParser.parseProtoIds((byte[])mObject);
		
		
	}

}
