package com.htc.pojo;

public class PowerSwitch {

	private int id;
	private LatLng location;
	private boolean status;
	private PowerLine powerLine;
	private int belongsTo;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public int getBelongsTo() {
		return belongsTo;
	}
	public void setBelongsTo(int belongsTo) {
		this.belongsTo = belongsTo;
	}
	
	
}
