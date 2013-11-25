package com.sakin.sohojshoncoi.custom;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sakin.sohojshoncoi.Utils;

public class XMLParser {

	// Getting XML content by making HTTP request
	public String getXmlFromUrl(String url) {
        String xml = null;
 
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
 
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            xml = EntityUtils.toString(httpEntity);
 
        } catch (UnsupportedEncodingException e) {
        	Utils.print(e.getMessage());
        } catch (ClientProtocolException e) {
        	Utils.print(e.getMessage());
        } catch (IOException e) {
        	Utils.print(e.getMessage());
        }
        return xml;
    }
	
	// Parsing XML content and getting DOM element of xml
	public Document getDomElement(String xml){
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
 
            DocumentBuilder db = dbf.newDocumentBuilder();
 
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is); 
 
        } catch (ParserConfigurationException e) {
        	Utils.print(e.getMessage());
            return null;
        } catch (SAXException e) {
        	Utils.print(e.getMessage());
            return null;
        } catch (IOException e) {
        	Utils.print(e.getMessage());
            return null;
        }
        return doc;
	}
	
	// Get each xml child element value by passing element node name
	public String getValue(Element item, String str) {      
	    NodeList n = item.getElementsByTagName(str);        
	    return this.getElementValue(n.item(0));
	}
	 
	public final String getElementValue( Node elem ) {
		Node child;
		if( elem != null){
		    if (elem.hasChildNodes()){
		        for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
		           if( child.getNodeType() == Node.TEXT_NODE  ){
		                return child.getNodeValue();
		            }
		        }
		    }
		}
		return "";
	} 
}
