package com.htc.daoImpl;

import java.util.ArrayList;

import com.htc.dao.HolonElementDao;
import com.htc.hibernate.pojo.HolonElement;
import com.htc.hibernate.pojo.HolonObject;
import com.htc.hibernate.utilities.HolonElementHome;

public class HolonElementDaoImpl implements HolonElementDao {

	HolonElementHome holonElementHome = new HolonElementHome();
	
	public HolonElementHome getHolonElementHome() {
		return holonElementHome;
	}

	public void setHolonElementHome(HolonElementHome holonElementHome) {
		this.holonElementHome = holonElementHome;
	}

	@Override
	public Integer persist(HolonElement transientInstance) {
		return getHolonElementHome().persist(transientInstance);
	}

	@Override
	public HolonElement merge(HolonElement detachedInstance) {
		return getHolonElementHome().merge(detachedInstance);
	}

	@Override
	public HolonElement findById(int holonElementId) {
		return getHolonElementHome().findById(holonElementId);
	}

	@Override
	public boolean delete(HolonElement persistentInstance) {
		return getHolonElementHome().delete(persistentInstance);
	}

	@Override
	public ArrayList<HolonElement> getAllHolonElement() {
		return getHolonElementHome().getAllHolonElement();
	}

	@Override
	public ArrayList<HolonElement> getHolonElements(HolonObject holonObject) {
		return getHolonElementHome().getHolonElements(holonObject);
	}

}
