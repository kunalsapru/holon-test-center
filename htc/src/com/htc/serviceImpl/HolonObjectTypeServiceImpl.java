package com.htc.serviceImpl;

import java.util.ArrayList;

import com.htc.hibernate.pojo.HolonObjectType;
import com.htc.service.DaoAware;
import com.htc.service.HolonObjectTypeService;

public class HolonObjectTypeServiceImpl extends DaoAware implements HolonObjectTypeService {

	@Override
	public Integer persist(HolonObjectType transientInstance) {
		return getHolonObjectTypeDao().persist(transientInstance);
	}

	@Override
	public HolonObjectType merge(HolonObjectType detachedInstance) {
		return getHolonObjectTypeDao().merge(detachedInstance);
	}

	@Override
	public HolonObjectType findById(int holonObjectTypeId) {
		return getHolonObjectTypeDao().findById(holonObjectTypeId);
	}

	@Override
	public boolean delete(HolonObjectType persistentInstance) {
		return getHolonObjectTypeDao().delete(persistentInstance);
	}

	@Override
	public ArrayList<HolonObjectType> getAllHolonObjectType() {
		return getHolonObjectTypeDao().getAllHolonObjectType();
	}

}
