package com.sakin.sohojshoncoi.daylihisab;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.database.Planning;
import com.sakin.sohojshoncoi.database.PlanningDescription;
import com.sakin.sohojshoncoi.database.SSDAO;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MonthlyReportFragment extends Fragment {
	
	int id, month, year;
	ProgressBar totalProgressBar;
	ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<ReportElement>> listDataChild;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	     // The last two arguments ensure LayoutParams are inflated properly.
		View rootView = inflater.inflate(R.layout.month_fragment, container, false);
		
		Utils.print("this is in report fragment");
		id = getArguments().getInt(Utils.TAB_ID);
		month = getArguments().getInt(Utils.MONTH_ID);
		year = getArguments().getInt(Utils.YEAR_ID);
		
		ProgressBar totalProgressBar = (ProgressBar) rootView.findViewById(R.id.totalProgressBar);
		totalProgressBar.setProgressDrawable(rootView.getResources().getDrawable(R.drawable.total_progress_bar));
		totalProgressBar.setProgress(1);
		setText(rootView, R.id.ajLabel, "");
		
		TextView tv = (TextView) rootView.findViewById(R.id.totalLabel);
		tv.setTypeface(Utils.banglaTypeFaceSutonny);
		tv.setTextColor(Color.WHITE);
		tv.setText("me©‡gvU");
		TextView tv1 = (TextView) rootView.findViewById(R.id.planLabel);
		tv1.setTypeface(Utils.banglaTypeFaceSutonny);
		tv1.setTextColor(Color.WHITE);
		tv1.setText("cwiKíbv");
		
		TextView extraText = (TextView) rootView.findViewById(R.id.extraText);
		extraText.setText("");
		
		Calendar st = Calendar.getInstance();
		st.set(year, month, 1, 0, 0, 0);
		Date startDate = st.getTime();
		st.set(year, month, st.getActualMaximum(Calendar.DATE),
							st.getActualMaximum(Calendar.HOUR_OF_DAY),
							st.getActualMaximum(Calendar.MINUTE),
							st.getActualMaximum(Calendar.SECOND));
		Date endDate = st.getTime();
		Utils.print(startDate.toString());
		Utils.print(endDate.toString());
		
		double totalAmount = 0.0;
		double totalPlan = 0.0;
		
		//get values here
		try {
			String totalAmountString = SSDAO.getSSdao().getTransactionSumToDate(endDate);
			if(totalAmountString != null) {
				totalAmount = Double.parseDouble(totalAmountString);
			}
			totalPlan = SSDAO.getSSdao().getPlanningSumUptoDate(month, year);
			
		} catch (SQLException e) {
			Utils.print(e.toString());
		}
		boolean a,b;
        a = false; b = false;
        setText(rootView, R.id.amountText, Double.toString(totalAmount));
        setText(rootView, R.id.planText, Double.toString(totalPlan));
        totalProgressBar.setMax((int) totalAmount);
    	totalProgressBar.setProgress((int) totalAmount);
    	
        if(Double.compare(totalAmount, 0.0) != 0){
        	a = true;
        }
        if(Double.compare(totalPlan, 0.0) != 0){
        	totalProgressBar.setMax((int) totalPlan);
        	if(totalAmount > totalPlan) {
        		extraText.setText("(+" + Double.toString(totalAmount - totalPlan) + ")");
        		totalProgressBar.setProgress((int) totalPlan);
        		totalProgressBar.setProgressDrawable(rootView.getResources().getDrawable(R.drawable.total_progress_bar_red));
        	}
        	b = true;
        }     
        if(a == false && b == false) {
        	setText(rootView, R.id.amountText, "No Data Available");
        	setText(rootView, R.id.planText, "");
        	totalProgressBar.setVisibility(ProgressBar.INVISIBLE);
        } else {
        	totalProgressBar.setVisibility(ProgressBar.VISIBLE);
        }
		
        expListView = (ExpandableListView) rootView.findViewById(R.id.aeBaeLV);
        prepareListData(startDate, endDate);
        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
        
		return rootView;
	}
	
	private void setText(View view, int id, String item) {
		TextView tv = (TextView) view.findViewById(id);
		if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			tv.setTypeface(Utils.banglaTypeFace);
		}
		tv.setTextColor(Color.WHITE);
		tv.setText(item);
	}
	
	private void getTotalBaeValue(Date st, Date end) {
		try {
			String value = SSDAO.getSSdao().getTransactionSumBetweenDate(st, end, true);
			if(value == null){
				Utils.print("no value found");
			} else {
				Utils.print(value);
				value = SSDAO.getSSdao().getTransactionSumBetweenDate(st, end, false);
				Utils.print(value==null?"no value found":value);
			}
		} catch (SQLException e) {
			Utils.print(e.toString());
		}
	}
	
	private void prepareListData(Date start, Date end) {
		listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<ReportElement>>();
        
        // Adding child data
        listDataHeader.add("e¨q");
        listDataHeader.add("Avq");
        String[] baeTitle = getResources().getStringArray(R.array.support_category_title_bae);
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
        	baeTitle = getResources().getStringArray(R.array.category_title_bae);
        }
        
        String[] aeTitle = getResources().getStringArray(R.array.support_category_title_ae);
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
        	aeTitle = getResources().getStringArray(R.array.category_title_ae);
        }
        // Adding bae data
        List<ReportElement> baeList = new ArrayList<ReportElement>();
        List<ReportElement> aeList = new ArrayList<ReportElement>();
        try {
        	List<Planning> plannings = SSDAO.getSSdao().getPlanningOfMonthAndYear(month, year);
        	int planningID;
        	if(plannings == null || plannings.size() == 0) {
        		planningID = 0;
        	} else {
        		planningID = plannings.get(0).getPlanningId();
        	}
        	List<PlanningDescription> cpd = SSDAO.getSSdao()
        			.getPlanningDescriptionOfPlanning(planningID);
        	
        	int i = 1;
	        for(int j = 0; j < baeTitle.length; j++) {
	        	String baeString = SSDAO.getSSdao()
	        			.getTransactionSumOfCategoryBetweenDate(i, start, end, false);
	        	double bae = 0.0;
	        	if(baeString != null) {
	        		bae = Double.parseDouble(baeString);
	        		if(bae < 0.0) bae *= -1.0;
	        	}
	        	double plan = getAmountOfCategoryFromPD(cpd, i);
	        	baeList.add(new ReportElement(baeTitle[j], bae, plan));
	        	i++;
	        }
	        for(int j = 0; j < aeTitle.length; j++) {
	        	String aeString = SSDAO.getSSdao()
	        			.getTransactionSumOfCategoryBetweenDate(i, start, end, false);
	        	double ae = 0.0;
	        	if(aeString != null) {
	        		ae = Double.parseDouble(aeString);
	        	}
	        	double plan = getAmountOfCategoryFromPD(cpd, i);
	        	aeList.add(new ReportElement(aeTitle[j], ae, plan));
	        	i++;
	        }
        } catch (SQLException e) { 
        	Utils.print(e.toString());
        }
        
        listDataChild.put(listDataHeader.get(0), baeList);
        listDataChild.put(listDataHeader.get(1), aeList);
	}
	
	private double getAmountOfCategoryFromPD(List<PlanningDescription> cpd, int category) {
		if(cpd == null) return 0.0;
		for(int i=0; i<cpd.size(); i++) {
			PlanningDescription pd = cpd.get(i);
			if(pd.getCategory().getCategoryID() == category)return pd.getAmount();
		}
		return 0.0;
	}
}
