package helper;

import java.util.ArrayList;

import model.Neighbor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import android.util.Log;

public class JSONHelper {
	public static ArrayList<Neighbor> getNeighbor(String jsonStr){
		ArrayList<Neighbor> neiList = new ArrayList<Neighbor>();
		JSONParser parser = new JSONParser();
		try {
			JSONArray neiArray = (JSONArray) parser.parse(jsonStr);
			for (int i = 0; i < neiArray.size(); i++) {
				JSONObject neiJSON = (JSONObject) neiArray.get(i);
				String uId = (String) neiJSON.get("u_id");
				String firstName = (String)neiJSON.get("first_name");
				String lastName = (String) neiJSON.get("last_name");
				String picURL = (String) neiJSON.get("picture_url");
				String isConStr = (String) neiJSON.get("is_contact");
				boolean isContact = isConStr.equals("true") ? true : false;
				Neighbor nei = new Neighbor(uId, firstName, lastName, picURL, isContact);
				neiList.add(nei);
			}
		} catch (ParseException e) {
			Log.e(">>>", "Parse Neighbor JSON error");
			e.printStackTrace();
		}
		return neiList;
	}
}
