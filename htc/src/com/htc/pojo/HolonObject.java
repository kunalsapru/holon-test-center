package com.htc.pojo;

import java.util.List;

public class HolonObject {

	public enum HolonObjectType{
		Hosptitals,GovernmentInfrastructure,Physicians,Police,FireDepartment,Industries,HouseHold,
		PowerPlant, Transformers
	}
	
	private HolonManager holonManager;
	private HolonObjectType holonObjectType;
	private boolean lineConnectedState;//True for connected and false for not connected
	private int priority;// HolonObjectType Priority; lesser value means higher priority with '0' having the highest priority
	private List<HolonElement> listOfHe;
	private LatLng location;
	public LatLng getLocation() {
		return location;
	}

	public void setLocation(LatLng location) {
		this.location = location;
	}

	public HolonManager getHolonManager() {
		return holonManager;
	}

	public void setHolonManager(HolonManager holonManager) {
		this.holonManager = holonManager;
	}
	

	public List<HolonElement> getListOfHe() {
		return listOfHe;
	}

	public void setListOfHe(List<HolonElement> listOfHe) {
		this.listOfHe = listOfHe;
	}

	public HolonObjectType getHolonObjectType() {
		
		return holonObjectType;
	}

	public void setHolonObjectType(HolonObjectType holonObjectType) {
		this.holonObjectType = holonObjectType;
	}

	public boolean isLineConnectedState() {
		return lineConnectedState;
	}

	public void setLineConnectedState(boolean lineConnectedState) {
		this.lineConnectedState = lineConnectedState;
	}

	/**
	 *  HolonObjectType Priority; lesser value means higher priority with '0' having the highest priority
	 * @return the priority of the HolonObject as an Integer
	 */
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
}
