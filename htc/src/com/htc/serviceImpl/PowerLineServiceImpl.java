package com.htc.serviceImpl;

import java.util.ArrayList;

import com.htc.hibernate.pojo.PowerLine;
import com.htc.service.AbstractService;
import com.htc.service.PowerLineService;

public class PowerLineServiceImpl extends AbstractService implements PowerLineService{

	@Override
	public Integer persist(PowerLine transientInstance) {
		// TODO Auto-generated method stub
		return getPowerLineDao().persist(transientInstance);
	}

	@Override
	public PowerLine merge(PowerLine detachedInstance) {
		// TODO Auto-generated method stub
		return getPowerLineDao().merge(detachedInstance);
	}

	@Override
	public PowerLine findById(int powerLineId) {
		// TODO Auto-generated method stub
		return getPowerLineDao().findById(powerLineId);
	}

	@Override
	public boolean delete(PowerLine persistentInstance) {
		// TODO Auto-generated method stub
		return getPowerLineDao().delete(persistentInstance);
	}

	@Override
	public ArrayList<PowerLine> getAllPowerLine() {
		// TODO Auto-generated method stub
		return getPowerLineDao().getAllPowerLine();
	}

}
