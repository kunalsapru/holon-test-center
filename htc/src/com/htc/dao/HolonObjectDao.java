package com.htc.dao;

import java.util.ArrayList;

import com.htc.hibernate.pojo.HolonCoordinator;
import com.htc.hibernate.pojo.HolonObject;

public interface HolonObjectDao {

	public Integer persist(HolonObject transientInstance);
	public HolonObject merge(HolonObject detachedInstance);
	public HolonObject findById(int holonObjectId);
	public boolean delete(HolonObject persistentInstance);
	public ArrayList<HolonObject> getAllHolonObject();
	public ArrayList<HolonObject> findByHCoordinator(HolonCoordinator holonCoordinator);

}
