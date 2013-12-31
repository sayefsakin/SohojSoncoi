package com.sakin.sohojshoncoi.daylihisab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.custom.ImageAdapter;

@SuppressLint("ValidFragment")
public class CategoryFragment extends Fragment {
	OnCategorySelectedListener mCallback;

    // Container Activity must implement this interface
    public interface OnCategorySelectedListener {
        public void onCategorySelected(String cat);
    }
    
	View view;
	private Integer[] mThumbIdsBae = {
            R.drawable.khabar,
            R.drawable.poshak,
            R.drawable.basosthan,
            R.drawable.jogajog,
            R.drawable.shikkha,
            R.drawable.khela,
            R.drawable.binodon,
            R.drawable.gari,
            R.drawable.doctor,
            R.drawable.bill,
            R.drawable.biniyog,
            R.drawable.social,
            R.drawable.onnano
    };
	private Integer[] mThumbIdsAe = {
            R.drawable.khabar,
            R.drawable.poshak,
            R.drawable.basosthan,
            R.drawable.jogajog,
            R.drawable.shikkha
    };	
	private Boolean aeOrBae;
	public CategoryFragment(Fragment caller, Boolean aeOrBae){
		mCallback = (OnCategorySelectedListener) caller;
		this.aeOrBae = aeOrBae;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.category_view, container, false);
		Utils.setActionBarTitle(getActivity(), "ক্যাটাগরি");
		
		GridView gridview = (GridView) view.findViewById(R.id.gridview);
		Integer[] thumbs;
		final String[] title;
		if(aeOrBae) {
			title = getResources().getStringArray(R.array.category_title_bae);
			thumbs = mThumbIdsBae;
		} else {
			title = getResources().getStringArray(R.array.category_title_ae);
			thumbs = mThumbIdsAe;
		}
	    gridview.setAdapter(new ImageAdapter(getActivity(),
	    						title,
	    						thumbs));
	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        	mCallback.onCategorySelected(title[position]);
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.remove(CategoryFragment.this);
                ft.commit();
                getFragmentManager().popBackStack();
	        }
	    });
	    
	    return view;
	}
	
	public void setText(int id, String item) {
		TextView tv = (TextView) view.findViewById(id);
		tv.setTypeface(Utils.banglaTypeFace);
		tv.setText(item);
	}
}