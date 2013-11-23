package com.sakin.sohojshoncoi.daylihisab;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewReport extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_report);
		
		TextView tv = (TextView) findViewById(R.id.reportText);
		tv.setText("Add New Hisab Page");
		Utils.setActionBarTitle(this, "ক্যাটেগরি");
	}
    
    @Override
	protected void onDestroy() {
	    super.onDestroy();
	}
}
