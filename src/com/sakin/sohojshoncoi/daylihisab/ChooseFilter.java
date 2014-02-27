package com.sakin.sohojshoncoi.daylihisab;

import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.custom.ChooseDialogFragment;
import com.sakin.sohojshoncoi.custom.DatePickerFragment;
import com.sakin.sohojshoncoi.database.Category;
import com.sakin.sohojshoncoi.database.Reminder;

@SuppressLint("ValidFragment")
public class ChooseFilter extends Fragment
						implements CategoryFragment.OnCategorySelectedListener,
									DatePickerFragment.OnDateSelectedListener,
									ChooseDialogFragment.OnItemSelectedListener {
	OnFilterSelectedListener mCallback;

    // Container Activity must implement this interface
    public interface OnFilterSelectedListener {
        public void onFilterSelectedListener(String name, Calendar std, Calendar ed, boolean onOff);
    }
    
    private Button aeBaeButton, categoryButton, startDateButton, endDateButton, saveButton;
	private ToggleButton filterOnOffSwitch;
    
	View view;
	private boolean filterOnOff;//true on, false off
	private boolean aeOrBae;//true bae, false ae
	private Calendar startDate, endDate;
	private String categoryName;
	
	public ChooseFilter(Fragment caller) {
		this.mCallback = (OnFilterSelectedListener) caller;
		this.filterOnOff = false;
		this.aeOrBae = true;
		this.categoryName = "Both";
		this.startDate = Calendar.getInstance();
		this.endDate = Calendar.getInstance();
	}
	
	public ChooseFilter(Fragment caller, boolean filter, boolean aeOrBae, 
			Calendar startDate, Calendar endDate, String cat) {
		this.mCallback = (OnFilterSelectedListener) caller;
		this.filterOnOff = filter;
		this.aeOrBae = aeOrBae;
		this.startDate = startDate;
		this.endDate = endDate;
		this.categoryName = cat;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		if(view == null) {
			view = inflater.inflate(R.layout.choose_filter, container, false);
			
			setText(R.id.startDateLabel, "GB ZvwiL †_‡Kt");
			setText(R.id.endDateLabel, "GB ZvwiL ch©š—t");
			setText(R.id.categoryLabel, "K¨vUvMwit");

			filterOnOffSwitch = (ToggleButton) view.findViewById(R.id.onOffButton);
			filterOnOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					filterOnOff = isChecked;
					changeVisibility(isChecked);
				}
			});
			
			aeBaeButton = (Button) view.findViewById(R.id.aeBaeButton);
//			aeBaeButton.setTypeface(Utils.banglaTypeFace);
			aeBaeButton.setBackgroundResource(R.drawable.optionbtn);
			aeBaeButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					showRepeatedPickerDialog(v);
				}
			});
			
			categoryButton = (Button) view.findViewById(R.id.categoryButton);
			categoryButton.setBackgroundResource(R.drawable.optionbtn);
			if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
				categoryButton.setTypeface(Utils.banglaTypeFace);
			}
			categoryButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Fragment categoryFragment = new CategoryFragment(ChooseFilter.this, aeOrBae);
					FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
					ft.remove(ChooseFilter.this);
	                ft.add(R.id.content_frame, categoryFragment);
	                ft.addToBackStack(null);
	                ft.commit();
				}
			});
			
			startDateButton = (Button) view.findViewById(R.id.startDateButton);
			startDateButton.setBackgroundResource(R.drawable.optionbtn);
//			startDateButton.setTypeface(Utils.banglaTypeFace);
			startDateButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					showDatePickerDialog(v, startDate, true);
				}
			});
			
			endDateButton = (Button) view.findViewById(R.id.endDateButton);
			endDateButton.setBackgroundResource(R.drawable.optionbtn);
//			endDateButton.setTypeface(Utils.banglaTypeFace);
			endDateButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					showDatePickerDialog(v, endDate, false);
				}
			});
			
			saveButton = (Button) view.findViewById(R.id.saveButton);
			saveButton.setTypeface(Utils.banglaTypeFaceSutonny);
			saveButton.setBackgroundResource(R.drawable.save_button);
			saveButton.setTextColor(Color.WHITE);
			saveButton.setText("†`Lyb");
			saveButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					doSave();
				}
			});
			
			doReset();
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
		tv.setTypeface(Utils.banglaTypeFaceSutonny);
		tv.setText(item);
	}
	
	public void showDatePickerDialog(View v, Calendar date, boolean t) {//start date true, end date false
	    DialogFragment newFragment = new DatePickerFragment(ChooseFilter.this, date, t);
	    newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
	}
	
	public void showRepeatedPickerDialog(View v) {
		String[] list = {"Income", "Expense", "Both" };
	    DialogFragment newFragment = new ChooseDialogFragment(ChooseFilter.this, "", list);
	    newFragment.show(getActivity().getSupportFragmentManager(), "itemPicker");
	}
	
	private void changeVisibility(boolean visibility) {
		aeBaeButton.setEnabled(visibility);
		categoryButton.setEnabled(visibility);
		startDateButton.setEnabled(visibility);
		endDateButton.setEnabled(visibility);
		categoryButton.setEnabled(false);
		onItemSelected("Both");
	}
	
	private void doReset() {
		filterOnOffSwitch.setChecked(this.filterOnOff);
		changeVisibility(this.filterOnOff);
		if(categoryName.equals("Both")) {
			categoryButton.setEnabled(false);
			onItemSelected("Both");
		} else {
			if(aeOrBae) {
				aeBaeButton.setText("Expense");
				onItemSelected("Expense");
			} else {
				aeBaeButton.setText("Income");
				onItemSelected("Income");
			}
			categoryButton.setText("পছন্দ করুন");
		}
		onDateSelected(startDate, true);
		onDateSelected(endDate, false);
		
	}
	
	private void doSave() {
		if(endDate.before(startDate)) {
			Utils.showToast(getActivity(), "ZvwiLwU Bbf¨vwjW");
			return;
		}
		//need to handle date inclusion problem
		
		Utils.print("start date: " + startDate.toString());
		Utils.print("end date: " + endDate.toString());
		
		mCallback.onFilterSelectedListener(categoryName, startDate, endDate, this.filterOnOff);
		FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
		ft.remove(ChooseFilter.this);
        ft.commit();
        getActivity().getSupportFragmentManager().popBackStack();
	}
	
	@Override
	public void onCategorySelected(String cat) {
		Utils.print("category selected" + cat);
		categoryButton.setText(cat);
		categoryName = cat;
	}

	@Override
	public void onDateSelected(Calendar date, boolean se) {
		String dt = Integer.toString(date.get(Calendar.DAY_OF_MONTH)) + "-" + 
				Integer.toString(date.get(Calendar.MONTH)+1) + "-" +
				Integer.toString(date.get(Calendar.YEAR));
		Utils.print(dt);
		if(se){
			date.set(Calendar.HOUR_OF_DAY, 0);
			date.set(Calendar.MINUTE, 0);
			date.set(Calendar.SECOND, 0);
			
			this.startDate = date;
			startDateButton.setText(dt);
		} else {
			date.set(Calendar.HOUR_OF_DAY, 
					date.getActualMaximum(Calendar.HOUR_OF_DAY));
			date.set(Calendar.MINUTE, 
					date.getActualMaximum(Calendar.MINUTE));
			date.set(Calendar.SECOND, 
					date.getActualMaximum(Calendar.SECOND));
			this.endDate = date;
			endDateButton.setText(dt);
		}
	}
	
	@Override
	public void onItemSelected(String item) {
//		aeBaeButton.setTypeface(Utils.banglaTypeFace);
		if(item.equals("Income")) {
			aeBaeButton.setText("Income");
			categoryButton.setEnabled(true);
			onCategorySelected("All Income");
			this.aeOrBae = false;
		} else if (item.equals("Expense")) {
			aeBaeButton.setText("Expense");
			categoryButton.setEnabled(true);
			onCategorySelected("All Expense");
			this.aeOrBae = true;
		} else {
			aeBaeButton.setText("Both");
			categoryButton.setEnabled(false);
			onCategorySelected("Both");
			this.aeOrBae = false;
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
