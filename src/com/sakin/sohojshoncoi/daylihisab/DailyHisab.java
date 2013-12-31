package com.sakin.sohojshoncoi.daylihisab;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.database.Reminder;
import com.sakin.sohojshoncoi.database.SSDAO;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DailyHisab extends Fragment {
	
	RelativeLayout mainLayout;
	int currentViewID;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dailyhisab, container, false);
		Utils.setActionBarTitle(getActivity(), "দৈনিক হিসাব");
		double balance = getCurrentBalance();
		setText(view, "ব্যালেন্সঃ " + Double.toString(balance));
		
		Button addNewButton = (Button) view.findViewById(R.id.addNewButton);
		addNewButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
//				Intent addNewHisabIntent = new Intent(getActivity(), AddNewHisab.class);
//				startActivity(addNewHisabIntent);
				Fragment addNewHisab = new AddNewHisab();
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.remove(DailyHisab.this);
                ft.add(R.id.content_frame, addNewHisab, Utils.ADDNEWHISABTAG);
                ft.addToBackStack("dailyhisab");
                ft.commit();
			}
		});
		
		Button reminderButon = (Button) view.findViewById(R.id.reminderButton);
		reminderButon.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Fragment reminder = new ReminderList();
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.remove(DailyHisab.this);
                ft.add(R.id.content_frame, reminder);
                ft.addToBackStack("dailyhisab");
                ft.commit();
			}
		});
		
		Button agerhisabButton = (Button) view.findViewById(R.id.agerHisabButton);
		agerhisabButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Fragment hisabList = new HisabList();
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.remove(DailyHisab.this);
                ft.add(R.id.content_frame, hisabList);
                ft.addToBackStack("dailyhisab");
                ft.commit();
			}
		});
		
		Button aeBaeReportButton = (Button) view.findViewById(R.id.aeBaeRiportButton);
		aeBaeReportButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Fragment monthlyReport = new MonthlyReport();
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.remove(DailyHisab.this);
                ft.add(R.id.content_frame, monthlyReport);
                ft.addToBackStack("dailyhisab");
                ft.commit();
			}
		});
		
		Button planningButton = (Button) view.findViewById(R.id.planningButton);
		planningButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Fragment planningList = new PlanningList();
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.remove(DailyHisab.this);
                ft.add(R.id.content_frame, planningList);
                ft.addToBackStack("dailyhisab");
                ft.commit();
			}
		});
		
		// creating bokea bil table
		mainLayout = (RelativeLayout) view.findViewById(R.id.dailyhisab_back_layout);
		
		currentViewID = 1;
		addTitleView(R.id.reminderButton);
		try {
			List<Reminder> remList = SSDAO.getSSdao().getReminderFromDate(new Date());
			if(remList == null || remList.size() == 0) {
				currentViewID++;
				addRowsToBill(currentViewID - 1,"কোন বকেয়া বিল নেই","","");
			} else {
				for(int j = 0; j<remList.size(); j++ ) {
					Reminder rem = remList.get(j);
					currentViewID++;
					addRowsToBill(currentViewID - 1,
								rem.getDescription(),
								getDateDifference(new Date(), rem.getDueDate()),
								Double.toString(rem.getAmount()));
				}
			}
		} catch (SQLException e) {
			Utils.print(e.toString());
		}
		for(int i=1; i<5;i++){
			
		}

		return view;
	}
	
	private String getDateDifference(Date st, Date end) {
    	String ret = "Due in ";
    	String last = "";
    	if(end.before(st)) {
    		ret = "Time already passed by ";
    		//swap st, end
    		Date temp = st;
    		st = end;
    		end = temp;
    	}
    	long endTimeinMilis = end.getTime();
    	long stTimeinMilis = st.getTime();
    	long diff = endTimeinMilis - stTimeinMilis;
    	
    	long MILISEC = 1;
    	long SEC = 1000 * MILISEC;
    	long MIN = 60 * SEC;
    	long HOUR = 60 * MIN;
    	long DAY = 24 * HOUR;
    	long MONTH = 30 * DAY;
    	long YEAR = 12 * MONTH;
    	if(diff >= YEAR ) {
    		diff /= YEAR;
    		last = "year";
    	} else if(diff >= MONTH) {
    		diff /= MONTH;
    		last = "month";
    	} else if(diff >= DAY) {
    		diff /= DAY;
    		last = "day";
    	} else if(diff >= HOUR) {
    		diff /= HOUR;
    		last = "hour";
    	} else if(diff >= MIN) {
    		diff /= MIN;
    		last = "minute";
    	} else {
    		diff = -1;
    		last = "seconds";
    	}
    	
    	if(diff == -1) {
    		ret += "few";
    	} else {
    		ret += Long.toString(diff);
    		if(diff > 1){
    			last += "s";
    		}
    	}
    	ret = ret + " " + last;
    	return ret;
    }
	
	protected void addTitleView(int buttonID){
		RelativeLayout.LayoutParams bokeaBillTitleParams =
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 
												RelativeLayout.LayoutParams.WRAP_CONTENT);
		bokeaBillTitleParams.addRule(RelativeLayout.BELOW, buttonID);
		bokeaBillTitleParams.setMargins(25, 20, 25, 0);
		RelativeLayout titleView = new RelativeLayout(getActivity());
		titleView.setBackgroundColor(Color.DKGRAY);
		titleView.setId(currentViewID);
		mainLayout.addView(titleView, bokeaBillTitleParams);
		
		TextView tv = new TextView(getActivity());
		tv.setText("বকেয়া বিল");
		tv.setTextColor(Color.WHITE);
		tv.setTypeface(Utils.banglaTypeFace);
		tv.setTextSize(20);

		RelativeLayout.LayoutParams bokeaBillTitleTextParams =
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
												RelativeLayout.LayoutParams.WRAP_CONTENT);
		bokeaBillTitleTextParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
		bokeaBillTitleTextParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		titleView.addView(tv, bokeaBillTitleTextParams);
	}
	
	protected int addRowsToBill(int previousViewID, String title, String time, String amount){
		RelativeLayout.LayoutParams bokeaBillTitleParams =
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 
												RelativeLayout.LayoutParams.WRAP_CONTENT);
		bokeaBillTitleParams.addRule(RelativeLayout.BELOW, previousViewID);
		bokeaBillTitleParams.setMargins(25, 0, 25, 0);
		RelativeLayout titleView = new RelativeLayout(getActivity());
		titleView.setBackgroundColor(Color.LTGRAY);
		titleView.setId(currentViewID);
		mainLayout.addView(titleView, bokeaBillTitleParams);
		
		TextView tv = new TextView(getActivity());
		tv.setText(title);
		tv.setTextColor(Color.BLACK);
		tv.setTypeface(Utils.banglaTypeFace);
		tv.setTextSize(16);
		tv.setId(100);
		RelativeLayout.LayoutParams bokeaBillTitleTextParams =
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
												RelativeLayout.LayoutParams.WRAP_CONTENT);
		bokeaBillTitleTextParams.addRule(RelativeLayout.ALIGN_LEFT, RelativeLayout.TRUE);
		bokeaBillTitleTextParams.setMargins(10, 5, 100, 0);
		titleView.addView(tv, bokeaBillTitleTextParams);
		
		TextView tv1 = new TextView(getActivity());
		tv1.setText(time);
		tv1.setTextColor(Color.BLACK);
		tv1.setTypeface(Utils.banglaTypeFace);
		tv1.setTextSize(10);

		RelativeLayout.LayoutParams bokeaBillTimeTextParams =
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
												RelativeLayout.LayoutParams.WRAP_CONTENT);
		bokeaBillTimeTextParams.addRule(RelativeLayout.ALIGN_LEFT, RelativeLayout.TRUE);
		bokeaBillTimeTextParams.addRule(RelativeLayout.BELOW, tv.getId());
		bokeaBillTimeTextParams.setMargins(10, 6, 100, 0);
		titleView.addView(tv1, bokeaBillTimeTextParams);
		
		TextView amountText = new TextView(getActivity());
		amountText.setText(amount);
		amountText.setTextColor(Color.BLACK);
		amountText.setTypeface(Utils.banglaTypeFace);
		amountText.setTextSize(16);
		
		RelativeLayout.LayoutParams bokeaBillAmountTextParams =
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
												RelativeLayout.LayoutParams.WRAP_CONTENT);
		bokeaBillAmountTextParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		bokeaBillAmountTextParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		bokeaBillAmountTextParams.setMargins(0, 0, 10, 0);
		titleView.addView(amountText, bokeaBillAmountTextParams);
		return 0;
	}

	public void setText(View view, String item) {
		TextView tv = (TextView) view.findViewById(R.id.dailyhisabText);
		tv.setTypeface(Utils.banglaTypeFace);
		tv.setText(item);
	}
	
	private double getCurrentBalance() { 
		Calendar cal = Calendar.getInstance();
		double ret = 0.0;
		try {
			String balanceS = SSDAO.getSSdao().getTransactionSumToDate(cal.getTime());
			if(balanceS == null)ret = 0.0;
			else ret = Double.parseDouble(balanceS);
		} catch (SQLException e) {
			Utils.print(e.toString());
		}
		return ret;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//		inflater.inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//        case R.id.action_settings:
//            return true;
//        default:
            return super.onOptionsItemSelected(item);
//		}
	}

}
