package com.sakin.sohojshoncoi;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;

import com.sakin.sohojshoncoi.custom.TypefaceSpan;
import com.sakin.sohojshoncoi.database.Account;
import com.sakin.sohojshoncoi.database.Category;
import com.sakin.sohojshoncoi.database.SSDAO;

public class Utils {
	//properties
	public static final boolean DEBUG = true;
	public static Account userAccount = null;
	public static Typeface banglaTypeFace = null;
	
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
    
	public static void createCustomCategory(){
		//default categories
		int i = 1;
		List<Category> categories = new ArrayList<Category>();
		categories.add(new Category(i++,"food", Category.CategoryType.EXPENSE, "", -1));
		categories.add(new Category(i++,"cloth", Category.CategoryType.EXPENSE, "", -1));
		categories.add(new Category(i++,"home", Category.CategoryType.EXPENSE, "", -1));
		categories.add(new Category(i++,"transport", Category.CategoryType.EXPENSE, "", -1));
		categories.add(new Category(i++,"education", Category.CategoryType.EXPENSE, "", -1));
		categories.add(new Category(i++,"play", Category.CategoryType.EXPENSE, "", -1));
		categories.add(new Category(i++,"entertainment", Category.CategoryType.EXPENSE, "", -1));
		categories.add(new Category(i++,"car", Category.CategoryType.EXPENSE, "", -1));
		categories.add(new Category(i++,"medical", Category.CategoryType.EXPENSE, "", -1));
		categories.add(new Category(i++,"Bills", Category.CategoryType.EXPENSE, "", -1));
		categories.add(new Category(i++,"Deposit to Savings", Category.CategoryType.EXPENSE, "", -1));
		categories.add(new Category(i++,"Donations", Category.CategoryType.EXPENSE, "", -1));
		categories.add(new Category(i++,"Others", Category.CategoryType.EXPENSE, "", -1));
		
		categories.add(new Category(i++,"Net Salary", Category.CategoryType.INCOME, "", -1));
		categories.add(new Category(i++,"Bank Withdrawl", Category.CategoryType.INCOME, "", -1));
		categories.add(new Category(i++,"Interest", Category.CategoryType.INCOME, "", -1));
		categories.add(new Category(i++,"Loan", Category.CategoryType.INCOME, "", -1));
		categories.add(new Category(i++,"Others", Category.CategoryType.INCOME, "", -1));

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
}
