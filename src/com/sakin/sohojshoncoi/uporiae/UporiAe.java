package com.sakin.sohojshoncoi.uporiae;


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
import com.sakin.sohojshoncoi.sofol.SofolVideosFragment;

public class UporiAe extends Fragment {
	ViewPager uporiaePager;
	TabsAdapter mTabsAdapter;
	String[] playlist_url;
	String[] tab_title;
	private NetworkReceiver receiver = null;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.uporiae, container, false);

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
		receiver = new NetworkReceiver();
        getActivity().registerReceiver(receiver, filter);
        
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        Utils.sPref = sharedPrefs.getString("listPref", "Any");

        receiver.updateConnectedFlags(getActivity());
	}
	
	private void loadTabs(View view){
		final ActionBar bar = getActivity().getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS );
		bar.removeAllTabs();
        uporiaePager = (ViewPager) view.findViewById(R.id.uporiae_pager);

        mTabsAdapter = new TabsAdapter(getActivity(), uporiaePager);
        playlist_url = getResources().getStringArray(R.array.uporiae_tab_url);
        tab_title = getResources().getStringArray(R.array.uporiae_tab_title);
        int totalTabs = tab_title.length;
        uporiaePager.setOffscreenPageLimit(totalTabs);
        
        Bundle args = null;
        for(int i=0; i<totalTabs; i++){
        	args = new Bundle();
        	args.putString(Utils.TAB_URL_ID, playlist_url[i]);
    		args.putInt(Utils.TAB_ID, i + totalTabs);
    		mTabsAdapter.addTab(bar.newTab().setText(tab_title[i]), SofolVideosFragment.class, args);
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
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (receiver != null) {
            getActivity().unregisterReceiver(receiver);
        }
	}

}