package info.androidhive.slidingmenu;

import helper.GetContactHelper;
import helper.ImageLoader;
import helper.LoginHelper;

import java.util.ArrayList;

import model.UserInfo;
import util.PicUtil;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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

	
	public static ArrayList<UserInfo> list = null;
	
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
		list = new ArrayList<UserInfo>();
		MyListAdapter mAdapter = new MyListAdapter(getActivity(),R.layout.contact_namecard_basic, list);
		ListView lv = (ListView) getActivity().findViewById(R.id.contact_list);
		new GetContactHelper(getActivity(),mAdapter).execute(LoginHelper.userInfo.getId());
		
		SwingBottomInAnimationAdapter alphaInAnimationAdapter = new SwingBottomInAnimationAdapter(mAdapter);
        alphaInAnimationAdapter.setAbsListView(lv);
        alphaInAnimationAdapter.setInitialDelayMillis(800);
        lv.setAdapter(alphaInAnimationAdapter);
        
        lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				UserInfo userInfo = list.get(position);
				
				DetailFragment fragment = new DetailFragment();
				fragment.userInfo = userInfo;
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.frame_container, fragment).addToBackStack( null ).commit();

				
			}
        	
        });
		
		
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
