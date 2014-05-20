package com.intuit.cabtracker.asynctask;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;
import org.w3c.dom.Document;


import com.google.android.gms.maps.model.LatLng;
import com.intuit.cabtracker.activity.CabLocationActivity;
import com.intuit.cabtracker.activity.MainActivity;
import com.intuit.cabtracker.activity.wsclient.GMapV2Direction;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

public class GetDistanceAsyncTask extends AsyncTask<Map<String, String>, Object,String>
{
    public static final String USER_CURRENT_LAT = "user_current_lat";
    public static final String USER_CURRENT_LONG = "user_current_long";
    public static final String DESTINATION_LAT = "destination_lat";
    public static final String DESTINATION_LONG = "destination_long";
    public static final String DIRECTIONS_MODE = "directions_mode";
    private CabLocationActivity activity;
    private Exception exception;
    private ProgressDialog progressDialog;
    String distance;
 
    public GetDistanceAsyncTask(CabLocationActivity activity)
    {
        super();
        this.activity = activity;
    }
 
    public void onPreExecute()
    {
        progressDialog = new ProgressDialog(activity);
       
        progressDialog.setMessage("Calculating directions");
        progressDialog.show();
    }
 
    @Override
    public void onPostExecute(String result)
    {
    	
        progressDialog.dismiss();
        if (exception == null)
        {	activity.handleGetDistanceResult(result);
            
        }
        else
        {
            processException();
        }
    }
 
    @Override
    protected String doInBackground(Map<String, String>... params)
    {
        Map<String, String> paramMap = params[0];
        try
        {	 
         
            LatLng fromPosition = new LatLng(Double.valueOf(paramMap.get(USER_CURRENT_LAT)) , Double.valueOf(paramMap.get(USER_CURRENT_LONG)));
            LatLng toPosition = new LatLng(Double.valueOf(paramMap.get(DESTINATION_LAT)) , Double.valueOf(paramMap.get(DESTINATION_LONG)));
            GMapV2Direction md = new GMapV2Direction();
            Document doc = md.getDocument(fromPosition, toPosition, paramMap.get(DIRECTIONS_MODE));
            distance = md.getTotalDistanceText(doc);;
            return distance;
        }
        catch (Exception e)
        {
            exception = e;
            return null;
        }
    }
 
    private void processException()
    {
        Toast.makeText(activity, "Error retriving data", 3000).show();
    }
}