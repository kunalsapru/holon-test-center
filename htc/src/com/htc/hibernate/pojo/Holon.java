package com.htc.hibernate.pojo;

public class Holon implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private Integer radius;
	private Integer holonCenter;

	public Holon() {
	}

	public Holon(int id) {
		this.id = id;
	}

	public Holon(int id, String name, Integer radius, Integer holonCenter) {
		this.id = id;
		this.name = name;
		this.radius = radius;
		this.holonCenter = holonCenter;
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

	public Integer getRadius() {
		return this.radius;
	}

	public void setRadius(Integer radius) {
		this.radius = radius;
	}

	public Integer getHolonCenter() {
		return this.holonCenter;
	}

	public void setHolonCenter(Integer holonCenter) {
		this.holonCenter = holonCenter;
	}

}
