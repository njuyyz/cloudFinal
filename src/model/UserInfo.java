package model;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

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
//	private String[] honorList; 
	private String summary;
	private String dateOfBirth;
	private String currentStatus;
	private String[] languageList;
	private String mainAddress;
	private String picUrl;
	private String videoUrl;
	private String audioUrl;
	private String styleUrl;
	
	

	public UserInfo(JSONObject jsonObject){
		 firstName = (String)jsonObject.get("first_name");
		 lastName = (String)jsonObject.get("last_name");
		 headline = (String)jsonObject.get("headline");
		 String educations = (String)jsonObject.get("educations");
		 educationList = educations.split(":");
		 String phoneNumbers = (String)jsonObject.get("phone_numbers");
		 phoneList = phoneNumbers.split(":");
		 industry = (String)jsonObject.get("industry");
//		 String honors = (String)jsonObject.get("honors");
//		 honorList = honors.split(":");
		 summary = (String)jsonObject.get("summary");
		 dateOfBirth = (String)jsonObject.get("date_of_birth");
		 currentStatus = (String)jsonObject.get("current_status");
		 String languages = (String)jsonObject.get("languages");
		 languageList = languages.split(":");
		 mainAddress = (String)jsonObject.get("main_address");
		 picUrl = (String)jsonObject.get("picture_url");
		 videoUrl = (String)jsonObject.get("video_url");
		 audioUrl = (String)jsonObject.get("audio_url");
		 styleUrl = (String)jsonObject.get("style_id");
		 id = (String)jsonObject.get("u_id");
	}
	
	public UserInfo(Person profile){
		
	
		firstName = profile.getFirstName();
		lastName = profile.getLastName();
		headline = profile.getHeadline();
		Educations educations = profile.getEducations();
		List<Education> educationss = educations.getEducationList();
		ArrayList<String> schoolList = new ArrayList<String>();
		for(Education e: educationss){
			schoolList.add(e.getSchoolName());
		}
		educationList = (String [])schoolList.toArray();
		
		PhoneNumbers pns = profile.getPhoneNumbers();
		List<PhoneNumber> pnList = pns.getPhoneNumberList();
		ArrayList<String> phoneNumberList = new ArrayList<String>();
		for(PhoneNumber pn: pnList){
			phoneNumberList.add(pn.getPhoneNumber());
		}
		phoneList = (String[]) phoneNumberList.toArray();
		
		industry = profile.getIndustry();
		
//		honors = profile.getHonors();
		
		summary = profile.getSummary();
		DateOfBirth dob = profile.getDateOfBirth();
		long day = dob.getDay();
		long month = dob.getMonth();
		long year = dob.getYear();
		dateOfBirth = ""+year+"-"+month+"-"+day;
		
		currentStatus = profile.getCurrentStatus();
		Languages languages = profile.getLanguages();
		List<Language> languagess = languages.getLanguageList();
		ArrayList<String> ll = new ArrayList<String>();
		for( Language l : languagess){
			ll.add(l.getLanguage().getName());
		}
		languageList = (String[]) ll.toArray();
		mainAddress = profile.getMainAddress();
		picUrl = profile.getMainAddress();
		videoUrl = null;
		audioUrl = null;
		styleUrl = null;
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
