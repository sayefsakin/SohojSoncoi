package com.sakin.sohojshoncoi;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.sakin.sohojshoncoi.database.Account;
import com.sakin.sohojshoncoi.database.Category;
import com.sakin.sohojshoncoi.database.DatabaseHelper;
import com.sakin.sohojshoncoi.database.Media;
import com.sakin.sohojshoncoi.database.MediaCategory;
import com.sakin.sohojshoncoi.database.Reminder;
import com.sakin.sohojshoncoi.database.SSDAO;
import com.sakin.sohojshoncoi.database.Transaction;
import com.sakin.sohojshoncoi.daylihisab.DayliHisabMain;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class Main extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();

		Account account = new Account("Sakin",0.0,0.0);
		SSDAO.getSSdao().getAccountDAO().create(account);
		
		WebView webView = (WebView)findViewById(R.id.test1);
		WebSettings settings = webView.getSettings();
		settings.setDefaultTextEncodingName("utf-16");
		String htmlString = "আমার বাংলা";
//		webView.loadDataWithBaseURL(null, htmlString, "text/html", "utf-16", null);
		webView.loadData(htmlString, "text/html", "utf-16");
		
		
		TextView tv = (TextView)findViewById(R.id.textView1);
		Button dayliHisabB = (Button)findViewById(R.id.dayliHisabB);
//		dayliHisabB.setText(R.string.bangla);
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
