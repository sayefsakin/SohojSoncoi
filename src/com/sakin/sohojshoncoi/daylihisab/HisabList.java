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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class HisabList extends ListFragment {
	
	private HisabListAdapter adapter;
	private List<Transaction> hisabList;
	
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
	public void onListItemClick(ListView l, View v, int position, long id) {
	  	Utils.print("clicked");
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

}
