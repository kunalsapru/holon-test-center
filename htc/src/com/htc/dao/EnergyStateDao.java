package com.htc.dao;

import java.util.ArrayList;

import com.htc.hibernate.pojo.EnergyState;

public interface EnergyStateDao {
	
	public EnergyState findById(int energyStateId);
	public ArrayList<EnergyState> getAllEnergyState();

}
