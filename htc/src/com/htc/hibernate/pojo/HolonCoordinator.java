package com.htc.hibernate.pojo;
// Generated 16 Aug, 2015 3:28:11 PM by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;

/**
 * HolonCoordinator generated by hbm2java
 */
public class HolonCoordinator implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private Holon holon;
	private HolonObject holonObject;
	private String name;
	private Set<?> holonObjects = new HashSet<Object>(0);
	private Set<?> powerSources = new HashSet<Object>(0);

	public HolonCoordinator() {
	}

	public HolonCoordinator(int id) {
		this.id = id;
	}

	public HolonCoordinator(int id, Holon holon, HolonObject holonObject,
			String name, Set<?> holonObjects, Set<?> powerSources) {
		this.id = id;
		this.holon = holon;
		this.holonObject = holonObject;
		this.name = name;
		this.holonObjects = holonObjects;
		this.powerSources = powerSources;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Holon getHolon() {
		return this.holon;
	}

	public void setHolon(Holon holon) {
		this.holon = holon;
	}

	public HolonObject getHolonObject() {
		return this.holonObject;
	}

	public void setHolonObject(HolonObject holonObject) {
		this.holonObject = holonObject;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<?> getHolonObjects() {
		return this.holonObjects;
	}

	public void setHolonObjects(Set<?> holonObjects) {
		this.holonObjects = holonObjects;
	}

	public Set<?> getPowerSources() {
		return this.powerSources;
	}

	public void setPowerSources(Set<?> powerSources) {
		this.powerSources = powerSources;
	}

}
