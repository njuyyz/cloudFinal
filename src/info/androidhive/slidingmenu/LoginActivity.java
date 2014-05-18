package info.androidhive.slidingmenu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import model.UserInfo;
import helper.LoginHelper;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginActivity extends Activity {

	Button btLogin;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// get id from recordFile
		Log.i(">>>log in ", "before test id");
		String id = getId();
		if (id != null) {
			Log.i(">>>log in", "id = "+id);
			LoginHelper.tryLogin(id);

			jumpToMainActivity();
		} else {

			setContentView(R.layout.login);
			initView();
		}

	}

	private void initView() {
		btLogin = (Button) findViewById(R.id.login_bt);
		btLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startMainActivity();
			}

		});
	}

	private String getId() {
		File idFile = new File(this.getFilesDir(), "id");
		if (idFile.exists()) {
			FileInputStream fis = null;
			try {
				fis = this.openFileInput("id");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader bufferedReader = new BufferedReader(isr);
			try {
				String line = bufferedReader.readLine();
				Log.i("google", line);
				return line;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	private void writeId() {
		String filename = "id";
		String string = LoginHelper.userInfo.getId();
		FileOutputStream outputStream;

		try {
			outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
			outputStream.write(string.getBytes());
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void startMainActivity() {
		String redirectUrl = LoginHelper.login();
		Log.i("url = ", redirectUrl);
		Intent intent = new Intent(this, AuthActivity.class);
		intent.putExtra("redirectURL", redirectUrl);
		startActivityForResult(intent, 0);
	}

	private void jumpToMainActivity() {

		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Check which request we're responding to
		if (requestCode == 0) {
			// Make sure the request was successful
			if (resultCode == RESULT_OK) {
				// The user picked a contact.
				// The Intent's data Uri identifies which contact was selected.
				Bundle bundle = data.getExtras();
				String accessToken = (String) bundle.get("access_token");
				String accessSecret = (String) bundle.get("access_secret");
				Log.i("token", "a" + accessToken);
				Log.i("secret", "b" + accessSecret);

				// create file and write id into the file
				writeId();
				LoginHelper.tryLogin(LoginHelper.userInfo.getId());

				jumpToMainActivity();

				// Do something with the contact here (bigger example below)
			}
		}
	}
}
