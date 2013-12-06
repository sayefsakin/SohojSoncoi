package com.sakin.sohojshoncoi.daylihisab;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.YouTubeFullScreen;
import com.sakin.sohojshoncoi.custom.VideoElement;
import com.sakin.sohojshoncoi.database.SSDAO;
import com.sakin.sohojshoncoi.database.Transaction;
import com.sakin.sohojshoncoi.custom.SwipeDismissListViewTouchListener;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

@SuppressLint("UseValueOf")
public class HisabList extends ListFragment {
	
	private HisabListAdapter adapter;
	private List<Transaction> hisabList;
	ListView listView;
	SwipeDismissListViewTouchListener touchListener;
	private boolean editMode = false;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	     // The last two arguments ensure LayoutParams are inflated properly.
		View rootView = inflater.inflate(R.layout.hisab_list, container, false);
		
		try {
			hisabList = SSDAO.getSSdao().getTransaction();
		} catch (SQLException e) {
			Utils.print("getting transaction error: " + e.toString());
		}
		adapter = new HisabListAdapter(getActivity(), R.layout.hisab_list_item, hisabList);
		setListAdapter(adapter);
		return rootView;
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
//        if(editMode){
//        	listView.setOnTouchListener(touchListener);
//        	listView.setOnScrollListener(touchListener.makeScrollListener());
//        }
        
		super.onViewCreated(view, savedInstanceState);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
	  	Utils.print("clicked");
	  	if(!editMode) {
		  	Fragment addNewHisab = new AddNewHisab((Transaction)l.getItemAtPosition(position));
			FragmentTransaction ft = getFragmentManager().beginTransaction();
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
			editItem.setIcon(R.drawable.ic_action_discard);
		} else {
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
		Fragment addNewHisab = new AddNewHisab();
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.remove(HisabList.this);
        ft.add(R.id.content_frame, addNewHisab, Utils.ADDNEWHISABTAG);
        ft.addToBackStack("hisablist");
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
