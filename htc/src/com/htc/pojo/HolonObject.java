package com.htc.pojo;

import java.util.List;

import org.apache.log4j.Logger;


/**
 * This class models a physical entity(Note:Not a abstract entity). Each HO may be physically compared to a building on the map.
 * Each HO has one HM and may have multiple HEs 
 * 
 * @author Achal
 *
 */
public class HolonObject {
	static Logger log = Logger.getLogger(HolonObject.class);
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
	private int id;
	private boolean canCommunicate;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public boolean isCanCommunicate() {
		return canCommunicate;
	}

	public void setCanCommunicate(boolean canCommunicate) {
		this.canCommunicate = canCommunicate;
	}
}
