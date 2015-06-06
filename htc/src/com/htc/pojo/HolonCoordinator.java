package com.htc.pojo;

import java.util.List;



public class HolonCoordinator {

	
	private int id;
	private String name;
	private List<HolonManager> listOfHm;
	
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
	public List<HolonManager> getListOfHm() {
		return listOfHm;
	}
	public void setListOfHm(List<HolonManager> listOfHm) {
		this.listOfHm = listOfHm;
	}
	
	//yet to implement
	public void contactHolonManager(HolonManager Hm){
		
	}
	
	//yet to implement
	public void contactNeighBourHolonCoordinator(HolonCoordinator Hk){
		
	}
	
}
