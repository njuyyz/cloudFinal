package info.androidhive.slidingmenu;

import model.UserInfo;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DetailFragment extends Fragment {

	public UserInfo userInfo = null;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.detail, container, false);
		Log.i("profile fragment","on create");

		return view;
	}
}
