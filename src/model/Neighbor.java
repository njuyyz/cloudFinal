package model;

public class Neighbor {
	public String uId;
	public String firstName;
	public String lastName;
	public String picURL;
	public boolean isContact;
	
	public Neighbor(String uId, String firstName, String lastName,
			String picURL, boolean isContact) {
		super();
		this.uId = uId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.picURL = picURL;
		this.isContact = isContact;
	}
}
