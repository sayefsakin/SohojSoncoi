package com.sakin.sohojshoncoi.daylihisab;

import java.sql.SQLException;
import java.util.List;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.custom.SwipeDismissListViewTouchListener;
import com.sakin.sohojshoncoi.database.Reminder;
import com.sakin.sohojshoncoi.database.SSDAO;
import com.sakin.sohojshoncoi.database.Transaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

@SuppressLint("UseValueOf")
public class ReminderList extends ListFragment {
	
	private ReminderListAdapter adapter;
	private List<Reminder> reminderList;
	ListView listView;
	SwipeDismissListViewTouchListener touchListener;
	private boolean editMode = false;
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
		}
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
                                	SSDAO.getSSdao().getReminderDAO().delete(selectedReminder);
                                	adapter.remove(selectedReminder);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });
        
		super.onViewCreated(view, savedInstanceState);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
	  	Utils.print("clicked");
	  	if(!editMode) {
		  	Fragment addReminder = new AddReminder((Reminder)l.getItemAtPosition(position));
			FragmentTransaction ft = getFragmentManager().beginTransaction();
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
			editItem.setIcon(R.drawable.ic_action_discard);
		} else {
			menu.findItem(R.id.action_add).setVisible(true);
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
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void goToAddNewPage() {
		Fragment addReminder = new AddReminder();
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.remove(ReminderList.this);
        ft.add(R.id.content_frame, addReminder);
        ft.addToBackStack("reminderlist");
        ft.commit();
	}
	
	private void editModeToggle() {
		editMode = !editMode;
		getActivity().invalidateOptionsMenu();
		if(editMode){
			listView.setOnTouchListener(touchListener);
        	listView.setOnScrollListener(touchListener.makeScrollListener());
        	Utils.showToast(getActivity(), "Enter in Edit Mode");
		} else {
			listView.setOnTouchListener(null);
        	listView.setOnScrollListener(null);
        	Utils.showToast(getActivity(), "Normal Mode");
		}
	}
}
