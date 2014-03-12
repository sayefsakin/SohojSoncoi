package com.sakin.sohojshoncoi.daylihisab;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.custom.SwipeDismissListViewTouchListener;
import com.sakin.sohojshoncoi.database.Category;
import com.sakin.sohojshoncoi.database.Reminder;
import com.sakin.sohojshoncoi.database.SSDAO;
import com.sakin.sohojshoncoi.database.Transaction;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

@SuppressLint("UseValueOf")
public class ReminderList extends ListFragment
							implements ChooseReminderFilter.OnFilterSelectedListener {
	
	private ReminderListAdapter adapter;
	private List<Reminder> reminderList;
	ListView listView;
	SwipeDismissListViewTouchListener touchListener;
	private boolean editMode = false, isRefresh = false;
	View view;
	
	public ReminderList() {
		editMode = false;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view == null){
			view = inflater.inflate(R.layout.reminder_list, container, false);
			
			try {
				reminderList = SSDAO.getSSdao().getReminder();
			} catch (SQLException e) {
				Utils.print("getting transaction error: " + e.toString());
			}
			adapter = new ReminderListAdapter(getActivity(), R.layout.reminder_list_item, reminderList);
			setListAdapter(adapter);
		} else {
			Utils.print(" view already created ");
			ViewGroup parent = (ViewGroup) view.getParent();
			parent.removeView(view);
			
			if(isRefresh){
				Utils.print("view refreshing....");
				reminderList.clear();
				try {
					reminderList.addAll(SSDAO.getSSdao().getReminder());
					adapter.notifyDataSetChanged();
				} catch (SQLException e) {
					Utils.print(e.toString());
				}
			}
		}
		Utils.setActionBarTitle(getActivity(), "we‡ji wigvBÛvimg~n");
		return view;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		listView = getListView();
        touchListener = new SwipeDismissListViewTouchListener(
                        listView,
                        new SwipeDismissListViewTouchListener.OnDismissCallback() {
                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                	Reminder selectedReminder = adapter.getItem(position);
                                	removeAlarm(selectedReminder.getReminderID());
                                	SSDAO.getSSdao().getReminderDAO().delete(selectedReminder);
                                	adapter.remove(selectedReminder);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });
        
		super.onViewCreated(view, savedInstanceState);
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
	public void onListItemClick(ListView l, View v, int position, long id) {
	  	Utils.print("clicked");
	  	if(!editMode) {
	  		isRefresh = true;
		  	Fragment addReminder = new AddReminder((Reminder)l.getItemAtPosition(position));
			FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
			ft.remove(ReminderList.this);
		    ft.add(R.id.content_frame, addReminder);
		    ft.addToBackStack("reminderlist");
		    ft.commit();
	  	}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.add_new_reminder_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		MenuItem editItem = menu.findItem(R.id.action_edit);
		if(editMode) {
			menu.findItem(R.id.action_add).setVisible(false);
//			menu.findItem(R.id.action_filter).setVisible(false);
			
			editItem.setIcon(R.drawable.ic_action_discard);
		} else {
			menu.findItem(R.id.action_add).setVisible(true);
//			menu.findItem(R.id.action_filter).setVisible(true);
			
			editItem.setIcon(R.drawable.ic_action_edit);
		}
		super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
	        case R.id.action_add:
	            goToAddNewPage();
	            return true;
	        case R.id.action_edit:
	            editModeToggle();
	            return true;
	        case R.id.action_filter:
	            goToFilter();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void goToAddNewPage() {
		isRefresh = true;
		Fragment addReminder = new AddReminder();
		FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
		ft.remove(ReminderList.this);
        ft.add(R.id.content_frame, addReminder);
        ft.addToBackStack("reminderlist");
        ft.commit();
	}
	
	private void editModeToggle() {
		editMode = !editMode;
		((ActionBarActivity) getActivity()).supportInvalidateOptionsMenu();
		if(editMode){
			listView.setOnTouchListener(touchListener);
        	listView.setOnScrollListener(touchListener.makeScrollListener());
        	Utils.showEnglishToast(getActivity(), "Swipe to delete item");
		} else {
			listView.setOnTouchListener(null);
        	listView.setOnScrollListener(null);
        	Utils.showEnglishToast(getActivity(), "Normal Mode");
		}
	}
	
	private void goToFilter() {
		isRefresh = false;
		Fragment chooseFilter = new ChooseReminderFilter(ReminderList.this);
		FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
		ft.remove(ReminderList.this);
        ft.add(R.id.content_frame, chooseFilter);
        ft.addToBackStack(null);
        ft.commit();
	}

	@Override
	public void onFilterSelectedListener(String repeated, String status, Calendar std, Calendar ed, boolean onOff) {
		Utils.print(repeated + " " + status);
		Calendar startDate = std;
		Calendar endDate = ed;
		try {
			reminderList.clear();
			if(onOff){
				reminderList.addAll(
						SSDAO.getSSdao().getReminderWithFilters(
												repeated,
												status, 
												startDate.getTime(),
												endDate.getTime()));
			} else {
				reminderList.addAll(SSDAO.getSSdao().getReminder());
			}
			adapter.notifyDataSetChanged();
		} catch (SQLException e) {
			Utils.print(e.toString());
		}
	}
}
