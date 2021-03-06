package com.sakin.sohojshoncoi.sofol;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.YouTubeFullScreen;
import com.sakin.sohojshoncoi.custom.VideoElement;
import com.sakin.sohojshoncoi.custom.VideoFragmentAdapter;
import com.sakin.sohojshoncoi.custom.XMLParser;

public class SofolVideosFragment extends ListFragment {
	String playlistUrl = "";
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
		Utils.print("sofol fragment created");
		
		videoList = new ArrayList<VideoElement>();
		adapter = new VideoFragmentAdapter(getActivity(), R.layout.sofol_item, videoList);
		setListAdapter(adapter);
		
		videoList.add(new VideoElement("", "", "Loading....", "", 0));
		adapter.notifyDataSetChanged();
		
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
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
	  	// do something with the data
		VideoElement item = (VideoElement) getListAdapter().getItem(position);
		if(item.getVideoTitle().equalsIgnoreCase("No video found")) {
			return;
		}
		if(item.getVideoTitle().equalsIgnoreCase("Loading....")) {
			return;
		}
		Intent youTubeFullScreen = new Intent(getActivity(), YouTubeFullScreen.class);
		youTubeFullScreen.putExtra(Utils.VIDEO_ELEMENT_ID, item);
		startActivity(youTubeFullScreen);
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
			videoList.clear();
		Utils.print(Integer.toString(videoElementList.size()));
		for(int i=0;i<videoElementList.size();i++){
			videoList.add(videoElementList.get(i));
		}
		if(videoElementList.size() == 0) {
			videoList.add(new VideoElement("", "", "No video found", "", 0));
		}
		adapter.notifyDataSetChanged();
		} else {
			videoList.clear();
			videoList.add(new VideoElement("", "", "No video found", "", 0));
			adapter.notifyDataSetChanged();
		}
	}
}