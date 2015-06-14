package com.htc.hibernate.pojo;

public class HollonElementState implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;

	public HollonElementState() {
	}

	public HollonElementState(int id) {
		this.id = id;
	}

	public HollonElementState(int id, String name) {
		this.id = id;
		this.name = name;
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

}
