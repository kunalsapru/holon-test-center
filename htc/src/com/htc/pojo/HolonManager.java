package com.htc.pojo;


/**
 * This class 'HolonManager' models an abstract entity(Note:not a physical entity). The role of this HM is to manage the HolonElements
 * (HE) of a HolonObject(HO). Main functionality of HM is turn on and turn off HEs.Each HO has only one HM.
 * 
 * @author Achal
 *
 */
public class HolonManager {

	private int id;
	private String name;
	private HolonCoordinator holonCoordinator;
	
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
