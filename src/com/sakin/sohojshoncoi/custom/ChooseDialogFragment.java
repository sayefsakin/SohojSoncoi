package com.sakin.sohojshoncoi.custom;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

@SuppressLint("ValidFragment")
public class ChooseDialogFragment extends DialogFragment
								implements DialogInterface.OnClickListener {
	
	OnItemSelectedListener mCallback;
    // Container Activity must implement this interface
    public interface OnItemSelectedListener {
        public void onItemSelected(String item);
    }
	String title;
	final String[] list;
	
	public ChooseDialogFragment(Fragment caller, String title, String[] list){
		this.mCallback = (OnItemSelectedListener) caller;
		this.title = title;
		this.list = list;
	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    builder.setTitle(title);
	    builder.setItems(list, this);
	    return builder.create();
	}
	@Override
	public void onClick(DialogInterface dialog, int id) {
		mCallback.onItemSelected(list[id]);
	}
}
