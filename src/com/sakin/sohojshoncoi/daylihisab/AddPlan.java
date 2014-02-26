package com.sakin.sohojshoncoi.daylihisab;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AddPlan extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_plan);
		
		TextView tv = (TextView) findViewById(R.id.planText);
		tv.setText("Add New Hisab Page");
		Utils.setActionBarTitle(this, "K¨vUvMwi");
	}
    
    @Override
	protected void onDestroy() {
	    super.onDestroy();
	}
}
