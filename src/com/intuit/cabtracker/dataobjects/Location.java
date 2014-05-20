package com.intuit.cabtracker.dataobjects;

import java.util.Date;

public class Location {

	double lattitude;
	double longitude;
	Date updateTime;
	
	public Location(){}
	
	public Location(double latt, double longt)
	{
		this.lattitude = latt;
		this.longitude = longt;
		this.updateTime = new Date();
	}
	
	public double getLattitude() {
		return lattitude;
	}
	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}