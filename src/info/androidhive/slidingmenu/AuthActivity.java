package info.androidhive.slidingmenu;

import java.util.EnumSet;
import java.util.List;

import helper.LoginHelper;
import model.UserInfo;

import org.scribe.model.Token;
import org.scribe.model.Verifier;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.code.linkedinapi.client.LinkedInApiClient;
import com.google.code.linkedinapi.client.LinkedInApiClientFactory;
import com.google.code.linkedinapi.client.enumeration.ProfileField;
import com.google.code.linkedinapi.schema.DateOfBirth;
import com.google.code.linkedinapi.schema.Education;
import com.google.code.linkedinapi.schema.Educations;
import com.google.code.linkedinapi.schema.Language;
import com.google.code.linkedinapi.schema.Languages;
import com.google.code.linkedinapi.schema.Person;
import com.google.code.linkedinapi.schema.PhoneNumber;
import com.google.code.linkedinapi.schema.PhoneNumbers;

public class AuthActivity extends Activity {

	public static WebView mWebView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = getIntent().getExtras();
		String url = (String) bundle.get("redirectURL");
		Log.i("MyActivity", url);
		setContentView(R.layout.authpage);

		mWebView = (WebView) findViewById(R.id.linkedin_webview);

		mWebView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				super.shouldOverrideUrlLoading(view, url);

				if (url.startsWith("oauth")) {
					mWebView.setVisibility(WebView.GONE);

					final String url1 = url;
					Thread t1 = new Thread() {
						public void run() {
							Uri uri = Uri.parse(url1);

							String verifier = uri
									.getQueryParameter("oauth_verifier");
							Verifier v = new Verifier(verifier);
							Token accessToken = LoginHelper.mService
									.getAccessToken(LoginHelper.requestToken, v);

							final LinkedInApiClientFactory factory = LinkedInApiClientFactory
									.newInstance(LoginHelper.APIKEY,
											LoginHelper.APISECRET);
							final LinkedInApiClient client = factory
									.createLinkedInApiClient(
											accessToken.getToken(),
											accessToken.getSecret());
							Person profile = client.getProfileForCurrentUser(EnumSet
									.of(	
											ProfileField.FIRST_NAME,
											ProfileField.LAST_NAME,
											ProfileField.HEADLINE,
											ProfileField.EDUCATIONS,
											ProfileField.PHONE_NUMBERS,
											ProfileField.ID,
											ProfileField.INDUSTRY,
											ProfileField.HONORS,
											ProfileField.SUMMARY,
											ProfileField.DATE_OF_BIRTH,
											ProfileField.CURRENT_STATUS,
											ProfileField.LANGUAGES,
											ProfileField.EDUCATIONS_SCHOOL_NAME,
											ProfileField.MAIN_ADDRESS,
											ProfileField.PICTURE_URL
									));

							
							LoginHelper.userInfo = new UserInfo(profile);
							String associations = profile.getAssociations();
							Log.i("google", "associations:" + associations);

							String currentStatus = profile.getCurrentStatus();
							Log.i("google", "cuurentStatus:" + currentStatus);

							PhoneNumbers pns = profile.getPhoneNumbers();
							if (pns != null) {
								List<PhoneNumber> pnList = pns
										.getPhoneNumberList();
								if (pnList != null) {
									for (PhoneNumber pn : pnList) {
										Log.i("google",
												"phone:" + pn.getPhoneNumber());
									}
								}
							}

							DateOfBirth dob = profile.getDateOfBirth();
							if (dob != null) {
								Log.i("google",
										"date of birth:" + dob.getYear() + " "
												+ dob.getMonth() + " "
												+ dob.getDay());
							}
							Educations educations = profile.getEducations();
							if (educations != null) {
								List<Education> educationList = educations
										.getEducationList();
								for (int i = 0; i < educationList.size(); i++) {
									Log.i("google", "education:"
											+ educationList.get(i)
													.getSchoolName());
								}
							}
							String headline = profile.getHeadline();
							Log.i("google", "Headline:" + headline);

							String honors = profile.getHonors();
							Log.i("google", "Honors:" + honors);

							String id = profile.getId();
							Log.i("google", "id:" + id);

							String industry = profile.getIndustry();
							Log.i("google", "industry:" + industry);

							String interests = profile.getInterests();
							Log.i("google", "interests:" + interests);

							Languages languages = profile.getLanguages();
							if (languages != null) {
								List<Language> languageList = languages
										.getLanguageList();
								for (Language l : languageList) {
									Log.i("google", "language: "
											+ l.getLanguage().getName());
								}
							}
							profile.getLocation();
							
							String mainAddress = profile.getMainAddress();
							Log.i("google", "main address:" + mainAddress);
							
							String pictureUrl = profile.getPictureUrl();
							Log.i("google", "picture:" + pictureUrl);
							
							profile.getPositions();
							profile.getPublications();
							profile.getPublicProfileUrl();
							profile.getSkills();
							profile.getSpecialties();
							
							String summary = profile.getSummary();
							Log.i("google", "summary:" + summary);
							Log.i("google", "Name:" + profile.getFirstName()
									+ " " + profile.getLastName());
							Intent intent = new Intent();
							intent.putExtra("access_token",
									accessToken.getToken());
							intent.putExtra("access_secret",
									accessToken.getSecret());
							intent.putExtra("profile_firstname",
									profile.getFirstName());
							intent.putExtra("profile_lastname",
									profile.getLastName());
							setResult(RESULT_OK, intent);

							finish();
						}
					};
					t1.start();
				}

				return false;
			}
		});

		mWebView.loadUrl(url);
	}
}
