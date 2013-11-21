package com.sakin.sohojshoncoi;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.sakin.sohojshoncoi.bazardor.BazarDor;
import com.sakin.sohojshoncoi.custom.DrawerListAdapter;
import com.sakin.sohojshoncoi.custom.TypefaceSpan;
import com.sakin.sohojshoncoi.database.Category;
import com.sakin.sohojshoncoi.database.SSDAO;
import com.sakin.sohojshoncoi.database.Transaction;
import com.sakin.sohojshoncoi.daylihisab.DailyHisab;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class Main extends Activity {

	private String[] drawerItems;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
		//create the drawer
		drawerItems = getResources().getStringArray(R.array.drawermenulist);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new DrawerListAdapter(this,
                R.layout.drawer_list_item, new ArrayList<String>(Arrays.asList(drawerItems))));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
     // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
            
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
		
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
//        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
	protected void onDestroy() {
	    super.onDestroy();
	    SSDAO.getSSdao().close();
	}
    
	private void init(){
		// all initialization code goes here
		SSDAO.getSSdao().init(this);
		Utils.createCustomCategory();
		
		//include bangla font
		Utils.banglaTypeFace = Typeface.createFromAsset(getAssets(), getString(R.string.font_solaimanlipi));
//		try {
//			SSDAO.getSSdao().deleteAccountByName("User");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		Utils.createSingleAccount();
		
		//set the title
		SpannableString s = new SpannableString("সহজ সঞ্চয়");
		s.setSpan(new TypefaceSpan(this, Utils.banglaTypeFace, "banglaTypeFace"), 0, s.length(),
		        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		getActionBar().setTitle(s);

	}
	
	private void testing() {
//		TextView tv = (TextView) findViewById(R.id.textView1);
//		tv.setTypeface(Utils.banglaTypeFace);
//		tv.setText("hello");
//		
//		EditText ed = (EditText) findViewById(R.id.editText1);
//		ed.setTypeface(Utils.banglaTypeFace);
//		ed.setText("আমি বাংলায় গান গাই");
//		try {
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(new Date());
//			cal.add(Calendar.MONTH, -3);
//			String ss = SSDAO.getSSdao().getTransactionSumBetweenDate(cal.getTime(), new Date());
//			if(ss == null)
//				Utils.print("nothing found");
//			else 
//				Utils.print(ss);
//			List<Transaction> tr = SSDAO.getSSdao().getTransactionBetweenDate(cal.getTime(), new Date());
//			Utils.print(Integer.toString(tr.size()));
//			Category cat = SSDAO.getSSdao().getCategoryDAO().queryForId(4);
//			List<Transaction> tr = SSDAO.getSSdao().getTransactionOfCategoryBetweenDate(cat,cal.getTime(), new Date());
//			String ss = SSDAO.getSSdao().getTransactionSumOfCategoryBetweenDate(cat,cal.getTime(), new Date());
//			if(ss == null)
//				Utils.print("nothing found");
//			else 
//				Utils.print(ss);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	private void selectItem(int position) {
		Fragment fragment = null;
    	if(position == 1){
    		fragment = new DailyHisab();
    	} else {
    		fragment = new BazarDor();
    	}

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

//        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
//        setTitle(position);
        mDrawerLayout.closeDrawer(mDrawerList);
	}

	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	    	Utils.print("position clicked" + position);
	    	selectItem(position);
	    }
	}
}
