package model;


public class Constant {

	public final static String SERVER_ADDRESS = "http://LinkedOut-phbfh4nb3r.elasticbeanstalk.com";
	public final static String UPLOAD_BASIC_INFO_URL = SERVER_ADDRESS
			+ "/new_user";
	public final static String GET_USER_INFO = SERVER_ADDRESS
			+ "/get_user_by_id?uid=";
	public final static String UPLOAD_VIDEO = SERVER_ADDRESS + "/upload";
	public final static String GET_CONTACTS = SERVER_ADDRESS
			+ "/get_contacts?uid=";
	public final static String CHANGE_STYLE = SERVER_ADDRESS
			+ "/setstyle?uid=%s&styleid=%s";
	public final static String RADAR_REQUEST = SERVER_ADDRESS + "/radar?uid=%s&latitude=%f&longitude=%f";
	public final static String ADD_REQUEST = SERVER_ADDRESS + "/add?from=%s&to=%s";
	
	public final static String[] styles = {  "#FCFAF2",
		"#BDC0BA", "#B28FCE", "#9B90C2", "#3D9970","#FFFFFF", "#7B90D2", "#7DB9DE",
		"#81C7D4", "#66BAB7", "#86A697","#FFFFFB", "#ABD8B9", "#91B493", "#B5CAA0",
		"#BEC23F", "#D9CD90", "#EEA9A9", "#FEDFE1", "#DC9FD4"};
	// 0: WHITE #FFFFFF
	// 1: SILVER #DDDDDD
	// 2: GRAY #AAAAAA
	// 3 BLUE #0074D9
	// 4 AQUA #7FDBFF
	// 5 TEAL #39CCCC
	// 6 OLIVE #3D9970
	// 7 GREEN #2ECC40
	// 8 LIME #01FF70
	// 9 YELLOW #FFDC00
}
