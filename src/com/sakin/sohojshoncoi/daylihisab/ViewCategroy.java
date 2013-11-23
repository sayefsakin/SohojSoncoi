package com.sakin.sohojshoncoi.daylihisab;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewCategroy extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_category);
		
		TextView tv = (TextView) findViewById(R.id.categoryText);
		tv.setText("Add New Hisab Page");
		Utils.setActionBarTitle(this, "ক্যাটেগরি");
	}
    
    @Override
	protected void onDestroy() {
	    super.onDestroy();
	}
}
