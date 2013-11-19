package com.sakin.sohojshoncoi.daylihisab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sakin.sohojshoncoi.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DayliHisabMain extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.daylihisabmain);
		
		String[] drawerMenuList = getResources().getStringArray(R.array.drawermenulist);
		DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ListView mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, drawerMenuList));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
	}
	
	@Override
	protected void onDestroy() {
	    super.onDestroy();
	}
}
