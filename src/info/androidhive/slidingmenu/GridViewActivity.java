package info.androidhive.slidingmenu;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import helper.ChangeStyleHelper;
import helper.LoginHelper;

import java.util.ArrayList;
import java.util.List;

import model.UserInfo;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;

public class GridViewActivity extends Activity {

	public static final String[] colors = {"WHITE", "SILVER", "GRAY", "BLUE", "AQUA", "TEAL", "OLIVE", "GREEN", "LIME", "YELLOW"};
	// 0: WHITE #FFFFFF 
	// 1: SILVER #DDDDDD 
	// 2: GRAY #AAAAAA
	// 3  BLUE #0074D9     
	// 4  AQUA #7FDBFF 
	// 5  TEAL #39CCCC
	// 6  OLIVE #3D9970
	// 7  GREEN #2ECC40
	// 8  LIME #01FF70
	// 9  YELLOW #FFDC00

	public OnItemClickListener listener = new OnItemClickListener()	{
		public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
			Toast.makeText(GridViewActivity.this, "" + position, Toast.LENGTH_SHORT).show();
		
			String idString = LoginHelper.userInfo.getId();
			int styleString = position;
			
			// this str[] is for updating the database
			ChangeStyleHelper csh = new ChangeStyleHelper(v.getContext());
			csh.execute(idString,""+styleString);
			// send back data to previous activity
			Intent returnIntent = new Intent();
			returnIntent.putExtra("styleId", styleString);
			setResult(RESULT_OK,returnIntent);
			finish();
		}
		
	};

	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gridview);

		GridView gridView = (GridView) findViewById(R.id.activity_gridview_gv);
		SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(new MyAdapter(this, getItems()));
		swingBottomInAnimationAdapter.setAbsListView(gridView);
		swingBottomInAnimationAdapter.setInitialDelayMillis(300);
		gridView.setAdapter(swingBottomInAnimationAdapter);
		
		gridView.setOnItemClickListener(listener);

		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	private ArrayList<Integer> getItems() {
		ArrayList<Integer> items = new ArrayList<Integer>();
		for (int i = 0; i < 50; i++) {
			items.add(i);
		}
		return items;
	}

	private static class MyAdapter extends ArrayAdapter<Integer> {

		private final Context mContext;
		private final LruCache<Integer, Bitmap> mMemoryCache;

		public MyAdapter(final Context context, final List<Integer> list) {
			super(list);
			mContext = context;

			final int cacheSize = (int) (Runtime.getRuntime().maxMemory() / 1024);
			mMemoryCache = new LruCache<Integer, Bitmap>(cacheSize) {
				@Override
				protected int sizeOf(final Integer key, final Bitmap bitmap) {
					// The cache size will be measured in kilobytes rather than
					// number of items.
					return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
				}
			};
		}

		@Override
		public View getView(final int position, final View convertView, final ViewGroup viewGroup) {
			ImageView imageView = (ImageView) convertView;

			if (imageView == null) {
				imageView = new ImageView(mContext);
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			}

			int imageResId;
			switch (getItem(position) % 10) {
			case 0:
				imageResId = R.drawable.white;
				break;
			case 1:
				imageResId = R.drawable.silver;
				break;
			case 2:
				imageResId = R.drawable.gray;
				break;
			case 3:
				imageResId = R.drawable.blue;
				break;
			case 4:
				imageResId = R.drawable.aqua;
				break;
			case 5:
				imageResId = R.drawable.teal;
				break;
			case 6:
				imageResId = R.drawable.olive;
				break;
			case 7:
				imageResId = R.drawable.green;
				break;
			case 8:
				imageResId = R.drawable.lime;
				break;
			case 9:
				imageResId = R.drawable.yellow;
				break;
			default:
				imageResId = R.drawable.yellow;
			}

			Bitmap bitmap = getBitmapFromMemCache(imageResId);
			if (bitmap == null) {
				bitmap = BitmapFactory.decodeResource(mContext.getResources(), imageResId);
				addBitmapToMemoryCache(imageResId, bitmap);
			}

			// set the background Color
			imageView.setImageBitmap(bitmap);

			return imageView;
		}

		private void addBitmapToMemoryCache(final int key, final Bitmap bitmap) {
			if (getBitmapFromMemCache(key) == null) {
				mMemoryCache.put(key, bitmap);
			}
		}

		private Bitmap getBitmapFromMemCache(final int key) {
			return mMemoryCache.get(key);
		}
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
