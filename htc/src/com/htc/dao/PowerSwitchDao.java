package com.htc.dao;

import java.util.ArrayList;

import com.htc.hibernate.pojo.PowerLine;
import com.htc.hibernate.pojo.PowerSwitch;

public interface PowerSwitchDao {

	public Integer persist(PowerSwitch transientInstance);
	public PowerSwitch merge(PowerSwitch detachedInstance);
	public PowerSwitch findById(int powerSwitchId);
	public boolean delete(PowerSwitch persistentInstance);
	public ArrayList<PowerSwitch> getAllPowerSwitch();
	public PowerSwitch checkSwitchStatusBetweenPowerLines(PowerLine powerLineA, PowerLine powerLineB);
	public int deleteAllPowerSwitches();
}
