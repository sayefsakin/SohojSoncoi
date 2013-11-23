package com.sakin.sohojshoncoi.daylihisab;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;

public class ViewReminder extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.reminder, container, false);
		setText(view, "ব্যালেন্সঃ ৩০০");
	    return view;
	}

	public void setText(View view, String item) {
		TextView tv = (TextView) view.findViewById(R.id.reminderText);
		tv.setTypeface(Utils.banglaTypeFace);
		tv.setText(item);
	}
	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.reminder);
//		
//		TextView tv = (TextView) findViewById(R.id.reminderText);
//		tv.setText("Add New Hisab Page");
//		Utils.setActionBarTitle(this, "ক্যাটেগরি");
//	}
//    
//    @Override
//	protected void onDestroy() {
//	    super.onDestroy();
//	}
}
