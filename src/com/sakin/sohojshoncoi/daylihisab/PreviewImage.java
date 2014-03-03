package com.sakin.sohojshoncoi.daylihisab;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.custom.TouchImageView;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class PreviewImage extends Fragment {

	View view = null;
	String imageUrl;
	public PreviewImage() {
		imageUrl = "";
	}
	
	public PreviewImage(String url) {
		imageUrl = url;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		if(view == null){
			view = inflater.inflate(R.layout.preview_image, container, false);

			TouchImageView imageView = (TouchImageView) view.findViewById(R.id.imageView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(imageUrl));
		} else {
			Utils.print(" view already created ");
			ViewGroup parent = (ViewGroup) view.getParent();
			parent.removeView(view);
		}
		Utils.setActionBarTitle(getActivity(), "mnR mÂq");
		return view;
	}
	
	
}
