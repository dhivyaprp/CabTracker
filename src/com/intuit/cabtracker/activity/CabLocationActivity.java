package com.intuit.cabtracker.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.widget.TextView;
import android.widget.Toast;







import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.intuit.cabtracker.R;
import com.intuit.cabtracker.activity.wsclient.GMapV2Direction;
import com.intuit.cabtracker.application.ActivityStatus;
import com.intuit.cabtracker.asynctask.GetCabDetailsAsyncTask;
import com.intuit.cabtracker.asynctask.GetCabLocationAsyncTask;
import com.intuit.cabtracker.asynctask.GetDirectionsAsyncTask;
import com.intuit.cabtracker.asynctask.GetDistanceAsyncTask;




public class CabLocationActivity extends FragmentActivity implements 
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener
      {

	
	private  LatLng STOP = new LatLng(12.916906, 77.620889);
	private LatLng STOP1 =new LatLng(12.916266, 77.634460);
	private  LatLng CAB = new LatLng(12.921034, 77.644116);
	private  LatLng STOP2 = new LatLng(12.923395, 77.670682);
	private  LatLng STOP3= new LatLng(12.924242, 77.681266);
	private LatLng MYLOC;
	private GoogleMap map;
	private SupportMapFragment fragment;
	private LatLngBounds latlngBounds;
	private Polyline newPolyline;
	private int width, height;
	private LocationClient mLocationClient;
	private String cabNumber; 
	private String distance;
	TextView mymsg;
    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(5000)         // 5 seconds
            .setFastestInterval(16)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// set screen dimenstions for map
		getSreenDimenstions();
		
	    // intialize map
		fragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
		map = fragment.getMap(); 
		mLocationClient = new LocationClient(this, this, this);
		
		
		//Text from Previous activity displayed
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    cabNumber = extras.getString("cabNumber");
		    mymsg =(TextView)findViewById(R.id.textView1);
		    mymsg.setText("Your Route number is " + cabNumber);
		    
	}
		//Get route map
		findRouteDetails(cabNumber);
		
		//find cab location
		findCabDetails(cabNumber);
		
	}
	
	public void showCabLocation(LatLng cabLocation){
		map.addMarker(new MarkerOptions()
        .position(cabLocation)
        .title("Cab Location"));
      //  .icon(BitmapDescriptorFactory.fromResource(R.drawable.taxi_icon)));
		
		 //Get Cab Location
	    LatLng cabLoc = cabLocation;
	    if (mLocationClient != null && mLocationClient.isConnected()) {
            Location loc = mLocationClient.getLastLocation();
            MYLOC = new LatLng(loc.getLatitude(), loc.getLongitude());
           
        }
	    else MYLOC=STOP3;
	    findDistance(MYLOC.latitude, MYLOC.longitude, cabLoc.latitude, cabLoc.longitude, GMapV2Direction.MODE_DRIVING );
	}
	
	public void drawRoutemap(ArrayList<LatLng> stopsList){
		
		if (newPolyline != null)
		{
			newPolyline.remove();
		}
		
		for(int i = 0 ; i < stopsList.size()-1 ; i++) 
		{          
			findDirections(stopsList.get(i).latitude, stopsList.get(i).longitude, stopsList.get(i+1).latitude, stopsList.get(i+1).longitude,GMapV2Direction.MODE_DRIVING );
		}
		
		
	}
	
	public void findRouteDetails(String routeNumer)
	{
		
		GetCabDetailsAsyncTask asyncTask = new GetCabDetailsAsyncTask(this);
		asyncTask.execute(routeNumer);	
		//this function will return to drawRoutemap method
		
		
	}
	public void findCabDetails(String routeNumer)
	{
		GetCabLocationAsyncTask asyncTask2 = new GetCabLocationAsyncTask(this);
		asyncTask2.execute(routeNumer);	
		//this function will return to showCabLocation method
	}
	
	public void findDirections(double fromPositionDoubleLat, double fromPositionDoubleLong, double toPositionDoubleLat, double toPositionDoubleLong, String mode)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put(GetDirectionsAsyncTask.USER_CURRENT_LAT, String.valueOf(fromPositionDoubleLat));
		map.put(GetDirectionsAsyncTask.USER_CURRENT_LONG, String.valueOf(fromPositionDoubleLong));
		map.put(GetDirectionsAsyncTask.DESTINATION_LAT, String.valueOf(toPositionDoubleLat));
		map.put(GetDirectionsAsyncTask.DESTINATION_LONG, String.valueOf(toPositionDoubleLong));
		map.put(GetDirectionsAsyncTask.DIRECTIONS_MODE, mode);
		
		GetDirectionsAsyncTask asyncTask = new GetDirectionsAsyncTask(this);
		asyncTask.execute(map);	
	}
	
	public void findDistance(double fromPositionDoubleLat, double fromPositionDoubleLong, double toPositionDoubleLat, double toPositionDoubleLong, String mode)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put(GetDistanceAsyncTask.USER_CURRENT_LAT, String.valueOf(fromPositionDoubleLat));
		map.put(GetDistanceAsyncTask.USER_CURRENT_LONG, String.valueOf(fromPositionDoubleLong));
		map.put(GetDistanceAsyncTask.DESTINATION_LAT, String.valueOf(toPositionDoubleLat));
		map.put(GetDistanceAsyncTask.DESTINATION_LONG, String.valueOf(toPositionDoubleLong));
		map.put(GetDistanceAsyncTask.DIRECTIONS_MODE, mode);
		
		GetDistanceAsyncTask asyncTask = new GetDistanceAsyncTask(this);
		asyncTask.execute(map);	
	}
	
	
	@Override
	protected void onResume() {
		
		super.onResume();
	     ActivityStatus.activityResumed(); 
    	latlngBounds = createLatLngBoundsObject(CAB, STOP);
      map.moveCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, width, height, 100));

		findCabDetails(cabNumber);
       
	}

	public void handleGetDistanceResult(String distance) {
		 mymsg.setText(" Cab no " +cabNumber + " is "  + distance.toString() + " away from you");
				
	}
	
	public void handleGetDirectionResult(ArrayList<LatLng> directionPoints) {
		PolylineOptions rectLine = new PolylineOptions().width(8).color(Color.BLUE);

		for(int i = 0 ; i < directionPoints.size() ; i++) 
		{          
			rectLine.add(directionPoints.get(i));
		}
		
		newPolyline = map.addPolyline(rectLine);
		latlngBounds = createLatLngBoundsObject(directionPoints.get(0), directionPoints.get(directionPoints.size()-1));
	    map.animateCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, width, height, 150));
				
	}
	
	private void getSreenDimenstions()
	{
		Display display = getWindowManager().getDefaultDisplay();
		width = 500; 
		height = 500; 
	}
	
	private LatLngBounds createLatLngBoundsObject(LatLng firstLocation, LatLng secondLocation)
	{
		if (firstLocation != null && secondLocation != null)
		{
			LatLngBounds.Builder builder = new LatLngBounds.Builder();    
			builder.include(firstLocation).include(secondLocation);
			
			return builder.build();
		}
		return null;
	}
	
	

	 
	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}
	
	

	@Override
	public void onDisconnected() {
		Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
		
	}
	@Override
    protected void onStart() {
        super.onStart();
        mLocationClient.connect();
        
    }

	@Override
	public void onConnected(Bundle arg0) {
		   Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
		
	}
	
	@Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
        mLocationClient.disconnect();
        super.onStop();
    }
	
	@Override
    protected void onPause() {
        // Disconnecting the client invalidates it.
        mLocationClient.disconnect();
        ActivityStatus.activityPaused(); 
        super.onStop();
    }
	
}
