package com.sakin.sohojshoncoi.daylihisab;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.database.SSDAO;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MonthlyReportFragment extends Fragment {
	
	int id, month, year;
	ProgressBar totalProgressBar;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	     // The last two arguments ensure LayoutParams are inflated properly.
		View rootView = inflater.inflate(R.layout.month_fragment, container, false);
		
		Utils.print("this is in report fragment");
		id = getArguments().getInt(Utils.TAB_ID);
		month = getArguments().getInt(Utils.MONTH_ID);
		year = getArguments().getInt(Utils.YEAR_ID);
		
		setText(rootView, R.id.ajLabel, "আজ পর্যন্ত");
		setText(rootView, R.id.totalLabel, "সর্বমোট");
		setText(rootView, R.id.amountText, "700");
		setText(rootView, R.id.planText, "400");
		
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
		
		getTotalBaeValue(startDate, endDate);
		
		ProgressBar totalProgressBar = (ProgressBar) rootView.findViewById(R.id.totalProgressBar);
		totalProgressBar.setMax(700);
		totalProgressBar.setProgress(400);
		
		return rootView;
	}
	
	public void setText(View view, int id, String item) {
		TextView tv = (TextView) view.findViewById(id);
		tv.setTypeface(Utils.banglaTypeFace);
		tv.setTextColor(Color.WHITE);
		tv.setText(item);
	}
	
	public void getTotalBaeValue(Date st, Date end) {
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
}
