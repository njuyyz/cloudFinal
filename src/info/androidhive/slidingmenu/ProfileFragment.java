package info.androidhive.slidingmenu;

import helper.LoginHelper;
import helper.UploadVideoHelper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import model.UserInfo;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

public class ProfileFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.profile, container, false);
		Log.i("profile fragment","on create");

		return view;
	}

	@Override
	public void onStart() {
		Log.i("profile fragment", "on start");
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

//			LoginHelper.userInkfo.setVideoUrl("null");
			// insert video
			insertVideo();

			thumbNailIV.setImageBitmap(profile.getThumbNail());

			ImageView expandIV = (ImageView) getActivity().findViewById(
					R.id.extend_namecard);
			expandIV.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
//					Log.i("google",LoginHelper.userInfo.getVideoUrl());
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

	public void insertVideo() {

		RelativeLayout videoLayout = (RelativeLayout) getActivity()
				.findViewById(R.id.videoLayout);
		LayoutInflater videoInflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		if (LoginHelper.userInfo.getVideoUrl() == null
				|| LoginHelper.userInfo.getVideoUrl().equals("null")) {
			videoLayout.addView(videoInflater.inflate(R.layout.record_button,
					videoLayout, false), params2);

			// add action listener
			ImageButton recordBt = (ImageButton) getActivity().findViewById(
					R.id.record);
			recordBt.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.i("google", "video");

					ContentValues values = new ContentValues();
					values.put(MediaStore.Images.Media.TITLE,
							LoginHelper.userInfo.getId());
					Uri mImageUri = getActivity().getContentResolver().insert(
							MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
							values);

					Intent takeVideoIntent = new Intent(
							MediaStore.ACTION_VIDEO_CAPTURE);
					takeVideoIntent
							.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
					startActivityForResult(takeVideoIntent, 1);

				}

			});

		} else {
			videoLayout.addView(
					videoInflater.inflate(R.layout.video, videoLayout, false),
					params2);
			Log.i("google", "add view");
			VideoView videoView = (VideoView) getActivity().findViewById(
					R.id.video);
			videoView.setVideoURI(Uri.parse(LoginHelper.userInfo.getVideoUrl()));
			Log.i("google", LoginHelper.userInfo.getVideoUrl());
			// videoView.setMediaController(new MediaController(this));
			videoView.requestFocus();
			videoView.setMediaController(new MediaController(getActivity()));
//			videoView.start();
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
			
			Uri videoUri = data.getData();
			RelativeLayout record_button_layout = (RelativeLayout) getActivity()
					.findViewById(R.id.record_button_layout);
			record_button_layout.setVisibility(View.GONE);
			
			String[] proj = { MediaStore.Images.Media.DATA };
		    Cursor cursor = getActivity().getContentResolver().query(videoUri,  proj, null, null, null);
		    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		    cursor.moveToFirst();
			
			LoginHelper.userInfo.setVideoUrl(cursor.getString(column_index));

			new UploadVideoHelper(getActivity()).execute(LoginHelper.userInfo.getId(),LoginHelper.userInfo.getVideoUrl());
			
			insertVideo();
		}
	}

}
