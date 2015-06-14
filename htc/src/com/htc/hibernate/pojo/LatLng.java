package com.htc.hibernate.pojo;

public class LatLng implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private double lat;
	private double lng;

	public LatLng() {
	}

	public LatLng(double lat, double lng) {
		this.lat = lat;
		this.lng = lng;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getLat() {
		return this.lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return this.lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

}
