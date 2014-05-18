package helper;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import util.GetRequest;
import model.Constant;
import model.UserInfo;

public class GetContactHelper extends
		AsyncTask<String, Void, ArrayList<UserInfo>> {
	private static ArrayList<UserInfo> contactList;
	private static Context mContext;

	public GetContactHelper(Context context) {
		mContext = context;
	}

	@Override
	protected ArrayList<UserInfo> doInBackground(String... params) {
		GetRequest gr = new GetRequest(Constant.GET_CONTACTS + params[0]);

		JSONArray contactArray = (JSONArray) JSONValue.parse(gr.getContent());
		for (int i = 0; i < contactArray.size(); i++) {
			UserInfo userInfo = new UserInfo((JSONObject) contactArray.get(i));
			contactList.add(userInfo);
		}
		JSONObject jsonObject = (JSONObject) JSONValue.parse(gr.getContent());
		try {
			String id = (String) jsonObject.get("u_id");
			if (id.equals("0")) {
				return null;
			}
			LoginHelper.userInfo = new UserInfo(jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contactList;
	}
	

	@Override
	protected void onPostExecute(ArrayList<UserInfo> contactList) {
		// present the list on the screen.
	}
}
