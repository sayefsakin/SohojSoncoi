package com.sakin.sohojshoncoi.custom;

import java.util.Calendar;
import com.sakin.sohojshoncoi.Utils;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.DatePicker;

@SuppressLint("ValidFragment")
public class DatePickerFragment extends DialogFragment
								implements DatePickerDialog.OnDateSetListener {

	OnDateSelectedListener mCallback;
	private Calendar date;
    // Container Activity must implement this interface
    public interface OnDateSelectedListener {
        public void onDateSelected(Calendar date);
    }
    
    public DatePickerFragment(Fragment caller, Calendar date){
    	mCallback = (OnDateSelectedListener) caller;
    	this.date = date;
    }
    
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		
		int year = date.get(Calendar.YEAR);
		int month = date.get(Calendar.MONTH);
		int day = date.get(Calendar.DAY_OF_MONTH);
		
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}
	
	public void onDateSet(DatePicker view, int year, int month, int day) {
		Utils.print("Date set");
		Calendar c = Calendar.getInstance();
		c.set(year, month, day);
		mCallback.onDateSelected(c);
	}
}
