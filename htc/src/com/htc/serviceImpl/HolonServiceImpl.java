package com.htc.serviceImpl;

import java.util.ArrayList;
import com.htc.hibernate.pojo.Holon;
import com.htc.service.AbstractService;
import com.htc.service.HolonService;

public class HolonServiceImpl extends AbstractService implements HolonService {

	@Override
	public Integer persist(Holon transientInstance) {
		return getHolonDao().persist(transientInstance);
	}

	@Override
	public Holon merge(Holon detachedInstance) {
		return getHolonDao().merge(detachedInstance);
	}

	@Override
	public Holon findById(int holonId) {
		return getHolonDao().findById(holonId);
	}

	@Override
	public boolean delete(Holon persistentInstance) {
		return getHolonDao().delete(persistentInstance);
	}

	@Override
	public ArrayList<Holon> getAllHolon() {
		return getHolonDao().getAllHolon();
	}

}
