package com.sakin.sohojshoncoi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class AlarmPopup extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle extras = getIntent().getExtras();
    	String msg = extras.getString(Utils.ALARM_MSG);
    	double amount = extras.getDouble(Utils.ALARM_AMOUNT);
    	int rep = extras.getInt(Utils.ALARM_REPEATED);
    	final int alarmID = extras.getInt(Utils.COME_FROM_ALARM);
    	Utils.runAudio();
    	Utils.startVibrate(this);
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg + " " + Double.toString(amount) + "/-")
               .setPositiveButton("Go to Reminder", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   Utils.stopAudio();
                       Utils.stopVibrate();
                	   Intent mainIntent = new Intent(AlarmPopup.this, Main.class);
                	   mainIntent.putExtra(Utils.COME_FROM_ALARM, alarmID);
                	   Utils.SELECTED_ITEM = 0;
                	   startActivity(mainIntent);
                   }
               })
               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       Utils.stopAudio();
                       Utils.stopVibrate();
                       finish();
                   }
               });
        builder.create();
        builder.setCancelable(false);
        builder.show();
	}
}
