package model;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import android.graphics.Bitmap;

import com.google.code.linkedinapi.schema.DateOfBirth;
import com.google.code.linkedinapi.schema.Education;
import com.google.code.linkedinapi.schema.Educations;
import com.google.code.linkedinapi.schema.Language;
import com.google.code.linkedinapi.schema.Languages;
import com.google.code.linkedinapi.schema.Person;
import com.google.code.linkedinapi.schema.PhoneNumber;
import com.google.code.linkedinapi.schema.PhoneNumbers;

public class UserInfo {

	private String id;
	private String firstName;
	private String lastName;
	private String headline;
	private String[] educationList;
	private String[] phoneList;
	private String industry;
	// private String[] honorList;
	private String summary;
	private String dateOfBirth;
	private String currentStatus;
	private String[] languageList;
	private String mainAddress;
	private String picUrl;
	private String videoUrl;
	private String audioUrl;
	private String styleUrl;

	private Bitmap thumbNail;

	public Bitmap getThumbNail() {
		return thumbNail;
	}

	public void setThumbNail(Bitmap thumbNail) {
		this.thumbNail = thumbNail;
	}

	public UserInfo(JSONObject jsonObject) {
		firstName = (String) jsonObject.get("first_name");
		lastName = (String) jsonObject.get("last_name");
		headline = (String) jsonObject.get("headline");
		String educations = (String) jsonObject.get("educations");
		educationList = educations.split(":");
		String phoneNumbers = (String) jsonObject.get("phone_numbers");
		phoneList = phoneNumbers.split(":");
		industry = (String) jsonObject.get("industry");
		// String honors = (String)jsonObject.get("honors");
		// honorList = honors.split(":");
		summary = (String) jsonObject.get("summary");
		dateOfBirth = (String) jsonObject.get("date_of_birth");
		currentStatus = (String) jsonObject.get("current_status");
		String languages = (String) jsonObject.get("languages");
		languageList = languages.split(":");
		mainAddress = (String) jsonObject.get("main_address");
		picUrl = (String) jsonObject.get("picture_url");
		videoUrl = (String) jsonObject.get("video_url");
		audioUrl = (String) jsonObject.get("audio_url");
		styleUrl = (String) jsonObject.get("style_id");
		if (styleUrl == null || styleUrl.equals("null")) {
			styleUrl = "0";
		}
		id = (String) jsonObject.get("u_id");
	}

	@SuppressWarnings("unchecked")
	public JSONObject convertToJson() {
		JSONObject object = new JSONObject();
		object.put("u_id", id);
		object.put("first_name", firstName);
		object.put("last_name", lastName);
		object.put("headline", headline);
		StringBuffer educationBuffer = new StringBuffer();
		for (int i = 0; i < educationList.length; i++) {
			educationBuffer.append(educationList[i] + ":");
			// result.append( optional separator );
		}
		if (educationBuffer.length() - 1 > 0) {
			String educationString = educationBuffer.substring(0,
					educationBuffer.length() - 1);
			object.put("educations", educationString);
		}else{
			object.put("educations", "");
		}
		StringBuffer phoneBuffer = new StringBuffer();
		for (int i = 0; i < phoneList.length; i++) {
			phoneBuffer.append(phoneList[i] + ":");
			// result.append( optional separator );
		}
		if (phoneBuffer.length() - 1 > 0) {
			String phoneString = phoneBuffer.substring(0,
					phoneBuffer.length() - 1);
			object.put("phone_numbers", phoneString);
		} else {

			object.put("phone_numbers", "");
		}

		object.put("industry", industry);
		object.put("honors", null);
		object.put("summary", summary);
		object.put("date_of_birth", dateOfBirth);
		object.put("current_status", currentStatus);

		StringBuffer languageBuffer = new StringBuffer();
		for (int i = 0; i < languageList.length; i++) {
			languageBuffer.append(languageList[i] + ":");
			// result.append( optional separator );
		}
		if (languageBuffer.length() - 1 > 0) {
			String languageString = languageBuffer.substring(0,
					languageBuffer.length() - 1);
			object.put("languages", languageString);
		} else {

			object.put("languages", "");
		}
		object.put("main_address", mainAddress);
		object.put("picture_url", picUrl);
		object.put("video_url", videoUrl);
		object.put("audio_url", audioUrl);
		object.put("style_id", styleUrl);

		return object;
	}

	public UserInfo(Person profile) {

		firstName = profile.getFirstName();
		lastName = profile.getLastName();
		headline = profile.getHeadline();
		Educations educations = profile.getEducations();
		educationList = new String[] { "" };
		if (educations != null) {

			List<Education> educationss = educations.getEducationList();
			ArrayList<String> schoolList = new ArrayList<String>();
			educationList = new String[educationss.size()];
			for (Education e : educationss) {
				schoolList.add(e.getSchoolName());
			}
			educationList = schoolList.toArray(educationList);

		}
		PhoneNumbers pns = profile.getPhoneNumbers();
		phoneList = new String[] { "" };
		if (pns != null) {
			List<PhoneNumber> pnList = pns.getPhoneNumberList();
			ArrayList<String> phoneNumberList = new ArrayList<String>();
			phoneList = new String[phoneNumberList.size()];
			for (PhoneNumber pn : pnList) {
				phoneNumberList.add(pn.getPhoneNumber());
			}
			phoneList = phoneNumberList.toArray(phoneList);

		}

		industry = profile.getIndustry();

		// honors = profile.getHonors();

		summary = profile.getSummary();
		DateOfBirth dob = profile.getDateOfBirth();
		if (dob != null) {
			long day = dob.getDay();
			long month = dob.getMonth();
			long year = dob.getYear();
			dateOfBirth = "" + year + "-" + month + "-" + day;
		}
		currentStatus = profile.getCurrentStatus();
		Languages languages = profile.getLanguages();
		languageList = new String[] { "" };
		if (languages != null) {
			List<Language> languagess = languages.getLanguageList();
			ArrayList<String> ll = new ArrayList<String>();
			languageList = new String[ll.size()];
			for (Language l : languagess) {
				ll.add(l.getLanguage().getName());
			}
			languageList = ll.toArray(languageList);
		}
		mainAddress = profile.getMainAddress();
		picUrl = profile.getPictureUrl();
		videoUrl = null;
		audioUrl = null;
		styleUrl = "0";
		id = profile.getId();

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String[] getEducationList() {
		return educationList;
	}

	public void setEducationList(String[] educationList) {
		this.educationList = educationList;
	}

	public String[] getPhoneList() {
		return phoneList;
	}

	public void setPhoneList(String[] phoneList) {
		this.phoneList = phoneList;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public String[] getLanguageList() {
		return languageList;
	}

	public void setLanguageList(String[] languageList) {
		this.languageList = languageList;
	}

	public String getMainAddress() {
		return mainAddress;
	}

	public void setMainAddress(String mainAddress) {
		this.mainAddress = mainAddress;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getAudioUrl() {
		return audioUrl;
	}

	public void setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl;
	}

	public String getStyleUrl() {
		return styleUrl;
	}

	public void setStyleUrl(String styleUrl) {
		this.styleUrl = styleUrl;
	}

}
