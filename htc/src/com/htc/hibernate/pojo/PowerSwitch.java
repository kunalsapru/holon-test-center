package com.htc.hibernate.pojo;

/**
 * PowerSwitch generated by hbm2java
 */
public class PowerSwitch implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private HolonObject holonObject;
	private LatLng latLng;
	private PowerLine powerLineA;
	private PowerLine powerLineB;
	private Boolean status;

	public PowerSwitch() {
	}

	public PowerSwitch(int id, LatLng latLng) {
		this.id = id;
		this.latLng = latLng;
	}

	public PowerSwitch(int id, HolonObject holonObject, LatLng latLng,
			PowerLine powerLineA,PowerLine powerLineB, Boolean status) {
		this.id = id;
		this.holonObject = holonObject;
		this.latLng = latLng;
		this.powerLineA = powerLineA;
		this.powerLineB = powerLineB;
		this.status = status;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public HolonObject getHolonObject() {
		return this.holonObject;
	}

	public void setHolonObject(HolonObject holonObject) {
		this.holonObject = holonObject;
	}

	public LatLng getLatLng() {
		return this.latLng;
	}

	public void setLatLng(LatLng latLng) {
		this.latLng = latLng;
	}

	public PowerLine getPowerLineA() {
		return this.powerLineA;
	}

	public void setPowerLineA(PowerLine powerLineA) {
		this.powerLineA = powerLineA;
	}

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public PowerLine getPowerLineB() {
		return powerLineB;
	}

	public void setPowerLineB(PowerLine powerLineB) {
		this.powerLineB = powerLineB;
	}

}
