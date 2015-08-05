package com.htc.dao;

import java.util.ArrayList;

import com.htc.hibernate.pojo.HolonCoordinator;

public interface HolonCoordinatorDao {

	public Integer persist(HolonCoordinator transientInstance);
	public HolonCoordinator merge(HolonCoordinator detachedInstance);
	public HolonCoordinator findById(int holonCoordinatorId);
	public boolean delete(HolonCoordinator persistentInstance);
	public ArrayList<HolonCoordinator> getAllHolonCoordinator();
	
	
}
