package com.sakin.sohojshoncoi;


import com.sakin.sohojshoncoi.database.SSDAO;
import com.sakin.sohojshoncoi.daylihisab.DayliHisabMain;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class Main extends Activity {

	private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
//		testing();//all testing code goes here
//		Button dayliHisabB = (Button)findViewById(R.id.dayliHisabB);
//		dayliHisabB.setOnClickListener(new OnClickListener() {
//			
//			public void onClick(View arg0) {
				Intent loginIntent = new Intent(Main.this, DayliHisabMain.class);
		        startActivity(loginIntent);
//			}
//		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void init(){
		// all initialization code goes here
		SSDAO.getSSdao().init(this);
		Utils.createCustomCategory();
		
		//include bangla font
		Utils.banglaTypeFace = Typeface.createFromAsset(getAssets(), getString(R.string.font_solaimanlipi));
//		try {
//			SSDAO.getSSdao().deleteAccountByName("User");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		Utils.createSingleAccount();
	}

	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    SSDAO.getSSdao().close();
	}
	
	private void testing() {
//		TextView tv = (TextView) findViewById(R.id.textView1);
//		tv.setTypeface(Utils.banglaTypeFace);
//		tv.setText("hello");
//		
//		EditText ed = (EditText) findViewById(R.id.editText1);
//		ed.setTypeface(Utils.banglaTypeFace);
//		ed.setText("আমি বাংলায় গান গাই");
//		try {
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(new Date());
//			cal.add(Calendar.MONTH, -3);
//			String ss = SSDAO.getSSdao().getTransactionSumBetweenDate(cal.getTime(), new Date());
//			if(ss == null)
//				Utils.print("nothing found");
//			else 
//				Utils.print(ss);
//			List<Transaction> tr = SSDAO.getSSdao().getTransactionBetweenDate(cal.getTime(), new Date());
//			Utils.print(Integer.toString(tr.size()));
//			Category cat = SSDAO.getSSdao().getCategoryDAO().queryForId(4);
//			List<Transaction> tr = SSDAO.getSSdao().getTransactionOfCategoryBetweenDate(cat,cal.getTime(), new Date());
//			String ss = SSDAO.getSSdao().getTransactionSumOfCategoryBetweenDate(cat,cal.getTime(), new Date());
//			if(ss == null)
//				Utils.print("nothing found");
//			else 
//				Utils.print(ss);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		mPlanetTitles = getResources().getStringArray(R.array.drawermenulist);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
	}

	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	    	Utils.print("position clicked" + position);
	    }
	}
}
