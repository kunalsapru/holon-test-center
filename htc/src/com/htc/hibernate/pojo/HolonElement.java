package com.htc.hibernate.pojo;

public class HolonElement implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private Integer holonElementType;
	private Integer holonElementState;
	private String usage;
	private Integer maxCapacity;
	private Integer minCapacity;
	private Integer currentCapacity;
	private Byte currentEnergyStatus;
	private byte[] history;
	private Integer holonManager;
	private Integer holonObject;

	public HolonElement() {
	}

	public HolonElement(int id) {
		this.id = id;
	}

	public HolonElement(int id, Integer holonElementType,
			Integer holonElementState, String usage, Integer maxCapacity,
			Integer minCapacity, Integer currentCapacity,
			Byte currentEnergyStatus, byte[] history, Integer holonManager,
			Integer holonObject) {
		this.id = id;
		this.holonElementType = holonElementType;
		this.holonElementState = holonElementState;
		this.usage = usage;
		this.maxCapacity = maxCapacity;
		this.minCapacity = minCapacity;
		this.currentCapacity = currentCapacity;
		this.currentEnergyStatus = currentEnergyStatus;
		this.history = history;
		this.holonManager = holonManager;
		this.holonObject = holonObject;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getHolonElementType() {
		return this.holonElementType;
	}

	public void setHolonElementType(Integer holonElementType) {
		this.holonElementType = holonElementType;
	}

	public Integer getHolonElementState() {
		return this.holonElementState;
	}

	public void setHolonElementState(Integer holonElementState) {
		this.holonElementState = holonElementState;
	}

	public String getUsage() {
		return this.usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public Integer getMaxCapacity() {
		return this.maxCapacity;
	}

	public void setMaxCapacity(Integer maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public Integer getMinCapacity() {
		return this.minCapacity;
	}

	public void setMinCapacity(Integer minCapacity) {
		this.minCapacity = minCapacity;
	}

	public Integer getCurrentCapacity() {
		return this.currentCapacity;
	}

	public void setCurrentCapacity(Integer currentCapacity) {
		this.currentCapacity = currentCapacity;
	}

	public Byte getCurrentEnergyStatus() {
		return this.currentEnergyStatus;
	}

	public void setCurrentEnergyStatus(Byte currentEnergyStatus) {
		this.currentEnergyStatus = currentEnergyStatus;
	}

	public byte[] getHistory() {
		return this.history;
	}

	public void setHistory(byte[] history) {
		this.history = history;
	}

	public Integer getHolonManager() {
		return this.holonManager;
	}

	public void setHolonManager(Integer holonManager) {
		this.holonManager = holonManager;
	}

	public Integer getHolonObject() {
		return this.holonObject;
	}

	public void setHolonObject(Integer holonObject) {
		this.holonObject = holonObject;
	}

}
