package com.htc.service;

import java.util.ArrayList;

import com.htc.hibernate.pojo.EnergyState;

public interface EnergyStateService {	
	
	public EnergyState findById(int energyStateId);
	public ArrayList<EnergyState> getAllEnergyState();	

}
