package com.sakin.sohojshoncoi.custom;

import java.util.List;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.R.color;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

@SuppressWarnings("rawtypes")
public class DrawerListAdapter extends ArrayAdapter {

	private Context mContext;
    private int id;
    private List <String>items ;

    @SuppressWarnings("unchecked")
	public DrawerListAdapter(Context context, int textViewResourceId , List<String> list ) 
    {
        super(context, textViewResourceId, list);
        mContext = context;
        id = textViewResourceId;
        items = list ;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent)
    {
        View mView = v ;
        if(mView == null){
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = vi.inflate(id, null);
        }

        TextView text = (TextView) mView.findViewById(R.id.drawer_item_text);

        if(items.get(position) != null )
        {
//            text.setTextColor(Color.WHITE);
//            text.setBackgroundColor(color.custom_color);
        	if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
        		text.setTypeface(Utils.banglaTypeFace);
        	} else {
        		text.setTypeface(Utils.banglaTypeFaceSutonny);
        	}
            
            text.setText(items.get(position));
        }

        return mView;
    }
}
