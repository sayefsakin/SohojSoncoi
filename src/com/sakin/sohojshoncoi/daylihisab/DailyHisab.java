package com.sakin.sohojshoncoi.daylihisab;

import com.google.android.youtube.player.YouTubePlayerFragment;
import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.YouTubeFullScreen;
import com.sakin.sohojshoncoi.custom.VideoElement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class DailyHisab extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dailyhisab, container, false);
		setText(view, "ব্যালেন্সঃ ৩০০");
		Button addNewButton = (Button) view.findViewById(R.id.addNewButton);
		addNewButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent addNewHisabIntent = new Intent(getActivity(), AddNewHisab.class);
				startActivity(addNewHisabIntent);
			}
		});
		
		Button reminderButon = (Button) view.findViewById(R.id.reminderButon);
		reminderButon.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Log.i("Testing","Reminder Button tapped");
				Fragment reminder = new ViewReminder();
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
				Intent youTubeFullScreen = new Intent(getActivity(), YouTubeFullScreen.class);
				VideoElement item = new VideoElement("www.youtube.com/watch?v=7JXWXG3hsRg",
						"","hello","Honey Bunny", 2);
				youTubeFullScreen.putExtra(Utils.VIDEO_ELEMENT_ID, item);
				startActivity(youTubeFullScreen);
			}
		});
	    return view;
	}

	public void setText(View view, String item) {
		TextView tv = (TextView) view.findViewById(R.id.dailyhisabText);
		tv.setTypeface(Utils.banglaTypeFace);
		tv.setText(item);
	}

}
