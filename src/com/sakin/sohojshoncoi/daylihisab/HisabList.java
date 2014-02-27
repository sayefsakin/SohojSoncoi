package com.sakin.sohojshoncoi.daylihisab;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.YouTubeFullScreen;
import com.sakin.sohojshoncoi.custom.VideoElement;
import com.sakin.sohojshoncoi.database.Category;
import com.sakin.sohojshoncoi.database.SSDAO;
import com.sakin.sohojshoncoi.database.Transaction;
import com.sakin.sohojshoncoi.custom.SwipeDismissListViewTouchListener;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

@SuppressLint("UseValueOf")
public class HisabList extends ListFragment 
						implements ChooseFilter.OnFilterSelectedListener {
	
	private HisabListAdapter adapter;
	private List<Transaction> hisabList;
	ListView listView;
	SwipeDismissListViewTouchListener touchListener;
	private boolean editMode = false, isRefresh = false;
	private Calendar startDate, endDate;
	private String categoryName;
	View view;
	
	public HisabList() { 
		editMode = false;
		startDate = null;
		endDate = null;
		categoryName = "Both";
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view == null){
			view = inflater.inflate(R.layout.hisab_list, container, false);
			
			try {
				hisabList = SSDAO.getSSdao().getTransaction();
			} catch (SQLException e) {
				Utils.print("getting transaction error: " + e.toString());
			}
			adapter = new HisabListAdapter(getActivity(), R.layout.hisab_list_item, hisabList);
			setListAdapter(adapter);
		} else {
			Utils.print(" view already created ");
			ViewGroup parent = (ViewGroup) view.getParent();
			parent.removeView(view);
			
			if(isRefresh){
				Utils.print("view refreshing....");
				hisabList.clear();
				try {
					hisabList.addAll(SSDAO.getSSdao().getTransaction());
					adapter.notifyDataSetChanged();
				} catch (SQLException e) {
					Utils.print(e.toString());
				}
			}
		}
		Utils.setActionBarTitle(getActivity(), "Av‡Mi wnmve");
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
                                	Transaction selectedTransaction = adapter.getItem(position);
                                	SSDAO.getSSdao().getTransactionDAO().delete(selectedTransaction);
                                	adapter.remove(selectedTransaction);
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
		  	Fragment addNewHisab = new AddNewHisab((Transaction)l.getItemAtPosition(position));
			FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
			ft.remove(HisabList.this);
		    ft.add(R.id.content_frame, addNewHisab, Utils.ADDNEWHISABTAG);
		    ft.addToBackStack("hisablist");
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
		inflater.inflate(R.menu.add_new_hisab_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		MenuItem editItem = menu.findItem(R.id.action_edit);
		if(editMode) {
			menu.findItem(R.id.action_add).setVisible(false);
			menu.findItem(R.id.action_filter).setVisible(false);
			
			editItem.setIcon(R.drawable.ic_action_discard);
		} else {
			menu.findItem(R.id.action_add).setVisible(true);
			menu.findItem(R.id.action_filter).setVisible(true);
			
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
		Fragment addNewHisab = new AddNewHisab();
		FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
		ft.remove(HisabList.this);
        ft.add(R.id.content_frame, addNewHisab, Utils.ADDNEWHISABTAG);
        ft.addToBackStack("hisablist");
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
		Fragment chooseFilter = new ChooseFilter(HisabList.this);
		FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
		ft.remove(HisabList.this);
        ft.add(R.id.content_frame, chooseFilter);
        ft.addToBackStack(null);
        ft.commit();
	}

	@Override
	public void onFilterSelectedListener(String name, Calendar std, Calendar ed, boolean onOff) {
		categoryName = name;
		startDate = std;
		endDate = ed;
		Utils.print("filter selected " + categoryName);
		try {
			hisabList.clear();
			if(onOff){
				if(categoryName.equals("Both")) {
					hisabList.addAll(SSDAO.getSSdao()
								.getTransactionBetweenDate(/*Utils.userAccount,*/ 
														startDate.getTime(), 
														endDate.getTime()));
				} else {
					if(categoryName.equals("সকল আয়")) {
						hisabList.addAll(SSDAO.getSSdao()
									.getTransactionOfCategoryBetweenDate(
												/*Utils.userAccount,*/
												0, 
												startDate.getTime(), endDate.getTime(),
												true));
					} else if(categoryName.equals("সকল ব্যয়")) {
						hisabList.addAll(SSDAO.getSSdao()
								.getTransactionOfCategoryBetweenDate(
											/*Utils.userAccount,*/
											1, 
											startDate.getTime(), endDate.getTime(),
											true));
					} else {
						Category cat = SSDAO.getSSdao().getCategoryFromName(categoryName);
						Utils.print(Integer.toString(cat.getCategoryID()));
						hisabList.addAll(SSDAO.getSSdao()
									.getTransactionOfCategoryBetweenDate(
												/*Utils.userAccount,*/
												cat.getCategoryID(), 
												startDate.getTime(), endDate.getTime(),
												false));
					}
				}
			} else {
				hisabList.addAll(SSDAO.getSSdao().getTransaction());
			}
			adapter.notifyDataSetChanged();
		} catch (SQLException e) {
			Utils.print(e.toString());
		}
	}
}
