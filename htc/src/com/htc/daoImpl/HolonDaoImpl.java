package com.htc.daoImpl;

import java.util.ArrayList;

import com.htc.dao.HolonDao;
import com.htc.hibernate.pojo.Holon;
import com.htc.hibernate.utilities.HolonHome;

public class HolonDaoImpl implements HolonDao {
	private HolonHome holonHome = new HolonHome();

	public HolonHome getHolonHome() {
		return holonHome;
	}

	public void setHolonHome(HolonHome holonHome) {
		this.holonHome = holonHome;
	}


	@Override
	public Integer persist(Holon transientInstance) {
		return getHolonHome().persist(transientInstance);
	}

	@Override
	public Holon merge(Holon detachedInstance) {
		return getHolonHome().merge(detachedInstance);
	}

	@Override
	public Holon findById(int holonId) {
		return getHolonHome().findById(holonId);
	}

	@Override
	public boolean delete(Holon persistentInstance) {
		return getHolonHome().delete(persistentInstance);
	}

	@Override
	public ArrayList<Holon> getAllHolon() {
		return getHolonHome().getAllHolon();
	}

}
