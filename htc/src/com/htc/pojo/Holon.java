package com.htc.pojo;

import java.util.List;

public class Holon {

	private int id;
	private String name;
	private HolonCoordinator holoncoordinator;
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
	public HolonCoordinator getHoloncoordinator() {
		return holoncoordinator;
	}
	public void setHoloncoordinator(HolonCoordinator holoncoordinator) {
		this.holoncoordinator = holoncoordinator;
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
