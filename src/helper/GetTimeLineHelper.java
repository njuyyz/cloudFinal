package helper;

import info.androidhive.slidingmenu.CommunityFragment;
import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.TimeLineFragment;
import com.ckt.vas.miles.dto.ActivityMessage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import model.Constant;
import model.TimeLineItem;
import model.UserInfo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.ckt.vas.miles.dto.ActivityMessage;
import com.ckt.vas.miles.ui.adapters.PublicActivityAdapter;
import com.google.code.linkedinapi.schema.NewPosition;

import util.GetRequest;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

public class GetTimeLineHelper extends
		AsyncTask<String, Void, ArrayList<UserInfo>> {
	private static ArrayList<UserInfo> contactList;
	private static Context mContext;
	private static PublicActivityAdapter adapter;

	public GetTimeLineHelper(Context context,
			PublicActivityAdapter chatHistoryAdapter) {
		mContext = context;
		this.adapter = chatHistoryAdapter;
	}

	@Override
	protected ArrayList<UserInfo> doInBackground(String... params) {
		GetRequest gr = new GetRequest(Constant.GET_CONTACTS + params[0],
				mContext);
		contactList = new ArrayList<UserInfo>();

		JSONArray contactArray = (JSONArray) JSONValue.parse(gr.getContent());
		for (int i = 0; i < contactArray.size(); i++) {
			UserInfo userInfo = new UserInfo((JSONObject) contactArray.get(i));
			contactList.add(userInfo);
			Log.i("haha", userInfo.getId());
		}
		return contactList;
	}

	@Override
	protected void onPostExecute(ArrayList<UserInfo> contactList) {
		// present the list on the screen.
		HashMap<String, ArrayList<TimeLineItem>> map = new HashMap<String, ArrayList<TimeLineItem>>();
		ArrayList<String> timelist = new ArrayList<String>();

		for (UserInfo ui : contactList) {
			TimeLineItem tLineItem = new TimeLineItem();
			tLineItem.setName(ui.getFirstName() + " " + ui.getLastName());
			tLineItem.setRealTime(ui.getRealtime());
			tLineItem.setTitleString(ui.getHeadline());
			tLineItem.setImgUrlString(ui.getPicUrl());
			TimeLineFragment.itemList.add(tLineItem);
			DateFormat df = new SimpleDateFormat("yyyy MM dd HH mm");
			StringBuffer bf = new StringBuffer();
			// bf.append("a.m. ");
			String temp = df.format(new Date(ui.getRealtime())).toString();

			if (!map.containsKey(temp)) {
				timelist.add(temp);
				map.put(temp, new ArrayList(Arrays.asList(tLineItem)));
			} else {
				map.get(temp).add(tLineItem);
			}
			Collections.sort(timelist.subList(1, timelist.size()));
		}
		List<ActivityMessage> mes = new ArrayList<ActivityMessage>();
		PublicActivityAdapter.msgs.clear();
		PublicActivityAdapter.msgs.add(new ActivityMessage("me", "boss",
				LoginHelper.userInfo.getPicUrl(), contactList.get(0)
						.getRealtime()));

		for (String s : timelist) {
			ArrayList<TimeLineItem> list = map.get(s);
			ArrayList<ArrayList<String>> te = new ArrayList<ArrayList<String>>();
			long timeA = 0;
			for (TimeLineItem it : list) {
				ArrayList<String> tem = new ArrayList<String>();
				tem.add(it.getName());
				Log.e(">>>>>", it.getName());
				tem.add(it.getTitleString());
				tem.add(it.getImgUrlString());
				timeA = it.getRealTime();
				te.add(tem);
			}
			ActivityMessage am = new ActivityMessage(0, "", "", "", 0, timeA,
					40.8131995, -73.957643, "", te, "");
			PublicActivityAdapter.msgs.add(am);
			mes.add(am);
		}

		adapter.notifyDataSetChanged();
	}
}
