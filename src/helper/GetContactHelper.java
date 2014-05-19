package helper;

import info.androidhive.slidingmenu.CommunityFragment;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import util.GetRequest;
import model.Constant;
import model.UserInfo;

public class GetContactHelper extends
		AsyncTask<String, Void, ArrayList<UserInfo>> {
	private static ArrayList<UserInfo> contactList;
	private static Context mContext;
	private static ArrayAdapter adapter;

	public GetContactHelper(Context context, ArrayAdapter adapter) {
		mContext = context;
		this.adapter = adapter;
	}

	@Override
	protected ArrayList<UserInfo> doInBackground(String... params) {
		GetRequest gr = new GetRequest(Constant.GET_CONTACTS + params[0], mContext);
		contactList = new ArrayList<UserInfo>();

		JSONArray contactArray = (JSONArray) JSONValue.parse(gr.getContent());
		for (int i = 0; i < contactArray.size(); i++) {
			UserInfo userInfo = new UserInfo((JSONObject) contactArray.get(i));
			contactList.add(userInfo);
			Log.i("haha",userInfo.getId());
		}
		return contactList;
	}
	

	@Override
	protected void onPostExecute(ArrayList<UserInfo> contactList) {
		// present the list on the screen.
		for(UserInfo ui : contactList){
			CommunityFragment.list.add(ui);
		}
		adapter.notifyDataSetChanged();
	}
}
