package com.htc.daoImpl;

import java.util.ArrayList;

import com.htc.dao.SimulationDao;
import com.htc.hibernate.pojo.PowerLine;
import com.htc.hibernate.pojo.PowerSwitch;
import com.htc.hibernate.pojo.Simulation;
import com.htc.hibernate.utilities.SimulationHome;

public class SimulationDaoImpl  implements SimulationDao {
	private SimulationHome simulationHome = new SimulationHome();

	public SimulationHome getSimulationHome() {
		return simulationHome;
	}

	public void setSimulationHome(SimulationHome simulationHome) {
		this.simulationHome = simulationHome;
	}

	@Override
	public Integer persist(Simulation transientInstance) {
		return getSimulationHome().persist(transientInstance);
	}

	@Override
	public boolean delete(Simulation persistentInstance) {
		return getSimulationHome().delete(persistentInstance);
	}

	@Override
	public ArrayList<Simulation> getAllSimulations() {
		return getSimulationHome().getAllSimulations();
	}

	@Override
	public ArrayList<PowerLine> getAllPowerLinesSimulation() {
		return getSimulationHome().getAllPowerLinesSimulation();
	}

	@Override
	public ArrayList<PowerSwitch> getAllPowerSwitchesSimulation() {
		return getSimulationHome().getAllPowerSwitchesSimulation();
	}

}
