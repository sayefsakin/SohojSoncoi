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
public class ChooseReminderFilter extends Fragment
						implements	DatePickerFragment.OnDateSelectedListener,
									ChooseDialogFragment.OnItemSelectedListener {
	OnFilterSelectedListener mCallback;

    // Container Activity must implement this interface
    public interface OnFilterSelectedListener {
        public void onFilterSelectedListener(String repeated, String status, Calendar std, Calendar ed, boolean onOff);
    }
    
    private Button filterRepeatedButton,
    				filterStatusButton,
    				startDateButton,
    				endDateButton,
    				saveButton;
	private ToggleButton filterOnOffSwitch;
    
	View view;
	private boolean filterOnOff;//true on, false off
	private Calendar startDate, endDate;
	private String repeated, status;
	
	public ChooseReminderFilter(Fragment caller) {
		this.mCallback = (OnFilterSelectedListener) caller;
		this.filterOnOff = false;
		this.repeated = "ALL";
		this.status = "BOTH";
		this.startDate = Calendar.getInstance();
		this.endDate = Calendar.getInstance();
	}
	
	public ChooseReminderFilter(Fragment caller, boolean filter, String status, String repeated, 
			Calendar startDate, Calendar endDate) {
		this.mCallback = (OnFilterSelectedListener) caller;
		this.filterOnOff = filter;
		this.repeated = repeated;
		this.status = status;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		if(view == null) {
			view = inflater.inflate(R.layout.choose_reminder_filter, container, false);
			
			setText(R.id.startDateLabel, "GB ZvwiL †_‡Kt");
			setText(R.id.endDateLabel, "GB ZvwiL ch©š—t");
			setText(R.id.filterRepeatedLabel, "wiwc‡UWt");
			setText(R.id.filterStatusLabel, "÷¨vUvmt");

			filterOnOffSwitch = (ToggleButton) view.findViewById(R.id.onOffButton);
			filterOnOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					filterOnOff = isChecked;
					changeVisibility(isChecked);
				}
			});
			
			filterRepeatedButton = (Button) view.findViewById(R.id.filterRepeatedButton);
//			aeBaeButton.setTypeface(Utils.banglaTypeFace);
			filterRepeatedButton.setBackgroundResource(R.drawable.optionbtn);
			filterRepeatedButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					showRepeatedPickerDialog(v);
				}
			});
			
			filterStatusButton = (Button) view.findViewById(R.id.filterStatusButton);
//			aeBaeButton.setTypeface(Utils.banglaTypeFace);
			filterStatusButton.setBackgroundResource(R.drawable.optionbtn);
			filterStatusButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					showStatusPickerDialog(v);
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
	    DialogFragment newFragment = new DatePickerFragment(ChooseReminderFilter.this, date, t);
	    newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
	}
	
	public void showRepeatedPickerDialog(View v) {
		String[] list = {"MONTHLY", "DAILY", "NONE", "ALL" };
	    DialogFragment newFragment = new ChooseDialogFragment(ChooseReminderFilter.this, "", list);
	    newFragment.show(getActivity().getSupportFragmentManager(), "itemPicker");
	}
	public void showStatusPickerDialog(View v) {
		String[] list = {"PAID", "NON_PAID", "ALARM", "BOTH" };
	    DialogFragment newFragment = new ChooseDialogFragment(ChooseReminderFilter.this, "", list);
	    newFragment.show(getActivity().getSupportFragmentManager(), "itemPicker");
	}
	
	private void changeVisibility(boolean visibility) {
		filterRepeatedButton.setEnabled(visibility);
		filterStatusButton.setEnabled(visibility);
		startDateButton.setEnabled(visibility);
		endDateButton.setEnabled(visibility);
	}
	
	private void doReset() {
		filterOnOffSwitch.setChecked(this.filterOnOff);
		changeVisibility(this.filterOnOff);
		filterRepeatedButton.setText(repeated);
		filterStatusButton.setText(status);
		onDateSelected(startDate, true);
		onDateSelected(endDate, false);
		
	}
	
	private void doSave() {
		if(endDate.before(startDate)) {
			Utils.showToast(getActivity(), "ZvwiLwU Bbf¨vwjW");
			return;
		}
//		Utils.print("start date: " + startDate.toString());
//		Utils.print("end date: " + endDate.toString());
		
		mCallback.onFilterSelectedListener(repeated, status, startDate, endDate, this.filterOnOff);
		FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
		ft.remove(ChooseReminderFilter.this);
        ft.commit();
        getActivity().getSupportFragmentManager().popBackStack();
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
		Utils.print("Item selected: " + item);
		if(item.equals("MONTHLY") ||
				item.equals("DAILY") ||
				item.equals("NONE") ||
				item.equals("ALL")) {
			repeated = item;
			filterRepeatedButton.setText(item);
		} else {
			status = item;
			filterStatusButton.setText(item);
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
