package com.htc.serviceImpl;

import java.util.ArrayList;
import com.htc.hibernate.pojo.HolonElement;
import com.htc.hibernate.pojo.HolonObject;
import com.htc.service.DaoAware;
import com.htc.service.HolonElementService;

public class HolonElementServiceImpl extends DaoAware implements HolonElementService {

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

	@Override
	public ArrayList<HolonElement> getHolonElements(HolonObject holonObject) {
		return getHolonElementDao().getHolonElements(holonObject);
	}

	@Override
	public int deleteAllHolonElements() {
		return getHolonElementDao().deleteAllHolonElements();
	}

}
