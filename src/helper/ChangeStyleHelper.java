package helper;

import info.androidhive.slidingmenu.CommunityFragment;

import java.util.ArrayList;

import model.Constant;
import model.UserInfo;
import util.GetRequest;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class ChangeStyleHelper extends
		AsyncTask<String, Void, String> {
	
	Context mContext;
	
	public ChangeStyleHelper(Context c){
		mContext = c;
	}


	@Override
	protected String doInBackground(String... params) {
		String url = String.format(Constant.CHANGE_STYLE, params[0],params[1]);
		GetRequest gr  = new GetRequest(url, mContext);
		String result = gr.getContent();
		
		return result;
	}
	@Override
	protected void onPostExecute(String result) {
		// present the list on the screen.
		Toast t = Toast.makeText(mContext, result, Toast.LENGTH_LONG);
		t.show();
	}

}
