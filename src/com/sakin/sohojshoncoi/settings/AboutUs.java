package com.sakin.sohojshoncoi.settings;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AboutUs extends Fragment {
	
	View rootView = null;
	private WebView webView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.about_us, container, false);
		
		webView = (WebView) rootView.findViewById(R.id.creditWebView);
//		webView.getSettings().setJavaScriptEnabled(true);
//		webView.setWebViewClient(new Callback());
		
//		String url = "https://www.facebook.com/boka.chele.58/posts/106746439407340";
		String customHtml = "<html><head><style type=\"text/css\">.heading{	font-size: medium;	text-decoration: none;	color: #7d7dfe;}.sub{	font-size: large; text-decoration: none; color: white;}</style></head><body bgcolor='#000016'><center><h1 style=\"color:white;\">Credits</h1></br><div style=\"color:red;font-size:25px;font-weight:bold\">SOHOJ SONCHOY</div><div class='sub'>This App is Created by</br> 'DU Dreamers'</br>for EATL Prothom Alo App Contest 2014</br>Version: 1.0</div></br><div class='heading'>Concept</div><div class='sub'>Amit Kumar Das</div></br><div class='heading'>Project Lead & Lead Programmer</div><div class='sub'>Sayef Azad Sakin</div></br><div class='heading'>Programmer</div><div class='sub'>Tamal Adhikary</div></br><div class='heading'>App Design</div><div class='sub'>Amit Kumar Das</br>Tamal Adhikary</br>Sayef Azad Sakin</br>Nazmus Sakib Miazi</div></br><div class='heading'>Bug fix and Testing </div><div class='sub'>Nazmus Sakib Miazi</br>Amit Kumar Das</div></br><div class='heading'>User Interface Design and Illustration</div><div class='sub'>Amit Kumar Das</br>Sayef Azad Sakin</div></br></center></body></html>";
		webView.loadDataWithBaseURL("http://www.example.com", customHtml, "text/html", null, null);
		
		Utils.setActionBarTitle(getActivity(), "mnR mÂq");
		
		return rootView;
	}
	
//	private class Callback extends WebViewClient{  //HERE IS THE MAIN CHANGE. 
//
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            return (false);
//        }
//        
//        @Override
//        public void onPageFinished(WebView view, String url) 
//        {       
//            // Obvious next step is: document.forms[0].submit()
////            view.loadUrl("javascript:(function(d, s, id) {var js, fjs = d.getElementsByTagName(s)[0];if (d.getElementById(id)) return;js = d.createElement(s); js.id = id;js.src = '//connect.facebook.net/en_US/all.js#xfbml=1&appId=296812620394179';fjs.parentNode.insertBefore(js, fjs);}(document, 'script', 'facebook-jssdk'));");       
//        }

//    }
}
