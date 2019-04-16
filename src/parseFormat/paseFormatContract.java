package parseFormat;

import java.util.HashMap;

import common.BasePresenter;
import common.BaseView;

public interface paseFormatContract {

	interface Presenter extends BasePresenter{
		
	}
	
	interface View extends BaseView<Presenter>{
		
		void showAllStructData(HashMap mHashMap);
	}
}
