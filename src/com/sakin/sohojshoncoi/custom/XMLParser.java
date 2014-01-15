package com.sakin.sohojshoncoi.custom;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
//		int pagerID = R.id.pager;
//		if(id>=5)
//			pagerID = R.id.uporiae_pager;
//		String tagString = "android:switcher:" + Integer.toString(pagerID)+":"+Integer.toString(id%5);
//		Utils.print(tagString);
//		Fragment fragment = ac.getSupportFragmentManager().findFragmentByTag(tagString);
//		if(fragment != null) {
//			if(fragment.getView() != null) {
//				// no need to call if fragment's onDestroyView() 
//				//	has since been called.
//				Utils.print("fragment found");
//				((SofolVideosFragment) fragment).updateDisplayWithList(result);
//			}
//		}
		if((Utils.SELECTED_ITEM == 1 && id < 5) || (Utils.SELECTED_ITEM == 3 && id >=5)) {
			ViewPager pager = (ViewPager) ac.findViewById((id<5)?R.id.pager:R.id.uporiae_pager);
			Fragment fragment = (Fragment) pager.getAdapter().instantiateItem(pager, id%5);
			if(fragment != null) {
				if(fragment.getView() != null) {
					Utils.print("fragment found for pager id: " + id);
					((SofolVideosFragment) fragment).updateDisplayWithList(result);
				}
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
