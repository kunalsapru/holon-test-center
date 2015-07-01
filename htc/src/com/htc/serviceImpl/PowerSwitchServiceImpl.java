package com.htc.serviceImpl;

import java.util.ArrayList;

import com.htc.hibernate.pojo.PowerSwitch;
import com.htc.service.AbstractService;
import com.htc.service.PowerSwitchService;

public class PowerSwitchServiceImpl extends AbstractService implements  PowerSwitchService{

	@Override
	public Integer persist(PowerSwitch transientInstance) {
		// TODO Auto-generated method stub
		return getPowerSwitchDao().persist(transientInstance);
	}

	@Override
	public PowerSwitch merge(PowerSwitch detachedInstance) {
		// TODO Auto-generated method stub
		return getPowerSwitchDao().merge(detachedInstance);
	}

	@Override
	public PowerSwitch findById(int powerSwitchId) {
		// TODO Auto-generated method stub
		return getPowerSwitchDao().findById(powerSwitchId);
	}

	@Override
	public boolean delete(PowerSwitch persistentInstance) {
		// TODO Auto-generated method stub
		return getPowerSwitchDao().delete(persistentInstance);
	}

	@Override
	public ArrayList<PowerSwitch> getAllPowerSwitch() {
		// TODO Auto-generated method stub
		return getPowerSwitchDao().getAllPowerSwitch();
	}

}
