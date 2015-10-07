package com.htc.hibernate.pojo;

public class Simulation implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private PowerSwitch powerSwitch;
	private PowerLine powerLine;
	private PowerSource powerSource;
	private HolonObject holonObject;
	
	public Simulation() {
	}

	public Simulation(Integer id, PowerSwitch powerSwitch, PowerLine powerLine,
			PowerSource powerSource, HolonObject holonObject) {
		super();
		this.id = id;
		this.powerSwitch = powerSwitch;
		this.powerLine = powerLine;
		this.powerSource = powerSource;
		this.holonObject = holonObject;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PowerSwitch getPowerSwitch() {
		return powerSwitch;
	}

	public void setPowerSwitch(PowerSwitch powerSwitch) {
		this.powerSwitch = powerSwitch;
	}

	public PowerLine getPowerLine() {
		return powerLine;
	}

	public void setPowerLine(PowerLine powerLine) {
		this.powerLine = powerLine;
	}

	public PowerSource getPowerSource() {
		return powerSource;
	}

	public void setPowerSource(PowerSource powerSource) {
		this.powerSource = powerSource;
	}

	public HolonObject getHolonObject() {
		return holonObject;
	}

	public void setHolonObject(HolonObject holonObject) {
		this.holonObject = holonObject;
	}

}
