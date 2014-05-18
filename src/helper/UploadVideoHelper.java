package helper;

import java.io.File;
import java.io.IOException;
import java.util.List;

import model.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class UploadVideoHelper extends AsyncTask<String, Void, String> {

	private Context mContext;

	public UploadVideoHelper(Context context) {
		mContext = context;
	}

	@Override
	protected String doInBackground(String... params) {
		Log.i("upload", "start upload");

		String charset = "UTF-8";
		File videoFile = new File(params[1]);
		String requestURL = Constant.UPLOAD_VIDEO;	
		String url = null;
		try {
			Log.i("upload", "start execute");
			MultipartUtility multi = new MultipartUtility(requestURL, charset);

			// multi.addHeaderField("upload", "video");
			multi.addFilePart("file", videoFile);

			multi.addFormField("uid", params[0]);
			Log.i("uid: ",params[0]);

			List<String> response = multi.finish();
			JSONObject result = new JSONObject(response.get(0)); // Convert String to
														// JSON Object

			url = result.getString("video_url");
			Log.i("upload", url);

		} catch (IOException ex) {
			System.err.println(ex);
		} catch (JSONException e) {
			e.printStackTrace();
		}


		return url;
	}

	@Override
	protected void onPostExecute(String url) {
		LoginHelper.userInfo.setVideoUrl(url);
		Log.i("upload", url);
		Toast t = Toast.makeText(mContext, url, Toast.LENGTH_LONG);
		t.show();
	}
}
