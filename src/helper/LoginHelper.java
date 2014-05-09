package helper;

import model.UserInfo;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import util.GetRequest;
import android.util.Log;

public class LoginHelper {

	public final static String APIKEY = "77jgcrfz1r1eu4";
	public final static String APISECRET = "53I9NkQ4YVKLmfPo";
	final static String CALLBACK = "oauth://linkedin";
	public static OAuthService mService;
	public static Token requestToken;
	
	public static UserInfo userInfo;

	private static String authUrl;
	
	public static boolean tryLogin(final String id){
		boolean islogined = false;
		Thread s = new Thread(){
			public void run(){
				GetRequest gr = new GetRequest(
						"http://192.168.47.19:8080/users/getGoodsList");
				JSONObject jsonObject = (JSONObject) JSONValue.parse(gr.getContent());
				try {
						String id = (String)jsonObject.get("u_id");
						if( id.equals("0")){
							return;
						}
						LoginHelper.userInfo = new UserInfo(jsonObject);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		s.start();
		try {
			s.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(LoginHelper.userInfo != null){
			islogined = true;
		}
		return islogined;
		
	}

	public static String login() {
		Thread s = new Thread() {

			public void run() {

				mService = new ServiceBuilder().provider(LinkedInApi.class)
						.apiKey(APIKEY).apiSecret(APISECRET).callback(CALLBACK)
						.scope("r_fullprofile r_contactinfo r_emailaddress").build();
				requestToken = mService.getRequestToken();
				authUrl = mService.getAuthorizationUrl(requestToken);
				Log.i("authUrl",authUrl);
			}
		};
		s.start();
		try {
			s.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return authUrl;
	}

}
