package com.htc.hibernate.pojo;

public class HolonManager implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private Integer holonObject;

	public HolonManager() {
	}

	public HolonManager(int id) {
		this.id = id;
	}

	public HolonManager(int id, String name, Integer holonObject) {
		this.id = id;
		this.name = name;
		this.holonObject = holonObject;
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

	public Integer getHolonObject() {
		return this.holonObject;
	}

	public void setHolonObject(Integer holonObject) {
		this.holonObject = holonObject;
	}

}
