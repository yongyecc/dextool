package parseFormat;

import common.CustomException;
import common.IBaseData.getDataCallBack;
import parseFormat.paseFormatContract.Presenter;

public class parseFormatPresenter implements paseFormatContract.Presenter{
	
	private String mFilePath;
	
	private paseFormatContract.View mView ;

	private Presenter mPresenter;
	
	private parseFormatData mParseFormatData;
	
	private byte[] bt;
	
	
	public parseFormatPresenter(String fpath, paseFormatContract.View mView) {
		// TODO Auto-generated constructor stub
		mFilePath = fpath;
		this.mPresenter = mPresenter;
		this.mView = mView;
		this.mView.setPresenter(this);
		mParseFormatData = new parseFormatData();
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		mParseFormatData.getData(mFilePath, new getDataCallBack() {
			
			@Override
			public void onDataNotAvailable() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onDataLoaded(Object data) {
				// TODO Auto-generated method stub
				bt = (byte[]) data;
				mView.showAllStructData(data);
				
			}
		});
	}

}
