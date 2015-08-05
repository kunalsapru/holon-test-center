package com.htc.serviceImpl;

import java.util.ArrayList;

import com.htc.hibernate.pojo.HolonCoordinator;
import com.htc.hibernate.pojo.HolonObject;
import com.htc.service.AbstractService;
import com.htc.service.HolonObjectService;

public class HolonObjectServiceImpl extends AbstractService implements HolonObjectService {

	@Override
	public Integer persist(HolonObject transientInstance) {
		return getHolonObjectDao().persist(transientInstance);
	}

	@Override
	public HolonObject merge(HolonObject detachedInstance) {
		return getHolonObjectDao().merge(detachedInstance);
	}

	@Override
	public HolonObject findById(int holonObjectId) {
		return getHolonObjectDao().findById(holonObjectId);
	}

	@Override
	public boolean delete(HolonObject persistentInstance) {
		return getHolonObjectDao().delete(persistentInstance);
	}

	@Override
	public ArrayList<HolonObject> getAllHolonObject() {
		return getHolonObjectDao().getAllHolonObject();
	}

	@Override
	public ArrayList<HolonObject> findByHCoordinator(HolonCoordinator holonCoordinator) {
		return getHolonObjectDao().findByHCoordinator(holonCoordinator);
	}


}
