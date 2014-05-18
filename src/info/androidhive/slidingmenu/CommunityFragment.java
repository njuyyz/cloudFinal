package info.androidhive.slidingmenu;

import helper.ImageLoader;
import helper.LoginHelper;

import java.util.ArrayList;

import model.UserInfo;
import util.PicUtil;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nhaarman.listviewanimations.itemmanipulation.ExpandableListItemAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingLeftInAnimationAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

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
		MyListAdapter mAdapter = new MyListAdapter(getActivity(),R.layout.contact_namecard_basic, list);
		ListView lv = (ListView) getActivity().findViewById(R.id.contact_list);
		
		SwingBottomInAnimationAdapter alphaInAnimationAdapter = new SwingBottomInAnimationAdapter(mAdapter);
        alphaInAnimationAdapter.setAbsListView(lv);
        alphaInAnimationAdapter.setInitialDelayMillis(500);
        lv.setAdapter(alphaInAnimationAdapter);
		
		
	}

	private static class MyListAdapter extends
			ArrayAdapter<UserInfo> {

		private final Context mContext;

		private ImageLoader imageLoader;

		public MyListAdapter(final Context context,int layoutResourceId,
				final ArrayList<UserInfo> items) {
			super(context, layoutResourceId, items);

			mContext = context;
			imageLoader = new ImageLoader(mContext);
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
		public View getView(final int position, final View convertView,
				final ViewGroup parent) {
			UserInfo currentNewsFeed = getItem(position);
			View view = convertView;
			final ViewHolder holder;
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) mContext
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.contact_namecard_basic,
						parent, false);

				holder = this.new ViewHolder();
				UserInfo profile = getItem(position);
				String name = profile.getFirstName() + " "
						+ profile.getLastName() + " ";
				holder.name = (TextView) view.findViewById(R.id.basic_name);
				holder.name.setText(name);
				holder.phone = (TextView) view.findViewById(R.id.phone);
				String phone = profile.getPhoneList()[0];
				holder.phone.setText(phone);
				holder.email = (TextView) view.findViewById(R.id.email);
				holder.email.setText(profile.getEducationList()[0]);
				holder.image = (ImageView) view.findViewById(R.id.thumbnail);

				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			imageLoader.DisplayImage(currentNewsFeed.getPicUrl(), holder.image);
			return view;
		}

		private class ViewHolder {
			public TextView name;
			public TextView phone;
			public TextView email;
			public ImageView image;
		}
	}
}
