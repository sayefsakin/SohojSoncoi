package com.sakin.sohojshoncoi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.R.color;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sakin.sohojshoncoi.custom.TypefaceSpan;
import com.sakin.sohojshoncoi.database.Account;
import com.sakin.sohojshoncoi.database.Category;
import com.sakin.sohojshoncoi.database.SSDAO;

public class Utils {
	//properties
	public static final boolean DEBUG = true;
	public static Account userAccount = null;
	public static Typeface banglaTypeFace = null;
	public static PendingIntent pendingIntent;
    public static BroadcastReceiver broadcastReceiver;
    public static AlarmManager alarmManagerm;
    public static Vibrator vibrator;
	
	public static final String TAB_TITLE_ID = "video_tab_title";
	public static final String TAB_URL_ID = "playlist_url";
	public static final String TAB_ID = "tab_id";
	public static final String VIDEO_ELEMENT_ID = "video_element";
	
	public static final String ADDNEWHISABTAG = "add_new_hisab_fragment";
	
	public final static int MAX_Video_Fragment = 5;
	public static int SELECTED_ITEM = -1; 
	
	public static final String WIFI = "Wi-Fi";
    public static final String ANY = "Any";
    public static boolean wifiConnected = false;
    public static boolean mobileConnected = false;
    public static boolean refreshDisplay = true;
    public static String sPref = null;//users current network preference setting
    
    public static String DEVELOPER_KEY = "AIzaSyCGDUyXYA7PeLXbj5Sv29hYjDAzi8unYA8";
    
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    public static final int CAMERA_RESULT_OK = -1;
    public static final int CAMERA_RESULT_CANCELED = 0;
    public static final String IMAGE_DIRECTORY_NAME = "sohojsoncoi";
    
    public static String ALARM_TONE_DIRECTORY = "";
    
    public static int CURRENT_VISIBLE_PAGE;
    public static int HOME_PAGE = 1;
    public static int ADD_NEW_HISAB_PAGE = 2;
    public static int SHOW_HISAB_PAGE = 3;
    public static int ADD_NEW_REMINDER_PAGE = 4;
    public static int SHOW_REMINDER_PAGE = 5;
    public static int DAILY_HISAB_PAGE = 6;
    
	public static void createCustomCategory(Activity ac){
		//default categories
		int i = 1;
		List<Category> categories = new ArrayList<Category>();
		String[] baeTitle = ac.getResources().getStringArray(R.array.category_title_bae);
		String[] aeTitle = ac.getResources().getStringArray(R.array.category_title_ae);
		for(int j = 0;j<baeTitle.length;j++){
			categories.add(new Category(i++,baeTitle[j], Category.CategoryType.EXPENSE, "", -1));
		}
		for(int j = 0;j<aeTitle.length;j++){
			categories.add(new Category(i++,aeTitle[j], Category.CategoryType.INCOME, "", -1));
		}

		for(Category thisCategory : categories){
			SSDAO.getSSdao().getCategoryDAO().createOrUpdate(thisCategory);
		}
	}
	
	public static void createSingleAccount(){
		//I tried to insest user as id=1 but I failed. will check later
		Account ac = new Account(28, "User", 0.0, 0.0);
		SSDAO.getSSdao().getAccountDAO().createOrUpdate(ac);
		userAccount = ac;
	}
	
	public static void print(String ss){
		if(Utils.DEBUG){
			Log.i("testing", ss);
		}
	}
	
	public static void setActionBarTitle(Activity ac, String ss){
		SpannableString s = new SpannableString(ss);
		s.setSpan(new TypefaceSpan(ac, Utils.banglaTypeFace, "banglaTypeFace"), 0, s.length(),
		        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ac.getActionBar().setTitle(s);
	}
	
	public static void showToast(Activity ac, String msg){
		RelativeLayout toastView = new RelativeLayout(ac.getApplicationContext());
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(400,200);
		toastView.setLayoutParams(params);
		toastView.setBackgroundColor(Color.DKGRAY);
		
		TextView tv = new TextView(ac.getApplicationContext());
		tv.setTypeface(Utils.banglaTypeFace);
		tv.setText(msg);
		tv.setTextSize(16);
//		tv.setBackgroundColor(color.darker_gray);
		toastView.addView(tv);
		
		
		Toast toast = new Toast(ac.getApplicationContext());
		toast.setView(toastView);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.show();
	}
	
	
	public static void runAudio(){
		MediaPlayer mp = new MediaPlayer();
		try {
			mp.setDataSource(Utils.ALARM_TONE_DIRECTORY);
			mp.prepare();
			mp.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void startVibrate(Activity ac) {
        long pattern[]={0,200,100,300,400};
 
        vibrator = (Vibrator)ac.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern, 0);        
    }
	
    public static void stopVibrate() {
        vibrator.cancel();
    }
}
