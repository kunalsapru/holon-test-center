package com.htc.serviceImpl;

import java.util.ArrayList;

import com.htc.hibernate.pojo.HolonElementType;
import com.htc.service.DaoAware;
import com.htc.service.HolonElementTypeService;

public class HolonElementTypeServiceImpl extends DaoAware implements HolonElementTypeService {

	@Override
	public Integer persist(HolonElementType transientInstance) {
		return getHolonElementTypeDao().persist(transientInstance);
	}

	@Override
	public HolonElementType merge(HolonElementType detachedInstance) {
		return getHolonElementTypeDao().merge(detachedInstance);
	}

	@Override
	public HolonElementType findById(int holonElementTypeId) {
		return getHolonElementTypeDao().findById(holonElementTypeId);
	}

	@Override
	public boolean delete(HolonElementType persistentInstance) {
		return getHolonElementTypeDao().delete(persistentInstance);
	}

	@Override
	public ArrayList<HolonElementType> getAllHolonElementType() {
		return getHolonElementTypeDao().getAllHolonElementType();
	}

}
