package com.sakin.sohojshoncoi.custom;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;

public class VideoFragmentAdapter extends ArrayAdapter<VideoElement> {

	private Context mContext;
    private int id;

	public VideoFragmentAdapter(Context context, int textViewResourceId, List<VideoElement> video) 
    {
        super(context, textViewResourceId, video);
        this.mContext = context;
        this.id = textViewResourceId;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
    	ViewHolder holder = null;
    	TextView title = null, duration = null;
        ImageView thumbnail = null; 
        VideoElement video = getItem(position);
        
        View mView = v ;
        if(mView == null){
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = vi.inflate(id, parent, false);
            
            holder = new ViewHolder(mView);
            mView.setTag(holder);
        }

        holder = (ViewHolder) mView.getTag();
        
        title = holder.getTitle();
        title.setTypeface(Utils.banglaTypeFace);
        title.setText(video.getVideoTitle());
        
        duration = holder.getDuration();
        duration.setTypeface(Utils.banglaTypeFace);
        int d = video.getVideoDuration();
        String time = "";
        int h = 0, m = 0, s = d;
        if(s>=60){
        	m = s / 60;
        	if(m>=60){
        		h = m / 60;
        		time += Integer.toString(h) + " ঘন্টা ";
        	}
        	m = m % 60;
        	time += Integer.toString(m) + " মিনিট ";
        }
        s = d % 60;
        time += Integer.toString(s) + " সেকেন্ড";
        duration.setText(time);
        
//        thumbnail = holder.getThumb();
//        thumbnail.setImageResource(R.drawable.jogajog);
//        
        return mView;
    }
    
    private class ViewHolder {      
        private View mRow;
        private TextView title = null, duration = null;
        private ImageView thumb = null;
        
        public ViewHolder(View row) {
        	mRow = row;
        }
        
        public TextView getTitle() {
            if(null == title){
            	title = (TextView) mRow.findViewById(R.id.videoTitle);
            }
            return title;
        }
        
        public TextView getDuration() {
            if(null == duration){
            	duration = (TextView) mRow.findViewById(R.id.videoDuration);
            }
            return duration;
        }
        
        public ImageView getThumb(){
        	if(null == thumb){
        		thumb = (ImageView) mRow.findViewById(R.id.videoThumbnail);
            }
            return thumb;
        }
    }
}