package com.htc.serviceImpl;

import java.util.ArrayList;

import com.htc.hibernate.pojo.EnergyState;
import com.htc.service.DaoAware;
import com.htc.service.EnergyStateService;

public class EnergyStateServiceImpl extends DaoAware  implements EnergyStateService {

	@Override
	public EnergyState findById(int energyStateId) {
		// TODO Auto-generated method stub
		return getEnergyStateDao().findById(energyStateId);
	}

	@Override
	public ArrayList<EnergyState> getAllEnergyState() {
		// TODO Auto-generated method stub
		return getEnergyStateDao().getAllEnergyState();
	}

}
