package com.sakin.sohojshoncoi.daylihisab;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.custom.DatePickerFragment;
import com.sakin.sohojshoncoi.database.Category;
import com.sakin.sohojshoncoi.database.Planning;
import com.sakin.sohojshoncoi.database.PlanningDescription;
import com.sakin.sohojshoncoi.database.SSDAO;

@SuppressLint({ "SimpleDateFormat", "ValidFragment" })
public class AddValueForOptions extends ListFragment implements 
						CategoryFragment.OnCategorySelectedListener,
						DatePickerFragment.OnDateSelectedListener {

	String[] list_items=new String[Utils.MAX_AE_INDEX+1];
	int count=0,Month=0,Year=0,Day=0;
	private OptionAdapter adapter;
	private List <OptionList> optionList;
	Button datebutton, optionbtnay, optionbtnbey, plansave;
	private Boolean isEdit, keyboardHidden;
	private Planning planning;
	View rootView=null;
	
	public AddValueForOptions() {
		this.isEdit = false;
		this.keyboardHidden = false;
	}
	
	public AddValueForOptions(Planning planning) {
		this.isEdit = true;
		this.planning = planning;
		this.keyboardHidden = false;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	     // The last two arguments ensure LayoutParams are inflated properly.
		if(rootView==null){
			rootView = inflater.inflate(R.layout.addvalueforoptions, container, false);
			
			Calendar cal = Calendar.getInstance();
			datebutton =(Button) rootView.findViewById (R.id.dailydatebutton);
			datebutton.setBackgroundResource(R.drawable.optionbtn);
//			datebutton.setTypeface(Utils.banglaTypeFace);
			datebutton.setText("Month");
			datebutton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showDatePickerDialog(v);
					Utils.print("button click hoise");
				}
			});
			
			optionList = new ArrayList<OptionList>();
			adapter = new OptionAdapter(getActivity(), R.layout.itemoptions, optionList, this);
			setListAdapter(adapter);
			
			if(isEdit) {
				cal.set(Calendar.MONTH, planning.getMonth());
				cal.set(Calendar.YEAR, planning.getYear());
				try {
					optionList.clear();
					List<PlanningDescription> pdList = SSDAO.getSSdao()
							.getPlanningDescriptionOfPlanning(planning.getPlanningId());
					for(PlanningDescription pd : pdList) {
						Category cat = SSDAO.getSSdao().getCategoryFromID( 
								pd.getCategory().getCategoryID());
						list_items[count] = cat.getName();
						count++;
						optionList.add(new OptionList(cat.getName(), pd.getAmount()));
					}
					adapter.notifyDataSetChanged();
				} catch (SQLException e) {
					Utils.print(e.toString());
				}
			}
			
			optionbtnay =(Button) rootView.findViewById (R.id.optionbuttonay);
			optionbtnay.setBackgroundResource(R.drawable.optionbtn);
			optionbtnay.setTypeface(Utils.banglaTypeFaceSutonny);
			optionbtnay.setText("bZyb Av‡qi cwiKíbv");
			optionbtnay.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					Fragment categoryFragment = new CategoryFragment(AddValueForOptions.this, false);
					FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
					ft.remove(AddValueForOptions.this);
	                ft.add(R.id.content_frame, categoryFragment);
	                ft.addToBackStack("addvalueforoptions");
	                ft.commit();
				}
			});
			
			optionbtnbey =(Button) rootView.findViewById (R.id.optionbuttonbey);
			optionbtnbey.setBackgroundResource(R.drawable.optionbtn);
			optionbtnbey.setTypeface(Utils.banglaTypeFaceSutonny);
			optionbtnbey.setText("bZyb e¨‡qi cwiKíbv");
			optionbtnbey.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Fragment categoryFragment = new CategoryFragment(AddValueForOptions.this, true);
					FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
					ft.remove(AddValueForOptions.this);
	                ft.add(R.id.content_frame, categoryFragment);
	                ft.addToBackStack("addvalueforoptions");
	                ft.commit();
				}
			});
			
			plansave= (Button) rootView.findViewById (R.id.plansavebutton);
			plansave.setBackgroundResource(R.drawable.save_button);
			plansave.setTypeface(Utils.banglaTypeFaceSutonny);
			plansave.setText("‡mf");
			plansave.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					doSave();
				}
			});
			
			//reset things
			onDateSelected(cal, false);
		}
		else{
			ViewGroup parent = (ViewGroup) rootView.getParent();
			parent.removeView(rootView);
		}
		Utils.setActionBarTitle(getActivity(), "bZzb cwiKíbv");
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
		//Utils.print(cat);
		//list_items=new String[15];
		updateList();
		int flag=0,i;
		for(i=0;i<count;i++){
			if(cat.equals(list_items[i])){
				flag=1;break;
			}
		}
		if(flag==0){
			list_items[i]=cat;
			count++;
			
			optionList.add(new OptionList(cat,0));
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
		   Utils.print(e.toString());
		} catch (IllegalArgumentException e) {
			Utils.print(e.toString());
		} catch (IllegalAccessException e) {
			Utils.print(e.toString());
		}
	    newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
	    
	}
	@Override
	public void onDateSelected(Calendar date, boolean se) {
		Utils.print(date.toString());
		Month=date.get(Calendar.MONTH);
		Year=date.get(Calendar.YEAR);
		String st = new SimpleDateFormat("MMM-yyyy").format(date.getTime());
		datebutton.setText(st);
	}
	
	public void deleteRow(int position){
		optionList.remove(position);
		adapter.notifyDataSetChanged();
	}
	
	public void updateList() {
		int i = 0;
		EditText et;
		for(OptionList ol : optionList) {
			ListView ls = (ListView) rootView.findViewById(android.R.id.list);
			View v = ls.getChildAt(i);
			et = (EditText) v.findViewById(R.id.OptionEditText);
			double amount = Double.parseDouble(et.getText().toString());
			ol.amount = amount;
			i++;
		}
		adapter.notifyDataSetChanged();
	}
	
	private boolean checkValidity() throws SQLException {
		List<Planning> planning = SSDAO.getSSdao().getPlanningOfMonthAndYear(Month, Year);
		if(planning.size() > 0) {
			return false;
		}
		return true;
	}
	
	private void doSave() {
		Utils.print("planning saving.......");
		try {
			boolean b = checkValidity();
			if(b == false && isEdit == false) {
				Utils.showToast(getActivity(), "GB gv‡mi Rb¨ cwiKíbv B‡Zvg‡a¨B †bqv n‡q‡Q");
				return;
			}
			if(isEdit) {
				SSDAO.getSSdao().removePlanningDescriptionOfPlanning(planning.getPlanningId());
				double ae, bae;
				ae = 0.0; bae = 0.0;
				for(OptionList ol : optionList) {
					Category cat = SSDAO.getSSdao().getCategoryFromName(ol.category);
					PlanningDescription pd = new PlanningDescription(planning, cat, ol.amount);
					SSDAO.getSSdao().getPlanningDescriptionDAO().create(pd);
					if(cat.getCategoryID() < Utils.MAX_BAE_INDEX) {
						bae += ol.amount;
					} else {
						ae += ol.amount;
					}
				}
				planning.setAeAmount(ae);
				planning.setBaeAmount(bae);
				planning.setMonth(Month);
				planning.setYear(Year);
				SSDAO.getSSdao().getPlanningDAO().update(planning);
				Utils.showToast(getActivity(), "cwiKíbv cwieZ©b msiw¶Z");
			} else {
				Planning plan = new Planning(Utils.userAccount, 0.0, 0.0, Month, Year);
				SSDAO.getSSdao().getPlanningDAO().create(plan);
				double ae, bae;
				ae = 0.0; bae = 0.0;
				int i = 0;
				EditText et;
				for(OptionList ol : optionList) {
					Category cat = SSDAO.getSSdao().getCategoryFromName(ol.category);
					
					ListView ls = (ListView) rootView.findViewById(android.R.id.list);
					View v = ls.getChildAt(i);
					et = (EditText) v.findViewById(R.id.OptionEditText);
					double amount = Double.parseDouble(et.getText().toString());
					
					PlanningDescription pd = new PlanningDescription(plan, cat, amount);
					SSDAO.getSSdao().getPlanningDescriptionDAO().create(pd);
					if(cat.getCategoryID() < Utils.MAX_BAE_INDEX) {
						bae += amount;
					} else {
						ae += amount;
					}
					i++;
				}
				plan.setAeAmount(ae);
				plan.setBaeAmount(bae);
				SSDAO.getSSdao().getPlanningDAO().update(plan);
				Utils.showToast(getActivity(), "cwiKíbv msiw¶Z");
			}
		} catch (SQLException e) {
			Utils.print(e.toString());
		}
		FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
		ft.remove(AddValueForOptions.this);
        ft.commit();
        getActivity().getSupportFragmentManager().popBackStack();
	}
	
}
