package info.androidhive.slidingmenu;

import helper.LoginHelper;

import java.util.List;

import model.UserInfo;
import util.PicUtil;
import android.app.Fragment;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class FindPeopleFragment extends Fragment {
	boolean finish = false;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
		View rootView = inflater.inflate(R.layout.fragment_find_people,
				container, false);
		ImageView radarShaddow = (ImageView) rootView
				.findViewById(R.id.radar_rotate_wave_iv);
		radarShaddow.setMaxWidth(radarShaddow.getHeight());
		Animation rotation = AnimationUtils.loadAnimation(this.getActivity(),
				R.anim.rotate);
		radarShaddow.startAnimation(rotation);
		ImageView iconSelf = (ImageView) rootView.findViewById(R.id.icon_self);
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error).cacheInMemory()
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED).cacheOnDisc()
				.displayer(new RoundedBitmapDisplayer(500)).build();
		UserInfo user = LoginHelper.userInfo;
		imageLoader.displayImage(user.getPicUrl(), iconSelf, options,
				PicUtil.animateListener);

		// get icon every 3 seconds
		Thread fetchThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!finish) {
					new FetchNearPeopleTask(getActivity()).execute();
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		fetchThread.start();

		return rootView;
	}

	private class FetchNearPeopleTask extends
			AsyncTask<Void, Void, List<UserInfo>> {
		Context context;

		public FetchNearPeopleTask(Context context) {
			this.context = context;
		}

		@Override
		protected List<UserInfo> doInBackground(Void... params) {
			UserInfo curUser = LoginHelper.userInfo;
			String uId = curUser.getId();
			double[] location = getLocation(context);
			Log.e(">>>", location[0] + ", " + location[1]);
			return null;
		}

		@Override
		protected void onPostExecute(List<UserInfo> result) {
			super.onPostExecute(result);
		}
	}

	private double[] getLocation(Context context) {
		LocationManager lm = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		List<String> providers = lm.getProviders(true);

		Location l = null;

		for (int i = providers.size() - 1; i >= 0; i--) {
			l = lm.getLastKnownLocation(providers.get(i));
			if (l != null)
				break;
		}

		double[] location = new double[2];
		if (l != null) {
			location[0] = l.getLatitude();
			location[1] = l.getLongitude();
		}

		return location;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		finish = true;
	}
}
