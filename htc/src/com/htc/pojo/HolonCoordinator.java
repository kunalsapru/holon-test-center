package com.htc.pojo;

import java.util.List;

import com.htc.hibernate.pojo.LatLng;


/**
 * This class 'HolonCoordinator'models an abstract concept called HolonCoordinator(HK). Every Holon has only one HK and this HK 
 * The HK is managing and communicating with the HMs inside its own holon and exchanging information with the HKs of other holons.
 * @author Achal
 *
 */
public class HolonCoordinator {

	
	private int id;
	private String name;
	private LatLng latLng;
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
	
	/**
	 * yet to implement; implementation requires future requirements
	 * @param Hm
	 */
	public void contactHolonManager(HolonManager Hm){
		
	}
	
	/**
	 * yet to implement; implementation requires future requirements
	 * @param Hk
	 */
	public void contactNeighBourHolonCoordinator(HolonCoordinator Hk){
		
	}
	public LatLng getLatLng() {
		return latLng;
	}
	public void setLatLng(LatLng latLng) {
		this.latLng = latLng;
	}
	
}
