package com.htc.dao;

import java.util.ArrayList;

import com.htc.hibernate.pojo.Disaster;
import com.htc.hibernate.pojo.HolonObject;
import com.htc.hibernate.pojo.LatLng;
import com.htc.hibernate.pojo.PowerLine;
import com.htc.hibernate.pojo.PowerSource;

public interface PowerLineDao {
	public Integer persist(PowerLine transientInstance);
	public PowerLine merge(PowerLine detachedInstance);
	public PowerLine findById(int holonObjectId);
	public boolean delete(PowerLine persistentInstance);
	public ArrayList<PowerLine> getAllPowerLine();
	public ArrayList<PowerLine> getConnectedPowerLines(PowerLine powerLine);
	public PowerLine getPowerLineByHolonObject(HolonObject holonObject);
	public PowerLine getPowerLineByPowerSource(PowerSource powerSource);
	public ArrayList<PowerLine> getPowerLineFromLatLng(LatLng latLng);
	public ArrayList<PowerLine> getAllPowerLineIdsHavingDisaster();
	public ArrayList<PowerLine> getAllPowerLinesWithDisasterId(Disaster disaster);
}
