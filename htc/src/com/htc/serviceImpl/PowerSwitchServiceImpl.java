package com.htc.serviceImpl;

import java.util.ArrayList;
import com.htc.hibernate.pojo.PowerLine;
import com.htc.hibernate.pojo.PowerSwitch;
import com.htc.service.DaoAware;
import com.htc.service.PowerSwitchService;

public class PowerSwitchServiceImpl extends DaoAware implements  PowerSwitchService{

	@Override
	public Integer persist(PowerSwitch transientInstance) {
		return getPowerSwitchDao().persist(transientInstance);
	}

	@Override
	public PowerSwitch merge(PowerSwitch detachedInstance) {
		return getPowerSwitchDao().merge(detachedInstance);
	}

	@Override
	public PowerSwitch findById(int powerSwitchId) {
		return getPowerSwitchDao().findById(powerSwitchId);
	}

	@Override
	public boolean delete(PowerSwitch persistentInstance) {
		return getPowerSwitchDao().delete(persistentInstance);
	}

	@Override
	public ArrayList<PowerSwitch> getAllPowerSwitch() {
		return getPowerSwitchDao().getAllPowerSwitch();
	}

	@Override
	public PowerSwitch checkSwitchStatusBetweenPowerLines(PowerLine powerLineA, PowerLine powerLineB) {
		return getPowerSwitchDao().checkSwitchStatusBetweenPowerLines(powerLineA, powerLineB);
	}

	@Override
	public int deleteAllPowerSwitches() {
		return getPowerSwitchDao().deleteAllPowerSwitches();
	}
	
}
