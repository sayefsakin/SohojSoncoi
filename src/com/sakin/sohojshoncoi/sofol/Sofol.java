package com.sakin.sohojshoncoi.sofol;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.custom.TabsAdapter;
import com.sakin.sohojshoncoi.custom.NetworkReceiver;

public class Sofol extends Fragment {
	ViewPager sofolPager;
	TabsAdapter mTabsAdapter;
	String[] playlist_url;
	String[] tab_title;
	private NetworkReceiver receiver = null;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sofol, container, false);

		init();
		if (Utils.refreshDisplay) {
			//check for network connection
			if (((Utils.sPref.equals(Utils.ANY)) && (Utils.wifiConnected || Utils.mobileConnected))
	                || ((Utils.sPref.equals(Utils.WIFI)) && (Utils.wifiConnected))) {
				loadTabs(view);
			} else {
				Utils.print("No network connectivity");
				Toast.makeText(getActivity(), R.string.lost_connection, Toast.LENGTH_SHORT).show();
			}
        }
		Utils.setActionBarTitle(getActivity(), "mdj‡`i mvdj¨Mv_v");
	    return view;
	}
	
	private void init(){
		IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		receiver = new NetworkReceiver();
        getActivity().registerReceiver(receiver, filter);
        
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        Utils.sPref = sharedPrefs.getString("listPref", "Any");

        receiver.updateConnectedFlags(getActivity());
	}
	
	private void loadTabs(View view){
		final ActionBar bar = ((ActionBarActivity) getActivity()).getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS );
		bar.removeAllTabs();
        sofolPager = (ViewPager) view.findViewById(R.id.pager);

        mTabsAdapter = new TabsAdapter((ActionBarActivity) getActivity(), sofolPager);
        playlist_url = getResources().getStringArray(R.array.sofol_tab_url);
        tab_title = getResources().getStringArray(R.array.support_sofol_tab_title);
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
        	tab_title = getResources().getStringArray(R.array.sofol_tab_title);
        }
        int totalTabs = tab_title.length;
        sofolPager.setOffscreenPageLimit(1);
        
        Bundle args = null;
        for(int i=0; i<totalTabs; i++){
        	args = new Bundle();
        	args.putString(Utils.TAB_URL_ID, playlist_url[i]);
    		args.putInt(Utils.TAB_ID, i);
    		ActionBar.Tab curTab = CreateNewTab(bar, i);
    		mTabsAdapter.addTab(curTab, SofolVideosFragment.class, args);
        }

//        Bundle args = new Bundle();
//		args.putString(Utils.TAB_URL_ID, playlist_url[0]);
//		args.putInt(Utils.TAB_ID, 0);
//		mTabsAdapter.addTab(bar.newTab().setText(tab_title[0]), SofolVideosFragment.class, args);
//		
//		
//		Bundle args1 = new Bundle();
//		args1.putString(Utils.TAB_URL_ID, playlist_url[1]);
//		args1.putInt(Utils.TAB_ID, 1);
//		mTabsAdapter.addTab(bar.newTab().setText(tab_title[1]), SofolVideosFragment.class, args1);
//		
//		Bundle args2 = new Bundle();
//		args2.putString(Utils.TAB_URL_ID, playlist_url[2]);
//		args2.putInt(Utils.TAB_ID, 2);
//		mTabsAdapter.addTab(bar.newTab().setText(tab_title[2]), SofolVideosFragment.class, args2);
	}
	
	private ActionBar.Tab CreateNewTab(ActionBar bar, int ind) {
		LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layoutView = (LinearLayout)inflater.inflate(R.layout.tab_title_view, null);
		TextView title = (TextView)layoutView.findViewById(R.id.tab_title_text);
		if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
			title.setTypeface(Utils.banglaTypeFace);
		} else {
			title.setTypeface(Utils.banglaTypeFaceSutonny);
		}
		title.setText(tab_title[ind]);
		return bar.newTab().setCustomView(layoutView);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (receiver != null) {
            getActivity().unregisterReceiver(receiver);
        }
	}

}

