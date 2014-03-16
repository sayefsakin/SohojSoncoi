package com.sakin.sohojshoncoi.daylihisab;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.database.Planning;

public class PlanningListAdapter extends ArrayAdapter<Planning>{
	
	private Context mContext;
    private int id;

	public PlanningListAdapter(Context context, int textViewResourceId, List<Planning> video) {
        super(context, textViewResourceId, video);
        this.mContext = context;
        this.id = textViewResourceId;
    }
	
	@SuppressLint("SimpleDateFormat")
	@Override
    public View getView(int position, View v, ViewGroup parent) {
    	ViewHolder holder = null;
    	
        Planning planning = getItem(position);
        
        View mView = v ;
        if(mView == null){
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = vi.inflate(id, parent, false);
            
            holder = new ViewHolder(mView);
            
            holder.monthView = holder.getMonthView();
            holder.monthView.setTextSize(16);
            
            holder.aeLabelView = holder.getAeLabelView();
            holder.aeLabelView.setTypeface(Utils.banglaTypeFaceSutonny);
            holder.aeLabelView.setTextSize(12);
            holder.aeLabelView.setText("Avqt");
            
            holder.baeLabelView = holder.getBaeLabelView();
            holder.baeLabelView.setTypeface(Utils.banglaTypeFaceSutonny);
            holder.baeLabelView.setTextSize(12);
            holder.baeLabelView.setText("e¨qt");
            
            holder.aeAmountView = holder.getAeAmountView();
            holder.aeAmountView.setTextSize(16);
            
            holder.baeAmountView = holder.getBaeAmountView();
            holder.baeAmountView.setTextSize(16);
            
            if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            	holder.monthView.setTypeface(Utils.banglaTypeFace);
            	holder.aeAmountView.setTypeface(Utils.banglaTypeFace);
            	holder.baeAmountView.setTypeface(Utils.banglaTypeFace);
            }
            
            mView.setTag(holder);
        }

        holder = (ViewHolder) mView.getTag();
        
        int month = planning.getMonth();
        int year = planning.getYear();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        String monthYearText = new SimpleDateFormat("MMM-yyyy").format(cal.getTime());
        holder.monthView.setText(monthYearText);
        
		holder.aeAmountView.setText(Double.toString(planning.getAeAmount()));
		holder.baeAmountView.setText(Double.toString(planning.getBaeAmount()));
        
    	return mView;
    }
    
    
    private static class ViewHolder {      
        private View mRow;
        private TextView monthView = null,
        				aeLabelView = null,
        				aeAmountView = null,
						baeLabelView = null,
        				baeAmountView = null;
        
        public ViewHolder(View row) {
        	mRow = row;
        }
        
        public TextView getMonthView() {
            if(null == monthView){
            	monthView = (TextView) mRow.findViewById(R.id.plan_month);
            }
            return monthView;
        }

        public TextView getAeLabelView() {
            if(null == aeLabelView){
            	aeLabelView = (TextView) mRow.findViewById(R.id.ae_label);
            }
            return aeLabelView;
        }
        
        public TextView getAeAmountView() {
            if(null == aeAmountView){
            	aeAmountView = (TextView) mRow.findViewById(R.id.ae_amount);
            }
            return aeAmountView;
        }
        
        public TextView getBaeLabelView() {
            if(null == baeLabelView){
            	baeLabelView = (TextView) mRow.findViewById(R.id.bae_label);
            }
            return baeLabelView;
        }
        
        public TextView getBaeAmountView() {
            if(null == baeAmountView){
            	baeAmountView = (TextView) mRow.findViewById(R.id.bae_amount);
            }
            return baeAmountView;
        }
    }
}
