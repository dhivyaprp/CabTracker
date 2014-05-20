package com.intuit.cabtracker.asynctask;

import java.util.ArrayList;
import com.google.android.gms.maps.model.LatLng;
import com.intuit.cabtracker.activity.CabLocationActivity;
import com.intuit.cabtracker.activity.wsclient.IntuitServerRestclient;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

public class GetCabDetailsAsyncTask extends AsyncTask<String, Object,ArrayList<LatLng>>
{
    public static final String ROUTE_NUMBER = "routeNumber";
    
    private Exception exception;
    private ProgressDialog progressDialog;
    CabLocationActivity activity;
    int duration;
    String routeNumber;
 
    public GetCabDetailsAsyncTask(CabLocationActivity activity)
    {
        super();
        this.activity = activity;
    }

    public void onPreExecute()
    {
        progressDialog = new ProgressDialog(activity);
       
        progressDialog.setMessage("Getting Route details");
        progressDialog.show();
    }
 
    @Override
    public void onPostExecute(ArrayList<LatLng> result)
    {
    	
        progressDialog.dismiss();
        if (exception == null)
        {
            activity.drawRoutemap(result);
            
        }
        else
        {
            processException();
        }
    }
 
   
 
    private void processException()
    {
        Toast.makeText(activity, "Error retriving data", 3000).show();
    }

	@Override
	protected ArrayList<LatLng> doInBackground(String... params) {
		// TODO Auto-generated method stub
		 routeNumber = params[0];
		IntuitServerRestclient client = new IntuitServerRestclient();
     	ArrayList<LatLng> routeList =client.getCabRoute(routeNumber);
     	return routeList;
		
	}
 
  
}