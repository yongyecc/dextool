package common;

public interface IBaseData {
	
	interface getDataCallBack{
		
		void onDataLoaded(Object data);

        void onDataNotAvailable();
        
	}
	
	void getData(String filepath,getDataCallBack callback);

}
