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
	private Integer[] mThumbIds = {
            R.drawable.khabar,
            R.drawable.poshak,
            R.drawable.basosthan,
            R.drawable.jogajog,
            R.drawable.shikkha,
            R.drawable.khela,
            R.drawable.binodon,
            R.drawable.mobile,
            R.drawable.gari,
            R.drawable.doctor,
            R.drawable.social,
            R.drawable.biniyog,
            R.drawable.onnano
    };
	private String[] title;
	
	public CategoryFragment(Fragment caller){
		mCallback = (OnCategorySelectedListener) caller;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.category_view, container, false);
		
		GridView gridview = (GridView) view.findViewById(R.id.gridview);
		title = getResources().getStringArray(R.array.category_title);
	    gridview.setAdapter(new ImageAdapter(getActivity(),
	    						title,
	    						mThumbIds));
	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//	        	Bundle args = new Bundle();
//	        	args.putString("selected_category", title[position]);
	        	
//	        	addNewHisab.setArguments(args);
	        	mCallback.onCategorySelected(title[position]);
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				
//				Fragment addNewHisabOld = getFragmentManager().findFragmentByTag(Utils.ADDNEWHISABTAG);
//				ft.remove(addNewHisabOld);
				
//				Fragment addNewHisab = new AddNewHisab();
//                ft.add(R.id.content_frame, addNewHisab, Utils.ADDNEWHISABTAG);
				ft.hide(CategoryFragment.this);
                ft.commit();
                getFragmentManager().popBackStack();
//	            Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
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