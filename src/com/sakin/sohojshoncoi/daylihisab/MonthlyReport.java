package com.sakin.sohojshoncoi.daylihisab;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.custom.TabsAdapter;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MonthlyReport extends Fragment {
	View view = null;
	ViewPager monthPager;
	TabsAdapter mTabsAdapter;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		if(view == null){
			view = inflater.inflate(R.layout.monthly_report, container, false);
			loadTabs(view);
		} else {
			Utils.print(" view already created ");
			ViewGroup parent = (ViewGroup) view.getParent();
			parent.removeView(view);
		}
	    return view;
	}
	
	@SuppressLint("SimpleDateFormat")
	private void loadTabs(View view){
		Utils.print("load Tabs called");
		final ActionBar bar = getActivity().getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS );
		bar.removeAllTabs();
		monthPager = (ViewPager) view.findViewById(R.id.view_pager);

        mTabsAdapter = new TabsAdapter(getActivity(), monthPager);
//        monthPager.setOffscreenPageLimit(totalTabs);

        Bundle args = null;
        for(int i=-3; i<3; i++){
        	args = new Bundle();
    		args.putInt(Utils.TAB_ID, i);
    		Calendar cal = Calendar.getInstance();
    		cal.add(Calendar.MONTH, i);
    		args.putInt(Utils.MONTH_ID, cal.get(Calendar.MONTH));
    		args.putInt(Utils.YEAR_ID, cal.get(Calendar.YEAR));
    		String text = new SimpleDateFormat("MMM-yyyy").format(cal.getTime());
    		mTabsAdapter.addTab(bar.newTab().setText(text), MonthlyReportFragment.class, args);
        }
        monthPager.setCurrentItem(3);
	}
	
	@Override
	public void onDestroyView() {
		getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		super.onDestroyView();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}