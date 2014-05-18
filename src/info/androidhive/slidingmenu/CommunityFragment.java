package info.androidhive.slidingmenu;

import helper.LoginHelper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import model.UserInfo;

import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.ExpandableListItemAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CommunityFragment extends Fragment {

	public CommunityFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.contact_list, container,
				false);

		return rootView;
	}

	@Override
	public void onStart() {
		super.onStart();
		ArrayList<UserInfo> list = new ArrayList<UserInfo>();
		list.add(LoginHelper.userInfo);
		list.add(LoginHelper.userInfo);
		list.add(LoginHelper.userInfo);
		list.add(LoginHelper.userInfo);
		list.add(LoginHelper.userInfo);
		list.add(LoginHelper.userInfo);
		list.add(LoginHelper.userInfo);
		list.add(LoginHelper.userInfo);
		list.add(LoginHelper.userInfo);
		list.add(LoginHelper.userInfo);
		list.add(LoginHelper.userInfo);
		list.add(LoginHelper.userInfo);
		list.add(LoginHelper.userInfo);
		list.add(LoginHelper.userInfo);
		list.add(LoginHelper.userInfo);
		list.add(LoginHelper.userInfo);
		list.add(LoginHelper.userInfo);
		MyListAdapter mAdapter = new MyListAdapter(getActivity(), list);
		SwingBottomInAnimationAdapter adapter = new SwingBottomInAnimationAdapter(
				mAdapter);
		ListView lv = (ListView) getActivity().findViewById(R.id.contact_list);
		adapter.setAbsListView(lv);
		lv.setAdapter(adapter);
	}

	private static class MyListAdapter extends
			ExpandableListItemAdapter<UserInfo> {

		private final Context mContext;

		public MyListAdapter(final Context context,
				final ArrayList<UserInfo> items) {
			super(context, items);
			mContext = context;
		}

		@Override
		public long getItemId(final int position) {
			return getItem(position).hashCode();
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public View getContentView(final int position, final View convertView,
				final ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView tv = (TextView) convertView;
			if (tv == null) {
				tv = (TextView) LayoutInflater.from(mContext).inflate(
						R.layout.list_row, parent, false);
			}
			tv.setText("This is row number " + getItem(position));
			return tv;
		}

		@Override
		public View getTitleView(final int position, final View convertView,
				final ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.contact_namecard_basic,
					parent, false);

			UserInfo profile = getItem(position);
			String name = profile.getFirstName() + " " + profile.getLastName()
					+ " ";
			TextView nameTV = (TextView) rowView.findViewById(R.id.basic_name);
			nameTV.setText(name);
			TextView phoneTV = (TextView) rowView.findViewById(R.id.phone);
			String phone = profile.getPhoneList()[0];
			phoneTV.setText(phone);
			TextView emailTV = (TextView) rowView.findViewById(R.id.email);
			emailTV.setText(profile.getEducationList()[0]);
			ImageView thumbNailIV = (ImageView) rowView
					.findViewById(R.id.thumbnail);
			try {
				Thread t = new Thread() {
					public void run() {
						try {
							URL thumb_u = new URL(
									LoginHelper.userInfo.getPicUrl());
							final URLConnection conn = thumb_u.openConnection();
							conn.connect();
							final BufferedInputStream bis = new BufferedInputStream(
									conn.getInputStream());
							final Bitmap bm = BitmapFactory.decodeStream(bis);
							bis.close();
							LoginHelper.userInfo.setThumbNail(bm);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				};
				t.start();
				t.join();

				thumbNailIV.setImageBitmap(profile.getThumbNail());
			} catch (Exception e) {

			}

			return rowView;
		}
	}
}
