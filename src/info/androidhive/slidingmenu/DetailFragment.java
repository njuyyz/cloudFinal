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
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class DetailFragment extends Fragment {

	public static UserInfo userInfo = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.detail, container, false);
		Log.i("profile fragment", "on create");

		return view;
	}

	public void onStart() {
		super.onStart();
		String name = userInfo.getFirstName() + " " + userInfo.getLastName()
				+ " ";
		TextView nameTV = (TextView) getActivity().findViewById(
				R.id.contact_basic_name);
		nameTV.setText(name);
		TextView phoneTV = (TextView) getActivity().findViewById(
				R.id.contact_phone);
		String phone = userInfo.getPhoneList()[0];
		phoneTV.setText(phone);
		TextView emailTV = (TextView) getActivity().findViewById(
				R.id.contact_email);
		emailTV.setText(userInfo.getEducationList()[0]);
		ImageView thumbNailIV = (ImageView) getActivity().findViewById(
				R.id.contact_thumbnail);
		try {
			Thread t = new Thread() {
				public void run() {
					try {
						URL thumb_u = new URL(userInfo.getPicUrl());
						final URLConnection conn = thumb_u.openConnection();
						conn.connect();
						final BufferedInputStream bis = new BufferedInputStream(
								conn.getInputStream());
						final Bitmap bm = BitmapFactory.decodeStream(bis);
						bis.close();
						userInfo.setThumbNail(bm);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			};
			t.start();
			t.join();
			

			thumbNailIV.setImageBitmap(userInfo.getThumbNail());

			TextView summaryTV = (TextView) getActivity().findViewById(
					R.id.contact_summary);
			summaryTV.setText(userInfo.getSummary());

			TextView EducationTV = (TextView) getActivity().findViewById(
					R.id.contact_Educations);
			String[] educationList = userInfo.getEducationList();
			StringBuilder sb = new StringBuilder();
			for (String s : educationList) {
				sb.append(s + "\n");
			}
			EducationTV.setText(sb.toString());
			
			insertVideo();

		} catch (Exception e) {
			Log.i("google", e.toString());
			// handle it
		}
	}

	public void insertVideo() {

		LinearLayout videoLayout = (LinearLayout) getActivity().findViewById(
				R.id.videoLayout2);
		LayoutInflater videoInflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		if (userInfo.getVideoUrl() == null
				|| LoginHelper.userInfo.getVideoUrl().equals("null")) {
//			VideoView vv = (VideoView) getActivity().findViewById(R.id.contact_video);
//			vv.setVisibility(View.GONE);
			return;

		} else {
			// no....
			videoLayout.addView(
					videoInflater.inflate(R.layout.video, videoLayout, false),
					params2);
			
			VideoView videoView = (VideoView) getActivity().findViewById(
					R.id.video);
			videoView
					.setVideoURI(Uri.parse(userInfo.getVideoUrl()));
			Log.i("google", userInfo.getVideoUrl());
//			videoView.requestFocus();
//			videoView.setMediaController(new MediaController(getActivity()));
//			MediaController mc = new MyMediaController(getActivity(),
//					(FrameLayout) getActivity().findViewById(
//							R.id.controllerAnchor));
//			mc.setAnchorView(getActivity().findViewById(R.id.controllerAnchor));
//			videoView.setMediaController(mc);	
		}

	}

}
