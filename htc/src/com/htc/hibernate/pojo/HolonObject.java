package com.htc.hibernate.pojo;

public class HolonObject implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private Integer holonObjectType;
	private Byte lineConnectedState;
	private Integer priority;
	private Integer location;
	private Integer holonCoordinator;
	private Integer holon;

	public HolonObject() {
	}

	public HolonObject(int id) {
		this.id = id;
	}

	public HolonObject(int id, Integer holonObjectType,
			Byte lineConnectedState, Integer priority, Integer location,
			Integer holonCoordinator, Integer holon) {
		this.id = id;
		this.holonObjectType = holonObjectType;
		this.lineConnectedState = lineConnectedState;
		this.priority = priority;
		this.location = location;
		this.holonCoordinator = holonCoordinator;
		this.holon = holon;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getHolonObjectType() {
		return this.holonObjectType;
	}

	public void setHolonObjectType(Integer holonObjectType) {
		this.holonObjectType = holonObjectType;
	}

	public Byte getLineConnectedState() {
		return this.lineConnectedState;
	}

	public void setLineConnectedState(Byte lineConnectedState) {
		this.lineConnectedState = lineConnectedState;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getLocation() {
		return this.location;
	}

	public void setLocation(Integer location) {
		this.location = location;
	}

	public Integer getHolonCoordinator() {
		return this.holonCoordinator;
	}

	public void setHolonCoordinator(Integer holonCoordinator) {
		this.holonCoordinator = holonCoordinator;
	}

	public Integer getHolon() {
		return this.holon;
	}

	public void setHolon(Integer holon) {
		this.holon = holon;
	}

}
