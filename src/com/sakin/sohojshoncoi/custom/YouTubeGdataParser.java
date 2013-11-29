package com.sakin.sohojshoncoi.custom;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.sakin.sohojshoncoi.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class YouTubeGdataParser {
	private static final String ns = null;

    public List<VideoElement> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }
    
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
            case XmlPullParser.END_TAG:
                    depth--;
                    break;
            case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
    
    private List<VideoElement> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<VideoElement> entries = new ArrayList<VideoElement>();

        parser.require(XmlPullParser.START_TAG, ns, "feed");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("entry")) {
                entries.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }
    
    private VideoElement readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "entry");
        VideoElement ve = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("media:group")) {
                ve = readMediaGroup(parser);
            } else {
                skip(parser);
            }
        }
        return ve;
    }
    
    private VideoElement readMediaGroup(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "media:group");
        String thumbnail = "";
        String title = "";
        String url = "";
        String desc = "";
        int duration = 0;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
//            Utils.print("found media:group");
            String name = parser.getName();
            if (name.equals("media:thumbnail")) {
            	thumbnail = readMediaThumbnail(parser);
            } else if(name.equals("media:title")) {
            	title = readMediaTitle(parser);
            } else if(name.equals("media:player")) {
            	url = readMediaPlayer(parser);
            } else if(name.equals("yt:duration")) {
            	duration = readYTDuration(parser);
            } else if(name.equals("media:description")) {
            	desc = readMediaDescription(parser);
            } else {
                skip(parser);
            }
        }
        return new VideoElement(url, thumbnail, title, desc, duration);
    }
    
    private String readMediaTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "media:title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "media:title");
//        Utils.print(title);
        return title;
    }
    
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }
    
    private String readMediaDescription(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "media:description");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "media:description");
        return title;
    }
    
    private int readYTDuration(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "yt:duration");
        String ytd = parser.getAttributeValue(null, "seconds");
        parser.nextTag();
        parser.require(XmlPullParser.END_TAG, ns, "yt:duration");
        return Integer.parseInt(ytd);
    }
    
    private String readMediaPlayer(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "media:player");
        String url = parser.getAttributeValue(null, "url");
        parser.nextTag();
        parser.require(XmlPullParser.END_TAG, ns, "media:player");
        return url.substring(0, url.indexOf('&'));
    }
    
//    private String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
//        String link = "";
//        parser.require(XmlPullParser.START_TAG, ns, "link");
//        String tag = parser.getName();
//        String relType = parser.getAttributeValue(null, "rel");
//        if (tag.equals("link")) {
//            if (relType.equals("alternate")) {
//                link = parser.getAttributeValue(null, "href");
//                parser.nextTag();
//            }
//        }
//        parser.require(XmlPullParser.END_TAG, ns, "link");
//        return link;
//    }
    
    private String readMediaThumbnail(XmlPullParser parser) throws IOException, XmlPullParserException {
    	String ret = "";
        parser.require(XmlPullParser.START_TAG, ns, "media:thumbnail");
        String url = parser.getAttributeValue(null, "url");
        String h = parser.getAttributeValue(null, "height");
        String w = parser.getAttributeValue(null, "width");
        String t = parser.getAttributeValue(null, "time");
        parser.nextTag();
        parser.require(XmlPullParser.END_TAG, ns, "media:thumbnail");
        if(h.equals("90"))
        	ret = url;
        return ret;
    }

}
