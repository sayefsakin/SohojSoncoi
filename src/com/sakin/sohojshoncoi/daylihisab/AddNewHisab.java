package com.sakin.sohojshoncoi.daylihisab;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.custom.DatePickerFragment;

import android.R.color;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddNewHisab extends Fragment 
				implements CategoryFragment.OnCategorySelectedListener	{

	private EditText mulloEditText, descriptionEditText;
	private Button categoryButton, dateButton, pictureButton;
	View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.addnewhisab, container, false);
		
		setText(R.id.mulloLabel, "মূল্যঃ");
		setText(R.id.categoryLabel, "ক্যাটাগরিঃ");
		setText(R.id.dateLabel, "তারিখঃ");
		setText(R.id.descriptionLabel, "বর্ণনাঃ");
		setText(R.id.pictureLabel, "ছবিঃ");
		
		categoryButton = (Button) view.findViewById(R.id.categoryButton);
		categoryButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Fragment categoryFragment = new CategoryFragment(AddNewHisab.this);
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.remove(AddNewHisab.this);
                ft.add(R.id.content_frame, categoryFragment);
                ft.addToBackStack(null);
                ft.commit();
			}
		});
		
		dateButton = (Button) view.findViewById(R.id.dateButton);
		dateButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDatePickerDialog(v);
			}
		});
		
	    return view;
	}

	public void setText(int id, String item) {
		TextView tv = (TextView) view.findViewById(id);
		tv.setTextColor(Color.WHITE);
		tv.setTypeface(Utils.banglaTypeFace);
		tv.setText(item);
	}
	
	public void showDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getFragmentManager(), "datePicker");
	}

	@Override
	public void onCategorySelected(String cat) {
		Utils.print("category selected" + cat);
//		categoryButton.setText("new cat");
		TextView tv = (TextView) view.findViewById(R.id.categoryLabel);
		tv.setTextColor(Color.WHITE);
		tv.setTypeface(Utils.banglaTypeFace);
		tv.setText("নাইরে");
	}
}
