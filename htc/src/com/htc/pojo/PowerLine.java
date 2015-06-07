package com.htc.pojo;

import org.apache.log4j.Logger;


/**
 * 
 * This class models the PowerLine connecting entities of the Holon System. The PowerLine can be either
 * 1) MainPowerLine
 * 2) SubPowerLine
 * 
 * source of MainPowerLine is HolonPowerSource.
 * source of SubPowerLine is HolonObjectDoor.
 * 
 * @author Abhinav 
 *         
 */

public class PowerLine {
	static Logger log = Logger.getLogger(PowerLine.class);
	private String id;
	private String type;
	private int currentCapacity;
	private int maximumCapacity;
	private LatLng source;
	private LatLng destination;
	private boolean isConnected;
	private String reasonDown;
	
	public PowerLine(LatLng source, LatLng destination, int maximumCapacity, String type) {
		log.info("Creating PowerLine Obj");
		this.source = source;
		this.destination = destination;
		this.maximumCapacity = maximumCapacity;
		this.type = type;
	}

	public String getReasonDown() {
		return reasonDown;
	}

	public void setReasonDown(String reasonDown) {
		this.reasonDown = reasonDown;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCurrentCapacity() {
		return currentCapacity;
	}

	public void setCurrentCapacity(int currentCapacity) {
		this.currentCapacity = currentCapacity;
	}

	public int getMaximumCapacity() {
		return maximumCapacity;
	}

	public void setMaximumCapacity(int maximumCapacity) {
		this.maximumCapacity = maximumCapacity;
	}

	public LatLng getSource() {
		return source;
	}

	public void setSource(LatLng source) {
		this.source = source;
	}

	public LatLng getDestination() {
		return destination;
	}

	public void setDestination(LatLng destination) {
		this.destination = destination;
	}

	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}

}
