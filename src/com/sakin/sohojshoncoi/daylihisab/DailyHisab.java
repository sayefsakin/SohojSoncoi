package com.sakin.sohojshoncoi.daylihisab;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.YouTubeFullScreen;
import com.sakin.sohojshoncoi.custom.DatePickerFragment;
import com.sakin.sohojshoncoi.custom.VideoElement;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DailyHisab extends Fragment {
	
	RelativeLayout mainLayout;
	int currentViewID;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dailyhisab, container, false);
		setText(view, "ব্যালেন্সঃ ৩০০");
		Button addNewButton = (Button) view.findViewById(R.id.addNewButton);
		addNewButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
//				Intent addNewHisabIntent = new Intent(getActivity(), AddNewHisab.class);
//				startActivity(addNewHisabIntent);
				Fragment addNewHisab = new AddNewHisab();
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.remove(DailyHisab.this);
                ft.add(R.id.content_frame, addNewHisab, Utils.ADDNEWHISABTAG);
                ft.addToBackStack("dailyhisab");
                ft.commit();
			}
		});
		
		Button reminderButon = (Button) view.findViewById(R.id.reminderButon);
		reminderButon.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Fragment reminder = new AddReminder();
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.remove(DailyHisab.this);
                ft.add(R.id.content_frame, reminder);
                ft.addToBackStack("dailyhisab");
                ft.commit();
			}
		});
		
		Button agerhisabButton = (Button) view.findViewById(R.id.agerHisabButton);
		agerhisabButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Fragment hisabList = new HisabList();
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.remove(DailyHisab.this);
                ft.add(R.id.content_frame, hisabList);
                ft.addToBackStack("dailyhisab");
                ft.commit();
			}
		});
		
		// creating bokea bil table
		mainLayout = (RelativeLayout) view.findViewById(R.id.dailyhisab_back_layout);
		
		currentViewID = 1;
		addTitleView(R.id.agerHisabButton);
		for(int i=1; i<5;i++){
			currentViewID++;
			addRowsToBill(i);
		}

		return view;
	}
	
	protected void addTitleView(int buttonID){
		RelativeLayout.LayoutParams bokeaBillTitleParams =
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 
												RelativeLayout.LayoutParams.WRAP_CONTENT);
		bokeaBillTitleParams.addRule(RelativeLayout.BELOW, buttonID);
		bokeaBillTitleParams.setMargins(25, 20, 25, 0);
		RelativeLayout titleView = new RelativeLayout(getActivity());
		titleView.setBackgroundColor(Color.DKGRAY);
		titleView.setId(currentViewID);
		mainLayout.addView(titleView, bokeaBillTitleParams);
		
		TextView tv = new TextView(getActivity());
		tv.setText("বকেয়া বিল");
		tv.setTextColor(Color.WHITE);
		tv.setTypeface(Utils.banglaTypeFace);
		tv.setTextSize(20);
		
		RelativeLayout.LayoutParams bokeaBillTitleTextParams =
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
												RelativeLayout.LayoutParams.WRAP_CONTENT);
		bokeaBillTitleTextParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
		bokeaBillTitleTextParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		titleView.addView(tv, bokeaBillTitleTextParams);
	}
	
	protected int addRowsToBill(int previousViewID){
		RelativeLayout.LayoutParams bokeaBillTitleParams =
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 
												RelativeLayout.LayoutParams.WRAP_CONTENT);
		bokeaBillTitleParams.addRule(RelativeLayout.BELOW, previousViewID);
		bokeaBillTitleParams.setMargins(25, 0, 25, 0);
		RelativeLayout titleView = new RelativeLayout(getActivity());
		titleView.setBackgroundColor(Color.LTGRAY);
		titleView.setId(currentViewID);
		mainLayout.addView(titleView, bokeaBillTitleParams);
		
		TextView tv = new TextView(getActivity());
		tv.setText("আগের বিলসমুহ");
		tv.setTextColor(Color.BLACK);
		tv.setTypeface(Utils.banglaTypeFace);
		tv.setTextSize(16);

		RelativeLayout.LayoutParams bokeaBillTitleTextParams =
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
												RelativeLayout.LayoutParams.WRAP_CONTENT);
		bokeaBillTitleTextParams.addRule(RelativeLayout.ALIGN_LEFT, RelativeLayout.TRUE);
		bokeaBillTitleTextParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		bokeaBillTitleTextParams.setMargins(10, 5, 100, 0);
		titleView.addView(tv, bokeaBillTitleTextParams);
		
		TextView amountText = new TextView(getActivity());
		amountText.setText("১০০");
		amountText.setTextColor(Color.BLACK);
		amountText.setTypeface(Utils.banglaTypeFace);
		amountText.setTextSize(16);
		
		RelativeLayout.LayoutParams bokeaBillAmountTextParams =
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
												RelativeLayout.LayoutParams.WRAP_CONTENT);
		bokeaBillAmountTextParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		bokeaBillAmountTextParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		titleView.addView(amountText, bokeaBillAmountTextParams);
		return 0;
	}

	public void setText(View view, String item) {
		TextView tv = (TextView) view.findViewById(R.id.dailyhisabText);
		tv.setTypeface(Utils.banglaTypeFace);
		tv.setText(item);
	}

}
