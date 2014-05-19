package model;

import de.passsy.holocircularprogressbar.HoloCircularProgressBar;

public class NeigborItem {
	public HoloCircularProgressBar progressBar;
	public int relation;
	public int uId;

	public NeigborItem(HoloCircularProgressBar progressBar, int relation,
			int uId) {
		super();
		this.progressBar = progressBar;
		this.relation = relation;
		this.uId = uId;
	}
}
