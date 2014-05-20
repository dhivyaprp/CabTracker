package com.intuit.cabtracker.activity.wsclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.animation.AnimatorSet.Builder;
import android.util.JsonReader;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.intuit.cabtracker.activity.MainActivity;

public class IntuitServerRestclient {
	
	String url="http://ec2-54-81-11-139.compute-1.amazonaws.com/trackmycab/rest/REST";
	
	public IntuitServerRestclient() {
		
	}
	
	public ArrayList<LatLng> getCabRoute(String cabNumber){
		
		HttpClient client = new DefaultHttpClient();
		
		String cabRouteurl =url+"/route/"+cabNumber;
		System.out.println(cabRouteurl);
		HttpGet request = new HttpGet(cabRouteurl);
		StringBuilder builder = new StringBuilder();
		HttpResponse response = null;
		try {
			 response = client.execute(request);
    		StatusLine statusLine = response.getStatusLine();
    		int statusCode = statusLine.getStatusCode();
    		if(statusCode == 200){
    			HttpEntity entity = response.getEntity();
    			InputStream content = entity.getContent();
    			BufferedReader reader = new BufferedReader(new InputStreamReader(content));
    			String line;
    			while((line = reader.readLine()) != null){
    				
					builder.append(line);
    			}
    			
    		} else {
    			Log.e(MainActivity.class.toString(),"Failed to get JSON object");
    		}
			
			System.out.println(builder);
		}
		 catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		
		ArrayList<LatLng> stopsList = new ArrayList<LatLng>();
		LatLng STOP = new LatLng(12.916906, 77.620889);
		LatLng STOP1 =new LatLng(12.916266, 77.634460);
		LatLng STOP2 = new LatLng(12.923395, 77.670682);
		LatLng STOP3= new LatLng(12.924242, 77.681266);
		stopsList.add(STOP);
		stopsList.add(STOP1);
		stopsList.add(STOP2);
		stopsList.add(STOP3);
		return stopsList;
		
	}
	
	public LatLng getCabLocation(String cabNumber){

		HttpClient client = new DefaultHttpClient();
		
		String cabRouteurl =url+"/cab/"+cabNumber;
		System.out.println(cabRouteurl);
		HttpGet request = new HttpGet(cabRouteurl);
		StringBuilder builder = new StringBuilder();
		HttpResponse response = null;
		try {
			 response = client.execute(request);
    		StatusLine statusLine = response.getStatusLine();
    		int statusCode = statusLine.getStatusCode();
    		if(statusCode == 200){
    			HttpEntity entity = response.getEntity();
    			InputStream content = entity.getContent();
    			BufferedReader reader = new BufferedReader(new InputStreamReader(content));
    			String line;
    			while((line = reader.readLine()) != null){
    				
					builder.append(line);
    			}
    		} else {
    			Log.e(MainActivity.class.toString(),"Failed to get JSON object");
    		}
			
			System.out.println(builder);
			//JsonReader reader = new JsonReader(new InputStreamReader(in));
		
	       // DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	        //Document doc = builder.parse(in);
	        
	       
		}
		 catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		LatLng cab = new LatLng(12.921034, 77.644116);
		
		return cab;
		
	}

			

}
