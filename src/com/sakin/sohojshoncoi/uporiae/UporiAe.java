package com.sakin.sohojshoncoi.uporiae;


import android.app.ActionBar;
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
		Utils.setActionBarTitle(getActivity(), "উপরি আয়");
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
    		ActionBar.Tab curTab = CreateNewTab(bar, i);
    		mTabsAdapter.addTab(curTab, SofolVideosFragment.class, args);
        }
        
	}
	
	private ActionBar.Tab CreateNewTab(ActionBar bar, int ind) {
		LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layoutView = (LinearLayout)inflater.inflate(R.layout.tab_title_view, null);
		TextView title = (TextView)layoutView.findViewById(R.id.tab_title_text);
		title.setTypeface(Utils.banglaTypeFace);
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