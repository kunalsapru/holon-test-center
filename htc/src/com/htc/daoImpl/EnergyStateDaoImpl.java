package com.htc.daoImpl;

import java.util.ArrayList;

import com.htc.dao.EnergyStateDao;
import com.htc.hibernate.pojo.EnergyState;
import com.htc.hibernate.utilities.EnergyStateHome;

public class EnergyStateDaoImpl implements EnergyStateDao{
	
	
	EnergyStateHome energyStateHome = new EnergyStateHome();

	public EnergyStateHome getEnergyStateHome() {
		return energyStateHome;
	}

	public void setEnergyStateHome(EnergyStateHome energyStateHome) {
		this.energyStateHome = energyStateHome;
	}

	@Override
	public EnergyState findById(int energyStateId) {
		// TODO Auto-generated method stub
		return getEnergyStateHome().findById(energyStateId);
	}

	@Override
	public ArrayList<EnergyState> getAllEnergyState() {
		// TODO Auto-generated method stub
		return getEnergyStateHome().getAllEnergyState();
	}

}
