package com.htc.service;

import java.util.ArrayList;

import com.htc.hibernate.pojo.PowerLine;
import com.htc.hibernate.pojo.Simulation;

public interface SimulationService {
	public Integer persist(Simulation transientInstance);
	public boolean delete(Simulation persistentInstance);
	public ArrayList<Simulation> getAllSimulations();
	public ArrayList<PowerLine> getAllPowerLinesSimulation();
}
