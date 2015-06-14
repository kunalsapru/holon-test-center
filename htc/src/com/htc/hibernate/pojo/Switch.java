package com.htc.hibernate.pojo;

public class Switch implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private int location;
	private Byte status;
	private Integer powerLine;
	private String belongsTo;

	public Switch() {
	}

	public Switch(int id, int location) {
		this.id = id;
		this.location = location;
	}

	public Switch(int id, int location, Byte status, Integer powerLine,
			String belongsTo) {
		this.id = id;
		this.location = location;
		this.status = status;
		this.powerLine = powerLine;
		this.belongsTo = belongsTo;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLocation() {
		return this.location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Integer getPowerLine() {
		return this.powerLine;
	}

	public void setPowerLine(Integer powerLine) {
		this.powerLine = powerLine;
	}

	public String getBelongsTo() {
		return this.belongsTo;
	}

	public void setBelongsTo(String belongsTo) {
		this.belongsTo = belongsTo;
	}

}
