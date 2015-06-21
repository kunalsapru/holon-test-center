package com.htc.daoImpl;

import java.util.ArrayList;

import com.htc.dao.HolonElementStateDao;
import com.htc.hibernate.pojo.HolonElementState;
import com.htc.hibernate.utilities.HolonElementStateHome;

public class HolonElementStateDaoImpl implements HolonElementStateDao {
	
	HolonElementStateHome holonElementStateHome = new HolonElementStateHome();

	public HolonElementStateHome getHolonElementStateHome() {
		return holonElementStateHome;
	}

	public void setHolonElementStateHome(HolonElementStateHome holonElementStateHome) {
		this.holonElementStateHome = holonElementStateHome;
	}

	@Override
	public Integer persist(HolonElementState transientInstance) {
		return getHolonElementStateHome().persist(transientInstance);
	}

	@Override
	public HolonElementState merge(HolonElementState detachedInstance) {
		return getHolonElementStateHome().merge(detachedInstance);
	}

	@Override
	public HolonElementState findById(int holonElementStateId) {
		return getHolonElementStateHome().findById(holonElementStateId);
	}

	@Override
	public boolean delete(HolonElementState persistentInstance) {
		return getHolonElementStateHome().delete(persistentInstance);
	}

	@Override
	public ArrayList<HolonElementState> getAllHolonElementState() {
		return getHolonElementStateHome().getAllHolonElementState();
	}

}
