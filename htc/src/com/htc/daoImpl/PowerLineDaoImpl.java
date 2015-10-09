package com.htc.daoImpl;

import java.util.ArrayList;
import com.htc.dao.PowerLineDao;
import com.htc.hibernate.pojo.Disaster;
import com.htc.hibernate.pojo.HolonObject;
import com.htc.hibernate.pojo.LatLng;
import com.htc.hibernate.pojo.PowerLine;
import com.htc.hibernate.pojo.PowerSource;
import com.htc.hibernate.utilities.PowerLineHome;

public class PowerLineDaoImpl implements PowerLineDao {
	
	private PowerLineHome powerLineHome = new PowerLineHome();

	public PowerLineHome getPowerLineHome() {
		return powerLineHome;
	}

	public void setPowerLineHome(PowerLineHome powerLineHome) {
		this.powerLineHome = powerLineHome;
	}

	@Override
	public Integer persist(PowerLine transientInstance) {
		return powerLineHome.persist(transientInstance);
	}

	@Override
	public PowerLine merge(PowerLine detachedInstance) {
		return powerLineHome.merge(detachedInstance);
	}

	@Override
	public PowerLine findById(int holonObjectId) {
		return powerLineHome.findById(holonObjectId);
	}

	@Override
	public boolean delete(PowerLine persistentInstance) {
		return powerLineHome.delete(persistentInstance);
	}

	@Override
	public ArrayList<PowerLine> getAllPowerLine() {
		return powerLineHome.getAllPowerLine();
	}

	@Override
	public ArrayList<PowerLine> getConnectedPowerLines(PowerLine powerLine) {
		return powerLineHome.getConnectedPowerLines(powerLine);
	}

	@Override
	public PowerLine getPowerLineByHolonObject(HolonObject holonObject) {
		return powerLineHome.getPowerLineByHolonObject(holonObject);
	}

	@Override
	public PowerLine getPowerLineByPowerSource(PowerSource powerSource) {
		return powerLineHome.getPowerLineByPowerSource(powerSource);
	}

	@Override
	public ArrayList<PowerLine> getPowerLineFromLatLng(LatLng latLng) {
		return powerLineHome.getPowerLineFromLatLng(latLng);
	}

	@Override
	public ArrayList<PowerLine> getAllPowerLineIdsHavingDisaster() {
		return powerLineHome.getAllPowerLineIdsHavingDisaster();
	}

	@Override
	public ArrayList<PowerLine> getAllPowerLinesWithDisasterId(Disaster disaster) {
		return powerLineHome.getAllPowerLinesWithDisasterId(disaster);
	}

	@Override
	public int deleteAllPowerLines() {
		return powerLineHome.deleteAllPowerLines();
	}

}
