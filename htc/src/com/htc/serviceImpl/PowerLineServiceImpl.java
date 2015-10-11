package com.htc.serviceImpl;

import java.util.ArrayList;

import com.htc.hibernate.pojo.Disaster;
import com.htc.hibernate.pojo.HolonObject;
import com.htc.hibernate.pojo.LatLng;
import com.htc.hibernate.pojo.PowerLine;
import com.htc.hibernate.pojo.PowerSource;
import com.htc.service.DaoAware;
import com.htc.service.PowerLineService;

public class PowerLineServiceImpl extends DaoAware implements PowerLineService{

	@Override
	public Integer persist(PowerLine transientInstance) {
		return getPowerLineDao().persist(transientInstance);
	}

	@Override
	public PowerLine merge(PowerLine detachedInstance) {
		return getPowerLineDao().merge(detachedInstance);
	}

	@Override
	public PowerLine findById(int powerLineId) {
		return getPowerLineDao().findById(powerLineId);
	}

	@Override
	public boolean delete(PowerLine persistentInstance) {
		return getPowerLineDao().delete(persistentInstance);
	}

	@Override
	public ArrayList<PowerLine> getAllPowerLine() {
		return getPowerLineDao().getAllPowerLine();
	}

	@Override
	public ArrayList<PowerLine> getConnectedPowerLines(PowerLine powerLine) {
		return getPowerLineDao().getConnectedPowerLines(powerLine);
	}

	@Override
	public PowerLine getPowerLineByHolonObject(HolonObject holonObject) {
		return getPowerLineDao().getPowerLineByHolonObject(holonObject);
	}

	@Override
	public PowerLine getPowerLineByPowerSource(PowerSource powerSource) {
		return getPowerLineDao().getPowerLineByPowerSource(powerSource);
	}

	@Override
	public ArrayList<PowerLine> getPowerLineFromLatLng(LatLng latLng) {
		return getPowerLineDao().getPowerLineFromLatLng(latLng);
	}

	@Override
	public ArrayList<PowerLine> getAllPowerLineIdsHavingDisaster() {
		return getPowerLineDao().getAllPowerLineIdsHavingDisaster();
	}

	@Override
	public ArrayList<PowerLine> getAllPowerLinesWithDisasterId(Disaster disaster) {
		return getPowerLineDao().getAllPowerLinesWithDisasterId(disaster);
	}

	@Override
	public int deleteAllPowerLines() {
		return getPowerLineDao().deleteAllPowerLines();
	}

}
