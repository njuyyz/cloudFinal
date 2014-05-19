package info.androidhive.slidingmenu;

import helper.LoginHelper;
import helper.UploadVideoHelper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import model.Constant;
import model.UserInfo;
import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

public class ProfileFragment extends Fragment {
	View view;
	UserInfo profile;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.profile, container, false);
		profile = LoginHelper.userInfo;
		LinearLayout rl = (LinearLayout) view
				.findViewById(R.id.namecard_bgcolor);

		Log.i("styleid", profile.getStyleUrl());
		int styleId = Integer.parseInt(profile.getStyleUrl());
		rl.setBackgroundColor(Color.parseColor(Constant.styles[styleId]));

		
		getActivity().getActionBar().show();
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		profile = LoginHelper.userInfo;
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

//		ImageButton bt = (ImageButton) getActivity().findViewById(
//				R.id.change_bg);
//		bt.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent changebgIntent = new Intent(getActivity(),
//						GridViewActivity.class);
//
//				startActivityForResult(changebgIntent, 2);
//			}
//
//		});

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
			
			LinearLayout namecard = (LinearLayout)view.findViewById(R.id.nameLayout);

			Log.i("namecard","namecard");
			namecard.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					View namebar = view
							.findViewById(R.id.detail_namecard_bgcolor);
					if (namebar == null) {
						Log.i("if null","null");
						LinearLayout mynewlayout = (LinearLayout) getActivity()
								.findViewById(R.id.nameLayout);
						LayoutInflater layoutInflater = (LayoutInflater) getActivity()
								.getSystemService(
										Context.LAYOUT_INFLATER_SERVICE);

						// insert detail into the view
						LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
								LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT);

						mynewlayout.addView(layoutInflater.inflate(
								R.layout.namecard_detail, mynewlayout, false),
								params);

						LinearLayout rl2 = (LinearLayout) getActivity()
								.findViewById(R.id.detail_namecard_bgcolor);
						rl2.setBackgroundColor(Color.parseColor(Constant.styles[Integer.parseInt(profile.getStyleUrl())]));
						TextView summaryTV = (TextView) getActivity()
								.findViewById(R.id.summary);
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
					} else {
						Log.i("if null","not null");
						((LinearLayout) namebar.getParent())
								.removeView(namebar);
					}

				}

			});
		} catch (Exception e) {
			Log.i("layout", e.toString());
			// handle it
		}

	}

	public void insertVideo() {

		LinearLayout videoLayout = (LinearLayout) getActivity()
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
			videoView
					.setVideoURI(Uri.parse(LoginHelper.userInfo.getVideoUrl()));
			Log.i("google", LoginHelper.userInfo.getVideoUrl());
			// videoView.setMediaController(new MediaController(this));
			videoView.requestFocus();
			MediaController mc =  new MyMediaController(getActivity(), (FrameLayout) getActivity().findViewById(R.id.controllerAnchor));
			mc.setAnchorView(getActivity().findViewById(R.id.controllerAnchor));
			videoView.setMediaController(mc);
			// videoView.start();
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {

			Uri videoUri = data.getData();
			LinearLayout record_button_layout = (LinearLayout) getActivity()
					.findViewById(R.id.record_button_layout);
			record_button_layout.setVisibility(View.GONE);

			String[] proj = { MediaStore.Images.Media.DATA };
			Cursor cursor = getActivity().getContentResolver().query(videoUri,
					proj, null, null, null);
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();

			LoginHelper.userInfo.setVideoUrl(cursor.getString(column_index));

			new UploadVideoHelper(getActivity()).execute(
					LoginHelper.userInfo.getId(),
					LoginHelper.userInfo.getVideoUrl());

			insertVideo();
		} else if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
			Log.i("requestcode", "requestCode");
			Bundle b = data.getExtras();
			int styleId = b.getInt("styleId");
			Log.i("null", "" + styleId);
			changeStyleId(styleId);

		}
	}
	
	private void changeStyleId(int styleId) {
		Log.i("change3", "" + styleId);
		LinearLayout rl = (LinearLayout) view
				.findViewById(R.id.namecard_bgcolor);
		// rl.setBackgroundColor(Constant.styles[styleId]);
		rl.setBackgroundColor(Color.parseColor(Constant.styles[styleId]));

		// views.refreshDrawableState();
		// views.invalidate();

		// Log.i("change2",""+styleId);
		LinearLayout rl2 = (LinearLayout) getActivity().findViewById(
				R.id.detail_namecard_bgcolor);
		if (rl2 != null)
			rl2.setBackgroundColor(Color.parseColor(Constant.styles[styleId]));

		// Log.i("change3",""+styleId);
		LoginHelper.userInfo.setStyleUrl("" + styleId);
	}
	
	@Override
	public void onPause(){
		
//		getActivity().getActionBar().hide();
		super.onPause();
	}

}
