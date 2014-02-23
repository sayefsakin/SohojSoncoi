package com.sakin.sohojshoncoi.settings;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Help extends Fragment{
View view;
TextView tx;
	
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, 
		Bundle savedInstanceState) {
	view = inflater.inflate(R.layout.help, container, false);
	
	//tx = (TextView) view.findViewById(R.id.help_text);
	setText(R.id.help_text,"ডান থেকে বামে স্ক্রল করুন");
	
	
    return view;
}

private void setText(int id, String string) {
	TextView tv = (TextView) view.findViewById(id);
	tv.setTextColor(Color.WHITE);
	tv.setTextSize(20);
	tv.setTypeface(Utils.banglaTypeFace);
	tv.setText(string);
	
}
}