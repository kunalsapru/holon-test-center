package com.htc.pojo;

public class Switch {

	private String id;
	private LatLng location;
	private boolean status;
	private PowerLine powerLine;
	private String belongsTo;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public LatLng getLocation() {
		return location;
	}
	public void setLocation(LatLng location) {
		this.location = location;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public PowerLine getPowerLine() {
		return powerLine;
	}
	public void setPowerLine(PowerLine powerLine) {
		this.powerLine = powerLine;
	}
	public String getBelongsTo() {
		return belongsTo;
	}
	public void setBelongsTo(String belongsTo) {
		this.belongsTo = belongsTo;
	}
	
	
}
