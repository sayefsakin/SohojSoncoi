package com.sakin.sohojshoncoi.custom;

import com.sakin.sohojshoncoi.sofol.VideosFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class CollectionPagerAdapter extends FragmentStatePagerAdapter {
	 
	public CollectionPagerAdapter(FragmentManager fm) {
	     super(fm);
	 }
	 
	 @Override
	 public Fragment getItem(int i) {
		 Fragment fragment = new VideosFragment();
	     Bundle args = new Bundle();
	     // Our object is just an integer :-P
	     args.putInt(VideosFragment.ARG_OBJECT, i + 1);
	     fragment.setArguments(args);
	     return fragment;
	 }

	 @Override
	 public int getCount() {
	     return 100;
	 }

	 @Override
	 public CharSequence getPageTitle(int position) {
	     return "Page No " + (position + 1);
	 }
}