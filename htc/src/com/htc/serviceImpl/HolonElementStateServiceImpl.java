package com.htc.serviceImpl;

import java.util.ArrayList;

import com.htc.hibernate.pojo.HolonElementState;
import com.htc.service.DaoAware;
import com.htc.service.HolonElementStateService;

public class HolonElementStateServiceImpl extends DaoAware implements HolonElementStateService {

	@Override
	public Integer persist(HolonElementState transientInstance) {
		return getHolonElementStateDao().persist(transientInstance);
	}

	@Override
	public HolonElementState merge(HolonElementState detachedInstance) {
		return getHolonElementStateDao().merge(detachedInstance);
	}

	@Override
	public HolonElementState findById(int holonElementStateId) {
		return getHolonElementStateDao().findById(holonElementStateId);
	}

	@Override
	public boolean delete(HolonElementState persistentInstance) {
		return getHolonElementStateDao().delete(persistentInstance);
	}

	@Override
	public ArrayList<HolonElementState> getAllHolonElementState() {
		return getHolonElementStateDao().getAllHolonElementState();
	}

}
