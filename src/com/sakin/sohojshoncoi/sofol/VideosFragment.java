package com.sakin.sohojshoncoi.sofol;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sakin.sohojshoncoi.R;

public class VideosFragment extends Fragment {
	 public static final String ARG_OBJECT = "object";

	 @Override
	 public View onCreateView(LayoutInflater inflater,
	         ViewGroup container, Bundle savedInstanceState) {
	     // The last two arguments ensure LayoutParams are inflated
	     // properly.
	     View rootView = inflater.inflate(
	             R.layout.fragment_collection_object, container, false);
	     Bundle args = getArguments();
	     TextView tv = (TextView) rootView.findViewById(R.id.fragmentText);
	     tv.setText(Integer.toString(args.getInt(ARG_OBJECT)));
	     return rootView;
	 }
}
