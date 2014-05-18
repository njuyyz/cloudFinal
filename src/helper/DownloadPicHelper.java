package helper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class DownloadPicHelper extends
		AsyncTask<String, Void, Bitmap> {

	@Override
	protected Bitmap doInBackground(String... params) {
		// TODO Auto-generated method stub
		URL thumb_u = null;
		try {
			thumb_u = new URL(params[0]);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		URLConnection conn;
		Bitmap bm = null;
		try {
			conn = thumb_u.openConnection();
			conn.connect();
			final BufferedInputStream bis = new BufferedInputStream(
					conn.getInputStream());
			bm = BitmapFactory.decodeStream(bis);
			bis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bm;
	}
	@Override
	protected void onPostExecute(Bitmap bm) {
		
	}

}
