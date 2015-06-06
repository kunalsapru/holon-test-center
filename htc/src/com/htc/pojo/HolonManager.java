package com.htc.pojo;

public class HolonManager {

	private int id;
	private String name;
	private HolonCoordinator holonCoordinator;
	//listHolonElements;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public HolonCoordinator getHolonCoordinator() {
		return holonCoordinator;
	}
	public void setHolonCoordinator(HolonCoordinator holonCoordinator) {
		this.holonCoordinator = holonCoordinator;
	}
	
	public void turnOnHolonElement(HolonElement he){
		he.setCurrentEnergyStatus(true);
	}
	
	
	public void turnOffHolonElement(HolonElement he){
		he.setCurrentEnergyStatus(false);
	}
}
