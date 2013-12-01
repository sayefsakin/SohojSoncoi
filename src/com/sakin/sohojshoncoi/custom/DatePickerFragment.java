package com.sakin.sohojshoncoi.custom;

import java.util.Calendar;

import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.daylihisab.CategoryFragment.OnCategorySelectedListener;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment
								implements DatePickerDialog.OnDateSetListener {

	OnDateSelectedListener mCallback;

    // Container Activity must implement this interface
    public interface OnDateSelectedListener {
        public void onDateSelected(int year, int month, int day);
    }
    
    public DatePickerFragment(Fragment caller){
    	mCallback = (OnDateSelectedListener) caller;
    }
    
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}
	
	public void onDateSet(DatePicker view, int year, int month, int day) {
		Utils.print("Date set");
		mCallback.onDateSelected(year, month, day);
	}
}
