
package com.ckt.vas.miles.ui.adapters;





import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;





import android.R.integer;
import android.R.string;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.SyncStateContract.Constants;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ckt.vas.miles.R;
import com.ckt.vas.miles.dto.ActivityMessage;
import com.ckt.vas.miles.helpers.Constant;
import com.ckt.vas.miles.ui.views.CircularImage;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
public class PublicActivityAdapter extends BaseAdapter {
    protected static final String TAG = "ChattingAdapter";

    private Context context;
    private final Context mContext;
    public static final String TEXT_FORMAT = "<font color='#1479ad'>%s</font>  <font color='#1479ad'><b>%s</b></font>";

    public static final String TEXT_ADDFRD_FORMAT_WITHFROM = "<font color='#1479ad'>%s</font>  <font color='#1479ad'>%s</font> ";

    public static final String TEXT_ADDFRD_NOFROM = " <font color='#1479ad'><b>%s</b></font> ";
    public static final String TEXT_MAP= "In <font color='#1479ad'>%s</font>, added <font color='#1479ad'><b>%s</b></font> contacts";

    public static List<ActivityMessage> msgs;

    public static AnimateFirstDisplayListener animateListener = null;

	public static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
    public PublicActivityAdapter(Context context, List<ActivityMessage> messages) {
        super();
        this.context = context;
  
        mContext = context;
        this.msgs = messages;


    }

    @Override
    public int getCount() {
        return msgs.size();
    }

    @Override
    public Object getItem(int position) {
        return msgs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ActivityMessage message = msgs.get(position);
        System.out.println("position===========" + position);
        ViewHolder holder;
        if (convertView == null || (holder = (ViewHolder) convertView.getTag()).flag != position) {
            holder = new ViewHolder();

            if (position == 0) {
                holder.flag = position;
                convertView = LayoutInflater.from(context).inflate(R.layout.mixed_feed_cover_row,
                        null);
                ImageView cover = (ImageView) convertView.findViewById(R.id.cover_image);
                cover.setImageResource(R.drawable.cover);
                CircularImage avatar = (CircularImage) convertView
                        .findViewById(R.id.cover_user_photo);
                ImageLoader imageLoader = ImageLoader.getInstance();
        		imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        		//Log.e("ERROR>>>>>", getMapURL);
        		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.activity_bar)
				.showImageOnFail(R.drawable.activity_badge).cacheInMemory()
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED).cacheOnDisc()
				.displayer(new RoundedBitmapDisplayer(500)).build();
        		imageLoader.displayImage(message.getIcon(), avatar);
//                avatar.setImageResource(R.drawable.gauss0);
                ImageView coverRefresh = (ImageView) convertView.findViewById(R.id.cover_refresh);
                coverRefresh.setVisibility(View.GONE);
//                RoundedCornersImage friend = (RoundedCornersImage) convertView
//                        .findViewById(R.id.cover_requests_photo);
//                friend.setImageResource(R.drawable.andrew0);

                // lay.setl
                // ((LinearLayout)convertView).setLayoutParams(new
                // LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 200));
            }

            else {

                int type = message.getType();
                holder.flag = position;

                // Item layout
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.mixed_feed_activity_item, null);

                RelativeLayout userIconLayout = (RelativeLayout) convertView.findViewById(R.id.mixed_feed_track);
                userIconLayout.setVisibility(View.INVISIBLE);
//                // author text
//                ImageView authorView = (ImageView) convertView
//                        .findViewById(R.id.mixed_feed_author_photo);
//                authorView.setImageResource(message.getAuthorAvatar());
//
//                // author img
//                TextView authorName = (TextView) convertView
//                        .findViewById(R.id.mixed_feed_authorname);
//                authorName.setText(message.getAuthorName());

                // big circle
                ImageView big = (ImageView) convertView.findViewById(R.id.moment_bigdot);

                // big smallcircle
                ImageView smal = (ImageView) convertView.findViewById(R.id.moment_smalldot);

                // image type
                ImageView imgType = (ImageView) convertView.findViewById(R.id.moment_people_photo);

                // feed type image
                ImageView feed_post_type = (ImageView) convertView
                        .findViewById(R.id.feed_post_type);

                // content layout
                LinearLayout contentLayout = (LinearLayout) convertView
                        .findViewById(R.id.feed_post_body);
                // Text
                if (ActivityMessage.MESSAGE_TYPE_TEXT == type) {
                    big.setVisibility(View.GONE);
                    smal.setVisibility(View.VISIBLE);
                    View view = LayoutInflater.from(context).inflate(
                            R.layout.moment_thought_partial, null);

                    TextView thought_main = (TextView) view.findViewById(R.id.thought_main);
                    // thought_main.setText(message.getBody());
                    String txtstr = String.format(TEXT_FORMAT, message.getAuthorName(),
                            message.getStoreName());

                    Spanned spt = Html.fromHtml(txtstr);

                    thought_main.setText(spt);

                    contentLayout.addView(view);

                }
                // Img
                else if (ActivityMessage.MESSAGE_TYPE_IMG == type) {
                    smal.setVisibility(View.GONE);
                    big.setVisibility(View.VISIBLE);

                    View view = LayoutInflater.from(context).inflate(R.layout.moment_photo_partial,
                            null);
                    feed_post_type.setImageResource(R.drawable.moment_icn_place);
                    // photo
                    ImageView photoView = (ImageView) view.findViewById(R.id.photo);
                    photoView.setImageResource(message.getStoreimg());

                    TextView comment = (TextView) view.findViewById(R.id.comment);
                    String txtstr = String.format(TEXT_FORMAT, message.getAuthorName(),
                            message.getStoreName());
                    Spanned spt = Html.fromHtml(txtstr);
                    comment.setText(spt);

                    contentLayout.addView(view);
                }

                // Friend
                else if (ActivityMessage.MESSAGE_TYPE_MKFRIENDS == type) {
                    smal.setVisibility(View.GONE);
                    big.setVisibility(View.VISIBLE);
                    View view = LayoutInflater.from(context).inflate(
                            R.layout.moment_people_partial, null);
                    imgType.setImageResource(R.drawable.m_san);

                    // mainView.setText("Gauss");
                    // main
                    TextView comment = (TextView) view.findViewById(R.id.people_body);
                    String txtstr = String.format(TEXT_ADDFRD_FORMAT_WITHFROM,
                            message.getAuthorName(), message.getBody());
                    Spanned spt = Html.fromHtml(txtstr);
                    comment.setText(spt);

                    // count of commment
                    TextView countcmt = (TextView) view.findViewById(R.id.comment_button_text);
                    countcmt.setText("5");

                    // friend photo
                    ImageView friendphoto = (ImageView) view.findViewById(R.id.friendphoto);
                    friendphoto.setImageResource(message.getStoreimg());
//
//                    RotateAnimation ra = new RotateAnimation(0f, 150f, Animation.RELATIVE_TO_SELF,
//                            0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//                    ra.setFillAfter(true);
//               
//                    ra.setDuration(10000);
//                    
//                    friendphoto.setAnimation(ra);

                    contentLayout.addView(view);
                }else if (ActivityMessage.MESSAGE_TYPE_MAP == type) {
                    smal.setVisibility(View.GONE);
                    big.setVisibility(View.VISIBLE);

                    View view = LayoutInflater.from(context).inflate(R.layout.moment_map_partial,
                            null);
                    feed_post_type.setImageResource(R.drawable.moment_icn_place);
                    // photo
                    RelativeLayout smileFace = (RelativeLayout) view.findViewById(R.id.comment_button);
                    smileFace.setVisibility(View.GONE);
                    ImageView photoView = (ImageView) view.findViewById(R.id.photo);
                    String getMapURL = "http://maps.googleapis.com/maps/api/staticmap?zoom=18&size=560x240&markers=size:mid|color:red|"  
                    		+message.getLongitude()+","+message.getLatitude()
                    		+ "&sensor=false";
                    
                    ImageLoader imageLoader = ImageLoader.getInstance();
            		imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
            		//Log.e("ERROR>>>>>", getMapURL);
            		DisplayImageOptions options = new DisplayImageOptions.Builder()
    				.showImageForEmptyUri(R.drawable.activity_bar)
    				.showImageOnFail(R.drawable.activity_badge).cacheInMemory()
    				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED).cacheOnDisc()
    				.displayer(new RoundedBitmapDisplayer(500)).build();
            		imageLoader.displayImage(getMapURL, photoView);
            		
            		//@SuppressLint("NewApi")
//            		Thread fetchThread = new Thread(new Runnable() {
//            			@Override
//            			public void run() {
//            				
//            					new FetchAddressTask().execute();
//            					
//            			}
//            		});
            		FetchAddressTask fetchTask = new FetchAddressTask(view,message.getContacts().size());
            		String getAddressURL = "https://maps.googleapis.com/maps/api/geocode/json?latlng="  
                    		+message.getLongitude()+","+message.getLatitude()
                    		+ "&sensor=true_or_false&key="+Constant.MAP_KEY;
            		Log.e(">>>>", getAddressURL);
            		fetchTask.execute(getAddressURL);
            		ArrayList<ArrayList<String>> cont = message.getContacts();
            		
            		
//                    RelativeLayout photoWrapper = (RelativeLayout) view.findViewById(R.id.photo_wrapper);
//                    photoWrapper.setVisibility(View.GONE);
                    
                    
                    TextView commentContent = (TextView) view.findViewById(R.id.comment_body);
                    commentContent.setText(cont.get(0).get(0));
                    TextView commentDate = (TextView) view.findViewById(R.id.comment_sub);
                    commentDate.setText(cont.get(0).get(1));
                    ImageView iconPhoto = (ImageView) view.findViewById(R.id.comment_profile_photo);
                    imageLoader.displayImage(cont.get(0).get(2), iconPhoto);
                    contentLayout.addView(view);
                    if(cont.size()>1){
	                    for(int i=1; i<cont.size(); i++){
	                    	Log.e(">>>>>", "inside loop");
	                    	View view2 = LayoutInflater.from(context).inflate(R.layout.moment_map_partial,
	                                null);
	//                        feed_post_type.setImageResource(R.drawable.moment_icn_place);
	                        // photo
	                        ImageView photoView2 = (ImageView) view2.findViewById(R.id.photo);
//	                        photoView2.setImageResource(message.getStoreimg());
	
	                        photoView2.setVisibility(View.GONE);
	                        RelativeLayout photoWrapper2 = (RelativeLayout) view2.findViewById(R.id.photo_wrapper);
	                        photoWrapper2.setVisibility(View.GONE);
	
	                        TextView comment2 = (TextView) view2.findViewById(R.id.comment);
	//                        String txtstr2 = String.format(TEXT_MAP, message.getAddress(),
	//                                message.getLongitude());
	//                        Spanned spt2 = Html.fromHtml(txtstr2);
	                        comment2.setVisibility(View.GONE);
	                        TextView commentContent2 = (TextView) view2.findViewById(R.id.comment_body);
	                        commentContent2.setText(cont.get(i).get(0));
	                        TextView commentDate2 = (TextView) view2.findViewById(R.id.comment_sub);
	                        commentDate2.setText(cont.get(i).get(1));
	                        ImageView iconPhoto2 = (ImageView) view2.findViewById(R.id.comment_profile_photo);
	                        imageLoader.displayImage(cont.get(i).get(2), iconPhoto2);
	                        contentLayout.addView(view2);
	            		}
                    }
                    
                }

                else {
                    smal.setVisibility(View.GONE);
                    big.setVisibility(View.VISIBLE);
                }

            }
            convertView.setTag(holder);

        }

        return convertView;
    }

    class FetchAddressTask extends
		AsyncTask<String, Void, String> {
//    	Context context;
    	View presentView;
    	int count;
    	public FetchAddressTask(View transportView, int c) {
//    		this.context = context;
    		presentView = transportView;
    		count = c;
    	}

    	@Override
    	protected String doInBackground(String... params) {

    		JSONObject result;
    		String address = "0";
			try {
				result = readJsonFromUrl(params[0]);
				JSONObject temp = (JSONObject) result.getJSONArray("results").get(0);
				address = temp.getString("formatted_address");
	    		Log.e(">>>>", address );
	    		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return address;
    	}
	
    	@Override
    	protected void onPostExecute(String address) {
    		super.onPostExecute(address);
    		TextView comment = (TextView) presentView.findViewById(R.id.comment);
            String txtstr = String.format(TEXT_MAP, address,
                   count);
            Spanned spt = Html.fromHtml(txtstr);
            comment.setText(spt);
    	}
    }
    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
          sb.append((char) cp);
//          Log.e(">>>>>", sb.toString());
        }
        return sb.toString();
      }

    private JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
          BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
          String jsonText = readAll(rd);
          JSONObject json = new JSONObject(jsonText);
          return json;
        } finally {
          is.close();
        }
      }    
    
    static class ViewHolder {
        TextView text;

        TextView time;

        TextView status;

        int flag = -1;
    }

}
