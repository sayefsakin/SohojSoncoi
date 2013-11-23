package com.sakin.sohojshoncoi.sofol;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sakin.sohojshoncoi.R;

public class Sofol extends FragmentActivity {
	DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
	ViewPager mViewPager;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sofol, container, false);
		
		// ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mDemoCollectionPagerAdapter =
                new DemoCollectionPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
		
		
	    return view;
	}
}

//Since this is an object collection, use a FragmentStatePagerAdapter,
//and NOT a FragmentPagerAdapter.
class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
 public DemoCollectionPagerAdapter(FragmentManager fm) {
     super(fm);
 }

 @Override
 public Fragment getItem(int i) {
	 Fragment fragment = new DemoObjectFragment();
     Bundle args = new Bundle();
     // Our object is just an integer :-P
     args.putInt(DemoObjectFragment.ARG_OBJECT, i + 1);
     fragment.setArguments(args);
     return fragment;
 }

 @Override
 public int getCount() {
     return 100;
 }

 @Override
 public CharSequence getPageTitle(int position) {
     return "OBJECT " + (position + 1);
 }
}
