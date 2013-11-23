package com.sakin.sohojshoncoi.daylihisab;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AddNewHisab extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addnewhisab);
		
		TextView tv = (TextView) findViewById(R.id.addNewText);
		tv.setText("Add New Hisab Page");
		Utils.setActionBarTitle(this, "নতুন হিসাব");
	}
    
    @Override
	protected void onDestroy() {
	    super.onDestroy();
	}
}
