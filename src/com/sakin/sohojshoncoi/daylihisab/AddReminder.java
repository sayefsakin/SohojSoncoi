package com.sakin.sohojshoncoi.daylihisab;

import java.io.File;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.AlarmClock;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.custom.TimePickerFragment;
import com.sakin.sohojshoncoi.custom.DatePickerFragment;
import com.sakin.sohojshoncoi.custom.ChooseDialogFragment;
import com.sakin.sohojshoncoi.database.Category;
import com.sakin.sohojshoncoi.database.Reminder;
import com.sakin.sohojshoncoi.database.SSDAO;
import com.sakin.sohojshoncoi.database.Transaction;

@SuppressLint({ "ValidFragment", "InlinedApi" })
public class AddReminder extends Fragment 
						implements TimePickerFragment.OnTimeSelectedListener,
									DatePickerFragment.OnDateSelectedListener,
									ChooseDialogFragment.OnItemSelectedListener {
	
	private EditText mulloEditText, descriptionEditText;
	private Button timeButton, dateButton, repeatButton, saveButton, resetButton;
	private Switch statusSwitch;
	private CheckBox alarmSwitch;
	View view = null;
	
	private Boolean status, alarm, isEdit;
	private double amount;
	private String description;
	private Calendar dateTime;
	private Reminder.Repeat repeated;
	private Reminder reminder;
	
	public AddReminder(){
		status = false;
		alarm = false;
		amount = 0.0;
		description = "";
		dateTime = Calendar.getInstance();
		repeated = Reminder.Repeat.NONE;
		this.isEdit = false;
	}
	
	public AddReminder(Boolean status, Boolean alarm, double amount,
						String desc, Calendar dateTime, Reminder.Repeat repeated){
		this.status = status;
		this.alarm = alarm;
		this.amount = amount;
		this.description = desc;
		this.dateTime = dateTime;
		this.repeated = repeated;
		this.isEdit = true;
	}
	
	public AddReminder(Reminder reminder) {
		
		this.reminder = reminder;
		Reminder.Status st = reminder.getStatus();
		if(st.toString().equals("PAID")) {
			this.status = true;
			this.alarm = false;
		} else if(st.toString().equals("NON_PAID")) {
			this.status = false;
			this.alarm = false;
		} else {
			this.status = false;
			this.alarm = true;
		}
		this.amount = reminder.getAmount();
		this.description = reminder.getDescription();
		this.dateTime = Utils.dateToCalendar(reminder.getDueDate());
		this.repeated = reminder.getRepeated();
		this.isEdit = true;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		if(view == null){
			view = inflater.inflate(R.layout.add_new_reminder, container, false);
			
			setText(R.id.mulloLabelReminder,"মূল্যঃ");
			setText(R.id.descriptionLabelReminder,"বর্ণনাঃ");
			setText(R.id.dateLabelReminder,"তারিখঃ");
			setText(R.id.timeLabel,"সময়ঃ");
			setText(R.id.alarmLabel,"এলার্মঃ");
			setText(R.id.repeatedLabel,"রিপিটেডঃ");
			
			mulloEditText = (EditText) view.findViewById(R.id.mulloEditTextReminder);
			descriptionEditText = (EditText) view.findViewById(R.id.descriptionEditTextReminder);
			
			statusSwitch = (Switch) view.findViewById(R.id.statusButton);
			statusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					status = isChecked;
				}
			});
			if(isEdit == true){
				statusSwitch.setVisibility(Switch.VISIBLE);
			} else {
				statusSwitch.setVisibility(Switch.INVISIBLE);
			}
			
			alarmSwitch = (CheckBox) view.findViewById(R.id.alarmSwitch);
			alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					alarm = isChecked;
				}
			});
			
			dateButton = (Button) view.findViewById(R.id.dateButtonReminder);
			dateButton.setTypeface(Utils.banglaTypeFace);
			dateButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					showDatePickerDialog(v);
				}
			});
			
			timeButton = (Button) view.findViewById(R.id.timeButton);
			timeButton.setTypeface(Utils.banglaTypeFace);
			timeButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					showTimePickerDialog(v);
				}
			});
			
			repeatButton = (Button) view.findViewById(R.id.repeatedButton);
			repeatButton.setTypeface(Utils.banglaTypeFace);
			repeatButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					showRepeatedPickerDialog(v);
				}
			});
			
			saveButton = (Button) view.findViewById(R.id.saveButtonReminder);
			saveButton.setBackgroundResource(R.drawable.save_button);
			saveButton.setTypeface(Utils.banglaTypeFace);
			saveButton.setText("সেভ");
			saveButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					doSave();
				}
			});
			
			resetButton = (Button) view.findViewById(R.id.resetButtonReminder);
			resetButton.setBackgroundResource(R.drawable.reset_button);
			resetButton.setTypeface(Utils.banglaTypeFace);
			resetButton.setText("রিসেট");
			resetButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					doReset();
				}
			});
			doReset();
		} else {
			Utils.print(" view already created ");
			ViewGroup parent = (ViewGroup) view.getParent();
			parent.removeView(view);
		}
		Utils.setActionBarTitle(getActivity(), "নতুন রিমাইন্ডার");
	    return view;
	}
	
	public void setText(int id, String item) {
		TextView tv = (TextView) view.findViewById(id);
		tv.setTextColor(Color.WHITE);
		tv.setTypeface(Utils.banglaTypeFace);
		tv.setText(item);
	}
	
	public void showDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment(AddReminder.this, this.dateTime);
	    newFragment.show(getFragmentManager(), "datePicker");
	}
	
	public void showTimePickerDialog(View v) {
	    DialogFragment newFragment = new TimePickerFragment(AddReminder.this, this.dateTime);
	    newFragment.show(getFragmentManager(), "timePicker");
	}
	
	public void showRepeatedPickerDialog(View v) {
		String[] list = {Reminder.Repeat.MONTHLY.name(),
						Reminder.Repeat.DAILY.name(),
						Reminder.Repeat.NONE.name() };
	    DialogFragment newFragment = new ChooseDialogFragment(AddReminder.this, "", list);
	    newFragment.show(getFragmentManager(), "itemPicker");
	}
	
	private void doReset(){
		statusSwitch.setChecked(false);
		alarmSwitch.setChecked(true);
		mulloEditText.setText(Double.toString(amount));
		descriptionEditText.setText(description);
		
		onDateSelected(dateTime, false);
		onTimeSelected(dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE));
		onItemSelected(repeated.name());
	}
	
	private void doSave(){
		Utils.print("reminder saving.......");
		description = descriptionEditText.getText().toString();
		amount = Double.parseDouble(mulloEditText.getText().toString());
		if(alarmSwitch.isChecked() && statusSwitch.isChecked()){
			Utils.showToast(getActivity(), "পরিশোধিত বিলের এলার্ম ডিসেলেক্ট করুন");
			return;
		}
		if(descriptionEditText.length() == 0 ||
				Double.compare(amount, 0.0) == 0) {
			
			Utils.showToast(getActivity(), "সকল ঘড় পুরন করুন");
		} else {
			int reminderID = 0;
			if(isEdit) {
				removeAlarm(reminder.getReminderID());
				Reminder.Status st;
				if(alarmSwitch.isChecked())st = Reminder.Status.ALARM;
				else if(statusSwitch.isChecked())st = Reminder.Status.PAID;
				else st = Reminder.Status.NON_PAID;
				
				reminder.setStatus(st);
				reminder.setAmount(amount);
				reminder.setDescription(description);
				reminder.setDueDate(dateTime.getTime());
				reminder.setRepeated(repeated);
				
				SSDAO.getSSdao().getReminderDAO().update(reminder);
				reminderID = reminder.getReminderID();
				Utils.showToast(getActivity(), "রিমাইন্ডার পরিবর্তন সংরক্ষিত");
			} else {
				Reminder.Status st;
				if(alarmSwitch.isChecked())st = Reminder.Status.ALARM;
				else if(statusSwitch.isChecked())st = Reminder.Status.PAID;
				else st = Reminder.Status.NON_PAID;
				Reminder rem = new Reminder(description, amount, st, dateTime.getTime(), repeated);
				SSDAO.getSSdao().getReminderDAO().create(rem);
				Utils.print("reminder id: " + Integer.toString(rem.getReminderID()));
				reminderID = rem.getReminderID();
				this.reminder = rem;
				Utils.showToast(getActivity(), "রিমাইন্ডার সংরক্ষিত");
			}
			if(alarm){
				setAlarm(reminderID);
			}
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.remove(AddReminder.this);
	        ft.commit();
	        getFragmentManager().popBackStack();
		}
	}
	
	private void setAlarm(int reqID){
		Utils.print(this.dateTime.toString());
		
		Calendar myAlarmDate = Calendar.getInstance();
		myAlarmDate.setTimeInMillis(System.currentTimeMillis());
		myAlarmDate.set(
				this.dateTime.get(Calendar.YEAR),
				this.dateTime.get(Calendar.MONTH),
				this.dateTime.get(Calendar.DAY_OF_MONTH),
				this.dateTime.get(Calendar.HOUR_OF_DAY),
				this.dateTime.get(Calendar.MINUTE), 0);
		
		Intent ti = new Intent("com.sakin.sohojshoncoi");
		ti.putExtra(Utils.ALARM_MSG, reminder.getDescription());
		ti.putExtra(Utils.ALARM_AMOUNT, reminder.getAmount());
		if(repeated.toString().equals("MONTHLY")) {
			ti.putExtra(Utils.ALARM_REPEATED, 2);
		} else if(repeated.toString().equals("DAILY")) {
			ti.putExtra(Utils.ALARM_REPEATED, 1);
		} else {
			ti.putExtra(Utils.ALARM_REPEATED, 0);
		}
		PendingIntent pendingIntent = PendingIntent.getBroadcast( getActivity(), 
				reqID, ti, 0 );
		
		Utils.alarmManagerm.set( AlarmManager.RTC_WAKEUP,
				myAlarmDate.getTimeInMillis(),
								pendingIntent );
		Utils.pendingIntents.put(reqID, pendingIntent);
	}
	
	private void removeAlarm(int id) {
		PendingIntent pi = Utils.pendingIntents.get(id);
		if(pi == null) {
			Utils.print("map elemnt doesnt exist");
		} else {
			Utils.alarmManagerm.cancel(pi);
			pi.cancel();
			Utils.pendingIntents.remove(id);
		}
	}
	
	@Override
	public void onItemSelected(String item) {
		this.repeated = Reminder.Repeat.valueOf(item);
		repeatButton.setText(this.repeated.name());
	}

	@Override
	public void onDateSelected(Calendar date, boolean se) {
		this.dateTime = date;
		String dt = Integer.toString(date.get(Calendar.DAY_OF_MONTH)) + "-" + 
					Integer.toString(date.get(Calendar.MONTH)) + "-" +
					Integer.toString(date.get(Calendar.YEAR));
		dateButton.setText(dt);
	}

	@Override
	public void onTimeSelected(int hour, int minute) {
		this.dateTime.set(Calendar.HOUR_OF_DAY, hour);
		this.dateTime.set(Calendar.MINUTE, minute);
		String AP = "AM";
		if(hour >= 12){
			AP = "PM";
			hour %= 12;
		}
		String tm = Integer.toString(hour) + ":" + 
					Integer.toString(minute) + " " + AP;
		timeButton.setText(tm);
	}

}
