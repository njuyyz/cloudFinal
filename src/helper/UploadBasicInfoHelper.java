package helper;

import model.Constant;

import org.json.simple.JSONObject;

import android.util.Log;
import util.PostRequest;

public class UploadBasicInfoHelper {

	public static void uploadBasicInfo(JSONObject profile){
		PostRequest pq = new PostRequest(Constant.UPLOAD_BASIC_INFO_URL,profile);
		boolean success = pq.post();
		Log.i("upload basic",success?"true":"false");
	}
}
