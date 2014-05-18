package info.androidhive.slidingmenu;

import helper.LoginHelper;
import model.UserInfo;
import util.PicUtil;
import android.app.Fragment;
import android.os.Bundle;
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
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.cacheOnDisc().displayer(new RoundedBitmapDisplayer(500))
				.build();
		UserInfo user = LoginHelper.userInfo;
		imageLoader.displayImage(user.getPicUrl(), iconSelf,
				options, PicUtil.animateListener);

		return rootView;
	}
}
