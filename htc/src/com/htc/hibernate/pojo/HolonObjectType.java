package com.htc.hibernate.pojo;

public class HolonObjectType implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;

	public HolonObjectType() {
	}

	public HolonObjectType(String name) {
		this.name = name;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
