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
		// TODO Auto-generated method stub
		return powerLineHome.persist(transientInstance);
	}

	@Override
	public PowerLine merge(PowerLine detachedInstance) {
		// TODO Auto-generated method stub
		return powerLineHome.merge(detachedInstance);
	}

	@Override
	public PowerLine findById(int holonObjectId) {
		// TODO Auto-generated method stub
		return powerLineHome.findById(holonObjectId);
	}

	@Override
	public boolean delete(PowerLine persistentInstance) {
		// TODO Auto-generated method stub
		return powerLineHome.delete(persistentInstance);
	}

	@Override
	public ArrayList<PowerLine> getAllPowerLine() {
		// TODO Auto-generated method stub
		return powerLineHome.getAllPowerLine();
	}

	@Override
	public ArrayList<PowerLine> getConnectedPowerLines(PowerLine powerLine) {
		// TODO Auto-generated method stub
		return powerLineHome.getConnectedPowerLines(powerLine);
	}

	@Override
	public PowerLine getPowerLineByHolonObject(HolonObject holonObject) {
		// TODO Auto-generated method stub
		return powerLineHome.getPowerLineByHolonObject(holonObject);
	}

	@Override
	public PowerLine getPowerLineByPowerSource(PowerSource powerSource) {
		// TODO Auto-generated method stub
		return powerLineHome.getPowerLineByPowerSource(powerSource);
	}

	@Override
	public ArrayList<PowerLine> getPowerLineFromLatLng(LatLng latLng) {
		// TODO Auto-generated method stub
		return powerLineHome.getPowerLineFromLatLng(latLng);
	}

	@Override
	public ArrayList<PowerLine> getAllPowerLineIdsHavingDisaster() {
		// TODO Auto-generated method stub
		return powerLineHome.getAllPowerLineIdsHavingDisaster();
	}

	@Override
	public ArrayList<PowerLine> getAllPowerLinesWithDisasterId(Disaster disaster) {
		// TODO Auto-generated method stub
		return powerLineHome.getAllPowerLinesWithDisasterId(disaster);
	}

}
