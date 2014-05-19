package model;

public class Neighbor {
	public String uId;
	public String firstName;
	public String lastName;
	public String picURL;
	public int relation;
	
	public Neighbor(String uId, String firstName, String lastName,
			String picURL, int relation) {
		super();
		this.uId = uId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.picURL = picURL;
		this.relation = relation;
	}
}
