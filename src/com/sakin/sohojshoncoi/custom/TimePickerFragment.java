package com.sakin.sohojshoncoi.custom;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import android.app.TimePickerDialog;

import com.sakin.sohojshoncoi.Utils;

@SuppressLint("ValidFragment")
public class TimePickerFragment extends DialogFragment
								implements TimePickerDialog.OnTimeSetListener {
	
	OnTimeSelectedListener mCallback;
	private Calendar time;
    // Container Activity must implement this interface
    public interface OnTimeSelectedListener {
        public void onTimeSelected(int hour, int minute);
    }
    
    public TimePickerFragment(Fragment caller, Calendar time){
    	mCallback = (OnTimeSelectedListener) caller;
    	this.time = time;
    }
    
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		
		int hour = time.get(Calendar.HOUR_OF_DAY);
		int minute = time.get(Calendar.MINUTE);
		
		// Create a new instance of DatePickerDialog and return it
		return new TimePickerDialog(getActivity(), this, hour, minute,
				  DateFormat.is24HourFormat(getActivity()));
	}
	
	public void onTimeSet(TimePicker view, int hour, int minute) {
		Utils.print("Time set");
		mCallback.onTimeSelected(hour, minute);
	}
}
