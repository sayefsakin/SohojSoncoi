package com.sakin.sohojshoncoi.sofol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.custom.VideoElement;
import com.sakin.sohojshoncoi.custom.VideoFragmentAdapter;
import com.sakin.sohojshoncoi.custom.XMLParser;

public class SofolVideosFragment extends ListFragment {
	public static final String ARG_OBJECT = "object";
	String playlistUrl = "http://gdata.youtube.com/feeds/api/playlists/PL55713C70BA91BD6E";
	XMLParser xmlParser;
	String[] list_items;
	private VideoFragmentAdapter adapter;
	private List<VideoElement> videoList;
	int id;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	     // The last two arguments ensure LayoutParams are inflated properly.
		View rootView = inflater.inflate(R.layout.sofol_videos_fragment, container, false);
//		list_items = getArguments().getStringArray("array_list");
//		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list_items));
		
		videoList = new ArrayList<VideoElement>();
		adapter = new VideoFragmentAdapter(getActivity(), R.layout.sofol_item, videoList);
		setListAdapter(adapter);
		
		id = getArguments().getInt(Utils.TAB_ID);
		playlistUrl = getArguments().getString(Utils.TAB_URL_ID);
		xmlParser = new XMLParser(getActivity(), id);
		xmlParser.execute(playlistUrl);
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
//		Utils.print("new fragment created");
	    
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
	  	// do something with the data
		String item = (String) getListAdapter().getItem(position);
		Utils.print(item);
	}
	
	public VideoFragmentAdapter getAdapter() {
		return adapter;
	}
	public void setAdapter(VideoFragmentAdapter adapter) {
		this.adapter = adapter;
	}
	public List<VideoElement> getVideoList() {
		return videoList;
	}
	public void setVideoList(List<VideoElement> videoList) {
		this.videoList = videoList;
	}
	
	public void updateDisplay(VideoElement videoElement){
		videoList.add(videoElement);
		adapter.notifyDataSetChanged();
	}
	
	public void updateDisplayWithList(List<VideoElement> videoElementList){
		if(videoElementList != null){
		Utils.print(Integer.toString(videoElementList.size()));
		for(int i=0;i<videoElementList.size();i++){
			videoList.add(videoElementList.get(i));
		}
		adapter.notifyDataSetChanged();
		}
	}
}