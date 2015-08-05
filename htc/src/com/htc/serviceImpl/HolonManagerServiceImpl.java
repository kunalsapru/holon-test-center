package com.htc.serviceImpl;

import java.util.ArrayList;

import com.htc.hibernate.pojo.HolonManager;
import com.htc.service.AbstractService;
import com.htc.service.HolonManagerService;

public class HolonManagerServiceImpl extends AbstractService implements HolonManagerService {

	@Override
	public Integer persist(HolonManager transientInstance) {
		return getHolonManagerDao().persist(transientInstance);
	}

	@Override
	public HolonManager merge(HolonManager detachedInstance) {
		return getHolonManagerDao().merge(detachedInstance);
	}

	@Override
	public HolonManager findById(int holonManagerId) {
		return getHolonManagerDao().findById(holonManagerId);
	}

	@Override
	public boolean delete(HolonManager persistentInstance) {
		return getHolonManagerDao().delete(persistentInstance);
	}

	@Override
	public ArrayList<HolonManager> getAllHolonManager() {
		return getHolonManagerDao().getAllHolonManager();
	}

}
