package com.htc.hibernate.pojo;
// Generated 16 Aug, 2015 3:28:11 PM by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;

/**
 * LatLng generated by hbm2java
 */
public class LatLng implements java.io.Serializable {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LatLng other = (LatLng) obj;
		if (Double.doubleToLongBits(latitude) != Double
				.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double
				.doubleToLongBits(other.longitude))
			return false;
		return true;
	}
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private double latitude;
	private double longitude;
	private Set<?> holonObjectsForNeLocation = new HashSet<Object>(0);
	private Set<?> powerSources = new HashSet<Object>(0);
	private Set<?> powerLinesForSource = new HashSet<Object>(0);
	private Set<?> holonObjectsForSwLocation = new HashSet<Object>(0);
	private Set<?> powerLinesForDestination = new HashSet<Object>(0);
	private Set<?> powerSwitches = new HashSet<Object>(0);

	public LatLng() {
	}

	public LatLng(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public LatLng(double latitude, double longitude,
			Set<?> holonObjectsForNeLocation, Set<?> powerSources,
			Set<?> powerLinesForSource, Set<?> holonObjectsForSwLocation,
			Set<?> powerLinesForDestination, Set<?> powerSwitches) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.holonObjectsForNeLocation = holonObjectsForNeLocation;
		this.powerSources = powerSources;
		this.powerLinesForSource = powerLinesForSource;
		this.holonObjectsForSwLocation = holonObjectsForSwLocation;
		this.powerLinesForDestination = powerLinesForDestination;
		this.powerSwitches = powerSwitches;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Set<?> getHolonObjectsForNeLocation() {
		return this.holonObjectsForNeLocation;
	}

	public void setHolonObjectsForNeLocation(Set<?> holonObjectsForNeLocation) {
		this.holonObjectsForNeLocation = holonObjectsForNeLocation;
	}

	public Set<?> getPowerSources() {
		return this.powerSources;
	}

	public void setPowerSources(Set<?> powerSources) {
		this.powerSources = powerSources;
	}

	public Set<?> getPowerLinesForSource() {
		return this.powerLinesForSource;
	}

	public void setPowerLinesForSource(Set<?> powerLinesForSource) {
		this.powerLinesForSource = powerLinesForSource;
	}

	public Set<?> getHolonObjectsForSwLocation() {
		return this.holonObjectsForSwLocation;
	}

	public void setHolonObjectsForSwLocation(Set<?> holonObjectsForSwLocation) {
		this.holonObjectsForSwLocation = holonObjectsForSwLocation;
	}

	public Set<?> getPowerLinesForDestination() {
		return this.powerLinesForDestination;
	}

	public void setPowerLinesForDestination(Set<?> powerLinesForDestination) {
		this.powerLinesForDestination = powerLinesForDestination;
	}

	public Set<?> getPowerSwitches() {
		return this.powerSwitches;
	}

	public void setPowerSwitches(Set<?> powerSwitches) {
		this.powerSwitches = powerSwitches;
	}

}
