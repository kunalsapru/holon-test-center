package com.htc.service;

import java.util.ArrayList;
import com.htc.hibernate.pojo.HolonObject;
import com.htc.hibernate.pojo.PowerSource;

public interface PowerSourceService {

	public Integer persist(PowerSource transientInstance);
	public PowerSource merge(PowerSource detachedInstance);
	public PowerSource findById(int powerSourceId);
	public boolean delete(PowerSource persistentInstance);
	public ArrayList<PowerSource> getAllPowerSource();
	public ArrayList<PowerSource> findByHolonCoordinator(HolonObject holonCoordinator);
	public int deleteAllPowerSources();

}
