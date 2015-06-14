package com.htc.hibernate.pojo;

public class PowerLine implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String type;
	private int currentCapacity;
	private int maximumCapacity;
	private byte isConnected;
	private String reasonDown;
	private Integer source;
	private Integer destination;

	public PowerLine() {
	}

	public PowerLine(String type, int currentCapacity, int maximumCapacity,
			byte isConnected) {
		this.type = type;
		this.currentCapacity = currentCapacity;
		this.maximumCapacity = maximumCapacity;
		this.isConnected = isConnected;
	}

	public PowerLine(String type, int currentCapacity, int maximumCapacity,
			byte isConnected, String reasonDown, Integer source,
			Integer destination) {
		this.type = type;
		this.currentCapacity = currentCapacity;
		this.maximumCapacity = maximumCapacity;
		this.isConnected = isConnected;
		this.reasonDown = reasonDown;
		this.source = source;
		this.destination = destination;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCurrentCapacity() {
		return this.currentCapacity;
	}

	public void setCurrentCapacity(int currentCapacity) {
		this.currentCapacity = currentCapacity;
	}

	public int getMaximumCapacity() {
		return this.maximumCapacity;
	}

	public void setMaximumCapacity(int maximumCapacity) {
		this.maximumCapacity = maximumCapacity;
	}

	public byte getIsConnected() {
		return this.isConnected;
	}

	public void setIsConnected(byte isConnected) {
		this.isConnected = isConnected;
	}

	public String getReasonDown() {
		return this.reasonDown;
	}

	public void setReasonDown(String reasonDown) {
		this.reasonDown = reasonDown;
	}

	public Integer getSource() {
		return this.source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getDestination() {
		return this.destination;
	}

	public void setDestination(Integer destination) {
		this.destination = destination;
	}

}
