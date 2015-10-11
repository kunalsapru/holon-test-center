package com.htc.serviceImpl;

import java.util.ArrayList;
import com.htc.hibernate.pojo.HolonObject;
import com.htc.hibernate.pojo.PowerSource;
import com.htc.service.DaoAware;
import com.htc.service.PowerSourceService;

public class PowerSourceServiceImpl extends DaoAware implements PowerSourceService {

	@Override
	public Integer persist(PowerSource transientInstance) {
		return getPowerSourceDao().persist(transientInstance);
	}

	@Override
	public PowerSource merge(PowerSource detachedInstance) {
		return getPowerSourceDao().merge(detachedInstance);
	}

	@Override
	public PowerSource findById(int powerSourceId) {
		return getPowerSourceDao().findById(powerSourceId);
	}

	@Override
	public boolean delete(PowerSource persistentInstance) {
		return getPowerSourceDao().delete(persistentInstance);
	}

	@Override
	public ArrayList<PowerSource> getAllPowerSource() {
		return getPowerSourceDao().getAllPowerSource();
	}

	@Override
	public ArrayList<PowerSource> findByHolonCoordinator(HolonObject holonCoordinator) {
		return getPowerSourceDao().findByHolonCoordinator(holonCoordinator);
	}

	@Override
	public int deleteAllPowerSources() {
		return getPowerSourceDao().deleteAllPowerSources();
	}

}
