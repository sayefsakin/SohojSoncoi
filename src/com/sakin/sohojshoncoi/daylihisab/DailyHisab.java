package com.sakin.sohojshoncoi.daylihisab;

import com.sakin.sohojshoncoi.R;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DailyHisab extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dailyhisab, container, false);
		setText(view, "DailyHisab");
	    return view;
	}

	public void setText(View view, String item) {
		TextView tv = (TextView) view.findViewById(R.id.dailyhisabText);
		tv.setText(item);
	}

}
