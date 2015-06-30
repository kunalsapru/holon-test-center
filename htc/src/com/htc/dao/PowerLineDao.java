package com.htc.dao;

import java.util.ArrayList;

import com.htc.hibernate.pojo.PowerLine;

public interface PowerLineDao {
	
	public Integer persist(PowerLine transientInstance);
	public PowerLine merge(PowerLine detachedInstance);
	public PowerLine findById(int holonObjectId);
	public boolean delete(PowerLine persistentInstance);
	public ArrayList<PowerLine> getAllPowerLine();

}