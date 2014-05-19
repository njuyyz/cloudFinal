package helper;

import model.Constant;

import org.json.simple.JSONObject;

import android.content.Context;
import android.util.Log;
import util.PostRequest;

public class UploadBasicInfoHelper {
	
	private static Context mContext;
	public UploadBasicInfoHelper(Context c){
		mContext = c;
	}

	public static void uploadBasicInfo(JSONObject profile){
		PostRequest pq = new PostRequest(Constant.UPLOAD_BASIC_INFO_URL,profile, mContext);
		boolean success = pq.post();
		Log.i("upload basic",success?"true":"false");
	}
}
