package com.htc.daoImpl;

import java.util.ArrayList;
import com.htc.dao.PowerSourceDao;
import com.htc.hibernate.pojo.HolonObject;
import com.htc.hibernate.pojo.PowerSource;
import com.htc.hibernate.utilities.PowerSourceHome;

public class PowerSourceDaoImpl implements PowerSourceDao {
	PowerSourceHome powerSourceHome = new PowerSourceHome();

	public PowerSourceHome getPowerSourceHome() {
		return powerSourceHome;
	}

	public void setPowerSourceHome(PowerSourceHome powerSourceHome) {
		this.powerSourceHome = powerSourceHome;
	}

	@Override
	public Integer persist(PowerSource transientInstance) {
		return getPowerSourceHome().persist(transientInstance);
	}

	@Override
	public PowerSource merge(PowerSource detachedInstance) {
		return getPowerSourceHome().merge(detachedInstance);
	}

	@Override
	public PowerSource findById(int powerSourceId) {
		return getPowerSourceHome().findById(powerSourceId);
	}

	@Override
	public boolean delete(PowerSource persistentInstance) {
		return getPowerSourceHome().delete(persistentInstance);
	}

	@Override
	public ArrayList<PowerSource> getAllPowerSource() {
		return getPowerSourceHome().getAllPowerSource();
	}

	@Override
	public ArrayList<PowerSource> findByHolonCoordinator(HolonObject holonCoordinator) {
		// TODO Auto-generated method stub
		return getPowerSourceHome().findByHolonCoordinator(holonCoordinator);
	}

}
