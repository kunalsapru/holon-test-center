package com.htc.pojo;

import com.google.api.gbase.client.Location;

/**
 * @author Abhinav This class models the PowerLine connecting entities of the
 *         Holon System
 */

public class MainPowerLine {

	private int lineId; // Models unique Id for PowerLine. Set Automatically
						// when object is persisted in DB
	private int currentPowerFlow; // Models the flow of the power at given time.
	private Location entityA; // Models location of Holon Entity connected to
								// one side if the line.
	private Location entityB; // Models location of Holon Entity connected to
								// one side if the line.
	private boolean lineStatus; // Models the status of the line true= Line has
								// current flowing, False=Power Supply has been
								// stopped
	private String reasonDown; // Models the reason of Line Shut down. Options:
								// Over supply , Not in Use!!

	public MainPowerLine(Location entityA, Location entityB, int maxCapacity) {// Constructor
																			// to
																			// create
																			// a
																			// PowerLine
																			// Object
		super();
		this.entityA = entityA;
		this.entityB = entityB;
		this.maxCapacity = maxCapacity;
	}

	public int getLineId() {
		return lineId;
	}

	public void setLineId(int lineId) {
		this.lineId = lineId;
	}

	public String getReasonDown() {
		return reasonDown;
	}

	public void setReasonDown(String reasonDown) {
		this.reasonDown = reasonDown;
	}

	private int maxCapacity;

	public int getCurrentPowerFlow() {
		return currentPowerFlow;
	}

	public void setCurrentPowerFlow(int currentPowerFlow) {
		this.currentPowerFlow = currentPowerFlow;
	}

	public Location getEntityA() {
		return entityA;
	}

	public void setEntityA(Location entityA) {
		this.entityA = entityA;
	}

	public Location getEntityB() {
		return entityB;
	}

	public void setEntityB(Location entityB) {
		this.entityB = entityB;
	}

	public boolean isLineStatus() {
		return lineStatus;
	}

	public void setLineStatus(boolean lineStatus) {
		this.lineStatus = lineStatus;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

}
