package com.htc.hibernate.pojo;
// Generated 16 Aug, 2015 3:28:11 PM by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;

/**
 * HolonElementState generated by hbm2java
 */
public class HolonElementState implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private Set holonElements = new HashSet(0);

	public HolonElementState() {
	}

	public HolonElementState(int id) {
		this.id = id;
	}

	public HolonElementState(int id, String name, Set holonElements) {
		this.id = id;
		this.name = name;
		this.holonElements = holonElements;
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

	public Set getHolonElements() {
		return this.holonElements;
	}

	public void setHolonElements(Set holonElements) {
		this.holonElements = holonElements;
	}

}
