package com.htc.pojo;


/**
 * This class 'HolonElement' models a physical entity(Note:not an abstract entity). Each HE corresponds to physical electrical appliances
 * like TV,Fride,Washing Machine etc 
 * @author Achal
 *
 */
public class HolonElement {

	public enum HolonElementState{
		OverSupply,NormalSupply,MinimumSupply,BrownOut,HeartBeat,BlackOut
	}
	
	public enum HolonElementType{
		Fridge,TV,WashingMachine,SolarPanel,Microwave,Light,Battery,Generator,PowerLines,gas2Power
	}
	
	private int id;
	private String name;
	private HolonElementType holonElementType;
	private HolonElementState holonElementState;
	private String usage;
	private int maxCapacity;
	private int minCapacity;
	private int currentCapacity;
	private boolean currentEnergyStatus;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private String history;
	private HolonManager holonManager;
	private HolonCoordinator holonCoordinator;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public HolonElementType getHolonElementType() {
		return holonElementType;
	}
	public void setHolonElementType(HolonElementType holonElementType) {
		this.holonElementType = holonElementType;
	}
	public HolonElementState getHolonElementState() {
		return holonElementState;
	}
	public void setHolonElementState(HolonElementState holonElementState) {
		this.holonElementState = holonElementState;
	}
	public String getUsage() {
		return usage;
	}
	public void setUsage(String usage) {
		this.usage = usage;
	}
	public int getMaxCapacity() {
		return maxCapacity;
	}
	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}
	public int getMinCapacity() {
		return minCapacity;
	}
	public void setMinCapacity(int minCapacity) {
		this.minCapacity = minCapacity;
	}
	public int getCurrentCapacity() {
		return currentCapacity;
	}
	public void setCurrentCapacity(int currentCapacity) {
		this.currentCapacity = currentCapacity;
	}
	public boolean getCurrentEnergyStatus() {
		return currentEnergyStatus;
	}
	public void setCurrentEnergyStatus(boolean currentEnergyStatus) {//On or Off
		this.currentEnergyStatus = currentEnergyStatus;
	}
	public String getHistory() {
		return history;
	}
	public void setHistory(String history) {
		this.history = history;
	}
	public HolonManager getHolonManager() {
		return holonManager;
	}
	public void setHolonManager(HolonManager holonManager) {
		this.holonManager = holonManager;
	}
	public HolonCoordinator getHolonCoordinator() {
		return holonCoordinator;
	}
	public void setHolonCoordinator(HolonCoordinator holonCoordinator) {
		this.holonCoordinator = holonCoordinator;
	}
	
	/* There is change here in HolonElement.java */ 
}
