package com.htc.serviceImpl;

import java.util.ArrayList;

import com.htc.hibernate.pojo.HolonElement;
import com.htc.service.AbstractService;
import com.htc.service.HolonElementService;

public class HolonElementServiceImpl extends AbstractService implements HolonElementService {

	@Override
	public Integer persist(HolonElement transientInstance) {
		return getHolonElementDao().persist(transientInstance);
	}

	@Override
	public HolonElement merge(HolonElement detachedInstance) {
		return getHolonElementDao().merge(detachedInstance);
	}

	@Override
	public HolonElement findById(int holonElementId) {
		return getHolonElementDao().findById(holonElementId);
	}

	@Override
	public boolean delete(HolonElement persistentInstance) {
		return getHolonElementDao().delete(persistentInstance);
	}

	@Override
	public ArrayList<HolonElement> getAllHolonElement() {
		return getHolonElementDao().getAllHolonElement();
	}

}
