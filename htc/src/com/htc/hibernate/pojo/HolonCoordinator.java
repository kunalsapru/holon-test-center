package com.htc.hibernate.pojo;

public class HolonCoordinator implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private Integer holon;

	public HolonCoordinator() {
	}

	public HolonCoordinator(int id) {
		this.id = id;
	}

	public HolonCoordinator(int id, String name, Integer holon) {
		this.id = id;
		this.name = name;
		this.holon = holon;
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

	public Integer getHolon() {
		return this.holon;
	}

	public void setHolon(Integer holon) {
		this.holon = holon;
	}

}
