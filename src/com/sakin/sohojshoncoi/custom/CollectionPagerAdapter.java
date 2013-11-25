package com.sakin.sohojshoncoi.custom;

import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.sofol.SofolVideosFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.LruCache;
import android.view.ViewGroup;

public class CollectionPagerAdapter extends FragmentStatePagerAdapter {
	
	public CollectionPagerAdapter(FragmentManager fm) {
	     super(fm);
	 }
	 
	 @Override
	 public Fragment getItem(int i) {
		 Fragment fragment = new SofolVideosFragment();
//	     Bundle args = new Bundle();
//	     args.putInt(VideosFragment.ARG_OBJECT, i + 1);
//	     fragment.setArguments(args);
	     return fragment;
	 }

	 @Override
	 public int getCount() {
	     return Utils.MAX_Video_Fragment;
	 }

	 @Override
	 public CharSequence getPageTitle(int position) {
	     return "Page No " + (position + 1);
	 }
	 
	 @Override
	 public void destroyItem(ViewGroup container, int position, Object object) {
	     super.destroyItem(container, position, object);
	 }
}