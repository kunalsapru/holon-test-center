package com.htc.hibernate.pojo;

public class Supplier implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private HolonObject holonObjectProducer;
	private HolonObject holonObjectConsumer;
	private PowerSource powerSource;
	private int powerSupplied;

	public Supplier() {}

	public Supplier(HolonObject holonObjectProducer, HolonObject holonObjectConsumer, PowerSource powerSource,
			int powerSupplied) {
		this.holonObjectProducer = holonObjectProducer;
		this.holonObjectConsumer = holonObjectConsumer;
		this.powerSource = powerSource;
		this.powerSupplied = powerSupplied;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public HolonObject getHolonObjectProducer() {
		return holonObjectProducer;
	}

	public void setHolonObjectProducer(HolonObject holonObjectProducer) {
		this.holonObjectProducer = holonObjectProducer;
	}

	public HolonObject getHolonObjectConsumer() {
		return holonObjectConsumer;
	}

	public void setHolonObjectConsumer(HolonObject holonObjectConsumer) {
		this.holonObjectConsumer = holonObjectConsumer;
	}

	public PowerSource getPowerSource() {
		return powerSource;
	}

	public void setPowerSource(PowerSource powerSource) {
		this.powerSource = powerSource;
	}

	public int getPowerSupplied() {
		return powerSupplied;
	}

	public void setPowerSupplied(int powerSupplied) {
		this.powerSupplied = powerSupplied;
	}

}
