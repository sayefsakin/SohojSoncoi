package com.sakin.sohojshoncoi.sofol;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.custom.CollectionPagerAdapter;

public class Sofol extends Fragment {
	CollectionPagerAdapter collectionPagerAdapter;
	ViewPager sofolPager;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sofol, container, false);

		collectionPagerAdapter = new CollectionPagerAdapter(getFragmentManager());
        sofolPager = (ViewPager) view.findViewById(R.id.pager);
        sofolPager.setAdapter(collectionPagerAdapter);

	    return view;
	}
}

