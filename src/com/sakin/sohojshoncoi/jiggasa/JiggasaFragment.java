package com.sakin.sohojshoncoi.jiggasa;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;

public class JiggasaFragment extends Fragment {

	private WebView webView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.jiggasa, container, false);

		webView = (WebView) view.findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new Callback());
		
		String url = "https://www.facebook.com/boka.chele.58/posts/106746439407340";
		String customHtml = "<html><head></head><body><div id='fb-root'></div><script>(function(d, s, id) {var js, fjs = d.getElementsByTagName(s)[0];if (d.getElementById(id)) return;js = d.createElement(s); js.id = id;js.src = '//connect.facebook.net/en_US/all.js#xfbml=1&appId=296812620394179';fjs.parentNode.insertBefore(js, fjs);}(document, 'script', 'facebook-jssdk'));</script><div class='fb-comments' data-href='" + url + "' data-numposts='5' data-colorscheme='dark' order_by='reverse_time' ></div></body></html>";
		webView.loadDataWithBaseURL("http://www.example.com", customHtml, "text/html", null, null);
		
		Utils.setActionBarTitle(getActivity(), "জিজ্ঞাসা");
	    return view;
	}

	public void setText(View view, String item) {
		TextView tv = (TextView) view.findViewById(R.id.bazardorText);
		tv.setText(item);
	}
	
	private class Callback extends WebViewClient{  //HERE IS THE MAIN CHANGE. 

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return (false);
        }
        
        @Override
        public void onPageFinished(WebView view, String url) 
        {       
            // Obvious next step is: document.forms[0].submit()
//            view.loadUrl("javascript:(function(d, s, id) {var js, fjs = d.getElementsByTagName(s)[0];if (d.getElementById(id)) return;js = d.createElement(s); js.id = id;js.src = '//connect.facebook.net/en_US/all.js#xfbml=1&appId=296812620394179';fjs.parentNode.insertBefore(js, fjs);}(document, 'script', 'facebook-jssdk'));");       
        }

    }
	
}
