package parseFormat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import common.IBaseData;

public class parseFormatData implements IBaseData{

	@Override
	public void getData(String fp, getDataCallBack callback) {
		// TODO Auto-generated method stub
		byte[] bt = null;
		try {
			FileInputStream mFileInputStream = new FileInputStream(fp);
			//因为下面读取字节时会多读一位返回-1，所以多申请一个字节空间，防止越界异常
			bt = new byte[mFileInputStream.available()+1];
			int off = 0;
			while((mFileInputStream.read(bt, off, 1)) != -1) {
				off++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(bt.length != 0) {
			callback.onDataLoaded(bt);
		}else {
			callback.onDataNotAvailable();
		}
		
	}

	
	
}
