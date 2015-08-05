package com.htc.pojo;

/**
 * Another Complex Data Structure to hold the 4 latitude and Longitude values needed to draw a rectangle on the google map.
 * @author Achal
 *
 */
public class HolonObjectLatLng {

	private LatLng latLngNorthEast;
	private LatLng latLngSouthWest;
	
	public HolonObjectLatLng(){
		
	}
	
	public HolonObjectLatLng(LatLng latLngNorthEast,LatLng latLngSouthWest){
		this.latLngNorthEast=latLngNorthEast;
		this.latLngSouthWest=latLngSouthWest;
	}
	
	public LatLng getLatLngNorthEast() {
		return latLngNorthEast;
	}
	public void setLatLngNorthEast(LatLng latLngNorthEast) {
		this.latLngNorthEast = latLngNorthEast;
	}
	public LatLng getLatLngSouthWest() {
		return latLngSouthWest;
	}
	public void setLatLngSouthWest(LatLng latLngSouthWest) {
		this.latLngSouthWest = latLngSouthWest;
	}
}
