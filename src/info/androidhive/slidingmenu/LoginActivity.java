package info.androidhive.slidingmenu;

import helper.LoginHelper;
import android.app.Activity;
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

		setContentView(R.layout.login);
		initView();

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

	private void startMainActivity() {
		// get id from recordFile
		String id = "id";
		
		boolean isRegistered = LoginHelper.tryLogin(id);
		String redirectUrl = LoginHelper.login();
		Log.i("url = ", redirectUrl);
		Intent intent = new Intent(this, AuthActivity.class);
		intent.putExtra("redirectURL", redirectUrl);
		startActivityForResult(intent, 0);
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
	    		String firstName =  (String) bundle.get("profile_firstname");
	    		String lastName =  (String) bundle.get("profile_lastname");
	    		Log.i("token","a"+accessToken);
	    		Log.i("secret","b"+accessSecret);

	    		Intent intent = new Intent(this, MainActivity.class);
	    		intent.putExtra("access_token", accessToken);
	    		intent.putExtra("access_secret", accessSecret);
	    		intent.putExtra("profile_firstname", firstName);
	    		intent.putExtra("profile_lastname", lastName);
	    		Log.i("profile: ",firstName+" "+lastName);
	    		startActivity(intent);
	    		
	            // Do something with the contact here (bigger example below)
	        }
	    }
	}
}
