package com.sakin.sohojshoncoi.sofol;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.custom.VideoFragmentAdapter;

public class SofolVideosFragment extends ListFragment {
	public static final String ARG_OBJECT = "object";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	     // The last two arguments ensure LayoutParams are inflated properly.
		View rootView = inflater.inflate(R.layout.sofol_videos_fragment, container, false);
	    return rootView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	    String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
	        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
	        "Linux", "OS/2" };
	    VideoFragmentAdapter adapter = new VideoFragmentAdapter(getActivity(),
	        R.layout.sofol_item, values, values, values, values);
	    setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
	  	// do something with the data
		String item = (String) getListAdapter().getItem(position);
		Utils.print(item);
	}
}