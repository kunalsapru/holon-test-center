package com.htc.hibernate.pojo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * HolonObject generated by hbm2java
 */
@SuppressWarnings("rawtypes")
public class HolonObject implements java.io.Serializable, Comparable{



	private static final long serialVersionUID = 1L;
	private int id;
	private HolonCoordinator holonCoordinator;
	private HolonManager holonManager;
	private HolonObjectType holonObjectType;
	private LatLng latLngByNeLocation;
	private LatLng latLngByDoorLocation;
	private LatLng latLngBySwLocation;
	private PowerLine powerLine;
	private PowerLine subLine;
	private Boolean lineConnectedState;
	private Boolean canCommunicate;
	private EnergyState energyState;
	private Integer consumption;
	private Set<HolonElement> holonElements = new HashSet<HolonElement>(0);
	private Set<?> powerSwitches = new HashSet<Object>(0);
	private Set<?> holonCoordinators = new HashSet<Object>(0);

	public HolonObject() {
	}

	public HolonObject(HolonCoordinator holonCoordinator) {
		this.holonCoordinator = holonCoordinator;
	}
	
	public HolonObject(int id, HolonCoordinator holonCoordinator) {
		this.id = id;
		this.holonCoordinator = holonCoordinator;
	}

	public HolonObject(int id, HolonCoordinator holonCoordinator,
			HolonManager holonManager, HolonObjectType holonObjectType,
			LatLng latLngByNeLocation, LatLng latLngByDoorLocation,
			LatLng latLngBySwLocation,EnergyState energyState, PowerLine powerLine,PowerLine subLine,
			Boolean lineConnectedState,Boolean canCommunicate, Integer consumption, Set<HolonElement> holonElements,
			Set<?> powerSwitches,Set<?> holonCoordinators) {
		this.id = id;
		this.holonCoordinator = holonCoordinator;
		this.holonManager = holonManager;
		this.holonObjectType = holonObjectType;
		this.latLngByNeLocation = latLngByNeLocation;
		this.latLngByDoorLocation = latLngByDoorLocation;
		this.latLngBySwLocation = latLngBySwLocation;
		this.powerLine = powerLine;
		this.lineConnectedState = lineConnectedState;
		this.consumption = consumption;
		this.holonElements = holonElements;
		this.powerSwitches = powerSwitches;
		this.holonCoordinators=holonCoordinators;
		this.energyState=energyState;
		this.subLine=subLine;
		this.canCommunicate=canCommunicate;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public HolonCoordinator getHolonCoordinator() {
		return this.holonCoordinator;
	}

	public void setHolonCoordinator(HolonCoordinator holonCoordinator) {
		this.holonCoordinator = holonCoordinator;
	}

	public HolonManager getHolonManager() {
		return this.holonManager;
	}

	public void setHolonManager(HolonManager holonManager) {
		this.holonManager = holonManager;
	}

	public HolonObjectType getHolonObjectType() {
		return this.holonObjectType;
	}

	public void setHolonObjectType(HolonObjectType holonObjectType) {
		this.holonObjectType = holonObjectType;
	}

	public LatLng getLatLngByNeLocation() {
		return this.latLngByNeLocation;
	}

	public void setLatLngByNeLocation(LatLng latLngByNeLocation) {
		this.latLngByNeLocation = latLngByNeLocation;
	}

	public LatLng getLatLngByDoorLocation() {
		return this.latLngByDoorLocation;
	}

	public void setLatLngByDoorLocation(LatLng latLngByDoorLocation) {
		this.latLngByDoorLocation = latLngByDoorLocation;
	}

	public LatLng getLatLngBySwLocation() {
		return this.latLngBySwLocation;
	}

	public void setLatLngBySwLocation(LatLng latLngBySwLocation) {
		this.latLngBySwLocation = latLngBySwLocation;
	}

	public PowerLine getPowerLine() {
		return this.powerLine;
	}

	public void setPowerLine(PowerLine powerLine) {
		this.powerLine = powerLine;
	}

	public Boolean getLineConnectedState() {
		return this.lineConnectedState;
	}

	public void setLineConnectedState(Boolean lineConnectedState) {
		this.lineConnectedState = lineConnectedState;
	}

	public Integer getConsumption() {
		return this.consumption;
	}

	public void setConsumption(Integer consumption) {
		this.consumption = consumption;
	}

	public Set<HolonElement> getHolonElements() {
		return this.holonElements;
	}

	public void setHolonElements(Set<HolonElement> holonElements) {
		this.holonElements = holonElements;
	}

	public Set<?> getPowerSwitches() {
		return this.powerSwitches;
	}

	public void setPowerSwitches(Set<?> powerSwitches) {
		this.powerSwitches = powerSwitches;
	}

	public Set<?> getHolonCoordinators() {
		return holonCoordinators;
	}

	public void setHolonCoordinators(Set<?> holonCoordinators) {
		this.holonCoordinators = holonCoordinators;
	}

	public EnergyState getEnergyState() {
		return energyState;
	}

	public void setEnergyState(EnergyState energyState) {
		this.energyState = energyState;
	}

	public PowerLine getSubLine() {
		return subLine;
	}

	public void setSubLine(PowerLine subLine) {
		this.subLine = subLine;
	}
	
	private int getProducerCount(HolonObject ho)
	{
		int count=0;
		
		Set<HolonElement> heSet = getHolonElements();
		Iterator<HolonElement> iter = heSet.iterator();
		while (iter.hasNext()) {
		    HolonElement he = (HolonElement) iter.next();
		    if(he.getHolonElementType().getProducer())
		    {
		    	count++;
		    }
		}
		return count;
		
	}

	@Override
	public int compareTo(Object obj) {
		int result=0;
		HolonObject ho2 = (HolonObject)(obj);
		result = (this.getHolonElements().size() < ho2.getHolonElements().size() ) ? -1: (this.getHolonElements().size() > ho2.getHolonElements().size() ) ? 1:0 ;
		if(result==0)
		{
			result = (getProducerCount(this) < getProducerCount(ho2) ) ? -1: (getProducerCount(this) > getProducerCount(ho2) ) ? 1:0 ;
		}
		return result;
	}

	public Boolean getCanCommunicate() {
		return canCommunicate;
	}

	public void setCanCommunicate(Boolean canCommunicate) {
		this.canCommunicate = canCommunicate;
	}

}
