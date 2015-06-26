package com.htc.pojo;

import java.util.List;

import com.htc.hibernate.pojo.LatLng;

public class Holon {

	private int id;
	private String name;
	private LatLng latLng;
	private Integer radius;
	private HolonCoordinator holonCoordinator;
	private List<HolonManager> listOfHm;
	private List<HolonObject> listOfHo;
	
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
	public void setHoloncoordinator(HolonCoordinator holonCoordinator) {
		this.holonCoordinator = holonCoordinator;
	}
	public List<HolonManager> getListOfHm() {
		return listOfHm;
	}
	public void setListOfHm(List<HolonManager> listOfHm) {
		this.listOfHm = listOfHm;
	}
	public List<HolonObject> getListOfHo() {
		return listOfHo;
	}
	public void setListOfHo(List<HolonObject> listOfHo) {
		this.listOfHo = listOfHo;
	}
}
