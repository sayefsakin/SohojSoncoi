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
	public static void createCustomCategory(){
		//default categories
		List<Category> categories = new ArrayList<Category>();
		categories.add(new Category(1,"food", Category.CategoryType.EXPENSE, "", -1));
		categories.add(new Category(2,"cloth", Category.CategoryType.EXPENSE, "", -1));
		categories.add(new Category(3,"home", Category.CategoryType.EXPENSE, "", -1));
		categories.add(new Category(4,"transport", Category.CategoryType.EXPENSE, "", -1));
		categories.add(new Category(5,"education", Category.CategoryType.EXPENSE, "", -1));
		categories.add(new Category(6,"car", Category.CategoryType.EXPENSE, "", -1));
		categories.add(new Category(7,"Bills & Utilites", Category.CategoryType.EXPENSE, "", -1));
		categories.add(new Category(8,"Deposit to Savings", Category.CategoryType.EXPENSE, "", -1));
		categories.add(new Category(9,"Gift", Category.CategoryType.EXPENSE, "", -1));
		categories.add(new Category(10,"Donations", Category.CategoryType.EXPENSE, "", -1));
		categories.add(new Category(11,"Others", Category.CategoryType.EXPENSE, "", -1));
		
		categories.add(new Category(12,"Net Salary", Category.CategoryType.INCOME, "", -1));
		categories.add(new Category(13,"Bank Withdrawl", Category.CategoryType.INCOME, "", -1));
		categories.add(new Category(14,"Interest", Category.CategoryType.INCOME, "", -1));
		categories.add(new Category(15,"Loan", Category.CategoryType.INCOME, "", -1));
		categories.add(new Category(16,"Others", Category.CategoryType.INCOME, "", -1));

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
