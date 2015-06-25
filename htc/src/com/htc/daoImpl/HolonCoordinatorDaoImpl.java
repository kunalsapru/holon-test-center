package com.htc.daoImpl;

import java.util.ArrayList;

import com.htc.dao.HolonCoordinatorDao;
import com.htc.hibernate.pojo.HolonCoordinator;
import com.htc.hibernate.utilities.HolonCoordinatorHome;

public class HolonCoordinatorDaoImpl implements HolonCoordinatorDao {

	private HolonCoordinatorHome holonCoordinatorHome = new HolonCoordinatorHome();
	
	public HolonCoordinatorHome getHolonCoordinatorHome() {
		return holonCoordinatorHome;
	}

	public void setHolonCoordinatorHome(HolonCoordinatorHome holonCoordinatorHome) {
		this.holonCoordinatorHome = holonCoordinatorHome;
	}

	@Override
	public Integer persist(HolonCoordinator transientInstance) {
		return getHolonCoordinatorHome().persist(transientInstance);
	}

	@Override
	public HolonCoordinator merge(HolonCoordinator detachedInstance) {
		return getHolonCoordinatorHome().merge(detachedInstance);
	}

	@Override
	public HolonCoordinator findById(int holonCoordinatorId) {
		return getHolonCoordinatorHome().findById(holonCoordinatorId);
	}

	@Override
	public boolean delete(HolonCoordinator persistentInstance) {
		return getHolonCoordinatorHome().delete(persistentInstance);
	}

	@Override
	public ArrayList<HolonCoordinator> getAllHolonCoordinator() {
		return getHolonCoordinatorHome().getAllHolonCoordinator();
	}

}
