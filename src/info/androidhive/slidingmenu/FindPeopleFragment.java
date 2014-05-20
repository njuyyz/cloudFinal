package info.androidhive.slidingmenu;

import helper.JSONHelper;
import helper.LoginHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Constant;
import model.NeigborItem;
import model.Neighbor;
import model.UserInfo;
import util.GetRequest;
import util.PicUtil;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import de.passsy.holocircularprogressbar.HoloCircularProgressBar;

public class FindPeopleFragment extends Fragment {
	boolean finish = false;
	ImageLoader imageLoader;
	DisplayImageOptions options;
	private ObjectAnimator mProgressBarAnimator;
	String uId;
	HashMap<ImageView, NeigborItem> neiMap = new HashMap<ImageView, NeigborItem>();

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
		final View rootView = inflater.inflate(R.layout.fragment_find_people,
				container, false);
		ImageView radarShaddow = (ImageView) rootView
				.findViewById(R.id.radar_rotate_wave_iv);
		radarShaddow.setMaxWidth(radarShaddow.getHeight());
		Animation rotation = AnimationUtils.loadAnimation(this.getActivity(),
				R.anim.rotate);
		radarShaddow.startAnimation(rotation);
		ImageView iconSelf = (ImageView) rootView.findViewById(R.id.icon_self);
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error).cacheInMemory()
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED).cacheOnDisc()
				.displayer(new RoundedBitmapDisplayer(500)).build();
		UserInfo user = LoginHelper.userInfo;
		uId = user.getId();
		displayIcon(user.getPicUrl(), iconSelf);

		// get icon every 3 seconds
		Thread fetchThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!finish) {
					new FetchNearPeopleTask(getActivity(), rootView).execute();
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

	private void displayIcon(String url, ImageView imageView) {
		imageLoader.displayImage(url, imageView, options,
				PicUtil.animateListener);
	}

	@SuppressLint("NewApi")
	private class FetchNearPeopleTask extends
			AsyncTask<Void, Void, List<Neighbor>> {
		Context context;
		View root;

		public FetchNearPeopleTask(Context context, View root) {
			this.context = context;
			this.root = root;
		}

		@Override
		protected List<Neighbor> doInBackground(Void... params) {
			UserInfo curUser = LoginHelper.userInfo;
			String uId = curUser.getId();
			double[] location = getLocation(context);
			String requestURL = String.format(Constant.RADAR_REQUEST, uId,
					location[0], location[1]);
			GetRequest request = new GetRequest(requestURL, getActivity());
			String res = request.getContent();
			ArrayList<Neighbor> neiList = new ArrayList<Neighbor>();
			if (res != null) {
				neiList = JSONHelper.getNeighbor(res);
				Log.e(">>>", res);
			}
			return neiList;
		}

		@Override
		protected void onPostExecute(List<Neighbor> neiList) {
			super.onPostExecute(neiList);
			ImageView[] iconList = new ImageView[3];
			HoloCircularProgressBar[] progressBarList = new HoloCircularProgressBar[3];
			iconList[0] = (ImageView) root.findViewById(R.id.contact1);
			iconList[1] = (ImageView) root.findViewById(R.id.contact2);
			iconList[2] = (ImageView) root.findViewById(R.id.contact3);
			progressBarList[0] = (HoloCircularProgressBar) root
					.findViewById(R.id.holoCircularProgressBar1);
			progressBarList[1] = (HoloCircularProgressBar) root
					.findViewById(R.id.holoCircularProgressBar2);
			progressBarList[2] = (HoloCircularProgressBar) root
					.findViewById(R.id.holoCircularProgressBar3);
			for (int i = 0; i < 3; i++) {
				if (i < neiList.size()) {
					Neighbor nei = neiList.get(i);
					iconList[i].setVisibility(View.VISIBLE);
					progressBarList[i].setVisibility(View.VISIBLE);
					displayIcon(nei.picURL, iconList[i]);
					if (nei.relation == 1 || nei.relation == 2) {
						animate(progressBarList[i], null, 0.5f, 1000);
					} else if (nei.relation == 3) {
						animate(progressBarList[i], null, 1f, 1000);
						// previous 2 or 1
						NeigborItem item = neiMap.get(iconList[i]);
						if (item != null
								&& (item.relation == 2 || item.relation == 1)) {
							spin(iconList[i]);
						}
					}
					iconList[i].setOnClickListener(new NeiClickListener());
					neiMap.put(iconList[i], new NeigborItem(progressBarList[i],
							nei.relation, nei.uId));
				}
			}
			for (int i = neiList.size(); i < 3; i++) {
				iconList[i].setVisibility(View.GONE);
				progressBarList[i].setVisibility(View.GONE);
			}
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

		// return location;
		return new double[] { 0, 0 };
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		finish = true;
	}

	private void animate(final HoloCircularProgressBar progressBar,
			final AnimatorListener listener, final float progress,
			final int duration) {

		mProgressBarAnimator = ObjectAnimator.ofFloat(progressBar, "progress",
				progress);
		mProgressBarAnimator.setDuration(duration);

		mProgressBarAnimator.addListener(new AnimatorListener() {

			@Override
			public void onAnimationCancel(final Animator animation) {
			}

			@Override
			public void onAnimationEnd(final Animator animation) {
				progressBar.setProgress(progress);
			}

			@Override
			public void onAnimationRepeat(final Animator animation) {
			}

			@Override
			public void onAnimationStart(final Animator animation) {
			}
		});
		if (listener != null) {
			mProgressBarAnimator.addListener(listener);
		}
		mProgressBarAnimator.reverse();
		mProgressBarAnimator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(final ValueAnimator animation) {
				progressBar.setProgress((Float) animation.getAnimatedValue());
			}
		});
		progressBar.setMarkerProgress(progress);
		mProgressBarAnimator.start();
	}

	private class AddContactTask extends AsyncTask<Void, Void, Void> {
		private String from;
		private String to;

		public AddContactTask(String from, String to) {
			this.from = from;
			this.to = to;
		}

		@Override
		protected Void doInBackground(Void... params) {
			GetRequest request = new GetRequest(String.format(
					Constant.ADD_REQUEST, from, to), getActivity());
			request.getContent();
			return null;
		}
	}

	private class NeiClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			ImageView imageView = (ImageView) v;
			NeigborItem item = neiMap.get(imageView);
			Log.e(">>> prev status", "" + item.relation);
			if (item.relation == 0) {
				animate(item.progressBar, null, 0.5f, 1000);
				// send request
				new AddContactTask(uId, item.uId).execute();
			} else if (item.relation == 2) {
				animate(item.progressBar, null, 1.0f, 1000);
				// send request to complete
				new AddContactTask(uId, item.uId).execute();
				spin(imageView);
			}
		}
	}
	
	private void spin(ImageView imageView){
		RotateAnimation r = new RotateAnimation(0f, 360f,
				Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		r.setDuration((long) 1500);
		r.setRepeatCount(0);
		imageView.startAnimation(r);
	}
}
