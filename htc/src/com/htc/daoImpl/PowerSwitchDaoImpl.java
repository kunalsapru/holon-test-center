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
		// TODO Auto-generated method stub
		return powerSwitchHome.persist(transientInstance);
	}

	@Override
	public PowerSwitch merge(PowerSwitch detachedInstance) {
		// TODO Auto-generated method stub
		return powerSwitchHome.merge(detachedInstance);
	}

	@Override
	public PowerSwitch findById(int powerSwitchId) {
		// TODO Auto-generated method stub
		return powerSwitchHome.findById(powerSwitchId);
	}

	@Override
	public boolean delete(PowerSwitch persistentInstance) {
		// TODO Auto-generated method stub
		return powerSwitchHome.delete(persistentInstance);
	}

	@Override
	public ArrayList<PowerSwitch> getAllPowerSwitch() {
		// TODO Auto-generated method stub
		return powerSwitchHome.getAllPowerSwitch();

	}

	@Override
	public PowerSwitch checkSwitchStatusBetweenPowerLines(PowerLine powerLineA,
			PowerLine powerLineB) {
		// TODO Auto-generated method stub
		return powerSwitchHome.checkSwitchStatusBetweenPowerLines(powerLineA, powerLineB);
	}

}
