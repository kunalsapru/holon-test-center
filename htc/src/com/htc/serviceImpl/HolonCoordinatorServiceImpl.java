package com.htc.serviceImpl;

import java.util.ArrayList;
import com.htc.hibernate.pojo.HolonCoordinator;
import com.htc.service.AbstractService;
import com.htc.service.HolonCoordinatorService;

public class HolonCoordinatorServiceImpl extends AbstractService implements HolonCoordinatorService {

	@Override
	public Integer persist(HolonCoordinator transientInstance) {
		return getHolonCoordinatorDao().persist(transientInstance);
	}

	@Override
	public HolonCoordinator merge(HolonCoordinator detachedInstance) {
		return getHolonCoordinatorDao().merge(detachedInstance);
	}

	@Override
	public HolonCoordinator findById(int holonCoordinatorId) {
		return getHolonCoordinatorDao().findById(holonCoordinatorId);
	}

	@Override
	public boolean delete(HolonCoordinator persistentInstance) {
		return getHolonCoordinatorDao().delete(persistentInstance);
	}

	@Override
	public ArrayList<HolonCoordinator> getAllHolonCoordinator() {
		return getHolonCoordinatorDao().getAllHolonCoordinator();
	}



}
