package com.htc.hibernate.pojo;

// default package
// Generated 19 Jun, 2015 7:20:49 PM by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;

/**
 * HolonObject generated by hbm2java
 */
public class HolonObject implements java.io.Serializable {

	private int id;
	private Holon holon;
	private HolonCoordinator holonCoordinator;
	private HolonObjectType holonObjectType;
	private HolonManager holonManager;
	private LatLng latLng;
	private Boolean lineConnectedState;
	private Integer priority;
	private Set holonElements = new HashSet(0);

	public HolonObject() {
	}

	public HolonObject(int id) {
		this.id = id;
	}

	public HolonObject(int id, Holon holon, HolonCoordinator holonCoordinator,
			HolonObjectType holonObjectType, HolonManager holonManager, LatLng latLng,
			Boolean lineConnectedState, Integer priority, Set holonElements,
			Set holonManagers) {
		this.id = id;
		this.holon = holon;
		this.holonCoordinator = holonCoordinator;
		this.holonObjectType = holonObjectType;
		this.latLng = latLng;
		this.lineConnectedState = lineConnectedState;
		this.priority = priority;
		this.holonElements = holonElements;
		this.setHolonManager(holonManager);
	}

	public Holon getHolon() {
		return this.holon;
	}

	public HolonCoordinator getHolonCoordinator() {
		return this.holonCoordinator;
	}

	public Set getHolonElements() {
		return this.holonElements;
	}

	public HolonObjectType getHolonObjectType() {
		return this.holonObjectType;
	}

	public int getId() {
		return this.id;
	}

	public LatLng getLatLng() {
		return this.latLng;
	}

	public Boolean getLineConnectedState() {
		return this.lineConnectedState;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public void setHolon(Holon holon) {
		this.holon = holon;
	}

	public void setHolonCoordinator(HolonCoordinator holonCoordinator) {
		this.holonCoordinator = holonCoordinator;
	}

	public void setHolonElements(Set holonElements) {
		this.holonElements = holonElements;
	}

	public void setHolonObjectType(HolonObjectType holonObjectType) {
		this.holonObjectType = holonObjectType;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLatLng(LatLng latLng) {
		this.latLng = latLng;
	}

	public void setLineConnectedState(Boolean lineConnectedState) {
		this.lineConnectedState = lineConnectedState;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public HolonManager getHolonManager() {
		return holonManager;
	}

	public void setHolonManager(HolonManager holonManager) {
		this.holonManager = holonManager;
	}

}
