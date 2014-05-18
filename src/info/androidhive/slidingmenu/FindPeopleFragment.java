package info.androidhive.slidingmenu;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class FindPeopleFragment extends Fragment {

	public FindPeopleFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_find_people,
				container, false);
		ImageView radarShaddow = (ImageView) rootView
				.findViewById(R.id.radar_rotate_wave_iv);
		radarShaddow.setMaxWidth(radarShaddow.getHeight());
        Animation rotation = AnimationUtils.loadAnimation(this.getActivity(), R.anim.rotate);
        radarShaddow.startAnimation(rotation);
		
		return rootView;
	}
}
