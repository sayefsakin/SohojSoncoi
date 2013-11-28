package com.sakin.sohojshoncoi.custom;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.sofol.SofolVideosFragment;

public class XMLParser extends AsyncTask<String, Void, List<VideoElement>>{
	
	// Getting XML content by making HTTP request
	FragmentActivity ac;
	int id;
	public XMLParser(FragmentActivity ac, int id){
		this.ac = ac;
		this.id = id;
	}
	

	@Override
	protected List<VideoElement> doInBackground(String... urls) {
		if(urls[0].length()<2)return null;
		
		try {
			return loadXmlFromNetwork(urls[0]);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		try {
//			while(true){
//				TimeUnit.SECONDS.sleep(5);
//				publishProgress();
//				Utils.print("New data added");
//			}
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return null;
	}
	
	@Override
	protected void onProgressUpdate(Void... v) {

	}
	
	@Override
    protected void onCancelled() {
		
    }
	
	@Override
	protected void onPostExecute(List<VideoElement> result) {
		Utils.print("xml parser finished");
		
		String tagString = "android:switcher:" + Integer.toString(R.id.pager)+":"+Integer.toString(id);
		Utils.print(tagString);
		SofolVideosFragment fragment = (SofolVideosFragment) ac.getSupportFragmentManager()
																.findFragmentByTag(tagString);
		if(fragment != null) {
			if(fragment.getView() != null) {
				// no need to call if fragment's onDestroyView() 
				//	has since been called.
		        fragment.updateDisplayWithList(result);
			}
		}
		
		super.onPostExecute(result);
	}
	
	
	private List<VideoElement> loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
        InputStream stream = null;
        YouTubeGdataParser youTubeGdataParser = new YouTubeGdataParser();
        List<VideoElement> videoElementList = null;

        try {
            stream = downloadUrl(urlString);
            videoElementList = youTubeGdataParser.parse(stream);
        // Makes sure that the InputStream is closed after the app is
        // finished using it.
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        
        return videoElementList;
    }

    // Given a string representation of a URL, sets up a connection and gets
    // an input stream.
    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        InputStream stream = conn.getInputStream();
        return stream;
    }
	
}
