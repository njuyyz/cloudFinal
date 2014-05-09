package info.androidhive.slidingmenu;

import helper.LoginHelper;
import model.UserInfo;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ProfileFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.profile, container, false);

		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		UserInfo profile = LoginHelper.userInfo;
		String name = profile.getProfile().getFirstName()+" "+profile.getProfile().getLastName();
		TextView tv = (TextView) getActivity().findViewById(R.id.name);
		tv.setText(name);
	}

}
