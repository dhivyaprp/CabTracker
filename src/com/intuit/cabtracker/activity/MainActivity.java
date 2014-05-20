package com.intuit.cabtracker.activity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.intuit.cabtracker.R;
import com.intuit.cabtracker.R.id;
import com.intuit.cabtracker.R.layout;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	NumberPicker picker1; 
	NumberPicker picker2;
	Button button1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_cab);
		
		 picker1 = (NumberPicker) findViewById(R.id.numberPicker1);
		 picker2 = (NumberPicker) findViewById(R.id.numberPicker2);
		picker1.setMinValue(0);
		picker1.setMaxValue(9);
		picker2.setMinValue(0);
		picker2.setMaxValue(9);
		picker1.setWrapSelectorWheel(false); 
		picker2.setWrapSelectorWheel(false); 
		
		 button1 = (Button) findViewById(R.id.button1);
		 button1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			int value1=	picker1.getValue();
			int value2=	picker2.getValue();
			String cabNumber;
			if(value1==0)
			 cabNumber=String.valueOf(value2);
			else
			 cabNumber=String.valueOf(value1) + String.valueOf(value2);
			Toast.makeText(getApplicationContext(), cabNumber, Toast.LENGTH_SHORT).show();
			Intent openCabLoctionPage = new Intent(getBaseContext(), CabLocationActivity.class);
			openCabLoctionPage.putExtra("cabNumber", cabNumber);
			
			
			startActivity(openCabLoctionPage);
			
				
			}
		});
		
		
	}

	

	

}
