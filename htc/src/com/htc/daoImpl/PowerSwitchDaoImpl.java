package com.htc.daoImpl;

import java.util.ArrayList;

import com.htc.dao.PowerSwitchDao;
import com.htc.hibernate.pojo.PowerLine;
import com.htc.hibernate.pojo.PowerSwitch;
import com.htc.hibernate.utilities.PowerSwitchHome;

public class PowerSwitchDaoImpl implements PowerSwitchDao {

	private PowerSwitchHome powerSwitchHome = new PowerSwitchHome();
	
	@Override
	public Integer persist(PowerSwitch transientInstance) {
		return powerSwitchHome.persist(transientInstance);
	}

	@Override
	public PowerSwitch merge(PowerSwitch detachedInstance) {
		return powerSwitchHome.merge(detachedInstance);
	}

	@Override
	public PowerSwitch findById(int powerSwitchId) {
		return powerSwitchHome.findById(powerSwitchId);
	}

	@Override
	public boolean delete(PowerSwitch persistentInstance) {
		return powerSwitchHome.delete(persistentInstance);
	}

	@Override
	public ArrayList<PowerSwitch> getAllPowerSwitch() {
		return powerSwitchHome.getAllPowerSwitch();

	}

	@Override
	public PowerSwitch checkSwitchStatusBetweenPowerLines(PowerLine powerLineA, PowerLine powerLineB) {
		return powerSwitchHome.checkSwitchStatusBetweenPowerLines(powerLineA, powerLineB);
	}

	@Override
	public int deleteAllPowerSwitches() {
		return powerSwitchHome.deleteAllPowerSwitches();
	}

}
