package com.sakin.sohojshoncoi.daylihisab;


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.daylihisab.AddValueForOptions.OptionList;

public class OptionAdapter extends ArrayAdapter<OptionList> {
	

	private Context mContext;
    private int id;
    List <OptionList> oplt;
    AddValueForOptions avfo = null;
    
	public OptionAdapter(Context context, int textViewResourceId, List<OptionList> optionlist, AddValueForOptions avf) 
    {
        super(context, textViewResourceId, optionlist);
        this.mContext = context;
        this.id = textViewResourceId;
        this.oplt=optionlist;
        avfo=avf;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
    	ViewHolder holder = null;
    	TextView title = null;
    	EditText et=null;
    	Button crossbtn;
        OptionList item = getItem(position);
        final int pos=position;


        View mView = v ;
        
        if(mView == null){
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = vi.inflate(id, parent, false);
            
            holder = new ViewHolder(mView);
            mView.setTag(holder);
        }
        
        holder = (ViewHolder) mView.getTag();
        
        
        crossbtn=(Button) mView.findViewById(R.id.crossbutton);
        crossbtn.setBackgroundResource(R.drawable.ic_action_cancel);
        crossbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Utils.print("cross button print hoise");
				 avfo.deleteRow(pos);
			}
		});
        
        title = holder.getTitle();
        title.setTypeface(Utils.banglaTypeFace);
        title.setText(item.category);
        
        et = holder.getText();
        et.setText(Double.toString(item.amount));
                
        return mView;
    }
    
    private class ViewHolder {      
        private View mRow;
        private TextView title = null;
        private EditText et=null;
        private Button btn=null;
        
        public ViewHolder(View row) {
        	mRow = row;
        }
        
        public TextView getTitle() {
            if(null == title){
            	title = (TextView) mRow.findViewById(R.id.OptionLabel);
            }
            return title;
        }
        
        public EditText getText() {
            if(null == et){
            	et = (EditText) mRow.findViewById(R.id.OptionEditText);
            }
            return et;
        }
        
    }
}