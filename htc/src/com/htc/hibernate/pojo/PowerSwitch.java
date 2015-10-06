package com.htc.hibernate.pojo;

import java.util.HashSet;
import java.util.Set;

// Generated 16 Aug, 2015 3:28:11 PM by Hibernate Tools 4.3.1

/**
 * PowerSwitch generated by hbm2java
 */
public class PowerSwitch implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private LatLng latLng;
	private PowerLine powerLineByPowerLineA;
	private PowerLine powerLineByPowerLineB;
	private Boolean status;
	private Set<?> simulations = new HashSet<Object>(0);
	
	public PowerSwitch() {
	}

	public PowerSwitch(int id, LatLng latLng) {
		this.id = id;
		this.latLng = latLng;
	}


	public PowerSwitch(int id, LatLng latLng, PowerLine powerLineByPowerLineA,
			PowerLine powerLineByPowerLineB, Boolean status, Set<?> simulations) {
		super();
		this.id = id;
		this.latLng = latLng;
		this.powerLineByPowerLineA = powerLineByPowerLineA;
		this.powerLineByPowerLineB = powerLineByPowerLineB;
		this.status = status;
		this.simulations = simulations;
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

	public PowerLine getPowerLineByPowerLineA() {
		return this.powerLineByPowerLineA;
	}

	public void setPowerLineByPowerLineA(PowerLine powerLineByPowerLineA) {
		this.powerLineByPowerLineA = powerLineByPowerLineA;
	}

	public PowerLine getPowerLineByPowerLineB() {
		return this.powerLineByPowerLineB;
	}

	public void setPowerLineByPowerLineB(PowerLine powerLineByPowerLineB) {
		this.powerLineByPowerLineB = powerLineByPowerLineB;
	}

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Set<?> getSimulations() {
		return simulations;
	}

	public void setSimulations(Set<?> simulations) {
		this.simulations = simulations;
	}

}
