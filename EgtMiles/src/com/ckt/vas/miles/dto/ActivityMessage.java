package com.ckt.vas.miles.dto;

import java.util.ArrayList;
import java.util.Date;

public class ActivityMessage {
	// Text
	public static final int MESSAGE_TYPE_TEXT = 1;
	// image
	public static final int MESSAGE_TYPE_IMG = 2;
	// make friends
	public static final int MESSAGE_TYPE_MKFRIENDS = 3;
	public static final int MESSAGE_TYPE_MAP = 4;
	private int authorAvatar;

	private String body;

	private int type;

	private String voiceURL;

	private String time;

	private String authorName;

	private String storeName;

	private int storeimg;
	 

	private Date date = null;
	private double longitude;
	private double latitude;
	private String address;
	private ArrayList<ArrayList<String>> contacts = null;
	private String icon;

	//empty used for loading header
	public ActivityMessage(){
		
	}
	
	/**
	 * // text
	 * 
	 * @param authorAvatar
	 * @param authorName
	 * @param storeName
	 * @param body
	 */
	public ActivityMessage(int authorAvatar, String authorName, String storeName, String body,long realtime) {
		this.type = 1;

		this.authorAvatar = authorAvatar;
		this.body = body;
		this.authorName = authorName;
		this.storeName = storeName;
		this.date = new Date(realtime);
	}

	/**
	 * // Image
	 * @param authorAvatar
	 * @param authorName
	 * @param storeName
	 * @param body
	 * @param storeimg
	 */
	public ActivityMessage(int authorAvatar, String authorName, String storeName, String body, int storeimg,long realtime) {

		this.authorAvatar = authorAvatar;
		this.body = body;
		this.type = 2;
		this.authorName = authorName;
		this.storeName = storeName;
		this.storeimg = storeimg;
		this.date = new Date(realtime);
	}

/**
 * 	// Friend
 * @param authorAvatar
 * @param authorName
 * @param friendName
 * @param friendImg
 */
	public ActivityMessage(int authorAvatar,String authorName, String friendName,int friendImg,long realtime) {

		this.authorAvatar = authorAvatar;
		this.type = 3;
		this.authorName = authorName;
		this.storeimg = friendImg;
		this.body = friendName;
		this.date = new Date(realtime);
	}
	/**
	 * 	// Map
	 * @param authorAvatar
	 * @param authorName
	 * @param friendName
	 * @param friendImg
	 */
	public ActivityMessage(int authorAvatar, String authorName, String storeName, String body, int storeimg,long realtime,double log,double lat,String add,ArrayList<ArrayList<String>> cont, String icn) {

		this.authorAvatar = authorAvatar;
		this.body = body;
		this.type = 4;
		this.authorName = authorName;
		this.storeName = storeName;
		this.storeimg = storeimg;
		this.date = new Date(realtime);
		this.longitude = log;
		this.latitude = lat;
		this.address = add;
		this.contacts = cont;
		this.icon = icn;
	}
	/**
	 * 	// cover
	 * @param authorAvatar
	 * @param authorName
	 * @param friendName
	 * @param friendImg
	 */
		public ActivityMessage(String authorName, String friendName,String ic,long realtime) {

			
			this.authorName = authorName;
			this.body = friendName;
			this.icon = ic;
			this.date = new Date(realtime);
		}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public ArrayList<ArrayList<String>> getContacts() {
		return contacts;
	}

	public void setContacts(ArrayList<ArrayList<String>> contacts) {
		this.contacts = contacts;
	}

	public int getHour(){
//		System.out.println("date======="+date);
		if(date!=null)
			return date.getHours();
		return 0;
	}
	public int getMin(){
//		System.out.println("date======="+date);
		if(date!=null)
			return date.getMinutes();
		return 0;
	}
	public int getMonth(){
//		System.out.println("date======="+date);
		if(date!=null)
			return date.getMonth();
		return 0;
	}
	public int getYear(){
//		System.out.println("date======="+date);
		if(date!=null)
			return date.getYear();
		return 0;
	}
	public int getDay(){
//		System.out.println("date======="+date);
		if(date!=null)
			return date.getDate();
		return 0;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getStoreimg() {
		return storeimg;
	}

	public void setStoreimg(int storeimg) {
		this.storeimg = storeimg;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	 

	public int getAuthorAvatar() {
		return authorAvatar;
	}

	public void setAuthorAvatar(int authorAvatar) {
		this.authorAvatar = authorAvatar;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getVoiceURL() {
		return voiceURL;
	}

	public void setVoiceURL(String voiceURL) {
		this.voiceURL = voiceURL;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	

}
