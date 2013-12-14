package com.sakin.sohojshoncoi.daylihisab;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.custom.DatePickerFragment;

public class AddValueForOptions extends ListFragment implements 
						CategoryFragment.OnCategorySelectedListener,
						DatePickerFragment.OnDateSelectedListener {

	String[] list_items=new String[15];
	int count=0,Month=0,Year=0,Day=0;
	private OptionAdapter adapter;
	private List <OptionList> Optionlist;
	Button datebutton, optionbtnay, optionbtnbey, plansave;
	
	View rootView=null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	     // The last two arguments ensure LayoutParams are inflated properly.
		if(rootView==null){
		rootView = inflater.inflate(R.layout.addvalueforoptions, container, false);
//		list_items = getArguments().getStringArray("array_list");
//		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list_items));
		
		datebutton =(Button) rootView.findViewById (R.id.dailydatebutton);
		datebutton.setBackgroundResource(R.drawable.date_button);
		datebutton.setTypeface(Utils.banglaTypeFace);
		datebutton.setText("à¦•à§�à¦¯à¦¾à¦²à§‡à¦¨à§�à¦¡à¦¾à¦°");
		datebutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDatePickerDialog(v);
				Utils.print("button click hoise");
			}
		});
		
		Optionlist = new ArrayList<OptionList>();
		adapter = new OptionAdapter(getActivity(), R.layout.itemoptions, Optionlist, this);
		setListAdapter(adapter);
		
		optionbtnay =(Button) rootView.findViewById (R.id.optionbuttonay);
		optionbtnay.setBackgroundResource(R.drawable.optionbtn);
		optionbtnay.setTypeface(Utils.banglaTypeFace);
		optionbtnay.setText("à¦†à§Ÿ");
		optionbtnay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Fragment categoryFragment = new CategoryFragment(AddValueForOptions.this, false);
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.remove(AddValueForOptions.this);
                ft.add(R.id.content_frame, categoryFragment);
                ft.addToBackStack("addvalueforoptions");
                ft.commit();
			}
		});
		
		optionbtnbey =(Button) rootView.findViewById (R.id.optionbuttonbey);
		optionbtnbey.setBackgroundResource(R.drawable.optionbtn);
		optionbtnbey.setTypeface(Utils.banglaTypeFace);
		optionbtnbey.setText("à¦¬à§�à¦¯à§Ÿ");
		optionbtnbey.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Fragment categoryFragment = new CategoryFragment(AddValueForOptions.this, true);
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.remove(AddValueForOptions.this);
                ft.add(R.id.content_frame, categoryFragment);
                ft.addToBackStack("addvalueforoptions");
                ft.commit();
			}
		});
		
		plansave= (Button) rootView.findViewById (R.id.plansavebutton);
		plansave.setBackgroundResource(R.drawable.optionbtn);
		plansave.setTypeface(Utils.banglaTypeFace);
		plansave.setText("");
		plansave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Utils.print("Save Koro");
				
			}
		});
		
		}
		else{
			ViewGroup parent = (ViewGroup) rootView.getParent();
			parent.removeView(rootView);
		}

	    return rootView;
	}
	public class OptionList{
		String category;
		double amount;
		public OptionList(String id,double amnt){
			category=id;
			amount=amnt;
		}
	}
	@Override
	public void onCategorySelected(String cat) {
		// TODO Auto-generated method stub
		//Utils.print(cat);
		//list_items=new String[15];
		int flag=0,i;
		for(i=0;i<count;i++){
			if(cat.equals(list_items[i])){
				flag=1;break;
			}
		}
		if(flag==0){
			list_items[i]=cat;
			count++;
			
			Optionlist.add(new OptionList(cat,0));
			adapter.notifyDataSetChanged();
		}
	}
	public void showDatePickerDialog(View v) {
		    DatePickerFragment newFragment = new DatePickerFragment(AddValueForOptions.this, Calendar.getInstance());
		    
		    try {
			    Field f[] = newFragment.getClass().getDeclaredFields();
			    for (Field field : f) {
			        if (field.getName().equals("mDayPicker")) {
			            field.setAccessible(true);
			            DatePicker datePicker = (DatePicker) field.get(newFragment);
			            Field datePickerFields[] = field.getType().getDeclaredFields();
			            for (Field datePickerField : datePickerFields) {
			               if ("mDayPicker".equals(datePickerField.getName())) {
			                  datePickerField.setAccessible(true);
			                  Object dayPicker = new Object();
			                  dayPicker = datePickerField.get(datePicker);
			                  ((View) dayPicker).setVisibility(View.GONE);
			               }
			            }
//			            Object dayPicker = new Object();
//			            dayPicker = field.get(datePicker);
//			            ((View) dayPicker).setVisibility(View.GONE);
			        }
			    }
			} catch (SecurityException e) {
			   // Log.d("ERROR", e.getMessage());
			} 
			catch (IllegalArgumentException e) {
			    //Log.d("ERROR", e.getMessage());
			} catch (IllegalAccessException e) {
			    //Log.d("ERROR", e.getMessage());
			}
		    newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
		    
		}
	@Override
	public void onDateSelected(Calendar date, boolean se) {
		// TODO Auto-generated method stub
		Utils.print(date.toString());
		Month=date.get(Calendar.MONTH);
		Year=date.get(Calendar.YEAR);
		String st = new SimpleDateFormat("MMM-yyyy").format(date.getTime());
		//String st= ""+Month+" "+Year+"!";
		datebutton.setText(st);
	}
	
	
	public void deleteRow(int position){
		Optionlist.remove(position);
		adapter.notifyDataSetChanged();
	}
	
}
