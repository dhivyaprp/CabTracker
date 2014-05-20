package com.intuit.cabtracker.asynctask;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;
import org.w3c.dom.Document;
import com.google.android.gms.maps.model.LatLng;
import com.intuit.cabtracker.activity.CabLocationActivity;
import com.intuit.cabtracker.activity.wsclient.GMapV2Direction;
import com.intuit.cabtracker.activity.wsclient.IntuitServerRestclient;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

public class GetCabLocationAsyncTask extends AsyncTask<String, Object,LatLng>
{
    public static final String ROUTE_NUMBER = "routeNumber";
    
    private Exception exception;
    private ProgressDialog progressDialog;
    CabLocationActivity activity;
    int duration;
    String routeNumber;
 
    public GetCabLocationAsyncTask(CabLocationActivity activity)
    {
        super();
        this.activity = activity;
    }

    public void onPreExecute()
    {
        progressDialog = new ProgressDialog(activity);
       
        progressDialog.setMessage("Getting Cab Location");
        progressDialog.show();
    }
 
    @Override
    public void onPostExecute(LatLng result)
    {
    	
        progressDialog.dismiss();
        if (exception == null)
        {
            activity.showCabLocation(result);
            
            
        }
        else
        {
            processException();
        }
        try {
			Thread.sleep(1*60*1000);
			progressDialog = new ProgressDialog(activity);
		       
	        progressDialog.setMessage("Refreshing");
	        progressDialog.show();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        new GetCabLocationAsyncTask(activity).execute(routeNumber);
    }
 
   
 
    private void processException()
    {
        Toast.makeText(activity, "Error retriving data", 3000).show();
    }

	@Override
	protected LatLng doInBackground(String... params) {
		// TODO Auto-generated method stub
		 routeNumber = params[0];
        try
        {	 
        	IntuitServerRestclient client = new IntuitServerRestclient();
        	LatLng cabLocation =client.getCabLocation(routeNumber);
            return cabLocation;
        }
        catch (Exception e)
        {
            exception = e;
            return null;
        }
		
		
	}
 
  
}