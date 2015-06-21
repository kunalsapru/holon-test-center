package com.htc.hibernate.pojo;

// default package
// Generated 19 Jun, 2015 7:20:49 PM by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;

/**
 * LatLng generated by hbm2java
 */
public class LatLng implements java.io.Serializable {

	private Integer id;
	private double lat;
	private double lng;
	private Set holons = new HashSet(0);
	private Set holonCoordinators = new HashSet(0);
	private Set holonObjects = new HashSet(0);
	private Set powerLinesForSource = new HashSet(0);
	private Set powerLineNodes = new HashSet(0);
	private Set powerLinesForDestination = new HashSet(0);
	private Set powerSwitches = new HashSet(0);

	public LatLng() {
	}

	public LatLng(double lat, double lng) {
		this.lat = lat;
		this.lng = lng;
	}

	public LatLng(double lat, double lng, Set holons, Set holonCoordinators,
			Set holonObjects, Set powerLinesForSource, Set powerLineNodes,
			Set powerLinesForDestination, Set powerSwitches) {
		this.lat = lat;
		this.lng = lng;
		this.holons = holons;
		this.holonCoordinators = holonCoordinators;
		this.holonObjects = holonObjects;
		this.powerLinesForSource = powerLinesForSource;
		this.powerLineNodes = powerLineNodes;
		this.powerLinesForDestination = powerLinesForDestination;
		this.powerSwitches = powerSwitches;
	}

	public Set getHolonCoordinators() {
		return this.holonCoordinators;
	}

	public Set getHolonObjects() {
		return this.holonObjects;
	}

	public Set getHolons() {
		return this.holons;
	}

	public Integer getId() {
		return this.id;
	}

	public double getLat() {
		return this.lat;
	}

	public double getLng() {
		return this.lng;
	}

	public Set getPowerLineNodes() {
		return this.powerLineNodes;
	}

	public Set getPowerLinesForDestination() {
		return this.powerLinesForDestination;
	}

	public Set getPowerLinesForSource() {
		return this.powerLinesForSource;
	}

	public Set getPowerSwitches() {
		return this.powerSwitches;
	}

	public void setHolonCoordinators(Set holonCoordinators) {
		this.holonCoordinators = holonCoordinators;
	}

	public void setHolonObjects(Set holonObjects) {
		this.holonObjects = holonObjects;
	}

	public void setHolons(Set holons) {
		this.holons = holons;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public void setPowerLineNodes(Set powerLineNodes) {
		this.powerLineNodes = powerLineNodes;
	}

	public void setPowerLinesForDestination(Set powerLinesForDestination) {
		this.powerLinesForDestination = powerLinesForDestination;
	}

	public void setPowerLinesForSource(Set powerLinesForSource) {
		this.powerLinesForSource = powerLinesForSource;
	}

	public void setPowerSwitches(Set powerSwitches) {
		this.powerSwitches = powerSwitches;
	}

}
