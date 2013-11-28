package com.sakin.sohojshoncoi.sofol;

import android.app.ActionBar;
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
		
	    return view;
	}
	
	private void init(){
		IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		NetworkReceiver receiver = new NetworkReceiver();
        getActivity().registerReceiver(receiver, filter);
        
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        Utils.sPref = sharedPrefs.getString("listPref", "Any");

        receiver.updateConnectedFlags(getActivity());
	}
	
	private void loadTabs(View view){
		final ActionBar bar = getActivity().getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS );
        sofolPager = (ViewPager) view.findViewById(R.id.pager);

        mTabsAdapter = new TabsAdapter(getActivity(), sofolPager);
        playlist_url = getResources().getStringArray(R.array.sofol_tab_url);
        tab_title = getResources().getStringArray(R.array.sofol_tab_title);
        
        Bundle args = new Bundle();
		args.putString(Utils.TAB_URL_ID, playlist_url[0]);
		args.putInt(Utils.TAB_ID, 0);
		mTabsAdapter.addTab(bar.newTab().setText("List Fragment 1"), SofolVideosFragment.class, args);
		
		
		Bundle args1 = new Bundle();
		args1.putString(Utils.TAB_URL_ID, playlist_url[1]);
		args1.putInt(Utils.TAB_ID, 1);
		mTabsAdapter.addTab(bar.newTab().setText("List Fragment 2"), SofolVideosFragment.class, args1);
		
		Bundle args2 = new Bundle();
//		args2.putStringArray("array_list", getResources().getStringArray(R.array.list2));
		args2.putString(Utils.TAB_URL_ID, playlist_url[2]);
		args2.putInt(Utils.TAB_ID, 2);
		mTabsAdapter.addTab(bar.newTab().setText("List Fragment 3"), SofolVideosFragment.class, args2);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		if (receiver != null) {
            getActivity().unregisterReceiver(receiver);
        }
	}
}

