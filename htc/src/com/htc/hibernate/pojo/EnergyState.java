package com.htc.hibernate.pojo;

import java.util.HashSet;
import java.util.Set;

public class EnergyState implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String state;
	private String color;
	private Set<?> holonObjects = new HashSet<Object>(0);
	
	public EnergyState() {
	}

	
	public EnergyState( int id,String state, String color,
			Set<?> holonObjects) {
		this.setId(id);
		this.setState(state);
		this.setColor(color);
		this.setHolonObjects(holonObjects);
	}


	public Set<?> getHolonObjects() {
		return holonObjects;
	}


	public void setHolonObjects(Set<?> holonObjects) {
		this.holonObjects = holonObjects;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	
}
