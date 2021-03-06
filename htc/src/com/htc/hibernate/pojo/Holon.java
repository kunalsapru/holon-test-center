package com.htc.hibernate.pojo;

import java.util.HashSet;
import java.util.Set;

/**
 * Holon generated by hbm2java
 */
public class Holon implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String color;
	private Set<?> holonObjects = new HashSet<Object>(0);

	public Holon() {
	}

	public Holon(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Set<?> getHolonObjects() {
		return this.holonObjects;
	}

	public void setHolonObjects(Set<?> holonObjects) {
		this.holonObjects = holonObjects;
	}

}
