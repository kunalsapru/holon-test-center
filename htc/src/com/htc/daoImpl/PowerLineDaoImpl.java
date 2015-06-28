package com.htc.daoImpl;

import java.util.ArrayList;

import com.htc.dao.PowerLineDao;
import com.htc.hibernate.pojo.PowerLine;
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

}
