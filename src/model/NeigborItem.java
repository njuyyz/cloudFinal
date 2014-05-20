package model;

import de.passsy.holocircularprogressbar.HoloCircularProgressBar;

public class NeigborItem {
	public HoloCircularProgressBar progressBar;
	public int relation;
	public String uId;

	public NeigborItem(HoloCircularProgressBar progressBar, int relation,
			String uId) {
		super();
		this.progressBar = progressBar;
		this.relation = relation;
		this.uId = uId;
	}
}
