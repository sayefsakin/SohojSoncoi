package com.sakin.sohojshoncoi.daylihisab;


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

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
    	Button crossbtn;
        final OptionList item = getItem(position);
        final int pos=position;


        View mView = v ;
        
        if(mView == null){
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = vi.inflate(id, parent, false);
            
            holder = new ViewHolder(mView);
            mView.setTag(holder);
        }
        
        holder = (ViewHolder) mView.getTag();
        
        final EditText et = holder.getText();
        
        crossbtn=(Button) mView.findViewById(R.id.crossbutton);
        crossbtn.setBackgroundResource(R.drawable.ic_action_cancel);
        crossbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 avfo.deleteRow(pos);
			}
		});	
        
        title = holder.getTitle();
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
        	title.setTypeface(Utils.banglaTypeFace);
        }
        title.setText(item.category);
        
        et.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(et.getText().toString().length() > 0){
					if(Double.compare(Double.parseDouble(et.getText().toString()),0.0) == 0) {
						et.setText("");
					}
				}
				if(!hasFocus) {
					item.setAmount(
							et.getText().toString().length() > 0?
							Double.parseDouble(et.getText().toString()):0.0);
				}
			}
		});
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