package com.htc.service;

import java.util.ArrayList;
import com.htc.hibernate.pojo.HolonCoordinator;

public interface HolonCoordinatorService {

	public Integer persist(HolonCoordinator transientInstance);
	public HolonCoordinator merge(HolonCoordinator detachedInstance);
	public HolonCoordinator findById(int holonCoordinatorId);
	public boolean delete(HolonCoordinator persistentInstance);
	public ArrayList<HolonCoordinator> getAllHolonCoordinator();
	
}
