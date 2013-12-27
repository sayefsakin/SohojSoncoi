package com.sakin.sohojshoncoi;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sakin.sohojshoncoi.bazardor.BazarDor;
import com.sakin.sohojshoncoi.custom.DrawerListAdapter;
import com.sakin.sohojshoncoi.custom.TypefaceSpan;
import com.sakin.sohojshoncoi.custom.VideoElement;
import com.sakin.sohojshoncoi.database.Category;
import com.sakin.sohojshoncoi.database.SSDAO;
import com.sakin.sohojshoncoi.database.Transaction;
import com.sakin.sohojshoncoi.daylihisab.DailyHisab;
import com.sakin.sohojshoncoi.sofol.Sofol;
import com.sakin.sohojshoncoi.uporiae.UporiAe;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.Toast;

public class Main extends FragmentActivity {

	private String[] drawerItems;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private int ITEM;
    
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
        getActionBar().setDisplayHomeAsUpEnabled(false);
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

        int ITEM = Utils.SELECTED_ITEM;
        if (ITEM == -1) {
        	Utils.print("instance not saved");
            selectItem(0);
        } else 
        	selectItem(ITEM);
		
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main, menu);
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
//	    Utils.alarmManagerm.cancel(Utils.pendingIntent);
	    unregisterReceiver(Utils.broadcastReceiver);
	}
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	
    	outState.putInt("selected_item", ITEM);
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	super.onRestoreInstanceState(savedInstanceState);
    	
    	   ITEM = savedInstanceState.getInt("selected_item");
    }
    
	private void init(){
		// all initialization code goes here
		SSDAO.getSSdao().init(this);
		Utils.createCustomCategory(Main.this);
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
		Utils.setActionBarTitle(this, "সহজ সঞ্চয়");
		Utils.pendingIntents = new HashMap<Integer, PendingIntent>();
		setupAlarm();
	}

	private void setupAlarm() {
		Utils.broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent i) {
            	Bundle b = i.getExtras();
            	String msg = b.getString(Utils.ALARM_MSG);
            	double amount = b.getDouble(Utils.ALARM_AMOUNT);
            	int rep = b.getInt(Utils.ALARM_REPEATED);
            	Toast.makeText(c, b.getString("msg") + " " + Double.toString(amount) + "/-", Toast.LENGTH_LONG).show();
            	Utils.runAudio();
//            	have to check vibration
//            	Utils.startVibrate(c);
            }
		};
		registerReceiver(Utils.broadcastReceiver, new IntentFilter("com.sakin.sohojshoncoi"));
		Utils.alarmManagerm = (AlarmManager)(getSystemService( Context.ALARM_SERVICE ));
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
		switch (position) {
			case 0:
				fragment = new DailyHisab();
				break;
			case 1:
				fragment = new BazarDor();
				break;
			case 2:
				fragment = new Sofol();
				break;
			case 4:
				fragment = new UporiAe();
				break;
			default:
				fragment = new DailyHisab();
				break;
		}
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        
        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
//        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
//        setTitle(position);
        mDrawerLayout.closeDrawer(mDrawerList);
        ITEM = position;
	}

	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	    	Utils.print("position clicked" + position);
	    	selectItem(position);
	    }
	}
}
