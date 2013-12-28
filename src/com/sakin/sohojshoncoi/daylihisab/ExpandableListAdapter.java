package com.sakin.sohojshoncoi.daylihisab;

import java.util.HashMap;
import java.util.List;
 



import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
 
public class ExpandableListAdapter extends BaseExpandableListAdapter {
 
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<ReportElement>> _listDataChild;
 
    public ExpandableListAdapter(Context context, List<String> listDataHeader,
            HashMap<String, List<ReportElement>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }
 
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }
 
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
 
    @Override
    public View getChildView(int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
 
        final ReportElement childElement = (ReportElement) getChild(groupPosition, childPosition);
 
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.report_list_item, null);
        }
 
        TextView totalLabel = (TextView) convertView.findViewById(R.id.itemTotalLabel);
        TextView amountLabel = (TextView) convertView.findViewById(R.id.itemAmountText);
        TextView planLabel = (TextView) convertView.findViewById(R.id.itemPlanText);
        ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.itemProgressBar);

        totalLabel.setTypeface(Utils.banglaTypeFace);
        totalLabel.setText(childElement.categoryName);
        
        boolean a,b;
        a = false; b = false;
        amountLabel.setText(Double.toString(childElement.totalAmount));
        planLabel.setText(Double.toString(childElement.totalPlan));
        progressBar.setMax((int) childElement.totalAmount);
    	progressBar.setProgress((int) childElement.totalAmount);
    	
        if(Double.compare(childElement.totalAmount, 0.0) != 0) {
        	a = true;
        }
        if(Double.compare(childElement.totalPlan, 0.0) != 0) {
        	progressBar.setMax((int) childElement.totalPlan);
        	if(childElement.totalAmount > childElement.totalPlan) {
        		progressBar.setProgress((int) childElement.totalPlan);
        	}
        	b = true;
        }
        
        if(a == false && b == false) {
        	amountLabel.setText("No Data Available");
        	planLabel.setText("");
        	progressBar.setVisibility(ProgressBar.INVISIBLE);
        } else {
        	progressBar.setVisibility(ProgressBar.VISIBLE);
        }
        return convertView;
    }
 
    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }
 
    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.report_list_header, null);
        }
 
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(Utils.banglaTypeFace, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
 
        return convertView;
    }
 
    @Override
    public boolean hasStableIds() {
        return false;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}