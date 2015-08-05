package com.htc.dao;

import java.util.ArrayList;
import com.htc.hibernate.pojo.PowerSource;

public interface PowerSourceDao {

	public Integer persist(PowerSource transientInstance);
	public PowerSource merge(PowerSource detachedInstance);
	public PowerSource findById(int powerSourceId);
	public boolean delete(PowerSource persistentInstance);
	public ArrayList<PowerSource> getAllPowerSource();

}
