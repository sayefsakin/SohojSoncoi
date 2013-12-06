package com.sakin.sohojshoncoi.daylihisab;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.database.Category;
import com.sakin.sohojshoncoi.database.SSDAO;
import com.sakin.sohojshoncoi.database.Transaction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HisabListAdapter extends ArrayAdapter<Transaction>{
	
	private Context mContext;
    private int id;

	public HisabListAdapter(Context context, int textViewResourceId, List<Transaction> video) 
    {
        super(context, textViewResourceId, video);
        this.mContext = context;
        this.id = textViewResourceId;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
    	ViewHolder holder = null;
    	
        Transaction transaction = getItem(position);
        
        View mView = v ;
        if(mView == null){
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = vi.inflate(id, parent, false);
            
            holder = new ViewHolder(mView);
            
            holder.descriptionView = holder.getDescriptionView();
            holder.descriptionView.setTypeface(Utils.banglaTypeFace);
            holder.descriptionView.setTextSize(16);
            
            holder.amountView = holder.getAmountView();
            holder.amountView.setTypeface(Utils.banglaTypeFace);
            holder.amountView.setTextSize(20);
            
            holder.categoryView = holder.getCategoryView();
            holder.categoryView.setTypeface(Utils.banglaTypeFace);
            holder.categoryView.setTextSize(12);

            holder.categoryTypeImage = holder.getCategoryTypeImage();
            
            holder.dateView = holder.getDateView();
            holder.dateView.setTypeface(Utils.banglaTypeFace);
            holder.dateView.setTextSize(12);
            
            mView.setTag(holder);
        }

        holder = (ViewHolder) mView.getTag();
        holder.descriptionView.setText(transaction.getDescription());
        holder.amountView.setText(Double.toString(transaction.getAmount()));
        
		try {
			Category cat = SSDAO.getSSdao().getCategoryFromID(transaction.getCategory().getCategoryID());
	        
			holder.categoryView.setText(cat.getName());
	        
	        if(cat.getType().toString().equals("INCOME")){
	        	holder.categoryTypeImage.setImageResource(R.drawable.ic_action_expand);
	        } else {
	        	holder.categoryTypeImage.setImageResource(R.drawable.ic_action_collapse);
	        }
		} catch (SQLException e) {
			Utils.print(e.toString());
		}
        
        String st = new SimpleDateFormat("EEE, d MM yyyy").format(transaction.getDate());
        holder.dateView.setText(st);
        
    	return mView;
    }
    
    
    private static class ViewHolder {      
        private View mRow;
        private TextView descriptionView = null,
        				amountView = null,
        				categoryView = null,
        				dateView = null;
        ImageView categoryTypeImage = null;
        
        public ViewHolder(View row) {
        	mRow = row;
        }
        
        public TextView getDescriptionView() {
            if(null == descriptionView){
            	descriptionView = (TextView) mRow.findViewById(R.id.item_description);
            }
            return descriptionView;
        }
        
        public TextView getAmountView() {
            if(null == amountView){
            	amountView = (TextView) mRow.findViewById(R.id.item_amount);
            }
            return amountView;
        }
        
        public TextView getCategoryView(){
        	if(null == categoryView){
        		categoryView = (TextView) mRow.findViewById(R.id.item_category);
            }
            return categoryView;
        }
        
        public TextView getDateView(){
        	if(null == dateView){
        		dateView = (TextView) mRow.findViewById(R.id.item_date);
            }
            return dateView;
        }
        
        public ImageView getCategoryTypeImage(){
        	if(null == categoryTypeImage){
        		categoryTypeImage = (ImageView) mRow.findViewById(R.id.item_type);
            }
            return categoryTypeImage;
        }
    }
}
