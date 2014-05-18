package helper;

import model.Constant;
import util.GetRequest;
import android.os.AsyncTask;

public class ChangeStyleHelper extends
		AsyncTask<String, Void, Void> {


	@Override
	protected Void doInBackground(String... params) {
		new GetRequest(Constant.CHANGE_STYLE + params[0] + Constant.CHANGE_STYLE2 + params[1]);
		return null;
	}

}
