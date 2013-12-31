package com.sakin.sohojshoncoi.settings;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingsFragment extends ListFragment {

	View rootView = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	     // The last two arguments ensure LayoutParams are inflated properly.
		if(rootView==null){
			rootView = inflater.inflate(R.layout.settings, container, false);
		} else{
			ViewGroup parent = (ViewGroup) rootView.getParent();
			parent.removeView(rootView);
		}
		Utils.setActionBarTitle(getActivity(), "সেটিংস");
		return rootView;
	}
}
