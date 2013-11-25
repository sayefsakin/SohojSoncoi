package com.sakin.sohojshoncoi.custom;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;

public class VideoFragmentAdapter extends ArrayAdapter<String> {

	private Context mContext;
    private int id;
    private String[] videoUrl ;
    private String[] videoThumbnail;
    private String[] videoTitle;
    private String[] videoDuration;

	public VideoFragmentAdapter(Context context, int textViewResourceId, String[] url, String[] thumbnail,
			String[] title, String[] duration  ) 
    {
        super(context, textViewResourceId, title);
        mContext = context;
        id = textViewResourceId;
        videoUrl = url;
        videoThumbnail = thumbnail;
        videoTitle = title;
        videoDuration = duration;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent)
    {
        View mView = v ;
        if(mView == null){
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = vi.inflate(id, parent, false);
        }

        TextView title = (TextView) mView.findViewById(R.id.videoTitle);
        TextView duration = (TextView) mView.findViewById(R.id.videoDuration);
        ImageView thumb = (ImageView) mView.findViewById(R.id.videoThumbnail);
        
        title.setText(videoTitle[position]);
        duration.setText(videoDuration[position]);
        thumb.setImageResource(R.drawable.notun_catagory_jog);

        return mView;
    }
}