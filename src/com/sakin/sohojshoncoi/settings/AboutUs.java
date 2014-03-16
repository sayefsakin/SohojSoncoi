package com.sakin.sohojshoncoi.settings;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

public class AboutUs extends Fragment {
	
	View rootView = null;
	private WebView webView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.about_us, container, false);
		
		TextView tv = (TextView) rootView.findViewById(R.id.about_text);
		tv.setText(""
				+ "Sayef Azad Sakin" + "\n" + "sayefsakin@gmail.com" + "\n\n"
				+ "Amit Kumar Das" + "\n" + "amit.csedu@gmail.com" + "\n\n"
				+ "Tamal Adhikary" + "\n" + "tamal.csedu@gmail.com" + "\n\n"
				+ "Nazmus Sakib Miazi" + "\n" + "sakibnm@gmail.com"
				+ "");
		
		Utils.setActionBarTitle(getActivity(), "mnR mÂq");
		
		return rootView;
	}
}
