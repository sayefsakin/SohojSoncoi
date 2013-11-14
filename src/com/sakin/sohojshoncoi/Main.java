package com.sakin.sohojshoncoi;

import com.sakin.sohojshoncoi.database.SSDAO;
import com.sakin.sohojshoncoi.daylihisab.DayliHisabMain;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
		Button dayliHisabB = (Button)findViewById(R.id.dayliHisabB);
		dayliHisabB.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				Intent loginIntent = new Intent(Main.this, DayliHisabMain.class);
		        startActivity(loginIntent);
			}
		});
		
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
	}

	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    SSDAO.getSSdao().close();
	}

}
