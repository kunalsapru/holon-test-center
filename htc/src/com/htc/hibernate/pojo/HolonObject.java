package com.htc.hibernate.pojo;
// Generated 16 Aug, 2015 3:28:11 PM by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * HolonObject generated by hbm2java
 */
@SuppressWarnings("rawtypes")
public class HolonObject implements java.io.Serializable, Comparable {

	private static final long serialVersionUID = 1L;
	private int id;
	private EnergyState energyState;
	private HolonCoordinator holonCoordinator;
	private HolonObjectType holonObjectType;
	private LatLng latLngByNeLocation;
	private LatLng latLngBySwLocation;
	private Boolean lineConnectedState;
	private Integer consumption;
	private Boolean canCommunicate;
	private Boolean createdFactory;
	private Integer flexibility;
	private Set<?> holonCoordinators = new HashSet<Object>(0);
	private Set<HolonElement> holonElements = new HashSet<HolonElement>(0);
	private Set<PowerLine> powerLines = new HashSet<PowerLine>(0);

	public HolonObject() {
	}

	public HolonObject(int id) {
		this.id = id;
	}

	public HolonObject(int id, EnergyState energyState,
			HolonCoordinator holonCoordinator, HolonObjectType holonObjectType,
			LatLng latLngByNeLocation, LatLng latLngBySwLocation,
			Boolean lineConnectedState, Integer consumption,
			Boolean canCommunicate, Boolean createdFactory, Integer flexibility,
			Set<?> holonCoordinators, Set<HolonElement> holonElements, 
			Set<PowerLine> powerLines) {
		this.id = id;
		this.energyState = energyState;
		this.holonCoordinator = holonCoordinator;
		this.holonObjectType = holonObjectType;
		this.latLngByNeLocation = latLngByNeLocation;
		this.latLngBySwLocation = latLngBySwLocation;
		this.lineConnectedState = lineConnectedState;
		this.consumption = consumption;
		this.canCommunicate = canCommunicate;
		this.createdFactory = createdFactory;
		this.flexibility = flexibility;
		this.holonCoordinators = holonCoordinators;
		this.holonElements = holonElements;
		this.setPowerLines(powerLines);
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EnergyState getEnergyState() {
		return this.energyState;
	}

	public void setEnergyState(EnergyState energyState) {
		this.energyState = energyState;
	}

	public HolonCoordinator getHolonCoordinator() {
		return this.holonCoordinator;
	}

	public void setHolonCoordinator(HolonCoordinator holonCoordinator) {
		this.holonCoordinator = holonCoordinator;
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

	public LatLng getLatLngBySwLocation() {
		return this.latLngBySwLocation;
	}

	public void setLatLngBySwLocation(LatLng latLngBySwLocation) {
		this.latLngBySwLocation = latLngBySwLocation;
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

	public Boolean getCanCommunicate() {
		return this.canCommunicate;
	}

	public void setCanCommunicate(Boolean canCommunicate) {
		this.canCommunicate = canCommunicate;
	}

	public Boolean getCreatedFactory() {
		return this.createdFactory;
	}

	public void setCreatedFactory(Boolean createdFactory) {
		this.createdFactory = createdFactory;
	}

	public Set<?> getHolonCoordinators() {
		return this.holonCoordinators;
	}

	public void setHolonCoordinators(Set<?> holonCoordinators) {
		this.holonCoordinators = holonCoordinators;
	}

	public Set<HolonElement> getHolonElements() {
		return this.holonElements;
	}

	public void setHolonElements(Set<HolonElement> holonElements) {
		this.holonElements = holonElements;
	}

	public Set<?> getPowerLines() {
		return this.powerLines;
	}

	public void setPowerLines(Set<PowerLine> powerLines) {
		this.powerLines = powerLines;
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

	public Integer getFlexibility() {
		return flexibility;
	}

	public void setFlexibility(Integer flexibility) {
		this.flexibility = flexibility;
	}

}
