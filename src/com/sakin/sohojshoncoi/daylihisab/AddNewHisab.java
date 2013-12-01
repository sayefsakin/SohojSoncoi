package com.sakin.sohojshoncoi.daylihisab;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.custom.DatePickerFragment;
import com.sakin.sohojshoncoi.custom.PhotoHandler;

import android.R.color;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Camera;
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
import android.widget.Toast;

public class AddNewHisab extends Fragment 
				implements CategoryFragment.OnCategorySelectedListener, DatePickerFragment.OnDateSelectedListener	{

	private EditText mulloEditText, descriptionEditText;
	private Button categoryButton, dateButton, pictureButton;
	View view = null;
	private Camera camera;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		if(view == null){
			view = inflater.inflate(R.layout.addnewhisab, container, false);
			Utils.print("addNewHisab created");
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
			
			pictureButton = (Button) view.findViewById(R.id.pictureButton);
			pictureButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Utils.print("picture button clicked");
					if(!getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
						Toast.makeText(getActivity(), "No camera found", Toast.LENGTH_SHORT).show();
					} else {
						Utils.print("camera found");
						camera = Camera.open();
//						camera.takePicture(null, null, new PhotoHandler(getActivity().getApplicationContext()));
					}
				}
			});
		} else {
			Utils.print(" view already created ");
			ViewGroup parent = (ViewGroup) view.getParent();
			parent.removeView(view);
		}
	    return view;
	}

	public void setText(int id, String item) {
		TextView tv = (TextView) view.findViewById(id);
		tv.setTextColor(Color.WHITE);
		tv.setTypeface(Utils.banglaTypeFace);
		tv.setText(item);
	}
	
	public void showDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment(AddNewHisab.this);
	    newFragment.show(getFragmentManager(), "datePicker");
	}

	@Override
	public void onCategorySelected(String cat) {
		Utils.print("category selected" + cat);
		categoryButton.setTypeface(Utils.banglaTypeFace);
		categoryButton.setText(cat);
	}

	@Override
	public void onDateSelected(int year, int month, int day) {
		dateButton.setTypeface(Utils.banglaTypeFace);
		String dt = Integer.toString(day) + "-" + Integer.toString(month) + "-" + Integer.toString(year);
		dateButton.setText(dt);
	}
	
	@Override
	public void onDestroy() {
		if (camera != null) {
		      camera.release();
		      camera = null;
		 }
		super.onDestroy();
	}
}
