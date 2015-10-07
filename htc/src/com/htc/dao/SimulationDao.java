package com.htc.dao;

import java.util.ArrayList;

import com.htc.hibernate.pojo.PowerLine;
import com.htc.hibernate.pojo.Simulation;

public interface SimulationDao {
	public Integer persist(Simulation transientInstance);
	public boolean delete(Simulation persistentInstance);
	public ArrayList<Simulation> getAllSimulations();
	public ArrayList<PowerLine> getAllPowerLinesSimulation();
}
