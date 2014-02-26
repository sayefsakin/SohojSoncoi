package com.sakin.sohojshoncoi.custom;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private String[] mThumbTitle;
    private Integer[] mThumbIds;
    public ImageAdapter(Context c, String[] title, Integer[] ids) {
        mContext = c;
        mThumbIds = ids;
        mThumbTitle = title;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
    	LayoutInflater inflater = (LayoutInflater) mContext
    			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
        	gridView = new View(mContext);
        	gridView = inflater.inflate(R.layout.image_adapter_item, null);
        	TextView title = (TextView) gridView.findViewById(R.id.grid_item_label);
        	if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
        		title.setTypeface(Utils.banglaTypeFace);
        	}
        	title.setText(mThumbTitle[position]);
        	
            ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_item_image);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(mThumbIds[position]);
        } else {
			gridView = (View) convertView;
		}

        return gridView;
    }
}