package com.htc.service;

import java.util.ArrayList;

import com.htc.hibernate.pojo.PowerLine;


public interface PowerLineService {
	public Integer persist(PowerLine transientInstance);
	public PowerLine merge(PowerLine detachedInstance);
	public PowerLine findById(int holonObjectId);
	public boolean delete(PowerLine persistentInstance);
	public ArrayList<PowerLine> getAllPowerLine();
}
