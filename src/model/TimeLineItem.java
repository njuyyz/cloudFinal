package model;

import android.provider.DocumentsContract.Document;

public class TimeLineItem {

	private long realTime;
	private String imgUrlString;
	private String name;
	private String titleString;
	private double[] loc;
	public long getRealTime() {
		return realTime;
	}
	public void setRealTime(long realTime) {
		this.realTime = realTime;
	}
	public String getImgUrlString() {
		return imgUrlString;
	}
	public void setImgUrlString(String imgUrlString) {
		this.imgUrlString = imgUrlString;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitleString() {
		return titleString;
	}
	public void setTitleString(String titleString) {
		this.titleString = titleString;
	}
	public double[] getLoc() {
		loc = new double[]{0.000,1.000};
		return loc;
	}
	public void setLoc(double[] loc) {
		this.loc = loc;
	}
	
	
}
