package com.sakin.sohojshoncoi.daylihisab;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.custom.SwipeDismissListViewTouchListener;
import com.sakin.sohojshoncoi.database.Planning;
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
public class PlanningList extends ListFragment 
							implements ChooseFilter.OnFilterSelectedListener {

	private PlanningListAdapter adapter;
	private List<Planning> planningList;
	ListView listView;
	SwipeDismissListViewTouchListener touchListener;
	private boolean editMode = false, isRefresh = false;
	View view;
	
	public PlanningList() {
		editMode = false;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view == null){
			view = inflater.inflate(R.layout.planning_list, container, false);
			
			try {
				planningList = SSDAO.getSSdao().getPlanning();
			} catch (SQLException e) {
				Utils.print("getting transaction error: " + e.toString());
			}
			adapter = new PlanningListAdapter(getActivity(), R.layout.planning_list_item, planningList);
			setListAdapter(adapter);
		} else {
			Utils.print(" view already created ");
			ViewGroup parent = (ViewGroup) view.getParent();
			parent.removeView(view);
			
			if(isRefresh){
				Utils.print("view refreshing....");
				planningList.clear();
				try {
					planningList.addAll(SSDAO.getSSdao().getPlanning());
					adapter.notifyDataSetChanged();
				} catch (SQLException e) {
					Utils.print(e.toString());
				}
			}
		}
		Utils.setActionBarTitle(getActivity(), "cwiKíbvmg~n");
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
                                	try {
	                                	Planning selectedPlanning = adapter.getItem(position);
	                                	SSDAO.getSSdao()
	                                		.removePlanningDescriptionOfPlanning(selectedPlanning.getPlanningId());
	                                	SSDAO.getSSdao().getPlanningDAO().delete(selectedPlanning);
	                                	adapter.remove(selectedPlanning);
                                	} catch (SQLException e) {
                                		Utils.print(e.toString());
                                	}
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
	  		isRefresh = true;
		  	Fragment addValueForOptions = new AddValueForOptions((Planning)l.getItemAtPosition(position));
			FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
			ft.remove(PlanningList.this);
		    ft.add(R.id.content_frame, addValueForOptions, Utils.ADDNEWPLANNINGTAG);
		    ft.addToBackStack("planninglist");
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
//	        case R.id.action_filter:
//	            goToFilter();
//	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void goToAddNewPage() {
		isRefresh = true;
		Fragment addValueForOptions = new AddValueForOptions();
		FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
		ft.remove(PlanningList.this);
        ft.add(R.id.content_frame, addValueForOptions, Utils.ADDNEWPLANNINGTAG);
        ft.addToBackStack("planninglist");
        ft.commit();
	}
	
	private void editModeToggle() {
		editMode = !editMode;
		getActivity().invalidateOptionsMenu();
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
		Fragment chooseFilter = new ChooseFilter(PlanningList.this);
		FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
		ft.remove(PlanningList.this);
        ft.add(R.id.content_frame, chooseFilter);
        ft.addToBackStack(null);
        ft.commit();
	}
	
	@Override
	public void onFilterSelectedListener(String name, Calendar std, Calendar ed, boolean onOff) {
		return;
	}
}
