package util;

import org.apache.http.client.methods.HttpGet;

import android.content.Context;


public class GetRequest extends Request{

	public GetRequest(String url,Context c) {
		super(c);
		HTTP_URL = url;
		requestBase = new HttpGet(url);
	}
	
}
