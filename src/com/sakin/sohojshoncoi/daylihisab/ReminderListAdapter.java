package com.sakin.sohojshoncoi.daylihisab;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import com.sakin.sohojshoncoi.database.Category;
import com.sakin.sohojshoncoi.database.Reminder;
import com.sakin.sohojshoncoi.database.SSDAO;

public class ReminderListAdapter extends ArrayAdapter<Reminder>{
	
	private Context mContext;
    private int id;

	public ReminderListAdapter(Context context, int textViewResourceId, List<Reminder> video) 
    {
        super(context, textViewResourceId, video);
        this.mContext = context;
        this.id = textViewResourceId;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
    	ViewHolder holder = null;
    	
    	Reminder reminder = getItem(position);
        
        View mView = v ;
        if(mView == null){
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = vi.inflate(id, parent, false);
            
            holder = new ViewHolder(mView);
            
            holder.descriptionView = holder.getDescriptionView();
            holder.descriptionView.setTextSize(16);
            
            holder.amountView = holder.getAmountView();
            holder.amountView.setTextSize(20);
            
            holder.dueDateView = holder.getDueDateView();
            holder.dueDateView.setTextSize(12);
            
            holder.dateView = holder.getDateView();
            holder.dateView.setTextSize(12);

            holder.repeatedImageView = holder.getRepeatedImageView();
            holder.alarmImageView = holder.getAlarmImageView();
            
            if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            	holder.dateView.setTypeface(Utils.banglaTypeFace);
            	holder.dueDateView.setTypeface(Utils.banglaTypeFace);
            	holder.amountView.setTypeface(Utils.banglaTypeFace);
            	holder.descriptionView.setTypeface(Utils.banglaTypeFace);
            }
            mView.setTag(holder);
        }

        holder = (ViewHolder) mView.getTag();
        holder.descriptionView.setText(reminder.getDescription());
        holder.amountView.setText(Double.toString(reminder.getAmount()));
        
        String st = new SimpleDateFormat("d-MMM-yyyy").format(reminder.getDueDate());
        holder.dateView.setText(st);

        holder.dueDateView.setText(getDateDifference(new Date(), reminder.getDueDate()));
        
        if(reminder.getRepeated().toString().equals("NONE")) {
        	holder.repeatedImageView.setVisibility(ImageView.INVISIBLE);
        } else {
        	holder.repeatedImageView.setImageResource(R.drawable.ic_action_refresh);
        }
        
        if(reminder.getStatus().toString().equals("ALARM")) {
        	holder.alarmImageView.setImageResource(R.drawable.ic_action_alarms);
        } else if(reminder.getStatus().toString().equals("PAID")) {
        	holder.alarmImageView.setImageResource(R.drawable.ic_action_accept);
        } else {
        	holder.alarmImageView.setVisibility(ImageView.INVISIBLE);
        }
    	return mView;
    }
    
    
    String getDateDifference(Date st, Date end) {
    	String ret = "Due in ";
    	String last = "";
    	if(end.before(st)) {
    		ret = "Time already passed by ";
    		//swap st, end
    		Date temp = st;
    		st = end;
    		end = temp;
    	}
    	long endTimeinMilis = end.getTime();
    	long stTimeinMilis = st.getTime();
    	long diff = endTimeinMilis - stTimeinMilis;
    	
    	long MILISEC = 1;
    	long SEC = 1000 * MILISEC;
    	long MIN = 60 * SEC;
    	long HOUR = 60 * MIN;
    	long DAY = 24 * HOUR;
    	long MONTH = 30 * DAY;
    	long YEAR = 12 * MONTH;
    	if(diff >= YEAR ) {
    		diff /= YEAR;
    		last = "year";
    	} else if(diff >= MONTH) {
    		diff /= MONTH;
    		last = "month";
    	} else if(diff >= DAY) {
    		diff /= DAY;
    		last = "day";
    	} else if(diff >= HOUR) {
    		diff /= HOUR;
    		last = "hour";
    	} else if(diff >= MIN) {
    		diff /= MIN;
    		last = "minute";
    	} else {
    		diff = -1;
    		last = "seconds";
    	}
    	
    	if(diff == -1) {
    		ret += "few";
    	} else {
    		ret += Long.toString(diff);
    		if(diff > 1){
    			last += "s";
    		}
    	}
    	ret = ret + " " + last;
    	return ret;
    }
    
    
    private static class ViewHolder {      
        private View mRow;
        private TextView descriptionView = null,
        				amountView = null,
        				dueDateView = null,
        				dateView = null;
        ImageView repeatedImageView = null,
        		alarmImageView = null;
        
        public ViewHolder(View row) {
        	mRow = row;
        }
        public TextView getDescriptionView() {
            if(null == descriptionView){
            	descriptionView = (TextView) mRow.findViewById(R.id.reminder_description);
            }
            return descriptionView;
        }
        public TextView getAmountView() {
            if(null == amountView){
            	amountView = (TextView) mRow.findViewById(R.id.amount_text);
            }
            return amountView;
        }
        public TextView getDueDateView(){
        	if(null == dueDateView){
        		dueDateView = (TextView) mRow.findViewById(R.id.due_text);
            }
            return dueDateView;
        }
        public TextView getDateView(){
        	if(null == dateView){
        		dateView = (TextView) mRow.findViewById(R.id.reminder_date);
            }
            return dateView;
        }
        public ImageView getRepeatedImageView(){
        	if(null == repeatedImageView){
        		repeatedImageView = (ImageView) mRow.findViewById(R.id.repeatedImage);
            }
            return repeatedImageView;
        }
        public ImageView getAlarmImageView(){
        	if(null == alarmImageView){
        		alarmImageView = (ImageView) mRow.findViewById(R.id.alarmImage);
            }
            return alarmImageView;
        }
    }
}