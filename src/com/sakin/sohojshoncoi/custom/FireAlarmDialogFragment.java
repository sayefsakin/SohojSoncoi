package com.sakin.sohojshoncoi.custom;

import com.sakin.sohojshoncoi.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class FireAlarmDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        String msg = savedInstanceState.getString(Utils.ALARM_MSG);
//    	double amount = savedInstanceState.getDouble(Utils.ALARM_AMOUNT);
//    	int rep = savedInstanceState.getInt(Utils.ALARM_REPEATED);
        builder.setMessage("hello honey bunny")
               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // FIRE ZE MISSILES!
                   }
               })
               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the dialog
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}