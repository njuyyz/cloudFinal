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
	public final static String RADAR_REQUEST = SERVER_ADDRESS + "/uid=%s&latitude=%f&longitude=%f";

	public final static String[] styles = { "#FFFFFF", "#DDDDDD", "#AAAAAA",
			"#0074D9", "#7FDBFF", "#39CCCC", "#3D9970", "#2ECC40", "#01FF70",
			"#FFDC00" };
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
