package info.androidhive.slidingmenu;

import helper.GetContactHelper;
import helper.ImageLoader;
import helper.LoginHelper;

import java.util.ArrayList;

import model.Constant;
import model.UserInfo;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
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

import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;

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
				DetailFragment.userInfo = userInfo;
				FragmentTransaction fragmentManager = getFragmentManager().beginTransaction();
				fragmentManager.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);

				fragmentManager.replace(R.id.frame_container, fragment).addToBackStack( null ).commit();

				
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
			Typeface face=Typeface.createFromAsset(getContext().getAssets(),
                    "fonts/kozuka_light.otf");

			UserInfo currentNewsFeed = getItem(position);
			View view = convertView;
			final ViewHolder holder;
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) mContext
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.contact_namecard_basic,
						parent, false);

				RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.contact_namecard_bgcolor);
				rl.setBackgroundColor(Color.parseColor(Constant.styles[Integer.parseInt(currentNewsFeed.getStyleUrl())]));
				holder = this.new ViewHolder();
				UserInfo profile = getItem(position);
				String name = profile.getFirstName() + " "
						+ profile.getLastName() + " ";
				holder.name = (TextView) view.findViewById(R.id.basic_name);
				holder.name.setText(name);
				holder.name.setTypeface(face);
				holder.phone = (TextView) view.findViewById(R.id.phone);
				String phone = profile.getPhoneList()[0];
				holder.phone.setText(phone);
				holder.phone.setTypeface(face);
				holder.email = (TextView) view.findViewById(R.id.email);
				holder.email.setText(profile.getEducationList()[0]);
				holder.email.setTypeface(face);
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
