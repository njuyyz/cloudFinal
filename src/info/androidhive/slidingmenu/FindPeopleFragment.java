package info.androidhive.slidingmenu;

import helper.JSONHelper;
import helper.LoginHelper;

import java.util.ArrayList;
import java.util.List;

import model.Constant;
import model.Neighbor;
import model.UserInfo;
import util.GetRequest;
import util.PicUtil;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
		displayIcon(user.getPicUrl(), iconSelf);

		final HoloCircularProgressBar mHoloCircularProgressBar = (HoloCircularProgressBar) rootView
				.findViewById(R.id.holoCircularProgressBar1);
		mHoloCircularProgressBar.setProgress(0f);
		iconSelf.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mProgressBarAnimator != null) {
					mProgressBarAnimator.cancel();
				}
				animate(mHoloCircularProgressBar, null, 0.5f, 1000);
				mHoloCircularProgressBar.setMarkerProgress(1f);
			}
		});
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
			GetRequest request = new GetRequest(requestURL);
			String res = request.getContent();
			ArrayList<Neighbor> neiList = new ArrayList<Neighbor>();
			if (res != null) {
				neiList = JSONHelper.getNeighbor(res);
			}
			return neiList;
		}

		@Override
		protected void onPostExecute(List<Neighbor> neiList) {
			super.onPostExecute(neiList);
			ImageView[] iconList = new ImageView[3];
			iconList[0] = (ImageView) root.findViewById(R.id.contact1);
			iconList[1] = (ImageView) root.findViewById(R.id.contact2);
			iconList[2] = (ImageView) root.findViewById(R.id.contact3);
			for (int i = 0; i < 3; i++) {
				if (i < neiList.size()) {
					Neighbor nei = neiList.get(i);
					iconList[i].setVisibility(View.VISIBLE);
					displayIcon(nei.picURL, iconList[i]);
				}
			}
			for (int i = neiList.size(); i < 3; i++) {
				iconList[i].setVisibility(View.GONE);
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

}
