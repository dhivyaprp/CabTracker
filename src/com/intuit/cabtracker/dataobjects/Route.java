package com.intuit.cabtracker.dataobjects;

import java.util.LinkedHashMap;

public class Route {

	String routeNo;
	String cabNo;
	LinkedHashMap<String,PickUpPoint> stopList;
	
	Route()
	{
		// fill up route data to the route no variable
		stopList = new LinkedHashMap<String,PickUpPoint>();
	}
	
	public String getRouteNo() {
		return routeNo;
	}

	public void setRouteNo(String routeNo) {
		this.routeNo = routeNo;
	}

	
	public String getCabNo() {
		return cabNo;
	}

	public void setCabNo(String cabNo) {
		this.cabNo = cabNo;
	}

	public LinkedHashMap<String,PickUpPoint> getStopList() {
		return stopList;
	}

	public void setStopList(LinkedHashMap<String,PickUpPoint> stopList) {
		this.stopList = stopList;
	}
}
