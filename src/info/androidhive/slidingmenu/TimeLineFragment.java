package info.androidhive.slidingmenu;

import helper.GetTimeLineHelper;
import helper.LoginHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import model.TimeLineItem;


import com.ckt.vas.miles.dto.ActivityMessage;
import com.ckt.vas.miles.ui.activities.MainViewActivity;
import com.ckt.vas.miles.ui.activities.PublicActivity;
import com.ckt.vas.miles.ui.adapters.PublicActivityAdapter;
import com.ckt.vas.miles.ui.views.ExtendedListView;
import com.ckt.vas.miles.ui.views.MenuRightAnimations;
import com.ckt.vas.miles.ui.views.ExtendedListView.OnPositionChangedListener;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;;

public class TimeLineFragment extends Fragment implements OnTouchListener, OnPositionChangedListener {
    /** Called when the activity is first created. */
    private boolean areButtonsShowing;

    private RelativeLayout composerButtonsWrapper;

    private ImageView composerButtonsShowHideButtonIcon;

    private RelativeLayout composerButtonsShowHideButton;

    private RelativeLayout overlayView;

    private ExtendedListView dataListView;
    
    public static ArrayList<TimeLineItem> itemList;

    // clock
    private FrameLayout clockLayout;

    // activity_overlay

    private TextView timeshow;

    @Override
    public void onStart(){
    	super.onStart();
    	itemList = new ArrayList<TimeLineItem>();
    	initControll();
    }
    private void initControll() {
    	MenuRightAnimations.initOffset(getActivity());
        System.out.println(" findViewById(R.id.composer_buttons_wrapper);=="
                + getView().findViewById(R.id.composer_buttons_wrapper));
        RelativeLayout barLayout = (RelativeLayout) getView().findViewById(R.id.qa_bar);
        barLayout.setVisibility(View.GONE);
        composerButtonsWrapper = (RelativeLayout) getView().findViewById(R.id.composer_buttons_wrapper);
        composerButtonsWrapper.setVisibility(View.GONE);
        composerButtonsShowHideButton = (RelativeLayout) getView().findViewById(R.id.composer_buttons_show_hide_button);
        composerButtonsShowHideButton.setVisibility(View.GONE);
//        composerButtonsShowHideButtonIcon = (ImageView) getView().findViewById(R.id.composer_buttons_show_hide_button_icon);
//
//        composerButtonsShowHideButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickView(v, false);
//            }
//        });
//        for (int i = 0; i < composerButtonsWrapper.getChildCount(); i++) {
//            composerButtonsWrapper.getChildAt(i).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View arg0) {
//                    System.out.println("argo=" + arg0.getId() + " click");
//                    Toast.makeText(getActivity().getApplicationContext(), "argo=" + arg0.getId() + " click", 300)
//                            .show();
//                }
//            });
//        }

//        composerButtonsShowHideButton.startAnimation(MenuRightAnimations.getRotateAnimation(0, 360,
//                200));
        //
        dataListView = (ExtendedListView) getView().findViewById(R.id.list_view);

        setAdapterForThis();
        dataListView.setCacheColorHint(Color.TRANSPARENT);
        dataListView.setOnPositionChangedListener(this);
        clockLayout = (FrameLayout)getView().findViewById(R.id.clock);
        // clockLayout.setLayoutChangedListener(dataListView);

        // splash.setVisibility(View.GONE);
//        View v = getView().findViewById(R.id.composer_buttons_wrapper);
//        v.setOnTouchListener(this);
	}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.feed_activity2, container, false);   
    }

    public static List<ActivityMessage> messages = new ArrayList<ActivityMessage>();

    private void initMessages() {
        // set header
//        messages.add(new ActivityMessage("me","boss","http://m.c.lnkd.licdn.com/mpr/mprx/0_q6hJ9Gmt7Fs09Re19b1o9TstSTY1nZZ1n3qQ9TyatkEGWp7PZhrzZ3Hh_fODzxM0zLGFJ6GmTJ-b",1333176510605l));
//
//        // data
//        // text
//        messages.add(new ActivityMessage(R.drawable.gauss0, "Gauss", "1", "hh",
//                1333153510605l));
//
//        // img
//        messages.add(new ActivityMessage(R.drawable.andrew0, "Andrew", "2", "hh",
//                R.drawable.coffe1, 1333163510605l));
//
//        // friend
//        messages.add(new ActivityMessage(R.drawable.andrew0, "Andrew", "Gauss", R.drawable.gauss1,
//                1333173510605l));
//        messages.add(new ActivityMessage(R.drawable.gauss0, "Gauss", "4", "hh",
//                R.drawable.coffe3, 1333193510605l));
//
//        // friend
//        messages.add(new ActivityMessage(R.drawable.gauss0, "Gauss", "Andrew", R.drawable.andrew1,
//                1333166510605l));
//        messages.add(new ActivityMessage(R.drawable.andrew0, "Andrew", "Gauss", R.drawable.gauss1,
//                1333170510605l));
//
//        // img
//        messages.add(new ActivityMessage(R.drawable.andrew0, "Andrew", "5", "hh",
//                R.drawable.coffe0, 1333171510605l));
//        messages.add(new ActivityMessage(R.drawable.gauss0, "Gauss", "6", "hh",
//                R.drawable.coffe5, 1333176510605l));
//
//        // img
//        messages.add(new ActivityMessage(R.drawable.andrew0, "Andrew", "7", "hh",
//                R.drawable.coffe1, 1333185510605l));
//        messages.add(new ActivityMessage(R.drawable.gauss0, "Gauss", "8", "hh",
//                R.drawable.coffe2, 1333187510605l));
//     
//        ArrayList<ArrayList<String>> cont = new ArrayList(Arrays.asList(new ArrayList(Arrays.asList("me","boss","http://m.c.lnkd.licdn.com/mpr/mprx/0_q6hJ9Gmt7Fs09Re19b1o9TstSTY1nZZ1n3qQ9TyatkEGWp7PZhrzZ3Hh_fODzxM0zLGFJ6GmTJ-b"))));
//        cont.add(new ArrayList(Arrays.asList("you","boss","http://m.c.lnkd.licdn.com/mpr/mprx/0_q6hJ9Gmt7Fs09Re19b1o9TstSTY1nZZ1n3qQ9TyatkEGWp7PZhrzZ3Hh_fODzxM0zLGFJ6GmTJ-b")));
//        cont.add(new ArrayList(Arrays.asList("he","boss","http://m.c.lnkd.licdn.com/mpr/mprx/0_q6hJ9Gmt7Fs09Re19b1o9TstSTY1nZZ1n3qQ9TyatkEGWp7PZhrzZ3Hh_fODzxM0zLGFJ6GmTJ-b")));
//        messages.add(new ActivityMessage(R.drawable.gauss0, "Gauss", "8", "hh",
//                R.drawable.coffe2, 1333187510605l,40.8131995,-73.957643,"home",cont,"http://m.c.lnkd.licdn.com/mpr/mprx/0_q6hJ9Gmt7Fs09Re19b1o9TstSTY1nZZ1n3qQ9TyatkEGWp7PZhrzZ3Hh_fODzxM0zLGFJ6GmTJ-b"));
        
    }

    PublicActivityAdapter chatHistoryAdapter;

    private void setAdapterForThis() {
        initMessages();
        
        chatHistoryAdapter = new PublicActivityAdapter(getActivity(), messages);
        dataListView.setAdapter(chatHistoryAdapter);
        new GetTimeLineHelper(getActivity(), chatHistoryAdapter).execute(LoginHelper.userInfo.getId());

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        System.out.println("ontouch...................");
        onClickView(v, true);
        return false;
    }

    public void onClickView(View v, boolean isOnlyClose) {
        if (isOnlyClose) {
            if (areButtonsShowing) {
                MenuRightAnimations.startAnimationsOut(composerButtonsWrapper, 300);
                composerButtonsShowHideButtonIcon.startAnimation(MenuRightAnimations
                        .getRotateAnimation(-315, 0, 300));
                areButtonsShowing = !areButtonsShowing;
            }

        } else {

            if (!areButtonsShowing) {
                MenuRightAnimations.startAnimationsIn(composerButtonsWrapper, 300);
                composerButtonsShowHideButtonIcon.startAnimation(MenuRightAnimations
                        .getRotateAnimation(0, -315, 300));
            } else {
                MenuRightAnimations.startAnimationsOut(composerButtonsWrapper, 300);
                composerButtonsShowHideButtonIcon.startAnimation(MenuRightAnimations
                        .getRotateAnimation(-315, 0, 300));
            }
            areButtonsShowing = !areButtonsShowing;
        }

    }

    private float[] computMinAndHour(int currentMinute, int currentHour) {
        float minuteRadian = 6f * currentMinute;

        float hourRadian = 360f / 12f * currentHour;

        float[] rtn = new float[2];
        rtn[0] = minuteRadian;
        rtn[1] = hourRadian;
        return rtn;
    }

    private float[] lastTime = {
            0f, 0f
    };

    private RotateAnimation[] computeAni(int min, int hour) {

        RotateAnimation[] rtnAni = new RotateAnimation[2];
        float[] timef = computMinAndHour(min, hour);
        System.out.println("min===" + timef[0] + " hour===" + timef[1]);
        RotateAnimation ra = new RotateAnimation(lastTime[0], timef[0], Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setFillAfter(true); 
        ra.setFillBefore(true);
        ra.setDuration(800);
        rtnAni[0] = ra;

        lastTime[0] = timef[0];
        RotateAnimation ra2 = new RotateAnimation(lastTime[1], timef[1], Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        ra2.setFillAfter(true);
        ra2.setFillBefore(true);
        ra2.setDuration(800);
        rtnAni[1] = ra2;
        lastTime[1] = timef[1];
        return rtnAni;
    }

    @Override
    public void onPositionChanged(ExtendedListView listView, int firstVisiblePosition,
            View scrollBarPanel) {
        System.out.println("layout=======padding top========"+scrollBarPanel.getPaddingTop());
        TextView datestr = ((TextView) getView().findViewById(R.id.clock_digital_date));
//        datestr.setText("am");
//        ActivityMessage msg = messages.get(firstVisiblePosition);
        ActivityMessage msg = messages.get(firstVisiblePosition);
        msg.getYear();
        DateFormat df = new SimpleDateFormat("MMM dd, yyyy");
        Date dateTime = msg.getDate();
        StringBuffer bf = new StringBuffer();
//        bf.append("a.m. ");
        bf.append(df.format(dateTime));
//        Log.e(">>>>",bf.toString());
        datestr.setText(bf.toString());

        System.out.println("firstVisiblePosition=============" + firstVisiblePosition);

        System.out.println("scrollBarPanel class===" + scrollBarPanel.getClass());
        int hour = msg.getHour();
        String tmpstr = "";
        
        if (hour >= 12) {
            hour = hour - 12;
            tmpstr += hour + ":" + msg.getMin();
            tmpstr += " p.m.";
        } else {
        	tmpstr += hour + ":" + msg.getMin();
            tmpstr += " a.m.";
        }
        ((TextView) getView().findViewById(R.id.clock_digital_time)).setText(tmpstr);
        RotateAnimation[] tmp = computeAni(msg.getMin(),hour);

        System.out.println("tmp==========" + tmp);

        ImageView minView = (ImageView) getView().findViewById(R.id.clock_face_minute);
        System.out.println("minView============" + minView);
         minView.startAnimation(tmp[0]);
        ImageView hourView = (ImageView) getView().findViewById(R.id.clock_face_hour);
        hourView.setImageResource(R.drawable.clock_hour_rotatable);
        hourView.startAnimation(tmp[1]);

    }

    @Override
    public void onScollPositionChanged(View scrollBarPanel,int top) {
       
        System.out.println("onScollPositionChanged======================");
        MarginLayoutParams layoutParams = (MarginLayoutParams)clockLayout.getLayoutParams();
        System.out.println("left=="+layoutParams.leftMargin+" top=="+layoutParams.topMargin+" bottom=="+layoutParams.bottomMargin+" right=="+layoutParams.rightMargin);
        layoutParams.setMargins(0, top, 0, 0);
        clockLayout.setLayoutParams(layoutParams);
         
        
    }

}
