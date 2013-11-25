package com.sakin.sohojshoncoi.sofol;

import java.util.ArrayList;
import java.util.List;

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
import com.sakin.sohojshoncoi.custom.VideoElement;
import com.sakin.sohojshoncoi.custom.VideoFragmentAdapter;
import com.sakin.sohojshoncoi.custom.XMLParser;

public class SofolVideosFragment extends ListFragment {
	public static final String ARG_OBJECT = "object";
	String playlistUrl = "http://gdata.youtube.com/feeds/api/playlists/PL55713C70BA91BD6E";
	XMLParser xmlParser;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	     // The last two arguments ensure LayoutParams are inflated properly.
		View rootView = inflater.inflate(R.layout.sofol_videos_fragment, container, false);
		Utils.print("new fragment loaded");
	    return rootView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
//	    VideoElement ve = new VideoElement("hello","nai","Testing","Duration");
//	    Utils.videoList.add(ve);
//	    VideoFragmentAdapter adapter = new VideoFragmentAdapter(getActivity(),
//	        R.layout.sofol_item, Utils.videoList);
//	    setListAdapter(adapter);
//	    Utils.videoListAdapter = adapter;
		Utils.print("new fragment created");
	    XMLParser parser = new XMLParser(getActivity());
	    setListAdapter(parser.getAdapter());
	    parser.execute(new String[]{playlistUrl});
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
	  	// do something with the data
		String item = (String) getListAdapter().getItem(position);
		Utils.print(item);
	}
}