package com.sakin.sohojshoncoi.daylihisab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.custom.ImageAdapter;

public class CategoryFragment extends Fragment {
	
	View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.category_view, container, false);

		GridView gridview = (GridView) view.findViewById(R.id.gridview);
	    gridview.setAdapter(new ImageAdapter(getActivity()));

	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
	        }
	    });
	    
	    return view;
	}

	public void setText(int id, String item) {
		TextView tv = (TextView) view.findViewById(id);
		tv.setTypeface(Utils.banglaTypeFace);
		tv.setText(item);
	}
}