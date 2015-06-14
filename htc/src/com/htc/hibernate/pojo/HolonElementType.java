package com.htc.hibernate.pojo;

public class HolonElementType implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;

	public HolonElementType() {
	}

	public HolonElementType(int id) {
		this.id = id;
	}

	public HolonElementType(int id, String name) {
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
