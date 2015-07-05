package com.htc.hibernate.pojo;

/**
 * PowerLineNode generated by hbm2java
 */
public class PowerLineNode implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private LatLng latLng;
	private String name;

	public PowerLineNode() {
	}

	public PowerLineNode(int id) {
		this.id = id;
	}

	public PowerLineNode(int id, LatLng latLng, String name) {
		this.id = id;
		this.latLng = latLng;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LatLng getLatLng() {
		return this.latLng;
	}

	public void setLatLng(LatLng latLng) {
		this.latLng = latLng;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
