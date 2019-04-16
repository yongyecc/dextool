package parseFormat;

import java.util.ArrayList;
import java.util.HashMap;

import parseFormat.paseFormatContract.Presenter;

public class paseFormatView implements paseFormatContract.View{
	
	private static String mFilePath;
	
	private static parseFormatPresenter mParseFormatPresenter;
	
	public void showAllStruct(String fp) {
		mFilePath = fp;
		setPresenter(new parseFormatPresenter(mFilePath));
	}

	@Override
	public void setPresenter(Presenter mPresenter) {
		// TODO Auto-generated method stub
		mParseFormatPresenter = (parseFormatPresenter) mPresenter;
		mParseFormatPresenter.start();
	}

	@Override
	public void showAllStructData(HashMap mHashMap) {
		// TODO Auto-generated method stub
		System.out.println("ParseHeader:");
		utils.praseDexHeader((ArrayList<String>) mHashMap.get("fileHeader"));
		System.out.println("++++++++++++++++++++++++++++++++++++++++");

		
	}

}
