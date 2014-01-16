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
import com.sakin.sohojshoncoi.custom.FireAlarmDialogFragment;
import com.sakin.sohojshoncoi.custom.TypefaceSpan;
import com.sakin.sohojshoncoi.custom.VideoElement;
import com.sakin.sohojshoncoi.database.Category;
import com.sakin.sohojshoncoi.database.SSDAO;
import com.sakin.sohojshoncoi.database.Transaction;
import com.sakin.sohojshoncoi.daylihisab.DailyHisab;
import com.sakin.sohojshoncoi.jiggasa.JiggasaFragment;
import com.sakin.sohojshoncoi.settings.SettingsFragment;
import com.sakin.sohojshoncoi.sofol.Sofol;
import com.sakin.sohojshoncoi.soncoi.SoncoiBriddhi;
import com.sakin.sohojshoncoi.uporiae.UporiAe;

import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
		init(savedInstanceState);
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
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		
		Fragment myFragment = (Fragment) getSupportFragmentManager().findFragmentByTag("HOME_PAGE");
		if (myFragment != null &&  myFragment.isVisible()) {
//		   Utils.print("fragment found");
	    	showExitDialog();
	    	return false;
		}
		
		Fragment fragment = (Fragment) getSupportFragmentManager().findFragmentByTag("dailyhisab");
		if (fragment != null &&  fragment.isVisible()) {
	    if (keyCode == KeyEvent.KEYCODE_BACK && ITEM != 0 ) {
	        selectItem(0);
	        return false;
	    }}
//	    else if (keyCode == KeyEvent.KEYCODE_BACK && Utils.SELECTED_ITEM == 0) {
//	    	
//	    	showExitDialog();
//	    	return false;
//	    }

	    return super.onKeyDown(keyCode, event);
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
    
	private void init(Bundle savedInstanceState){
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
            	int alermID = b.getInt(Utils.COME_FROM_ALARM);
//            	Toast.makeText(c, msg + " " + Double.toString(amount) + "/-", Toast.LENGTH_LONG).show();
            	Utils.runAudio();
            	Utils.startVibrate(c);
            	
            	Intent alarmPopup = new Intent(c, AlarmPopup.class);
            	alarmPopup.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            	alarmPopup.putExtra(Utils.ALARM_MSG, msg);
            	alarmPopup.putExtra(Utils.ALARM_AMOUNT, amount);
            	alarmPopup.putExtra(Utils.ALARM_REPEATED, rep);
            	alarmPopup.putExtra(Utils.COME_FROM_ALARM, alermID);
            	c.startActivity(alarmPopup);
//            	final NotificationManager nm = createNotificationAndNotify(c, msg, amount, rep);
//            	
////            	DialogFragment dialog = new FireAlarmDialogFragment();
////                dialog.show(getSupportFragmentManager(), "FireAlarmDialogFragment");
//            	AlertDialog.Builder builder = new AlertDialog.Builder(c);
//                builder.setMessage(msg + " " + Double.toString(amount) + "/-")
////                       .setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
////                           public void onClick(DialogInterface dialog, int id) {
////                               // FIRE ZE MISSILES!
////                           }
////                       })
//                       .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                           public void onClick(DialogInterface dialog, int id) {
//                               Utils.stopAudio();
//                               Utils.stopVibrate();
//                               nm.cancelAll();
//                           }
//                       });
//                builder.create();
//                builder.setCancelable(false);
//                builder.show();
                
            }
		};
		registerReceiver(Utils.broadcastReceiver, new IntentFilter("com.sakin.sohojshoncoi"));
		Utils.alarmManagerm = (AlarmManager)(getSystemService( Context.ALARM_SERVICE ));
	}
	
	private void showExitDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setMessage("আপনি কি সত্যিই বের হতে চান?")
	           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   finish();
	               }
	           })
	           .setNegativeButton("No", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	               }
	           });
	    builder.create();
	    builder.setCancelable(false);
	    AlertDialog alert = builder.show();
	    TextView msgView = (TextView) alert.findViewById(android.R.id.message);
	    msgView.setTypeface(Utils.banglaTypeFace);
	}
//	private NotificationManager createNotificationAndNotify(Context c, String msg, double amount, int rep) {
//		NotificationCompat.Builder mBuilder =
//		        new NotificationCompat.Builder(this)
//		        .setSmallIcon(R.drawable.ic_action_alarms)
//		        .setContentTitle("Sohojsoncoi Reminder")
//		        .setContentText(msg + " " + Double.toString(amount) + "/-")
//		        .setAutoCancel(true);
//		
//		// Creates an explicit intent for an Activity in your app
//		Intent myIntent = new Intent(c, Main.class);
//		myIntent.putExtra(Utils.COME_FROM_ALARM, true);
//		myIntent.putExtra(Utils.ALARM_MSG, msg);
//		myIntent.putExtra(Utils.ALARM_AMOUNT, amount);
//		myIntent.putExtra(Utils.ALARM_REPEATED, rep);
//	    PendingIntent resultPendingIntent = PendingIntent.getActivity(
//	      c, 
//	      0, 
//	      myIntent, 
//	      0);
//	    
//		mBuilder.setContentIntent(resultPendingIntent);
//		NotificationManager mNotificationManager =
//		    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//		// mId allows you to update the notification later on.
//		mNotificationManager.notify(1, mBuilder.build());
//		return mNotificationManager;
//	}
	
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
		int i = 0;
		switch (position) {
			case 0:
				fragment = new DailyHisab();
				i = 11;
				break;
//			case 1:
//				fragment = new BazarDor();
//				break;
			case 1:
				fragment = new Sofol();
				break;
			case 2:
				fragment = new SoncoiBriddhi();
				break;
			case 3:
				fragment = new UporiAe();
				break;
			case 4:
				fragment = new JiggasaFragment();
				break;
			case 5:
				fragment = new SettingsFragment();
				break;
			default:
				fragment = new DailyHisab();
				break;
		}
        FragmentManager fragmentManager = getSupportFragmentManager();
        String tag = "dailyhisab";
        if(i == 11) {
        	tag = "HOME_PAGE";
        }
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, tag).commit();
        
        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
//        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
        ITEM = position;
        Utils.SELECTED_ITEM = position;
	}

	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	    	Utils.print("position clicked" + position);
	    	selectItem(position);
	    }
	}
}
