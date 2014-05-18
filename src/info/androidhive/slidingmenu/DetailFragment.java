package info.androidhive.slidingmenu;

import helper.LoginHelper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import model.UserInfo;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DetailFragment extends Fragment {

	public UserInfo userInfo = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.detail, container, false);
		Log.i("profile fragment", "on create");

		return view;
	}

	public void onStart() {
		super.onStart();
		UserInfo profile = LoginHelper.userInfo;
		String name = profile.getFirstName() + " " + profile.getLastName()
				+ " ";
		TextView nameTV = (TextView) getActivity()
				.findViewById(R.id.basic_name);
		nameTV.setText(name);
		TextView phoneTV = (TextView) getActivity().findViewById(R.id.phone);
		String phone = profile.getPhoneList()[0];
		phoneTV.setText(phone);
		TextView emailTV = (TextView) getActivity().findViewById(R.id.email);
		emailTV.setText(profile.getEducationList()[0]);
		ImageView thumbNailIV = (ImageView) getActivity().findViewById(
				R.id.thumbnail);
		try {
			Thread t = new Thread() {
				public void run() {
					try {
						URL thumb_u = new URL(LoginHelper.userInfo.getPicUrl());
						final URLConnection conn = thumb_u.openConnection();
						conn.connect();
						final BufferedInputStream bis = new BufferedInputStream(
								conn.getInputStream());
						final Bitmap bm = BitmapFactory.decodeStream(bis);
						bis.close();
						LoginHelper.userInfo.setThumbNail(bm);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			};
			t.start();
			t.join();

			// LoginHelper.userInkfo.setVideoUrl("null");
			// insert video
			insertVideo();

			thumbNailIV.setImageBitmap(profile.getThumbNail());

			ImageView expandIV = (ImageView) getActivity().findViewById(
					R.id.extend_namecard);
			expandIV.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// Log.i("google",LoginHelper.userInfo.getVideoUrl());
					// TODO Auto-generated method stub
					ImageView expandIV = (ImageView) getActivity()
							.findViewById(R.id.extend_namecard);
					expandIV.setVisibility(View.GONE);

					RelativeLayout mynewlayout = (RelativeLayout) getActivity()
							.findViewById(R.id.nameLayout);
					LayoutInflater layoutInflater = (LayoutInflater) getActivity()
							.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

					// insert detail into the view
					RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT);
					params.addRule(RelativeLayout.BELOW, R.id.namecard_top);

					mynewlayout.addView(layoutInflater.inflate(
							R.layout.namecard_detail, mynewlayout, false),
							params);

					TextView summaryTV = (TextView) getActivity().findViewById(
							R.id.summary);
					summaryTV.setText(LoginHelper.userInfo.getSummary());

					TextView EducationTV = (TextView) getActivity()
							.findViewById(R.id.Educations);
					String[] educationList = LoginHelper.userInfo
							.getEducationList();
					StringBuilder sb = new StringBuilder();
					for (String s : educationList) {
						sb.append(s + "\n");
					}
					EducationTV.setText(sb.toString());

				}

			});
		} catch (Exception e) {
			Log.i("google", e.toString());
			// handle it
		}
	}
}
