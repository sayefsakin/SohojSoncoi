package com.sakin.sohojshoncoi.daylihisab;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;

public class ViewReminder extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reminder);
		
		TextView tv = (TextView) findViewById(R.id.reminderText);
		tv.setText("Add New Hisab Page");
		Utils.setActionBarTitle(this, "ক্যাটেগরি");
	}
    
    @Override
	protected void onDestroy() {
	    super.onDestroy();
	}
}
