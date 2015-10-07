package com.htc.serviceImpl;

import java.util.ArrayList;

import com.htc.hibernate.pojo.PowerLine;
import com.htc.hibernate.pojo.PowerSwitch;
import com.htc.hibernate.pojo.Simulation;
import com.htc.service.AbstractService;
import com.htc.service.SimulationService;

public class SimulationServiceImpl extends AbstractService implements SimulationService {

	@Override
	public Integer persist(Simulation transientInstance) {
		return getSimulationDao().persist(transientInstance);
	}

	@Override
	public boolean delete(Simulation persistentInstance) {
		return getSimulationDao().delete(persistentInstance);
	}

	@Override
	public ArrayList<Simulation> getAllSimulations() {
		return getSimulationDao().getAllSimulations();
	}

	@Override
	public ArrayList<PowerLine> getAllPowerLinesSimulation() {
		return getSimulationDao().getAllPowerLinesSimulation();
	}

	@Override
	public ArrayList<PowerSwitch> getAllPowerSwitchesSimulation() {
		return getSimulationDao().getAllPowerSwitchesSimulation();
	}
	
}
